package com.sptwin.apchy.web;

import java.util.HashMap;
import java.util.Hashtable;

class Test2{
    public static final int n = 2;
    static{
        System.out.println("test");
    }
}
public class Test {
    public static void main(String[] args) {
        Hashtable hashtable = new Hashtable();
        hashtable.put("","");
        HashMap hashMap = new HashMap();
        hashMap.put("","");
        System.out.println(Test2.n);
    }
}
