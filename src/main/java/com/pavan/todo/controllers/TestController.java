package com.pavan.todo.controllers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api/test")
@CrossOrigin
public class TestController {

    @Timed(value = "slow.request", description = "Slow API Response Time", histogram = true, percentiles = { 0.5, 0.95,
            0.99 })
    @GetMapping("/slow")
    public String slowAPI(@RequestParam(value = "delay", defaultValue = "0") Integer delay)
            throws InterruptedException {
        if (delay == 0) {
            Random random = new Random();
            delay = random.nextInt(10);
        }

        TimeUnit.SECONDS.sleep(delay);
        return "Success";
    }

}
