package com.xiaomi.demo.java.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: liuchiyun
 * @Date: 2023/11/9
 */
public class JavassistTest {
    public static void main(String[] args) {
        new A().sayHello();
    }


    @SneakyThrows
    @Test
    public void test1() throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass aClass = classPool.get("com.xiaomi.demo.java.agent.A");
        CtMethod method = CtMethod.make("public void say(){ System.out.println(\"hello\");}", aClass);
        aClass.addMethod(method);
        aClass.writeFile();
        Method[] methods = aClass.toClass().getMethods();
        System.out.println(Arrays.toString(methods));
        System.out.println(classPool);

    }
}
