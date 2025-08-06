package com.example.compile.table3.name;

public enum TypeEnum {
    INT(8, "int"),
    CHAR(8, "char"),
    VOID(8, "void"),
    ERROR(0, "error");

    private String type;
    private int width;

    TypeEnum(int width, String type) {
        this.width = width;
        this.type = type;
    }

    public static int getWidthByType(String type) {
        for (TypeEnum e : TypeEnum.values()) {
            if (e.type.equals(type)) {
                return e.width;
            }
        }
        throw new IllegalArgumentException("Invalid type: " + type);
    }
}
