package com.example.simulate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Assembly {

    public static void main(String[] args) throws IOException {
        List<AssemblyDto> commands = getCommands("/src/main/resources/cc/assembly.agc");
        System.out.println("===================================================");
        translate(commands);
        System.out.println("===================================================");
        for (AssemblyDto assemblyDto : commands) {
            System.out.print(String.format("0x%04X: ", assemblyDto.getAddress()));
            for (Byte machineCode : assemblyDto.getMachineCodes()) {
                System.out.print(String.format("%X ", machineCode));
            }
            for (String cmd : assemblyDto.getCommands()) {
                System.out.print(cmd + " ");
            }
            System.out.println();
        }
    }

    public static List<AssemblyDto> getCommands(String filePath) {
        List<AssemblyDto> assemblyDtos = new ArrayList<>();
        Integer address = null;
        File f = new File("");
        try {
            File file = new File(f.getAbsolutePath() + filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            AssemblyDto assemblyDto = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("\n" + line);
                //将注释内容是过滤掉
                if (line.contains(";")) {
                    line = line.substring(0, line.indexOf(";"));
                }
                //System.out.println(line);
                String[] split = line.split("[, ;]");
                for (int i = 0; i < split.length; i++) {
                    if ("org".equalsIgnoreCase(split[i])) {
                        address = Integer.parseInt(split[i + 1]);
                        break;
                    } else {
                        if (null == assemblyDto) {
                            assemblyDto = new AssemblyDto();
                        }
                        if ("".equals(split[i])) {
                            continue;
                        }
                        if (0 == i && split[i].endsWith(":")) {
                            assemblyDto.setMarkName(split[i].substring(0, split[i].length() - 1));
                        } else {
                            if (null == assemblyDto.getCommands()) {
                                assemblyDto.setCommands(new ArrayList<>());
                            }
                            assemblyDto.getCommands().add(split[i]);
                            System.out.print(split[i] + " ");
                        }
                    }
                }
                if (null != assemblyDto) {
                    if (null != address) {
                        assemblyDto.setAddress(address);
                    }
                    if (null != assemblyDto.getCommands() && !assemblyDto.getCommands().isEmpty()) {
                        assemblyDtos.add(assemblyDto);
                        assemblyDto = new AssemblyDto();
                        address = null;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assemblyDtos;
    }

    public static void translate(List<AssemblyDto> assemblyDtos) {
        if (null == assemblyDtos || assemblyDtos.isEmpty()) {
            return;
        }
        Integer address = assemblyDtos.get(0).getAddress();
        for (AssemblyDto assemblyDto : assemblyDtos) {
            assemblyDto.setAddress(address);
            List<String> commands = assemblyDto.getCommands();
            List<Byte> machineCodes = new ArrayList<>();
            Integer codeLength = translateCommand(commands, machineCodes);
            assemblyDto.setMachineCodes(machineCodes);
            assemblyDto.setCommandLength(codeLength);
            address += codeLength;
        }
    }

    public static Integer translateCommand(List<String> commands, List<Byte> machineCodes) {
        int rowIndex = -1;
        int colIndex = 0;
        System.out.println();
        for (String command : commands) {
            System.out.print(command + " ");
        }
        System.out.println();
        if (commands.size() > 1) {
            rowIndex = Constant.getRowIndex(commands.get(0));
            for (int i = 1; i < commands.size(); i++) {
                colIndex = Constant.getColIndex(commands.get(i));
                rowIndex = Constant.stateMap[rowIndex][colIndex];
            }
            if (-1 == rowIndex) {
                System.out.println("错误：" + commands.get(0));
            } else {
                machineCodes.add((byte) ((byte) rowIndex & 0xff));
            }
        } else {
            if ("NOP".equalsIgnoreCase(commands.get(0))) {
                machineCodes.add((byte) 0x00);
            } else if ("RET".equalsIgnoreCase(commands.get(0))) {
                machineCodes.add((byte) 0x22);
            } else if ("RETI".equalsIgnoreCase(commands.get(0))) {
                machineCodes.add((byte) 0x32);
            }
        }
        for (Byte machineCode : machineCodes) {
            System.out.print(String.format("%X ", machineCode));
        }
        int codeLengthIndex = Constant.getCodeLengthIndex(machineCodes.get(0));
        if (0 <= codeLengthIndex && codeLengthIndex < Constant.codeLengthMap.length) {
            return Constant.codeLengthMap[codeLengthIndex][1];
        } else {
            System.out.println("错误：" + commands.get(0));
        }
        return 1;
    }

    public static void updateAddress(AssemblyDto assemblyDto) {
    }

    public static void out2File(AssemblyDto assemblyDto) {
    }
}
