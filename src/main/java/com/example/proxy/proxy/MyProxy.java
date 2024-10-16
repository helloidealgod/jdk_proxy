package com.example.proxy.proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyProxy {
    private static String rt = "\r\n";

    public static Object newProxyInstance(ClassLoader classLoader, Class<?> intf, MyInvocationHandler h) {
        File file = new File("");
        //win
        //String path = file.getAbsolutePath()+"\\src\\main\\java\\com\\example\\proxy\\proxy\\$Proxy0.java";
        //mac
        String path = file.getAbsolutePath()+"/src/main/java/com/example/proxy/proxy/$Proxy0.java";
        //1、用字符串的方式拼凑出内存里的代理类
        String proxyClass = get$Proxy0(intf);
        //2、将字符串输出到一个.Java文件里
        outputFile(proxyClass, path);
        //3、编译.java文件
        compilerJava(path);
        //4、把编译后的字节码文件加载到内存
        return loadClassToJvm(h);
    }

    private static Object loadClassToJvm(MyInvocationHandler h) {
        File file = new File("");
        //win
        //String path = file.getAbsolutePath()+"\\src\\main\\java\\com\\example\\proxy\\proxy";
        //mac
        String path = file.getAbsolutePath()+"/src/main/java/com/example/proxy/proxy";
        MyClassLoader myClassLoader = new MyClassLoader(path);
        try {
            Class<?> $Proxy0 = myClassLoader.findClass("$Proxy0");
            Constructor<?> constructor = $Proxy0.getConstructor(MyInvocationHandler.class);
            Object o = constructor.newInstance(h);
            return o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void compilerJava(String fileName) {
        //拿到Java编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        //拿到标准的文件管理器
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        task.call();
    }

    /**
     * @description: 将一个类的字符串输出到一个.JAVA文件中
     * @param: proxyClass, path
     * @return: void
     * @auther: xiankun.jiang
     * @date: 2019/4/28 9:23
     */
    private static void outputFile(String proxyClass, String path) {
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(proxyClass);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 用字符串的方式拼凑出内存里的代理类
     * @param: inft
     * @return: String
     * @auther: xiankun.jiang
     * @date: 2019/4/28 9:22
     */
    private static String get$Proxy0(Class<?> intf) {
        Method[] methods = intf.getMethods();
        String proxyClass = "package com.example.proxy.proxy;" + rt
                + "import java.lang.reflect.Method;" + rt
                + "public class $Proxy0 implements " + intf.getName() + "{"
                + rt + "MyInvocationHandler h;" + rt
                + "public $Proxy0(MyInvocationHandler h){" + rt
                + "this.h = h;" + rt + "}" + getMethodString(methods, intf)
                + rt + "}";
        return proxyClass;
    }

    /**
     * @description: 获取方法的字符串
     * @param: methods, intf
     * @return: String
     * @auther: xiankun.jiang
     * @date: 2019/4/28 9:22
     */
    public static String getMethodString(Method[] methods, Class<?> intf) {
        String proxyMe = "";
        for (Method method : methods) {
            proxyMe += "public String " + method.getName()
                    + "() throws Throwable {" + rt + "Method md = "
                    + intf.getName() + ".class.getMethod(\"" + method.getName()
                    + "\",new Class[]{});" + rt
                    + "return (String)this.h.invoke(this,md,null);" + rt + "}" + rt;
        }
        return proxyMe;
    }
}
