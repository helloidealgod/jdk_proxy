package com.example.simulate.model;

/**
 * 8位ALU（使用寄存器）
 */
public class EightBitALU {
    private EnhancedEightBitRegister regA; // 操作数A
    private EnhancedEightBitRegister regB; // 操作数B
    private EnhancedEightBitRegister regResult; // 结果寄存器
    private EnhancedEightBitRegister flags;     // 标志寄存器

    // 标志位定义
    private static final int FLAG_CARRY = 0;    // 进位标志
    private static final int FLAG_ZERO = 1;     // 零标志
    private static final int FLAG_SIGN = 2;     // 符号标志
    private static final int FLAG_OVERFLOW = 3; // 溢出标志

    public EightBitALU() {
        regA = new EnhancedEightBitRegister();
        regB = new EnhancedEightBitRegister();
        regResult = new EnhancedEightBitRegister();
        flags = new EnhancedEightBitRegister();
    }

    /**
     * ALU操作
     */
    public enum ALUOperation {
        ADD,        // 加法
        SUB,        // 减法
        AND,        // 与
        OR,         // 或
        XOR,        // 异或
        NOT,        // 非
        SHIFT_LEFT, // 左移
        SHIFT_RIGHT // 右移
    }

    /**
     * 执行ALU操作
     */
    public void execute(ALUOperation op, boolean clock) {
        int a = regA.getValue();
        int b = regB.getValue();
        int result = 0;

        // 清空标志
        clearFlags();

        switch (op) {
            case ADD:
                result = add(a, b);
                break;
            case SUB:
                result = subtract(a, b);
                break;
            case AND:
                result = a & b;
                setZeroFlag(result == 0);
                setSignFlag((result & 0x80) != 0);
                break;
            case OR:
                result = a | b;
                setZeroFlag(result == 0);
                setSignFlag((result & 0x80) != 0);
                break;
            case XOR:
                result = a ^ b;
                setZeroFlag(result == 0);
                setSignFlag((result & 0x80) != 0);
                break;
            case NOT:
                result = (~a) & 0xFF;
                setZeroFlag(result == 0);
                setSignFlag((result & 0x80) != 0);
                break;
            case SHIFT_LEFT:
                result = shiftLeft(a);
                break;
            case SHIFT_RIGHT:
                result = shiftRight(a);
                break;
        }

        // 存储结果
        regResult.setValue(result);
        regResult.update(null, clock, true, true, true,
                EnhancedEightBitRegister.OperationMode.LOAD, false);
    }

    private int add(int a, int b) {
        int result = a + b;
        int carry = (result >> 8) & 1;

        setCarryFlag(carry != 0);
        setZeroFlag((result & 0xFF) == 0);
        setSignFlag((result & 0x80) != 0);

        // 溢出检测：两个同号数相加，结果符号相反
        boolean overflow = ((a ^ result) & (b ^ result) & 0x80) != 0;
        setOverflowFlag(overflow);

        return result & 0xFF;
    }

    private int subtract(int a, int b) {
        // 使用补码减法：A - B = A + (~B + 1)
        int bComplement = (~b + 1) & 0xFF;
        return add(a, bComplement);
    }

    private int shiftLeft(int a) {
        int result = (a << 1) & 0xFF;
        int carry = (a >> 7) & 1;

        setCarryFlag(carry != 0);
        setZeroFlag(result == 0);
        setSignFlag((result & 0x80) != 0);

        return result;
    }

    private int shiftRight(int a) {
        int result = (a >> 1) & 0xFF;
        int carry = a & 1;

        setCarryFlag(carry != 0);
        setZeroFlag(result == 0);
        setSignFlag((result & 0x80) != 0);

        return result;
    }

    // 标志位操作
    private void clearFlags() {
        for (int i = 0; i < 8; i++) {
            flags.setBit(i, false);
        }
    }

    private void setCarryFlag(boolean value) {
        flags.setBit(FLAG_CARRY, value);
    }

    private void setZeroFlag(boolean value) {
        flags.setBit(FLAG_ZERO, value);
    }

    private void setSignFlag(boolean value) {
        flags.setBit(FLAG_SIGN, value);
    }

    private void setOverflowFlag(boolean value) {
        flags.setBit(FLAG_OVERFLOW, value);
    }

    // 获取标志
    public boolean getCarryFlag() { return flags.getBit(FLAG_CARRY); }
    public boolean getZeroFlag() { return flags.getBit(FLAG_ZERO); }
    public boolean getSignFlag() { return flags.getBit(FLAG_SIGN); }
    public boolean getOverflowFlag() { return flags.getBit(FLAG_OVERFLOW); }

    // 寄存器访问
    public void setA(int value) { regA.setValue(value); }
    public void setB(int value) { regB.setValue(value); }
    public int getResult() { return regResult.getValue(); }
    public int getFlags() { return flags.getValue(); }

    public String getFlagString() {
        return String.format("C=%d Z=%d S=%d O=%d",
                getCarryFlag() ? 1 : 0,
                getZeroFlag() ? 1 : 0,
                getSignFlag() ? 1 : 0,
                getOverflowFlag() ? 1 : 0);
    }

    @Override
    public String toString() {
        return String.format("A=0x%02X, B=0x%02X, Result=0x%02X, Flags=[%s]",
                regA.getValue(), regB.getValue(),
                regResult.getValue(), getFlagString());
    }
}

/**
 * ALU测试
 */
class ALUTest {
    public static void main(String[] args) {
        EightBitALU alu = new EightBitALU();
        boolean clock = false;

        System.out.println("8位ALU测试:");
        System.out.println("===========\n");

        // 测试加法
        System.out.println("1. 加法测试:");
        alu.setA(0x45); // 69
        alu.setB(0x23); // 35
        alu.execute(EightBitALU.ALUOperation.ADD, true);
        System.out.println(alu);
        System.out.printf("  69 + 35 = %d (0x%02X)\n",
                alu.getResult(), alu.getResult());

        // 测试减法
        System.out.println("\n2. 减法测试:");
        alu.setA(0x50); // 80
        alu.setB(0x30); // 48
        alu.execute(EightBitALU.ALUOperation.SUB, true);
        System.out.println(alu);
        System.out.printf("  80 - 48 = %d\n", alu.getResult());

        // 测试逻辑运算
        System.out.println("\n3. 逻辑运算测试:");
        alu.setA(0xAA); // 10101010
        alu.setB(0x0F); // 00001111

        alu.execute(EightBitALU.ALUOperation.AND, true);
        System.out.printf("AND: 0xAA & 0x0F = 0x%02X\n", alu.getResult());

        alu.execute(EightBitALU.ALUOperation.OR, true);
        System.out.printf("OR:  0xAA | 0x0F = 0x%02X\n", alu.getResult());


    }
}