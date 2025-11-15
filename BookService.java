package com.example.library.service;

import com.example.library.exception.LibraryException;
import com.example.library.model.Book;
import com.example.library.model.BookStatus;
import com.example.library.model.BorrowRecord;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRecordRepository;
import com.example.library.repository.ReservationRepository;
import com.example.library.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final ReservationRepository reservationRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository,
                       BorrowRecordRepository borrowRecordRepository, ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException("ไม่พบหนังสือรหัส: " + bookId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new LibraryException("ไม่พบผู้ใช้รหัส: " + userId));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new LibraryException("หนังสือ '" + book.getTitle() + "' ไม่ว่างให้ยืมในขณะนี้");
        }

        book.setStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUser(user);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plus(14, ChronoUnit.DAYS)); // กำหนดส่งคืนใน 14 วัน

        return borrowRecordRepository.save(record);
    }

    @Transactional
    public BorrowRecord returnBook(Long borrowRecordId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new LibraryException("ไม่พบรายการยืมรหัส: " + borrowRecordId));

        if (record.getReturnDate() != null) {
            throw new LibraryException("รายการยืมนี้ถูกคืนไปแล้ว");
        }

        record.setReturnDate(LocalDate.now());

        Book book = record.getBook();
        // TODO: ตรวจสอบว่ามีคนจองหนังสือเล่มนี้หรือไม่ ถ้ามีให้เปลี่ยนสถานะเป็น RESERVED
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}