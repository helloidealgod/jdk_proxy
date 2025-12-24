package com.example.simulate;

public class Main {
    //64k 内存 : 4K RAM 60K ROM
    public static byte[] RAM = new byte[65536];
    public static int PC = 0;
    public static int PSW = 0xD0;//CY（PSW.7）：进位/借位标志、AC（PSW.6）：辅助进位标志（BCD运算）、F0（PSW.5）：用户标志位0 、RS1、RS0（PSW.4、PSW.3）：寄存器组选择位 、OV（PSW.2）：溢出标志 、P（PSW.0）：奇偶标志（ACC中1的个数为奇数则置1）
    public static int ACC = 0xE0;
    public static int DPL = 0x82;
    public static int DPH = 0x83;

    public static int[] n = {0, 0x8, 0x10, 0x18};

    public static void main(String[] args) {
        PC = 4096;
        RAM[0] = (byte) 0x20;
        RAM[ACC] = (byte) 0x02;
        //mov A,R0
        RAM[PC] = (byte) 0xE8;
        //mov R1,A
        RAM[PC + 1] = (byte) 0xF9;
        while (RAM[PC] != 0) {
            run();
        }
        System.out.println("ACC=" + ACC);
    }

    //程序计数器（PC）：16位，存放下一条指令地址，自动+1。
    //程序状态字（PSW）：8位，存标志位（如CY、OV），影响程序流程。
    //1、根据PC取指令
    //2、根据指令取操作数
    //3、执行指令
    //4、根据指令更新PC
    public static void run() {
        byte b = RAM[PC];
        if (0 < executeMov(b)) {
            return;
        }
        if (0 < executeArith(b)) {
            return;
        }
        if (0 < executeLogic(b)) {
            return;
        }
        if (0 < executeCtrl(b)) {
            return;
        }
        if (0 < executeBit(b)) {
            return;
        }
    }

