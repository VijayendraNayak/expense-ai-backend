package com.example.expense_ai_backend.service;

import com.example.expense_ai_backend.dto.CreateExpenseRequestDTO;
import com.example.expense_ai_backend.dto.ExpenseResponseDTO;
import com.example.expense_ai_backend.dto.UpdateExpenseRequestDTO;

import java.util.List;

public interface ExpenseService {

    String createExpense(CreateExpenseRequestDTO createExpenseRequestDTO);

    List<ExpenseResponseDTO> getAllExpenses();

    ExpenseResponseDTO getExpenseById(Long id);

    List<ExpenseResponseDTO> getExpensesByUserId(String userId);

    List<ExpenseResponseDTO> getExpensesByCategory(String category);

    String updateExpense(Long id, UpdateExpenseRequestDTO updateExpenseRequestDTO);

    String deleteExpense(Long id);

    long getTotalExpenseCount();
}
