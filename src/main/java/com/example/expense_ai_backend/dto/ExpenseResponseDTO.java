package com.example.expense_ai_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDateTime date;
    private String userId;
}

