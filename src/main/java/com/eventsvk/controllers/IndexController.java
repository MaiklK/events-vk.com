package com.eventsvk.controllers;

import com.eventsvk.services.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping()
    public String getIndexPage() {
        return "index";
    }

}
