package com.example.simulate;

import java.util.List;

public class AssemblyDto {
    private Integer address;
    private Integer commandLength;
    private String markName;
    private List<String> commands;
    private List<Byte> machineCodes;

    public Integer getAddress() {
        return address;
    }

    public Integer getCommandLength() {
        return commandLength;
    }

    public void setCommandLength(Integer commandLength) {
        this.commandLength = commandLength;
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
