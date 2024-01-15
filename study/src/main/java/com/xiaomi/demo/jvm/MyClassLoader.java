package com.xiaomi.demo.jvm;

/**
 * @author l
 */
public class MyClassLoader extends ClassLoader {
    private String path;

    public MyClassLoader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

//        return defineClass();
        return null;
    }

    private byte[] getBytes(String name) {
        name = name.replaceAll("\\.", "\\\\");
        String classPath = path + name + ".class";
        return null;
    }
}
