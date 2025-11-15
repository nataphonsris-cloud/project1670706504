# ระบบจัดการห้องสมุดออนไลน์ (Online Library Management System)

เอกสารนี้จัดทำขึ้นโดย AI Agent ตามข้อกำหนดของโปรเจกต์

## 1. ภาพรวม (Overview)

**ระบบจัดการห้องสมุดออนไลน์** เป็นเว็บแอปพลิเคชันที่พัฒนาขึ้นเพื่อปรับปรุงและอำนวยความสะดวกในการบริหารจัดการทรัพยากรหนังสือภายในห้องสมุด โปรเจกต์นี้มีวัตถุประสงค์เพื่อสร้างระบบศูนย์กลางที่ช่วยให้บรรณารักษ์สามารถจัดการข้อมูลหนังสือและสมาชิกได้อย่างมีประสิทธิภาพ และช่วยให้สมาชิกสามารถค้นหา, จอง, และยืม-คืนหนังสือผ่านช่องทางออนไลน์ได้อย่างสะดวกและรวดเร็ว

## 2. วัตถุประสงค์ (Objectives)

*   เพื่อพัฒนาระบบที่สามารถจัดการข้อมูลหนังสือ (เพิ่ม, ลบ, แก้ไข, ค้นหา) ได้อย่างเป็นระบบ
*   เพื่อพัฒนาระบบจัดการสมาชิกและการยืนยันตัวตน (Authentication & Authorization)
*   เพื่อสร้างระบบสำหรับการยืม-คืนหนังสือ พร้อมการคำนวณค่าปรับกรณีคืนล่าช้า
*   เพื่ออำนวยความสะดวกให้ผู้ใช้ (สมาชิก) สามารถค้นหาหนังสือที่ต้องการได้จากทุกที่ทุกเวลา
*   เพื่อลดขั้นตอนการทำงานที่ซ้ำซ้อนของบรรณารักษ์และเพิ่มประสิทธิภาพในการให้บริการ

## 3. คุณสมบัติหลัก (Key Features)

*   **สำหรับผู้ดูแลระบบ (Admin):**
    *   จัดการบัญชีผู้ใช้ (บรรณารักษ์, สมาชิก)
    *   ดูรายงานภาพรวมของระบบ
*   **สำหรับบรรณารักษ์ (Librarian):**
    *   จัดการข้อมูลหนังสือ (CRUD: Create, Read, Update, Delete)
    *   จัดการหมวดหมู่หนังสือ
    *   จัดการคำขอยืมและบันทึกการคืนหนังสือ
    *   จัดการการจองหนังสือ (ดูคิว, แจ้งเตือนเมื่อหนังสือพร้อมให้ยืม)
    *   ตรวจสอบประวัติการยืม-คืนทั้งหมด
*   **สำหรับสมาชิก (Member):**
    *   สมัครสมาชิกและเข้าสู่ระบบ
    *   ค้นหาและกรองหนังสือ (ตามชื่อ, ผู้แต่ง, หมวดหมู่)
    *   ดูรายละเอียดและสถานะของหนังสือ (ว่าง/ถูกยืม)
    *   ส่งคำขอยืมหนังสือ (เมื่อหนังสือสถานะ "ว่าง")
    *   จองหนังสือ (เมื่อหนังสือสถานะ "ถูกยืม")
    *   ดูประวัติการยืม-คืนของตนเอง

## 4. เทคโนโลยีที่ใช้ (Technology Stack)

*   **Backend:** Java (JDK 17+), Spring Boot 3
*   **Database:** MySQL / PostgreSQL
*   **Data Access:** Spring Data JPA, Hibernate
*   **Frontend:** Thymeleaf (Server-Side Rendering) หรือ React/Vue.js (Client-Side Rendering)
*   **Security:** Spring Security
*   **Build Tool:** Maven / Gradle

---

## 5. ขั้นตอนต่าง ๆ ของโปรเจกต์ (Project Phases)

