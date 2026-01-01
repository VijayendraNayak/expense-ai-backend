package com.example.expense_ai_backend.controller;

import com.example.expense_ai_backend.constant.AppConstant;
import com.example.expense_ai_backend.dto.CreateExpenseRequestDTO;
import com.example.expense_ai_backend.dto.ExpenseResponseDTO;
import com.example.expense_ai_backend.dto.UpdateExpenseRequestDTO;
import com.example.expense_ai_backend.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping(AppConstant.EXPENSE_API)
public class ExpenseController {

    ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    /**
     * Create a new expense
     * POST /api/expense
     */
    @PostMapping
    public ResponseEntity<Map<String,String>> createExpense(
            @RequestBody @Valid @NotNull CreateExpenseRequestDTO createExpenseRequestDTO){

        String message = expenseService.createExpense(createExpenseRequestDTO);
        Map<String,String> responseBody = Collections.singletonMap("message", message);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    /**
     * Get all expenses
     * GET /api/expense
     */
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses(){
        List<ExpenseResponseDTO> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    /**
     * Get expense by ID
     * GET /api/expense/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable @NotNull Long id){
        ExpenseResponseDTO expense = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    /**
     * Get expenses by user ID
     * GET /api/expense/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByUserId(
            @PathVariable @NotNull String userId){
        List<ExpenseResponseDTO> expenses = expenseService.getExpensesByUserId(userId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    /**
     * Get expenses by category
     * GET /api/expense/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByCategory(
            @PathVariable @NotNull String category){
        List<ExpenseResponseDTO> expenses = expenseService.getExpensesByCategory(category);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    /**
     * Update an expense
     * PUT /api/expense/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateExpense(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid @NotNull UpdateExpenseRequestDTO updateExpenseRequestDTO){

        String message = expenseService.updateExpense(id, updateExpenseRequestDTO);
        Map<String,String> responseBody = Collections.singletonMap("message", message);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * Delete an expense
     * DELETE /api/expense/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteExpense(@PathVariable @NotNull Long id){
        String message = expenseService.deleteExpense(id);
        Map<String,String> responseBody = Collections.singletonMap("message", message);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * Get total expense count
     * GET /api/expense/count
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String,Long>> getTotalExpenseCount(){
        long count = expenseService.getTotalExpenseCount();
        Map<String,Long> responseBody = Collections.singletonMap("count", count);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
