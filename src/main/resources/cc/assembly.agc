org 0
LJMP _main;
_main:   ADD A,#E1H  ;ADD A,#data;	24

_loop:   ADD A,F2H   ;ADD A,direct;	25
    ADD A,34    ;ADD A,direct;	25
    JB 01H,_loop    ;JB bit,rel;	20

org 400H
;ADD A,R0;	28
;ADD A,R1;	29
;ADD A,R2;	2A
;ADD A,R3;	2B
;ADD A,R4;	2C
;ADD A,R5;	2D
;ADD A,R6;	2E
;ADD A,R7;	2F
ADDC A,#3EH    ;ADDC A,#data;	34
;ADDC A,@R0;	36
;ADDC A,@R1;	37
;ADDC A,direct;	35
;ADDC A,R0;	38
;ADDC A,R1;	39
;ADDC A,R2;	3A
;ADDC A,R3;	3B
;ADDC A,R4;	3C
;ADDC A,R5;	3D
;ADDC A,R6;	3E
;ADDC A,R7;	3F
;ANL A,#data;	54
;ANL A,@R0;	56
;ANL A,@R1;	57
;ANL A,direct;	55
;ANL A,R0;	58
;ANL A,R1;	59
;ANL A,R2;	5A
;ANL A,R3;	5B
;ANL A,R4;	5C
;ANL A,R5;	5D
;ANL A,R6;	5E
;ANL A,R7;	5F
;ANL C,/bit;	B0
;ANL C,bit;	82
ANL 23H,#32H    ;ANL direct,#data;	52
;CJNE @R0,#data,rel;	B6
;CJNE @R1,#data,rel;	B7
;CJNE A,#data,rel;	B4
;CJNE A,direct,rel;	B5
;CJNE R0,#data,rel;	B8
;CJNE R1,#data,rel;	B9
;CJNE R2,#data,rel;	BA
;CJNE R3,#data,rel;	BB
;CJNE R4,#data,rel;	BC
;CJNE R5,#data,rel;	BD
;CJNE R6,#data,rel;	BE
;CJNE R7,#data,rel;	BF
;CLR A;	E4
;CLR bit;	C2
;CLR C;	C3
;CPL A;	F4
;CPL bit;	B2
;DA A;	D4
;DEC @R0;	16
;DEC @R1;	17
;DEC A;	14
;DEC direct;	15
;DEC R0;	18
;DEC R1;	19
;DEC R2;	1A
;DEC R3;	1B
;DEC R4;	1C
;DEC R5;	1D
;DEC R6;	1E
;DEC R7;	1F
;DIV AB;	84
;DJNZ R0,rel;	D8
;DJNZ R1,rel;	D9
;DJNZ R2,rel;	DA
;DJNZ R3,rel;	DB
;DJNZ R4,rel;	DC
;DJNZ R5,rel;	DD
;DJNZ R6,rel;	DE
;DJNZ R7,rel;	DF
;INC @R0;	6
;INC @R1;	7
;INC A;	4
;INC direct;	5
;INC DPTR;	A3
;INC R0;	8
;INC R1;	9
;INC R2;	A
;INC R3;	B
;INC R4;	C
;INC R5;	D
;INC R6;	E
;INC R7;	F
JB 45H,23H  ;JB bit,rel;	20
;JBC bit,rel;	10
;JC rel;	40
;JMP @A+DPTR;	73
;JNB bit,rel;	30
;JNC rel;	50
;JNZ rel;	70
;JZ rel;	60
LCALL 1618H;LCALL addr16;	12
LJMP 5432H;LJMP addr16;	2
;MOV @R0,#data;	76
;MOV @R1,#data;	77
;MOV @R0,A;	F6
;MOV @R1,A;	F7
;MOV @R0,direct;	A6
;MOV @R1,direct;	A7
;MOV A,#data;	74
;MOV A,@R0;	E6
;MOV A,@R1;	E7
;MOV A,direct;	E5
;MOV A,R0;	E8
;MOV A,R1;	E9
;MOV A,R2;	EA
;MOV A,R3;	EB
;MOV A,R4;	EC
;MOV A,R5;	ED
;MOV A,R6;	EE
;MOV A,R7;	EF
;MOV bit,C;	92
;MOV C,bit;	A2
;MOV direct,#data;	75
;MOV direct,@R0;	86
;MOV direct,@R1;	87
;MOV direct,A;	F5
;MOV direct,R0;	88
;MOV direct,R1;	89
;MOV direct,R2;	8A
;MOV direct,R3;	8B
;MOV direct,R4;	8C
;MOV direct,R5;	8D
;MOV direct,R6;	8E
;MOV direct,R7;	8F
;MOV direct,direct;	85
MOV DPTR,#1234H    ;MOV DPTR,#data16;	90
;MOV R0,#data;	78
;MOV R1,#data;	79
;MOV R2,#data;	7A
;MOV R3,#data;	7B
;MOV R4,#data;	7C
;MOV R5,#data;	7D
;MOV R6,#data;	7E
;MOV R7,#data;	7F
;MOV R0,A;	F8
;MOV R1,A;	F9
;MOV R2,A;	FA
;MOV R3,A;	FB
;MOV R4,A;	FC
;MOV R5,A;	FD
;MOV R6,A;	FE
;MOV R7,A;	FF
;MOV R0,direct;	A8
;MOV R1,direct;	A9
;MOV R2,direct;	AA
;MOV R3,direct;	AB
;MOV R4,direct;	AC
;MOV R5,direct;	AD
;MOV R6,direct;	AE
;MOV R7,direct;	AF
;MOVC A,@A+DPTR;	93
;MOVC A,@A+PC;	83
;MOVX @DPTR,A;	F0
;MOVX @R0,A;	F2
;MOVX @R1,A;	F3
;MOVX A,@DPTR;	E0
;MOVX A,@R0;	E2
;MOVX A,@R1;	E3
;MUL AB;	A4
;NOP;	0
;ORL A,#data;	44
;ORL A,@R0;	46
;ORL A,@R1;	47
;ORL A,direct;	45
;ORL A,R0;	48
;ORL A,R1;	49
;ORL A,R2;	4A
;ORL A,R3;	4B
;ORL A,R4;	4C
;ORL A,R5;	4D
;ORL A,R6;	4E
;ORL A,R7;	4F
;ORL C,/bit;	A0
ORL C, 7    ;ORL C,bit;	72
ORL C,07H   ;ORL C,bit;	72
;ORL direct,#data;	42
;POP direct;	D0
;PUSH direct;	C0
;RET;	22
;RETI;	32
;RL A;	23
;RLC A;	33
;RR A;	3
;RRC A;	13
;SETB bit;	D2
;SETB C;	D3
SJMP -2    ;SJMP rel;	80
;SUBB  A,#data;	94
;SUBB  A,@R0;	96
;SUBB  A,@R1;	97
;SUBB  A,direct;	95
;SUBB  A,R0;	98
;SUBB  A,R1;	99
;SUBB  A,R2;	9A
;SUBB  A,R3;	9B
;SUBB  A,R4;	9C
;SUBB  A,R5;	9D
;SUBB  A,R6;	9E
;SUBB  A,R7;	9F
;SWAP A;	C4
;XCH A,@R0;	C6
;XCH A,@R1;	C7
;XCH A,direct;	C5
;XCH A,R0;	C8
;XCH A,R1;	C9
;XCH A,R2;	CA
;XCH A,R3;	CB
;XCH A,R4;	CC
;XCH A,R5;	CD
;XCH A,R6;	CE
;XCH A,R7;	CF
;XCHD A,@R0;	D6
;XCHD A,@R1;	D7
;XRL A,#data;	64
;XRL A,@R0;	66
;XRL A,@R1;	67
;XRL A,direct;	65
;XRL A,R0;	68
;XRL A,R1;	69
;XRL A,R2;	6A
;XRL A,R3;	6B
;XRL A,R4;	6C
;XRL A,R5;	6D
;XRL A,R6;	6E
;XRL A,R7;	6F
;XRL direct,#data;	62

.byte 0908H
.byte 0A08H
.byte 1BH
.byte 96
.byte 2CH