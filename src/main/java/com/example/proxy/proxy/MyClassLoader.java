package com.example.proxy.proxy;

import java.io.*;

public class MyClassLoader extends ClassLoader {
    private File dir;

    public MyClassLoader(String path) {
        dir = new File(path);
    }

    /**
     * 加载对应的字节码文件
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (null != dir) {
            File classFile = new File(dir, name+".class");
            try {
                FileInputStream fileInputStream = new FileInputStream(classFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while (-1 != (len = fileInputStream.read(buffer))) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                //把字节流里的内容加载到内存中
                return defineClass( "com.example.proxy.proxy.$Proxy0", byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }

}
