package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // เมื่อผู้ใช้เข้าหน้าแรก (http://localhost:8080)
    public String home() {
        return "index"; // ให้แสดงผลไฟล์ index.html
    }
}