package com.example.library.model;

public enum ReservationStatus {
    PENDING,          // รอหนังสือคืน
    READY_FOR_PICKUP, // พร้อมให้มารับ
    COMPLETED,        // รับหนังสือที่จองแล้ว
    CANCELED,         // ยกเลิกการจอง
    EXPIRED           // หมดเวลาการจอง
}