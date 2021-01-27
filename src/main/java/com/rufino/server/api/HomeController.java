package com.rufino.server.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public Map<String, String> Home() {
        HashMap<String, String> res = new HashMap<>();
        res.put("message", "Hello from home");

        return res;
    }

}
