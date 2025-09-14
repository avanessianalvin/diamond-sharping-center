package com.vozni.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is for 'Single Page' applications so that for example to get /about it will not return not found!
 * All non api requests will be redirected to index.html
 */
@Controller
public class SpaController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        return "forward:/index.html";
    }
}

