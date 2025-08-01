package com.example.compile.table3;

public class Info {
    public String name;
    public String type;
    public String varName;
    public int typeWidth;
    public String value;
    public Long eAddress;
    public int segmentType;

    public Info(String name, String type, int typeWidth, String value, Long eAddress, int segmentType) {
        this.name = name;
        this.type = type;
        this.typeWidth = typeWidth;
        this.value = value;
        this.eAddress = eAddress;
        this.segmentType = segmentType;
    }

    public Info() {
    }

    public Info(String name) {
        this.name = name;
    }
}
