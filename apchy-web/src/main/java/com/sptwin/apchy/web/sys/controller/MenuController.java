package com.sptwin.apchy.web.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.sptwin.apchy.web.sys.pojo.MenuLeft;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {

    @RequestMapping("/getLeftMenu")
    @ResponseBody
    public List<MenuLeft> getLeftMenu(){
        String jsonString = readToString("F:/menu.txt");
        List<MenuLeft> list = JSONArray.parseArray(jsonString,MenuLeft.class);
        return list;
    }
    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
