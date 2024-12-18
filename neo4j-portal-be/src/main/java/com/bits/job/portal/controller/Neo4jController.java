package com.bits.job.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ind")
public class Neo4jController {

    @GetMapping("/")
    public String index() {
        // Render the page with the embedded Neo4j Browser
        return "index";
    }
}
