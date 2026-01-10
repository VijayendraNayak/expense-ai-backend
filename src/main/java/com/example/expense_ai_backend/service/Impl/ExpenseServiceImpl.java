package com.example.expense_ai_backend.service.Impl;

import com.example.expense_ai_backend.constant.AppConstant;
import com.example.expense_ai_backend.dto.CategorySummaryDTO;
import com.example.expense_ai_backend.dto.CreateExpenseRequestDTO;
import com.example.expense_ai_backend.dto.ExpenseResponseDTO;
import com.example.expense_ai_backend.dto.UpdateExpenseRequestDTO;
import com.example.expense_ai_backend.exception.ResourceNotFoundException;
import com.example.expense_ai_backend.model.Expense;
import com.example.expense_ai_backend.repository.ExpenseRepository;
import com.example.expense_ai_backend.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
        expense.setUserId(AppConstant.DEFAULT_USER);

        Expense savedExpense = expenseRepository.save(expense);
        return AppConstant.EXPENSE_CREATED + savedExpense.getId();
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
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXPENSE_NOT_FOUND + id));
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
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXPENSE_NOT_FOUND + id));

        expense.setDescription(updateExpenseRequestDTO.getDescription());
        expense.setAmount(updateExpenseRequestDTO.getAmount());
        expense.setCategory(updateExpenseRequestDTO.getCategory());

        expenseRepository.save(expense);
        return AppConstant.EXPENSE_UPDATED + id;
    }

    @Override
    public String deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException(AppConstant.EXPENSE_NOT_FOUND + id);
        }
        expenseRepository.deleteById(id);
        return AppConstant.EXPENSE_DELETED + id;
    }

    @Override
    public long getTotalExpenseCount() {
        return expenseRepository.count();
    }

    @Override
    public List<CategorySummaryDTO> getCategorySummary() {
        Map<String, List<Expense>> groupedByCategory = expenseRepository.findAll().stream()
                .collect(Collectors.groupingBy(Expense::getCategory));

        return groupedByCategory.entrySet().stream()
                .map(entry -> {
                    String category = entry.getKey();
                    List<Expense> expenses = entry.getValue();

                    BigDecimal totalAmount = expenses.stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    long count = expenses.size();
                    BigDecimal averageAmount = count > 0 ?
                            totalAmount.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP) :
                            BigDecimal.ZERO;

                    return new CategorySummaryDTO(category, totalAmount, count, averageAmount);
                })
                .collect(Collectors.toList());
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