    //1. 数据传送类指令
    public static int executeMov(byte b) {
        int nIndex = 0;
        switch (b) {
            // 指令格式	机器码	字节数	周期	说明
            // MOV A, Rn	E8-EF	1	1	A←Rn
            // MOV A, direct	E5 direct	2	1	A←(direct)
            // MOV A, @Ri	E6-E7	1	1	A←(Ri)
            // MOV A, #data	74 data	2	1	A←立即数
            // MOV Rn, A	F8-FF	1	1	Rn←A
            // MOV Rn, direct	A8-AF direct	2	2	Rn←(direct)
            // MOV Rn, #data	78-7F data	2	1	Rn←立即数
            // MOV direct, A	F5 direct	2	1	(direct)←A
            // MOV direct, Rn	88-8F direct	2	2	(direct)←Rn
            // MOV direct, @Ri	86-87 direct	2	2	(direct)←(Ri)
            // MOV direct, #data	75 direct data	3	2	(direct)←立即数
            // MOV @Ri, A	F6-F7	1	1	(Ri)←A
            // MOV @Ri, direct	A6-A7 direct	2	2	(Ri)←(direct)
            // MOV @Ri, #data	76-77 data	2	1	(Ri)←立即数
            // MOV DPTR, #data16	90 dataH dataL	3	2	DPTR←16位立即数
            // MOVC A, @A+DPTR	93	1	2	A←(A+DPTR)查表
            // MOVC A, @A+PC	83	1	2	A←(A+PC)查表
            // MOVX A, @Ri	E2-E3	1	2	A←外部RAM(Ri)
            // MOVX A, @DPTR	E0	1	2	A←外部RAM(DPTR)
            // MOVX @Ri, A	F2-F3	1	2	外部RAM(Ri)←A
            // MOVX @DPTR, A	F0	1	2	外部RAM(DPTR)←A
            // PUSH direct	C0 direct	2	2	压栈
            // POP direct	D0 direct	2	2	出栈
            // XCH A, Rn	C8-CF	1	1	A↔Rn
            // XCH A, direct	C5 direct	2	1	A↔(direct)
            // XCH A, @Ri	C6-C7	1	1	A↔(Ri)
            // XCHD A, @Ri	D6-D7	1	1	A低4位↔(Ri)低4位
            // SWAP A	C4	1	1	A高4位与低4位交换

            //助记符	操作数			机器码	字节数	周期数

            // MOV		Rn,A			F8~FF	1		1
            case (byte) 0xF8:
            case (byte) 0xF9:
            case (byte) 0xFA:
            case (byte) 0xFB:
            case (byte) 0xFC:
            case (byte) 0xFD:
            case (byte) 0xFE:
            case (byte) 0xFF:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[n[nIndex] + (b & 0xFF - 0xF8)] = RAM[ACC];
                PC += 1;
                break;
            // MOV		Rn,#data		78~7F	2		1
            case (byte) 0x78:
            case (byte) 0x79:
            case (byte) 0x7A:
            case (byte) 0x7B:
            case (byte) 0x7C:
            case (byte) 0x7D:
            case (byte) 0x7E:
            case (byte) 0x7F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[n[nIndex] + (b & 0xFF - 0xF8)] = RAM[PC + 1];
                PC += 2;
                break;
            // MOV		direct,A		F5		2		1 direct：8位直接寻址地址，00H~FFH或SFR。
            case (byte) 0xF5:
                RAM[PC + 1] = RAM[ACC];
                PC += 2;
                break;
            // MOV		A,direct		E5		2		1
            case (byte) 0xE5:
                RAM[ACC] = RAM[PC + 1];
                PC += 2;
                break;
            // MOV		DPTR,#data16	90		3		1
            case (byte) 0x90:
                RAM[DPL] = RAM[PC + 1];
                RAM[DPH] = RAM[PC + 2];
                PC += 3;
                break;
            // MOVX	A,@DPTR			    E0		1		2
            case (byte) 0xE0:
                RAM[ACC] = RAM[RAM[DPL] + RAM[DPH]];
                PC += 1;
                break;
            // MOVC	A,@A+DPTR		    93		1		2
            case (byte) 0x93:
                RAM[ACC] = RAM[RAM[ACC] + RAM[DPL] + RAM[DPH]];
                PC += 1;
                break;
            default:
                return 0;
        }
        return 1;
    }

