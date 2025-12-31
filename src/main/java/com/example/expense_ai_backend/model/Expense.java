package com.example.expense_ai_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    private Long id;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDateTime date;
    private String userId;
}
