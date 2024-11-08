package com.xiaomi.demo.java.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/26
 */
@Slf4j
public class TestClassLoader {
    /**
     * 1 加载是指查找字节流，并且据此创建类的过程
     * 2 验证,在于确保被加载类能够满足 Java 虚拟机的约束条件
     * 3 准备,则是为被加载类的静态字段分配内存
     * 4 解析阶段的目的，正是将这些符号引用解析成为实际引用
     * 5 初始化，则是为标记为常量值的字段赋值，以及执行 < clinit > 方法的过程。类的初始化仅会被执行一次，这个特性被用来实现单例的延迟初始化
     */
    @Test
    public void test() throws ClassNotFoundException {
        MyClassLoader loader = new MyClassLoader("");
        Class<?> aClass = loader.findClass("com.xiaomi.demo.java.MyString");
        Method[] methods = aClass.getMethods();
        System.out.println(Arrays.toString(methods));
    }

    class MyClassLoader extends ClassLoader {
        private java.lang.String classPath;

        public MyClassLoader(java.lang.String classPath) {
            this.classPath = classPath;
        }


        @Override
        protected Class<?> findClass(java.lang.String name) throws ClassNotFoundException {
            // Check if the class has been loaded already
            Class<?> cls = findLoadedClass(name);
            if (cls != null) {
                return cls;
            }

            try {
                // Delegate to parent class loader first
                cls = getParent().loadClass(name);
                return cls;  // Use the class loaded by the parent
            } catch (ClassNotFoundException e) {
                // Parent didn't find the class, we attempt to load it ourselves
                log.info("Class {} not found in parent class loader, attempting to load it ourselves", name);
            }

            // Convert the class name to a path
            java.lang.String filePath = classPath + File.separator + name.replace('.', File.separatorChar) + ".class";
            try (FileInputStream fis = new FileInputStream(filePath)) {
                byte[] classData = new byte[fis.available()];
                fis.read(classData);
                return defineClass(name, classData, 0, classData.length);
            } catch (IOException e) {
                throw new ClassNotFoundException("Could not load class " + name, e);
            }
        }
    }
}
