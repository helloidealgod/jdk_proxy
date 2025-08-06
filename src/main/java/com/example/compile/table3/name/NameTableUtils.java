package com.example.compile.table3.name;

import java.util.ArrayList;
import java.util.List;

public class NameTableUtils {
    public static Long offset = 0L;
    public static List<NameInfo> nameTable = new ArrayList<>();

    public static NameInfo findNameTable(String name) {
        for (NameInfo nameInfo : nameTable) {
            if (nameInfo.name.equals(name)) {
                return nameInfo;
            }
        }
        return null;
    }

    public static NameInfo generaNameTable(String name, String type) {
        NameInfo nameInfo = new NameInfo();
        nameInfo.name = name;
        nameInfo.type = type;
        nameInfo.typeWidth = TypeEnum.getWidthByType(type);
        nameInfo.address = offset;
        offset += nameInfo.typeWidth;
        nameTable.add(nameInfo);
        return nameInfo;
    }
}
