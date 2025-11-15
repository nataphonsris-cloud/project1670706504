package com.example.library.controller;

import com.example.library.dto.BorrowRequestDto;
import com.example.library.model.BorrowRecord;
import com.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(@PathVariable Long bookId, @RequestBody BorrowRequestDto borrowRequest) {
        BorrowRecord record = bookService.borrowBook(bookId, borrowRequest.getUserId());
        return ResponseEntity.ok(record);
    }

    @PostMapping("/borrow-records/{recordId}/return")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long recordId) {
        BorrowRecord record = bookService.returnBook(recordId);
        return ResponseEntity.ok(record);
    }
}