โปรเจกต์นี้ถูกแบ่งออกเป็นขั้นตอนหลัก ๆ เพื่อให้การพัฒนามีแบบแผนและสามารถติดตามความคืบหน้าได้ง่าย ดังนี้

### **ขั้นตอนที่ 1: การวางแผนและวิเคราะห์ระบบ (Planning & Analysis)**

1.  **รวบรวมความต้องการ (Requirement Gathering):**
    *   กำหนดขอบเขตของโปรเจกต์และฟังก์ชันการทำงานที่ชัดเจน
    *   ระบุประเภทของผู้ใช้ (User Roles) ได้แก่ `ADMIN`, `LIBRARIAN`, และ `MEMBER`
    *   ลิสต์รายการฟังก์ชันที่แต่ละ Role สามารถทำได้ (Use Case)

2.  **ออกแบบฐานข้อมูล (Database Design):**
    *   สร้าง Entity-Relationship Diagram (ER Diagram) เพื่อแสดงความสัมพันธ์ระหว่างตารางต่าง ๆ
    *   **ตารางหลัก:**
        *   `users`: เก็บข้อมูลผู้ใช้ (id, username, password, role)
        *   `books`: เก็บข้อมูลหนังสือ (id, title, author, isbn, publication_year, status)
        *   `categories`: เก็บหมวดหมู่หนังสือ
        *   `borrow_records`: เก็บประวัติการยืม-คืน (id, user_id, book_id, borrow_date, due_date, return_date)
        *   `reservations`: เก็บข้อมูลการจอง (id, user_id, book_id, reservation_date, status)

3.  **ออกแบบสถาปัตยกรรม (System Architecture Design):**
    *   เลือกใช้สถาปัตยกรรมแบบ Model-View-Controller (MVC)
    *   **Model:** Java classes (Entities) ที่สื่อสารกับฐานข้อมูล
    *   **View:** หน้าเว็บที่ผู้ใช้โต้ตอบด้วย (สร้างจาก Thymeleaf หรือ Frontend Framework)
    *   **Controller:** ตัวกลางที่รับคำสั่งจาก View และเรียกใช้ Logic ใน Service Layer

4.  **ออกแบบหน้าจอผู้ใช้งาน (UI/UX Wireframing):**
    *   ร่างโครงสร้างหน้าเว็บหลัก ๆ เช่น หน้าแรก, หน้าค้นหา, หน้ารายละเอียดหนังสือ, หน้าโปรไฟล์ผู้ใช้ เพื่อให้เห็นภาพรวมของ User Flow

### **ขั้นตอนที่ 2: การพัฒนาระบบ (Development)**

1.  **ตั้งค่าโปรเจกต์ (Project Setup):**
    *   สร้างโปรเจกต์ Spring Boot ผ่าน Spring Initializr (start.spring.io)
    *   เพิ่ม Dependencies ที่จำเป็น: `Spring Web`, `Spring Data JPA`, `Spring Security`, `Thymeleaf`, `MySQL Driver`
    *   ตั้งค่าการเชื่อมต่อฐานข้อมูลในไฟล์ `application.properties`

2.  **พัฒนาส่วน Backend (Backend Development):**
    *   **สร้าง Model/Entity:** สร้าง Java class ให้ตรงกับตารางในฐานข้อมูลที่ออกแบบไว้ พร้อม Annotation ของ JPA (`@Entity`, `@Id`, `@ManyToOne`, etc.)
    *   **สร้าง Repository Layer:** สร้าง Interface ที่ extends `JpaRepository` เพื่อจัดการกับการเข้าถึงข้อมูล (Query) โดยไม่ต้องเขียน SQL เอง
    *   **สร้าง Service Layer:** สร้าง Class ที่รวบรวม Business Logic ของระบบ เช่น `borrowBook()`, `returnBook()`, `calculateFine()`
    *   **สร้าง Controller Layer:** สร้าง RESTful APIs หรือ Controller ปกติเพื่อรับ HTTP Request จากผู้ใช้, เรียกใช้ Service และส่งผลลัพธ์กลับไปให้ View

