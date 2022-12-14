package com.eacuamba.dev.reddit_clone_using_angular_spring.mvc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//@Controller
public class FrontendController {

    @GetMapping(path = {"/**"})
    public ModelAndView index(){
        return new ModelAndView("../static/index");
    }
}
