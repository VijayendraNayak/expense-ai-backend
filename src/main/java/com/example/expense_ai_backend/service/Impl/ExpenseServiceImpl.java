package com.example.expense_ai_backend.service.Impl;

import com.example.expense_ai_backend.dto.CreateExpenseRequestDTO;
import com.example.expense_ai_backend.dto.ExpenseResponseDTO;
import com.example.expense_ai_backend.dto.UpdateExpenseRequestDTO;
import com.example.expense_ai_backend.model.Expense;
import com.example.expense_ai_backend.repository.ExpenseRepository;
import com.example.expense_ai_backend.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public String createExpense(CreateExpenseRequestDTO createExpenseRequestDTO) {
        Expense expense = new Expense();
        expense.setDescription(createExpenseRequestDTO.getDescription());
        expense.setAmount(createExpenseRequestDTO.getAmount());
        expense.setCategory(createExpenseRequestDTO.getCategory());
        expense.setDate(LocalDateTime.now());
        expense.setUserId("default-user"); // You can modify this to get from context/auth

        Expense savedExpense = expenseRepository.save(expense);
        return "Expense created successfully with ID: " + savedExpense.getId();
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        return convertToDTO(expense);
    }

    @Override
    public List<ExpenseResponseDTO> getExpensesByUserId(String userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseResponseDTO> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String updateExpense(Long id, UpdateExpenseRequestDTO updateExpenseRequestDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        expense.setDescription(updateExpenseRequestDTO.getDescription());
        expense.setAmount(updateExpenseRequestDTO.getAmount());
        expense.setCategory(updateExpenseRequestDTO.getCategory());

        expenseRepository.save(expense);
        return "Expense updated successfully with ID: " + id;
    }

    @Override
    public String deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
        return "Expense deleted successfully with ID: " + id;
    }

    @Override
    public long getTotalExpenseCount() {
        return expenseRepository.count();
    }

    /**
     * Helper method to convert Expense entity to ExpenseResponseDTO
     */
    private ExpenseResponseDTO convertToDTO(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDate(),
                expense.getUserId()
        );
    }
}