3.  **พัฒนาส่วน Frontend (Frontend Development):**
    *   สร้างไฟล์ HTML ด้วย Thymeleaf เพื่อแสดงผลข้อมูลที่ได้จาก Backend
    *   ออกแบบหน้าเว็บด้วย CSS (อาจใช้ Framework เช่น Bootstrap เพื่อความรวดเร็ว)
    *   เพิ่ม JavaScript เพื่อทำให้หน้าเว็บมีการโต้ตอบกับผู้ใช้แบบไดนามิก (เช่น การตรวจสอบข้อมูลในฟอร์ม)

4.  **การจัดการความปลอดภัย (Security Implementation):**
    *   ติดตั้งและตั้งค่า Spring Security
    *   กำหนดสิทธิ์การเข้าถึงหน้าเว็บและ API ตาม Role ของผู้ใช้ (เช่น เฉพาะ `LIBRARIAN` ที่สามารถเข้าหน้าจัดการหนังสือได้)
    *   สร้างหน้า Login และจัดการ Session ของผู้ใช้

### **ขั้นตอนที่ 3: การทดสอบระบบ (Testing)**

1.  **Unit Testing:**
    *   ใช้ JUnit และ Mockito เพื่อทดสอบ Logic ใน Service Layer แยกเป็นส่วน ๆ เพื่อให้มั่นใจว่าแต่ละฟังก์ชันทำงานถูกต้อง

2.  **Integration Testing:**
    *   ทดสอบการทำงานร่วมกันของแต่ละ Layer (Controller -> Service -> Repository) เพื่อให้แน่ใจว่าการเชื่อมต่อเป็นไปอย่างราบรื่น

3.  **User Acceptance Testing (UAT):**
    *   ทดสอบระบบในมุมมองของผู้ใช้จริง (Admin, Librarian, Member) เพื่อตรวจสอบว่าระบบทำงานตรงตามความต้องการและใช้งานง่ายหรือไม่

### **ขั้นตอนที่ 4: การติดตั้งและปรับใช้ (Deployment)**

1.  **เตรียม Production Environment:**
    *   ตั้งค่า Server และ Database สำหรับการใช้งานจริง
2.  **Build โปรเจกต์:**
    *   ใช้ Maven หรือ Gradle เพื่อแพ็กโปรเจกต์เป็นไฟล์ `.jar`
3.  **Deploy:**
    *   นำไฟล์ `.jar` ที่ได้ไปรันบน Server เพื่อเปิดให้บริการระบบออนไลน์

### **ขั้นตอนที่ 5: การบำรุงรักษา (Maintenance)**

1.  **ตรวจสอบและแก้ไขข้อผิดพลาด (Bug Fixing):**
    *   คอยตรวจสอบ Log และรับ Feedback จากผู้ใช้เพื่อแก้ไขข้อผิดพลาดที่อาจเกิดขึ้นหลังการใช้งานจริง
2.  **อัปเดตและพัฒนาเพิ่มเติม (Updates & Enhancements):**
    *   วางแผนเพิ่มฟังก์ชันใหม่ ๆ หรือปรับปรุงประสิทธิภาพของระบบในเวอร์ชันถัดไป

## 6. วิธีการติดตั้งและรันโปรเจกต์ (Installation & Setup)

1.  **Clone a repository:**
    ```bash
    git clone https://github.com/your-username/online-library-system.git
    ```
2.  **ตั้งค่าฐานข้อมูล:**
    *   สร้าง Database schema ใน MySQL
    *   แก้ไขข้อมูลการเชื่อมต่อในไฟล์ `src/main/resources/application.properties`
3.  **รันโปรเจกต์:**
    *   ใช้คำสั่ง Maven:
      ```bash
      mvn spring-boot:run
      ```
4.  **เข้าใช้งาน:**
    *   เปิดเว็บเบราว์เซอร์แล้วไปที่ `http://localhost:8080`
