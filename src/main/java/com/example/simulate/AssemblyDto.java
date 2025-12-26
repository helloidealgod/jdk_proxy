package com.example.simulate;

import java.util.List;

public class AssemblyDto {
    private Integer address;
    private String markName;
    private List<String> commands;
    private List<Byte> machineCodes;
    private Integer cmdByteLength;

    public Integer getAddress() {
        return address;
    }

    public Integer getCmdByteLength() {
        return cmdByteLength;
    }

    public void setCmdByteLength(Integer cmdByteLength) {
        this.cmdByteLength = cmdByteLength;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<Byte> getMachineCodes() {
        return machineCodes;
    }

    public void setMachineCodes(List<Byte> machineCodes) {
        this.machineCodes = machineCodes;
    }
}
