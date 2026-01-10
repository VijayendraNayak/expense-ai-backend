package com.example.expense_ai_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for category-wise expense summary
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDTO {
    private String category;
    private BigDecimal totalAmount;
    private long count;
    private BigDecimal averageAmount;
}

