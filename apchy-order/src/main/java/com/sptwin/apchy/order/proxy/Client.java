package com.sptwin.apchy.order.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        Person realPerson = new Student();
        InvocationHandler invocationHandler = new DynamicInvocationHandler<Person>(realPerson);
        Person person = (Person) Proxy.newProxyInstance(realPerson.getClass().getClassLoader(),realPerson.getClass().getInterfaces(),invocationHandler);
        person.play();
    }
}
