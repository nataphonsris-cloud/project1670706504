package com.example.library;

import com.example.library.model.*;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import com.example.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@SpringBootApplication
public class LibraryManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    /**
     * Bean นี้จะทำงานตอนที่แอปพลิเคชันเริ่มทำงาน
     * เพื่อเพิ่มข้อมูลตัวอย่างลงในฐานข้อมูล (ถ้ายังไม่มีข้อมูล)
     */
    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return args -> {
            // สร้างข้อมูลเมื่อฐานข้อมูลว่างเท่านั้น
            if (bookRepository.count() == 0 && categoryRepository.count() == 0) {
                System.out.println(">>> Initializing sample data...");

                // 1. สร้าง Categories
                Category techCategory = new Category();
                techCategory.setName("Technology");

                Category fictionCategory = new Category();
                fictionCategory.setName("Fiction");
                categoryRepository.saveAll(List.of(techCategory, fictionCategory));

                // 3. สร้าง Books
                Book book1 = new Book();
                book1.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
                book1.setAuthor("Robert C. Martin");
                book1.setIsbn("978-0132350884");
                book1.setPublicationYear(Year.of(2008).getValue());
                book1.setStatus(BookStatus.AVAILABLE);
                book1.setCategory(techCategory);

                Book book2 = new Book();
                book2.setTitle("The Pragmatic Programmer: Your Journey to Mastery");
                book2.setAuthor("David Thomas, Andrew Hunt");
                book2.setIsbn("978-0201616224");
                book2.setPublicationYear(Year.of(1999).getValue());
                book2.setStatus(BookStatus.BORROWED);
                book2.setCategory(techCategory);

                Book book3 = new Book();
                book3.setTitle("The Hobbit");
                book3.setAuthor("J.R.R. Tolkien");
                book3.setIsbn("978-0345339683");
                book3.setPublicationYear(Year.of(1937).getValue());
                book3.setStatus(BookStatus.AVAILABLE);
                book3.setCategory(fictionCategory);

                bookRepository.saveAll(List.of(book1, book2, book3));
                System.out.println(">>> Sample data has been added to the H2 database.");
            }
        };
    }
}