    public static int executeArith(byte b) {
//            2. 算术运算类指令
//            指令格式	机器码	字节数	周期	说明
//            ADD A, Rn	28-2F	1	1	A←A+Rn
//            ADD A, direct	25 direct	2	1	A←A+(direct)
//            ADD A, @Ri	26-27	1	1	A←A+(Ri)
//            ADD A, #data	24 data	2	1	A←A+立即数
//            ADDC A, Rn	38-3F	1	1	A←A+Rn+CY
//            ADDC A, direct	35 direct	2	1	A←A+(direct)+CY
//            ADDC A, @Ri	36-37	1	1	A←A+(Ri)+CY
//            ADDC A, #data	34 data	2	1	A←A+立即数+CY
//            SUBB A, Rn	98-9F	1	1	A←A-Rn-CY
//            SUBB A, direct	95 direct	2	1	A←A-(direct)-CY
//            SUBB A, @Ri	96-97	1	1	A←A-(Ri)-CY
//            SUBB A, #data	94 data	2	1	A←A-立即数-CY
//            INC A	04	1	1	A←A+1
//            INC Rn	08-0F	1	1	Rn←Rn+1
//            INC direct	05 direct	2	1	(direct)←(direct)+1
//            INC @Ri	06-07	1	1	(Ri)←(Ri)+1
//            INC DPTR	A3	1	2	DPTR←DPTR+1
//            DEC A	14	1	1	A←A-1
//            DEC Rn	18-1F	1	1	Rn←Rn-1
//            DEC direct	15 direct	2	1	(direct)←(direct)-1
//            DEC @Ri	16-17	1	1	(Ri)←(Ri)-1
//            MUL AB	A4	1	4	A×B，积高8位在B，低8位在A
//            DIV AB	84	1	4	A÷B，商在A，余数在B
//            DA A	D4	1	1	十进制调整
        int nIndex = 0;
        switch (b) {
            // 算术运算类指令
            // 助记符	操作数			机器码	字节数	周期数
            // ADD		A,Rn			28~2F	1		1
            case (byte) 0x28:
            case (byte) 0x29:
            case (byte) 0x2A:
            case (byte) 0x2B:
            case (byte) 0x2C:
            case (byte) 0x2D:
            case (byte) 0x2E:
            case (byte) 0x2F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[ACC] += RAM[n[nIndex] + (b & 0xFF - 0x28)];
                PC += 1;
                break;
            // ADD		A,#data			25		2		1
            case (byte) 0x25:
                RAM[ACC] += RAM[PC + 1];
                PC += 2;
                break;
            // SUBB	A,Rn			    98~9F	1		1
            case (byte) 0x98:
            case (byte) 0x99:
            case (byte) 0x9A:
            case (byte) 0x9B:
            case (byte) 0x9C:
            case (byte) 0x9D:
            case (byte) 0x9E:
            case (byte) 0x9F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[ACC] -= RAM[n[nIndex] + (b & 0xFF - 0x98)];
                PC += 1;
                break;
            // SUBB	A,#data			    94		2		1
            case (byte) 0x94:
                RAM[ACC] -= RAM[PC + 1];
                PC += 2;
                break;
            // INC		A				04		1		1
            case (byte) 0x04:
                RAM[ACC] += 1;
                PC += 1;
                break;
            // INC      Rn	            08-0F	1	1	Rn←Rn+1
            case (byte) 0x08:
            case (byte) 0x09:
            case (byte) 0x0A:
            case (byte) 0x0B:
            case (byte) 0x0C:
            case (byte) 0x0D:
            case (byte) 0x0E:
            case (byte) 0x0F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[n[nIndex] + (b & 0xFF - 0x08)] += 1;
                PC += 1;
                break;
            // DEC		A				14		1		1
            case (byte) 0x14:
                RAM[ACC] -= 1;
                PC += 1;
                break;
            // DEC		Rn				18~1F	1		1
            case (byte) 0x18:
            case (byte) 0x19:
            case (byte) 0x1A:
            case (byte) 0x1B:
            case (byte) 0x1C:
            case (byte) 0x1D:
            case (byte) 0x1E:
            case (byte) 0x1F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[n[nIndex] + (b & 0xFF - 0x18)] -= 1;
                PC += 1;
                break;
            default:
                return 0;
        }
        return 1;
    }

