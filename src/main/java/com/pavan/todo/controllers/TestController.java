package com.pavan.todo.controllers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin
public class TestController {

    @GetMapping("/slow")
    public String timeConsumingAPI(@RequestParam(value = "delay", defaultValue = "0") Integer delay)
            throws InterruptedException {
        if (delay == 0) {
            Random random = new Random();
            delay = random.nextInt(10);
        }

        TimeUnit.SECONDS.sleep(delay);
        return "Success";
    }

}
