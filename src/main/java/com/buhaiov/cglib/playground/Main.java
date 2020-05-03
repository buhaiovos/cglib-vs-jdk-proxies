package com.buhaiov.cglib.playground;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
        enhancer.setCallback(new WrapAroundInterceptor());

        PersonService proxy = (PersonService) enhancer.create();

        proxy.sayHello("John");

        System.out.println("=".repeat(20));

        var target = new PersonService();
        Service proxy2 = (Service) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class<?>[]{Service.class},
                new WrapAroundHandler(target)
        );

        proxy2.sayHello("Johnny");
    }

    static class WrapAroundInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("Opening Transaction");
            Object callResult = methodProxy.invokeSuper(o, objects);
            System.out.println("Closing Transaction");
            return callResult;
        }
    }

    static class WrapAroundHandler implements InvocationHandler {
        private final Object target;

        public WrapAroundHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("JDK Opening Transaction");
            Object callResult = method.invoke(target, args);
            System.out.println("JDK Closing Transaction");
            return callResult;
        }
    }
}