    public static int executeLogic(byte b) {
//            3. 逻辑运算类指令
//            指令格式	机器码	字节数	周期	说明
//            ANL A, Rn	58-5F	1	1	A←A∧Rn
//            ANL A, direct	55 direct	2	1	A←A∧(direct)
//            ANL A, @Ri	56-57	1	1	A←A∧(Ri)
//            ANL A, #data	54 data	2	1	A←A∧立即数
//            ANL direct, A	52 direct	2	1	(direct)←(direct)∧A
//            ANL direct, #data	53 direct data	3	2	(direct)←(direct)∧立即数
//            ORL A, Rn	48-4F	1	1	A←A∨Rn
//            ORL A, direct	45 direct	2	1	A←A∨(direct)
//            ORL A, @Ri	46-47	1	1	A←A∨(Ri)
//            ORL A, #data	44 data	2	1	A←A∨立即数
//            ORL direct, A	42 direct	2	1	(direct)←(direct)∨A
//            ORL direct, #data	43 direct data	3	2	(direct)←(direct)∨立即数
//            XRL A, Rn	68-6F	1	1	A←A⊕Rn
//            XRL A, direct	65 direct	2	1	A←A⊕(direct)
//            XRL A, @Ri	66-67	1	1	A←A⊕(Ri)
//            XRL A, #data	64 data	2	1	A←A⊕立即数
//            XRL direct, A	62 direct	2	1	(direct)←(direct)⊕A
//            XRL direct, #data	63 direct data	3	2	(direct)←(direct)⊕立即数
//            CLR A	E4	1	1	A←0
//            CPL A	F4	1	1	A←按位取反
//            RL A	23	1	1	A循环左移
//            RLC A	33	1	1	A带CY循环左移
//            RR A	03	1	1	A循环右移
//            RRC A	13	1	1	A带CY循环右移
        int nIndex = 0;
        switch (b) {
            // 逻辑运算类指令
            // 助记符	操作数			机器码	字节数	周期数
            // ANL		A,Rn			58~5F	1		1
            case (byte) 0x58:
            case (byte) 0x59:
            case (byte) 0x5A:
            case (byte) 0x5B:
            case (byte) 0x5C:
            case (byte) 0x5D:
            case (byte) 0x5E:
            case (byte) 0x5F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[ACC] &= RAM[n[nIndex] + (b & 0xFF - 0x58)];
                PC += 1;
                break;
            // ANL		A,#data			54		2		1
            case (byte) 0x54:
                RAM[ACC] &= RAM[PC + 1];
                PC += 2;
                break;
            // ORL		A,Rn			48~4F	1		1
            case (byte) 0x48:
            case (byte) 0x49:
            case (byte) 0x4A:
            case (byte) 0x4B:
            case (byte) 0x4C:
            case (byte) 0x4D:
            case (byte) 0x4E:
            case (byte) 0x4F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[ACC] |= RAM[n[nIndex] + (b & 0xFF - 0x48)];
                PC += 1;
                break;
            // ORL		A,#data			44		2		1
            case (byte) 0x44:
                RAM[ACC] |= RAM[PC + 1];
                PC += 2;
                break;
            // XRL		A,Rn			68~6F	1		1
            case (byte) 0x68:
            case (byte) 0x69:
            case (byte) 0x6A:
            case (byte) 0x6B:
            case (byte) 0x6C:
            case (byte) 0x6D:
            case (byte) 0x6E:
            case (byte) 0x6F:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                RAM[ACC] ^= RAM[n[nIndex] + (b & 0xFF - 0x68)];
                PC += 1;
                break;
            // XRL		A,#data			64		2		1
            case (byte) 0x64:
                RAM[ACC] ^= RAM[PC + 1];
                PC += 2;
                break;
            // CLR		A				E4		1		2
            case (byte) 0xE4:
                RAM[ACC] = 0;
                PC += 1;
                break;
            // CPL		A				F4		1		1
            case (byte) 0xF4:
                RAM[ACC] = (byte) (RAM[ACC] ^ 0xFF);
                PC += 1;
                break;
            default:
                return 0;
        }
        return 1;
    }

