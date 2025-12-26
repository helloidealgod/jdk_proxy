package com.example.simulate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Assembly {

    public static void main(String[] args) throws IOException {
        List<AssemblyDto> commands = getCommands("/src/main/resources/cc/assembly.agc");
        System.out.println("===================================================");
        translate(commands);
        updateLankMark(commands);
        out2File(commands, "/src/main/resources/cc/assembly.out");
        System.out.println("===================================================");
        for (AssemblyDto assemblyDto : commands) {
            System.out.print(String.format("0x%04X: ", assemblyDto.getAddress()));
            for (Byte machineCode : assemblyDto.getMachineCodes()) {
                System.out.print(String.format("%02X ", machineCode));
            }
            for (String cmd : assemblyDto.getCommands()) {
                System.out.print(cmd + " ");
            }
            if (null != assemblyDto.getMarkName()) {
                System.out.print(" :" + assemblyDto.getMarkName());
            }
            System.out.println();
        }
        printHex("/src/main/resources/cc/assembly.out");
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
                        if (split[i + 1].endsWith("H")) {
                            address = hexStringToInt(split[i + 1]);
                        } else {
                            address = Integer.parseInt(split[i + 1]);
                        }
                        break;
                    } else {
                        if (null == assemblyDto) {
                            assemblyDto = new AssemblyDto();
                        }
                        if ("".equals(split[i])) {
                            continue;
                        }
                        if (0 == i && split[i].endsWith(":")) {
                            //查看标黄是否已存在
                            final int fi = i;
                            if (assemblyDtos.stream().anyMatch(assemblyDto1 -> null != assemblyDto1.getMarkName()
                                    && assemblyDto1.getMarkName().equalsIgnoreCase(split[fi].substring(0, split[fi].length() - 1)
                            ))) {
                                System.out.println("标号重复：" + split[fi].substring(0, split[fi].length() - 1));
                                break;
                            } else {
                                assemblyDto.setMarkName(split[i].substring(0, split[i].length() - 1));
                            }
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
            if (null == assemblyDto.getAddress()) {
                assemblyDto.setAddress(address);
            } else {
                address = assemblyDto.getAddress();
            }
            List<String> commands = assemblyDto.getCommands();
            List<Byte> machineCodes = new ArrayList<>();
            Integer codeLength = translateCommand(commands, machineCodes);
            assemblyDto.setMachineCodes(machineCodes);
            assemblyDto.setCmdByteLength(codeLength);
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
        String token = "";
        List<Byte> dataBytes = new ArrayList<>();
        if (commands.size() > 1) {
            rowIndex = Constant.getRowIndex(commands.get(0));
            for (int i = 1; i < commands.size(); i++) {
                //字符串转token #data16(2 byte)、#data(1 byte)、/bit(1 byte)、bit(1 byte)、direct(1 byte)、rel(1 byte)
                if (commands.get(i).startsWith("#")) {
                    if (commands.get(i).endsWith("H")) {
                        if (4 == commands.get(i).length()) {
                            token = "#data";//#00H
                            byte[] bytes = hexStringToByteArray(commands.get(i));
                            for (byte b : bytes) {
                                dataBytes.add(b);
                            }
                        } else if (6 == commands.get(i).length()) {
                            token = "#data16";//#0000H
                            byte[] bytes = hexStringToByteArray(commands.get(i));
                            for (byte b : bytes) {
                                dataBytes.add(b);
                            }
                        }
                    }
                } else if (commands.get(i).startsWith("/")) {
                    token = "/bit";
                } else if (commands.get(i).endsWith("H")) {
                    if (3 == commands.get(i).length()) {
                        token = "direct";//00H
                        if (-1 == Constant.stateMap[rowIndex][Constant.getColIndex(token)]) {
                            token = "rel";//00H
                            if (rowIndex == Constant.getRowIndex("MOV")
                                    && "C".equalsIgnoreCase(commands.get(commands.size() - 1))) {
                                token = "bit";
                            }
                        }
                        if (-1 == Constant.stateMap[rowIndex][Constant.getColIndex(token)]) {
                            token = "bit";
                        }
                        byte[] bytes = hexStringToByteArray(commands.get(i));
                        for (byte b : bytes) {
                            dataBytes.add(b);
                        }
                    } else if (5 == commands.get(i).length()) {
                        token = "addr16";
                        byte[] bytes = hexStringToByteArray(commands.get(i));
                        for (byte b : bytes) {
                            dataBytes.add(b);
                        }
                    }
                } else if (commands.get(i).matches("^[-+]?\\d+$")) {
                    token = "direct";
                    if (-1 == Constant.stateMap[rowIndex][Constant.getColIndex(token)]) {
                        token = "rel";
                        if (rowIndex == Constant.getRowIndex("MOV")
                                && "C".equalsIgnoreCase(commands.get(commands.size() - 1))) {
                            token = "bit";
                        }
                    }
                    if (-1 == Constant.stateMap[rowIndex][Constant.getColIndex(token)]) {
                        token = "bit";
                    }
                    Integer value = Integer.valueOf(commands.get(i));
                    dataBytes.add((byte) (value & 0xff));
                } else if (commands.get(i).startsWith("_")) {
                    token = "rel";
                    if (-1 == Constant.stateMap[rowIndex][Constant.getColIndex(token)]) {
                        token = "addr16";
                    }
                } else {
                    token = commands.get(i);
                }
                colIndex = Constant.getColIndex(token);
                rowIndex = Constant.stateMap[rowIndex][colIndex];
            }
            if (-1 == rowIndex) {
                System.out.println("错误1：" + commands.get(0));
            } else {
                machineCodes.add((byte) ((byte) rowIndex & 0xff));
                if (!dataBytes.isEmpty()) {
                    machineCodes.addAll(dataBytes);
                }
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
            System.out.print(String.format("%02X ", machineCode));
        }
        int codeLengthIndex = Constant.getCodeLengthIndex(machineCodes.get(0) & 0xff);
        if (0 <= codeLengthIndex && codeLengthIndex < Constant.codeLengthMap.length) {
            return Constant.codeLengthMap[codeLengthIndex][1];
        } else {
            System.out.println("错误2：codeLengthIndex=" + codeLengthIndex);
        }
        return 1;
    }

    public static int charToValue(char c) {
        int value = Character.digit(c, 16);
        if (value < 0) {
            throw new IllegalArgumentException("无效的十六进制字符: '" + c + "'");
        }
        return value;
    }

    /**
     * 十六进制字符串转字节数组
     */
    public static byte[] hexStringToByteArray(String hex) {
        String s = hex.replaceAll("[#H]", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((charToValue(s.charAt(i)) << 4) + charToValue(s.charAt(i + 1)));
        }
        return data;
    }

    public static int hexStringToInt(String hex) {
        String s = hex.replaceAll("[#H]", "");
        int len = s.length();
        int data = charToValue(s.charAt(0));
        for (int i = 1; i < len; i++) {
            data = (data << 4) + charToValue(s.charAt(i));
        }
        return data;
    }

    public static void updateLankMark(List<AssemblyDto> assemblyDto) {
        for (AssemblyDto item : assemblyDto) {
            if (item.getCmdByteLength() > item.getMachineCodes().size()) {
                //需要替换的标号
                String markName = item.getCommands().get(item.getCommands().size() - 1);
                if (markName.startsWith("_")) {
                    List<AssemblyDto> target = assemblyDto.stream().filter(x -> null != x.getMarkName()
                            && x.getMarkName().equalsIgnoreCase(markName)).collect(Collectors.toList());
                    Integer address = target.get(0).getAddress();
                    if ("LJMP".equalsIgnoreCase(item.getCommands().get(0)) || "LCALL".equalsIgnoreCase(item.getCommands().get(0))) {
                        item.getMachineCodes().add(0, (byte) ((address >> 8) & 0xff));
                        item.getMachineCodes().add(1, (byte) (address & 0xff));
                    } else {
                        int rel = address - item.getAddress();
                        item.getMachineCodes().add((byte) (rel & 0xff));
                    }
                } else {
                    System.out.println("错误3：");
                }
            }
        }
    }

    public static void out2File(List<AssemblyDto> assemblyDtoList, String filePath) {
        File f = new File("");
        int address = 0;
        try (FileOutputStream fos = new FileOutputStream(f.getAbsolutePath() + filePath)) {
            AssemblyDto assemblyDto = assemblyDtoList.get(assemblyDtoList.size() - 1);
            int maxAddress = assemblyDto.getAddress() + assemblyDto.getCmdByteLength();
            int index = 0;
            for (address = 0; address < maxAddress; ) {
                if (address < assemblyDtoList.get(index).getAddress()) {
                    fos.write(0x00);
                    address++;
                } else {
                    List<Byte> bytes = assemblyDtoList.get(index).getMachineCodes();
                    for (Byte b : bytes) {
                        fos.write(b);
                    }
                    address += bytes.size();
                    index++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printHex(String filePath) throws IOException {
        File f = new File("");
        try (FileInputStream fis = new FileInputStream(f.getAbsolutePath() + filePath)) {
            int bytesRead;
            byte[] buffer = new byte[16];
            long offset = 0;
            while ((bytesRead = fis.read(buffer)) != -1) {
                // 输出偏移地址
                System.out.printf("%08X: ", offset);
                // 输出十六进制
                for (int i = 0; i < 16; i++) {
                    if (i < bytesRead) {
                        System.out.printf("%02X ", buffer[i]);
                    } else {
                        System.out.print("   "); // 补空格对齐
                    }
                    if (i == 7) {
                        System.out.print(" "); // 每8个字节加空格
                    }
                }
                System.out.print(" |");
                // 输出ASCII字符
                for (int i = 0; i < bytesRead; i++) {
                    char c = (char) buffer[i];
                    System.out.print((c >= 32 && c < 127) ? c : '.');
                }
                System.out.println("|");
                offset += bytesRead;
            }
        }
    }
}
