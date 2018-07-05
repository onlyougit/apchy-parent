package com.sptwin.apchy.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Value("${product.image.path}")
    private String productImage;

    @GetMapping("/getPath")
    public String getPath(){
        return productImage;
    }
}