    public static int executeCtrl(byte b) {
//            4. 控制转移类指令
//            指令格式	机器码	字节数	周期	说明
//            ACALL addr11	xxx1 0001 addr7-0	2	2	绝对调用
//            LCALL addr16	12 addrH addrL	3	2	长调用
//            RET	22	1	2	子程序返回
//            RETI	32	1	2	中断返回
//            AJMP addr11	xxx0 0001 addr7-0	2	2	绝对跳转
//            LJMP addr16	02 addrH addrL	3	2	长跳转
//            SJMP rel	80 rel	2	2	短跳转(-128~+127)
//            JMP @A+DPTR	73	1	2	间接跳转
//            JZ rel	60 rel	2	2	A=0跳转
//            JNZ rel	70 rel	2	2	A≠0跳转
//            JC rel	40 rel	2	2	CY=1跳转
//            JNC rel	50 rel	2	2	CY=0跳转
//            JB bit, rel	20 bit rel	3	2	位=1跳转
//            JNB bit, rel	30 bit rel	3	2	位=0跳转
//            JBC bit, rel	10 bit rel	3	2	位=1跳转并清0
//            CJNE A, direct, rel	B5 direct rel	3	2	比较不相等跳转
//            CJNE A, #data, rel	B4 data rel	3	2	比较不相等跳转
//            CJNE Rn, #data, rel	B8-BF data rel	3	2	比较不相等跳转
//            CJNE @Ri, #data, rel	B6-B7 data rel	3	2	比较不相等跳转
//            DJNZ Rn, rel	D8-DF rel	2	2	Rn减1不为0跳转
//            DJNZ direct, rel	D5 direct rel	3	2	(direct)减1不为0跳转
//            NOP	00	1	1	空操作
        int nIndex = 0;
        switch (b) {
            // 控制转移类指令
            // 助记符	操作数			机器码	字节数	周期数
            // JMP		@A+DPTR			82		1		2
            case (byte) 0x82:
                PC = RAM[RAM[DPL] + RAM[DPH]];
                break;
            // JZ		rel				80		2		2   A=0跳转 rel 表示相对地址（-128~127）
            case (byte) 0x80:
                PC += 2;
                break;
            // JNZ		rel				81		2		2   A!=0跳转 rel 表示相对地址（-128~127）
            case (byte) 0x81:
                PC += 2;
                break;
            // CJNE	A,#data,rel		    85		3		2   CY=1跳转
            case (byte) 0x85:
                PC += 3;
                break;
            // DJNZ Rn, rel	            D8-DF rel	2	2	Rn减1不为0跳转
            case (byte) 0xD8:
            case (byte) 0xD9:
            case (byte) 0xDA:
            case (byte) 0xDB:
            case (byte) 0xDC:
            case (byte) 0xDD:
            case (byte) 0xDE:
            case (byte) 0xDF:
                nIndex = (byte) (RAM[PSW] & 0x0C) >> 2;
                //RAM[n[nIndex] + (b & 0xFF - 0xF8)]
                PC += 2;
                break;
            // ACALL	addr11			12		2		2
            case (byte) 0x12:
                PC += 2;
                break;
            // LCALL	addr16			11		3		2
            case (byte) 0x11:
                PC += RAM[PC + 1] + RAM[PC + 2];
                break;
            // RET						88		1		2
            case (byte) 0x88:
                PC += 1;
                break;
            default:
                return 0;
        }
        return 1;
    }

    public static int executeBit(byte b) {
//            5. 位操作类指令
//            指令格式	机器码	字节数	周期	说明
//            CLR C	C3	1	1	CY←0
//            CLR bit	C2 bit	2	1	bit←0
//            SETB C	D3	1	1	CY←1
//            SETB bit	D2 bit	2	1	bit←1
//            CPL C	B3	1	1	CY←~CY
//            CPL bit	B2 bit	2	1	bit←~bit
//            ANL C, bit	82 bit	2	2	CY←CY∧bit
//            ANL C, /bit	B0 bit	2	2	CY←CY∧~bit
//            ORL C, bit	72 bit	2	2	CY←CY∨bit
//            ORL C, /bit	A0 bit	2	2	CY←CY∨~bit
//            MOV C, bit	A2 bit	2	1	CY←bit
//            MOV bit, C	92 bit	2	2	bit←CY
        switch (b) {
            // 位操作类指令
            // 助记符	操作数			机器码	字节数	周期数
            // CLR		C				C3		1		1 CY = 0
            case (byte) 0xC3:
                RAM[PSW] &= 0x7F;
                PC += 1;
                break;
            //CLR bit	C2 bit	2	1	bit←0
            case (byte) 0xC2:
                PC += 2;
                break;
            // SETB	C				    D3		1		1 CY = 1
            case (byte) 0xD3:
                RAM[PSW] |= 0x80;
                PC += 1;
                break;
            // SETB bit	D2 bit	2	1	bit←1
            case (byte) 0xD2:
                PC += 2;
                break;
            // CPL      C	B3	1	1	CY←~CY
            case (byte) 0xB3:
                PC += 1;
                break;
            // CPL      bit	B2 bit	2	1	bit←~bit
            case (byte) 0xB2:
                PC += 2;
                break;
            // JB		bit,rel			83		3		2 位=1跳转
            case (byte) 0x83:
                PC += 3;
                break;
            // JNB		bit,rel			84		3		2 位!=1跳转
            case (byte) 0x84:
                PC += 3;
                break;
            default:
                return 0;
        }
        return 1;
    }
}
