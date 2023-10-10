package com.thesisreading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class Hello {
    @RequestMapping("/login")
    public String Hello() {
        return "login";
    }

}
