#include <reg51.h>
#include <string.h>

// 输入引脚（接 NE567 输出）
sbit MORSE_IN = P1^0;

// 摩尔斯码表（点=0，划=1，结束=0xFF）
code unsigned char morse_table[][8] = {
    {0,1,0xFF},           // A .-
    {1,0,0,0,0xFF},      // B -...
    {1,0,1,0,0xFF},      // C -.-.
    {1,0,0,0xFF},        // D -..
    {0,0xFF},            // E .
    {0,0,1,0,0xFF},      // F ..-.
    {1,1,0,0xFF},        // G --.
    {0,0,0,0,0xFF},      // H ....
    {0,0,0xFF},          // I ..
    {0,1,1,1,0xFF},      // J .---
    {1,0,1,0xFF},        // K -.-
    {0,1,0,0,0xFF},      // L .-..
    {1,1,0xFF},          // M --
    {1,0,0xFF},          // N -.
    {1,1,1,0xFF},        // O ---
    {0,1,1,0,0xFF},      // P .--.
    {1,1,0,1,0xFF},      // Q --.-
    {0,1,0,0xFF},        // R .-.
    {0,0,0,0xFF},        // S ...
    {1,0xFF},            // T -
    {0,0,1,0xFF},        // U ..-
    {0,0,0,1,0xFF},      // V ...-
    {0,1,1,0xFF},        // W .--
    {1,0,0,1,0xFF},      // X -..-
    {1,0,1,1,0xFF},      // Y -.--
    {1,1,0,0,0xFF},      // Z --..
};

// 解码缓冲区
unsigned char symbol_buf[8];
unsigned char symbol_len = 0;

// 时间变量
unsigned int high_time = 0;
unsigned int low_time = 0;
bit last_state = 0;
bit receiving = 0;

// 自适应测速
unsigned int dot_time = 0;      // 动态测量的点时长(ms)
unsigned int dash_time = 0;     // 划时长(ms)
unsigned int min_dot = 30;      // 最小点(30WPM)
unsigned int max_dot = 120;     // 最大点(10WPM)
bit speed_learnt = 0;

// 解码结果存放
unsigned char result[64];
unsigned char result_idx = 0;

// 查找摩尔斯码对应字符
unsigned char morse_to_char(unsigned char *buf, unsigned char len) {
    unsigned char i, j;
    for (i = 0; i < 26; i++) {
        for (j = 0; morse_table[i][j] != 0xFF; j++) {
            if (j >= len || buf[j] != morse_table[i][j]) break;
        }
        if (morse_table[i][j] == 0xFF && j == len) {
            return 'A' + i;
        }
    }
    return '?';
}

// 更新速度参数
void update_speed(unsigned int t) {
    if (!speed_learnt) {
        if (dot_time == 0) {
            dot_time = t;
        } else {
            // 取最小的有效高电平作为点
            if (t >= min_dot && t <= max_dot && t < dot_time) {
                dot_time = t;
            }
        }
        dash_time = dot_time * 3;
        // 收到一个划之后，速度已确定
        if (t >= dot_time * 2) {
            speed_learnt = 1;
        }
    }
}

// 解码当前符号
void decode_symbol() {
    if (symbol_len == 0) return;

    unsigned char ch = morse_to_char(symbol_buf, symbol_len);
    if (result_idx < 63) {
        result[result_idx++] = ch;
    }
    symbol_len = 0;
}

// 定时器0初始化（1ms中断）
void timer0_init() {
    TMOD &= 0xF0;
    TMOD |= 0x01;
    TH0 = 0xFC;     // 1ms @ 12MHz
    TL0 = 0x18;
    ET0 = 1;
    EA = 1;
    TR0 = 1;
}

// 定时器中断（1ms）
void timer0_isr() interrupt 1 {
    TH0 = 0xFC;
    TL0 = 0x18;

    bit current = MORSE_IN;

    if (current == last_state) {
        if (current == 1) {
            high_time++;
        } else {
            low_time++;
        }
    } else {
        // 电平变化
        if (last_state == 1) {
            // 高电平结束 → 判断是点还是划
            if (dot_time > 0 && high_time >= dot_time) {
                if (high_time >= dash_time) {
                    symbol_buf[symbol_len++] = 1;   // 划
                } else {
                    symbol_buf[symbol_len++] = 0;   // 点
                }
            }
            // 学习速度（取最短的高电平）
            update_speed(high_time);
            high_time = 0;
            receiving = 1;
        } else {
            // 低电平结束 → 判断间隔
            if (dot_time > 0) {
                if (low_time >= dot_time * 7) {
                    // 单词间隔
                    if (receiving) {
                        decode_symbol();
                        if (result_idx < 63) result[result_idx++] = ' ';
                        receiving = 0;
                    }
                } else if (low_time >= dot_time * 3) {
                    // 字符间隔
                    if (receiving) {
                        decode_symbol();
                    }
                }
            }
            low_time = 0;
        }
        last_state = current;
    }
}

// 获取解码结果
unsigned char* get_result() {
    result[result_idx] = '\0';
    return result;
}

// 重置解码器
void reset_decoder() {
    symbol_len = 0;
    high_time = 0;
    low_time = 0;
    receiving = 0;
    result_idx = 0;
    last_state = MORSE_IN;
    dot_time = 0;
    dash_time = 0;
    speed_learnt = 0;
}

// 主函数
void main() {
    timer0_init();
    reset_decoder();

    while (1) {
        // 可选：串口输出或 LED 指示
    }
}