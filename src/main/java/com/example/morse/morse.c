/*****************************************************
  摩尔斯自动发报机 (C51版)
  - 固定码速: 20WPM (点=60ms)
  - 报文: "CQ DE BD4XXK PSE K"
  - 输出引脚: P1.0 (低有效，驱动NPN三极管)
  - 定时器0: 1ms中断
  - 自动循环发送
*****************************************************/

#include <reg51.h>
#include <string.h>

// 输出引脚定义
sbit OUT_PIN = P1^0;

// 摩尔斯编码表（大写字母 A-Z, 数字 0-9）
// 编码规则：1位长度(0~7) + 7位数据 (1=划, 0=点)
// 格式: len<<6 | code
// 例如: 点 = 1t, 划 = 3t
code unsigned char morse_table[] = {
    // A-Z (按照ASCII顺序)
    0x40, // A .-         (01)
    0x80, // B -...       (1000)
    0xA0, // C -.-.       (1010)
    0x80, // D -..        (100)
    0x00, // E .          (0)
    0x20, // F ..-.       (0010)
    0xC0, // G --.        (110)
    0x00, // H ....       (0000)
    0x00, // I ..         (00)
    0x70, // J .---       (0111)
    0xA0, // K -.-        (101)
    0x40, // L .-..       (0100)
    0xC0, // M --         (11)
    0x80, // N -.         (10)
    0xE0, // O ---        (111)
    0x60, // P .--.       (0110)
    0xE8, // Q --.-       (1110 1)
    0x40, // R .-.        (010)
    0x00, // S ...        (000)
    0xC0, // T -          (1)
    0x20, // U ..-        (001)
    0x10, // V ...-       (0001)
    0x60, // W .--        (011)
    0x90, // X -..-       (1001)
    0xA8, // Y -.--       (1010 1)
    0x90, // Z --..       (1001)
    
    // 数字 0-9 (部分简化，完整使用时请扩展)
    0xE0, // 0 -----      (11111)
    0xE0, // 1 .----      (01111)
    ...
};

// 根据字符获取摩尔斯编码
unsigned char get_morse_code(unsigned char ch) {
    if (ch >= 'A' && ch <= 'Z') {
        return morse_table[ch - 'A'];
    }
    if (ch >= '0' && ch <= '9') {
        return morse_table[26 + (ch - '0')];
    }
    return 0; // 非字母数字返回0
}

// 全局变量
unsigned int tick_ms;        // ms计数
unsigned int dit_time;       // 点时长(ms) 20WPM=60ms
bit sending;                 // 发送中标志
bit output_state;            // 输出电平状态
unsigned char code_len;      // 当前字符编码长度
unsigned char code_data;     // 当前字符编码数据
unsigned char bit_index;     // 当前发送位索引
unsigned char wait_time;     // 等待时长(ms)
unsigned char *text_ptr;     // 报文指针

// 报文内容 (以\0结束)
code unsigned char msg[] = "CQ DE BD4XXK PSE K ";

// 定时器0中断 (1ms)
void timer0_isr(void) interrupt 1 {
    TH0 = 0xFC;   // 1ms @ 12MHz
    TL0 = 0x18;
    
    if (!sending) return;
    
    if (wait_time > 0) {
        wait_time--;
        if (wait_time == 0) {
            // 等待结束，翻转输出
            OUT_PIN = !output_state;
            output_state = !output_state;
            
            // 如果输出变低，表示正在发符号
            if (output_state == 1) {
                // 开始新符号，设置等待时间
                if (bit_index < code_len) {
                    // 当前还有位要发
                    unsigned char bit = (code_data >> (7 - bit_index)) & 1;
                    if (bit == 0) {
                        wait_time = dit_time;          // 点
                    } else {
                        wait_time = dit_time * 3;      // 划
                    }
                    bit_index++;
                } else {
                    // 当前字符发完，字符间间隔
                    wait_time = dit_time * 3;
                    bit_index = 0;
                    text_ptr++;
                    if (*text_ptr == '\0') {
                        // 报文结束，暂停后从头开始
                        sending = 0;
                        wait_time = 0;
                        OUT_PIN = 1;
                        output_state = 0;
                        return;
                    }
                    // 获取下一个字符的编码
                    code_data = get_morse_code(*text_ptr);
                    if (code_data == 0) {
                        // 非字母数字，单词间加大间隔
                        wait_time = dit_time * 7;
                        text_ptr++;
                    }
                    code_len = code_data >> 6;
                    bit_index = 0;
                }
            } else {
                // 输出变低，符号内间隔
                wait_time = dit_time;
            }
        }
    }
}

// 启动发送
void start_sending(void) {
    if (sending) return;
    
    sending = 1;
    output_state = 0;
    OUT_PIN = 1;
    text_ptr = msg;
    dit_time = 60;   // 20WPM
    
    // 获取第一个字符编码
    code_data = get_morse_code(*text_ptr);
    if (code_data == 0) {
        code_len = 0;
        wait_time = dit_time * 7;
        text_ptr++;
    } else {
        code_len = code_data >> 6;
        wait_time = dit_time;
        bit_index = 0;
    }
    wait_time = 1;   // 立即启动
}

void main(void) {
    // 定时器0初始化
    TMOD &= 0xF0;
    TMOD |= 0x01;    // 模式1
    TH0 = 0xFC;
    TL0 = 0x18;
    ET0 = 1;
    EA = 1;
    TR0 = 1;
    
    sending = 0;
    OUT_PIN = 1;
    
    // 自动开始发送
    start_sending();
    
    while (1) {
        // 可以在这里添加按键处理，重新开始发送
        // 例如：按P3.2重新启动
        if (sending == 0) {
            // 延时1秒后重新开始
            unsigned int i;
            for (i = 0; i < 5000; i++); // 简单延时
            start_sending();
        }
    }
}