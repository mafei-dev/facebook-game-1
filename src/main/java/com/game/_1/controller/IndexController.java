package com.game._1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
  @Author kalhara@bowsin
  @Created 1/11/2021 6:15 PM  
*/
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        System.out.println("IndexController.index");
        return "index";
    }
}
