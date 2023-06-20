package com.example.compile.tabledrive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;

public class MainTableDrive {
    public static PushbackReader pr;
    public static int[][] stateMap = initStateMap();

    public static int getIndex(char c) {
        int index = c;
        if (47 >= index && 32 <= index) {
            index -= 32;
        } else if (57 >= index && 48 <= index) {
            index = 16;
        } else if (58 <= index && 64 >= index) {
            index -= 41;
        } else if (90 >= index && 65 <= index) {
            index = 24;
        } else if (96 >= index && 91 <= index) {
            index -= 66;
        } else if (122 >= index && 97 <= index) {
            index = 31;
        } else if (126 >= index && 123 <= index) {
            index -= 91;
        } else {
            index = 0;
        }
        return index;
    }

    public static int[][] initStateMap() {
        int[][] stateMap = {{-3, 4, 1, 3, -2, 16, 18, 2, 0, 0, 12, 6, 0, 9, -2, 14, 3, 0, 0, 26, 24, 30, 0, 0, 3, 0, -2, 0, -2, 3, -2, 3, 0, 21, 0, 0},
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {-1, -2, -2, -2, -2, -1, -1, -2, -1, -1, -1, -1, -1, -1, 3, -1, 3, -1, -1, -1, -1, -1, -1, -2, 3, -1, -2, -1, -2, 3, -2, 3, -1, -1, -1, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 5, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -1, -2, 7, -2, -2, -2, -2, -1, -2, -2, -2, 8, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, 10, -2, -2, -1, -2, -2, -2, 11, 34, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -1, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -2, -1, -2, -2, -2, -1, -2, -2, -2, 13, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 15, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 17, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, 19, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 20, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -1, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 23, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, 22, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 25, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -1, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, 27, 28, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 29, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 32, 31, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2}};
        return stateMap;
    }

    public static Stack stack = new Stack();

    public static void main(String[] args) {
        try {
            File f = new File("");
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/src/main/resources/cc/main_table_drive.c"));
            pr = new PushbackReader(reader);
            translationUnit();
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getToken() throws IOException {
        int read;
        char c;
        StringBuilder token = new StringBuilder("");
        int status = 0;
        int index;
        while ((read = pr.read()) != -1) {
            c = (char) read;
            index = getIndex(c);
            status = stateMap[status][index];
            if (0 == status) {
                token.append(c);
                return token.toString();
            } else if (-1 == status) {
                pr.unread(c);
                return token.toString();
            } else if (-2 == status) {
                System.out.println("error:" + token.toString() + " " + c);
                break;
            } else if (-3 == status) {
                status = 0;
            } else {
                token.append(c);
            }
        }
        return null;
    }

    /**
     * <translationUnit>:={<externalDeclaration>}<TK_EOF>
     *
     * @throws IOException
     */
    public static void translationUnit() throws IOException {
        String token;
        Integer status = 0;
        int index = -1;
        SegmentStatus[][] segmentStatus = new SegmentStatus[11][7];
        String s = "[[{\"status\":1,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":2,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":1,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":3,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":4,\"action\":\"replaceT\"},{\"status\":5,\"action\":\"replaceF\"},{\"status\":-1,\"action\":\"acc\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":-2,\"action\":\"\"},{\"status\":2,\"action\":\"pop2f\"},{\"status\":-2,\"action\":\"\"},{\"status\":6,\"action\":\"replaceT\"},{\"status\":7,\"action\":\"replaceF\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":1,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":8,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":1,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":2,\"action\":\"MulDiv\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":1,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":9,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":1,\"action\":\"push\"},{\"status\":-2,\"action\":\"\"},{\"status\":3,\"action\":\"MulDiv\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":4,\"action\":\"AddSub\"},{\"status\":5,\"action\":\"replaceF\"},{\"status\":10,\"action\":\"AddSubAgain\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":-2,\"action\":\"\"},{\"status\":10,\"action\":\"AddPop(\"},{\"status\":-2,\"action\":\"\"},{\"status\":6,\"action\":\"AddSub\"},{\"status\":5,\"action\":\"replaceF\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"}],[{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":-2,\"action\":\"\"},{\"status\":4,\"action\":\"push\"},{\"status\":5,\"action\":\"replaceF\"},{\"status\":-1,\"action\":\"acc\"},{\"status\":-2,\"action\":\"\"}]]";
        JSONArray objects = JSON.parseArray(s);
        for (int i = 0; i < objects.size(); i++) {
            JSONArray jsonArray = objects.getJSONArray(i);
            for (int j = 0; j < jsonArray.size(); j++) {
                String s1 = JSON.toJSONString(jsonArray.get(j));
                segmentStatus[i][j] = JSON.parseObject(s1, SegmentStatus.class);
            }
        }


        while ((token = getToken()) != null) {

            if ("end__".equals(token)) {
                break;
            }
            String tranceToken = token;
            int colIndex = 0;
            if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                tranceToken = "f";
                colIndex = 2;
            } else if (";".equals(token)) {
                colIndex = 5;
            } else if ("+".equals(token) || "-".equals(token)) {
                colIndex = 3;
            } else if ("*".equals(token) || "/".equals(token)) {
                colIndex = 4;
            } else if ("(".equals(token)) {
                colIndex = 0;
            } else if (")".equals(token)) {
                colIndex = 1;
            } else {
                colIndex = 6;
            }
            boolean doAgain;
            do {
                doAgain = false;
                SegmentStatus segment = segmentStatus[status][colIndex];
                Integer status1 = segment.getStatus();
                System.out.println("符号：" + tranceToken + " status=" + status + " colIndex=" + colIndex + " NextStatus=" + status1);
                stack.pushStatus(status);
                if (-2 == status1) {
                    System.out.println("Error");
                } else if (-1 == status1) {

                    //接收
                    status1 = 0;
                    System.out.println("ACC");
                    stack.empty();
                } else {
                    String action = segment.getAction();
                    if ("push".equals(action)) {
                        stack.push(tranceToken);
                    } else if ("replaceT".equals(action)) {
                        stack.pop();
                        stack.push("T");
                        stack.push(tranceToken);
                    } else if ("replaceF".equals(action)) {
                        stack.pop();
                        stack.push("F");
                        stack.push(tranceToken);
                    } else if ("MulDiv".equals(action)) {
                        System.out.println("MulDiv");
                        stack.pop();
                        stack.pop();
                        //do MulDiv
                        stack.push(tranceToken);
                        stack.popStatus();
                        status1 = stack.popStatus();
                        //stack.popStatus();
                    } else if ("AddSub".equals(action)) {
                        System.out.println("AddSub");
                        stack.pop();
                        stack.pop();
                        //do AddSub
                        stack.push(tranceToken);
                        //stack.popStatus();
                    } else if ("AddSubAgain".equals(action)) {
                        System.out.println("AddSubAgain");
                        stack.pop();
                        stack.pop();
                        stack.pop();
                        stack.popStatus();
                        stack.popStatus();
                        stack.popStatus();
                        //do AddSub
                        stack.push("T");
                        //stack.popStatus();
                        doAgain = true;
                    }
                }

                stack.printToken();
                stack.printStatus();
                status = status1;
            } while (doAgain);
        }
    }
}
