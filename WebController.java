package com.example.library.controller;

import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller สำหรับจัดการหน้าเว็บที่แสดงผลเป็น HTML (ใช้ Thymeleaf)
 */
@Controller
public class WebController {

    private final BookRepository bookRepository;

    // ใช้ Constructor Injection เพื่อให้ Spring ส่ง BookRepository เข้ามาให้โดยอัตโนมัติ
    public WebController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // คืนค่าชื่อไฟล์ HTML (index.html)
    }

    @GetMapping("/books")
    public String showBooksListPage(Model model) {
        // 1. ดึงข้อมูลหนังสือทั้งหมดจากฐานข้อมูล
        // 2. เพิ่มข้อมูลลงใน Model เพื่อส่งไปให้ Thymeleaf ใช้ในหน้า books-list.html
        model.addAttribute("books", bookRepository.findAll());
        return "books-list"; // คืนค่าชื่อไฟล์ HTML (books-list.html)
    }
}