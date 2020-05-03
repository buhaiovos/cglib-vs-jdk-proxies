package com.buhaiov.cglib.playground;

public class PersonService implements Service {
    public String sayHello(String name) {
        System.out.println("sayHello");
        System.out.println("length of name is " + lengthOfName(name));
        return "Hello " + name;
    }

    public String lengthOfName(String name) {
        System.out.println("lengthOfName");
        return Integer.toString(name.length());
    }
}
