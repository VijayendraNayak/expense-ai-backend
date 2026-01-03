package com.example.expense_ai_backend.repository;

import com.example.expense_ai_backend.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ExpenseRepository {

    // Thread-safe in-memory storage using ConcurrentHashMap
    private final Map<Long, Expense> expenseStore = new ConcurrentHashMap<>();

    // Auto-increment ID generator
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            expense.setId(idGenerator.getAndIncrement());
        }
        expenseStore.put(expense.getId(), expense);
        return expense;
    }

    public Optional<Expense> findById(Long id) {
        return Optional.ofNullable(expenseStore.get(id));
    }

    public List<Expense> findAll() {
        return new ArrayList<>(expenseStore.values());
    }

    public List<Expense> findByUserId(String userId) {
        return expenseStore.values().stream()
                .filter(expense -> userId.equals(expense.getUserId()))
                .collect(Collectors.toList());
    }

    public List<Expense> findByCategory(String category) {
        return expenseStore.values().stream()
                .filter(expense -> category.equals(expense.getCategory()))
                .collect(Collectors.toList());
    }

    public boolean deleteById(Long id) {
        return expenseStore.remove(id) != null;
    }

    public void deleteAll() {
        expenseStore.clear();
    }

    public long count() {
        return expenseStore.size();
    }

    public boolean existsById(Long id) {
        return expenseStore.containsKey(id);
    }
}

