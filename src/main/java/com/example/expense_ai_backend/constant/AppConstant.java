package com.example.expense_ai_backend.constant;

public class AppConstant {

    // API Endpoints
    public static final String EXPENSE_API = "/api/expense";
    public static final String EXPENSE_BY_ID = "/{id}";
    public static final String EXPENSE_BY_USER = "/user/{userId}";
    public static final String EXPENSE_BY_CATEGORY = "/category/{category}";
    public static final String EXPENSE_COUNT = "/count";
    public static final String EXPENSE_SUMMARY_CATEGORIES = "/summary/categories";

    // Response Messages
    public static final String EXPENSE_CREATED = "Expense created successfully with ID: ";
    public static final String EXPENSE_UPDATED = "Expense updated successfully with ID: ";
    public static final String EXPENSE_DELETED = "Expense deleted successfully with ID: ";

    // Exception Messages
    public static final String EXPENSE_NOT_FOUND = "Expense not found with id: ";
    public static final String INVALID_EXPENSE_DATA = "Invalid expense data provided";
    public static final String EXPENSE_ID_REQUIRED = "Expense ID is required";

    // Response Keys
    public static final String MESSAGE_KEY = "message";
    public static final String COUNT_KEY = "count";

    // Default Values
    public static final String DEFAULT_USER = "default-user";

    public static final String FRONTEND_URL = "http://localhost:5173";

    private AppConstant() {
        // Private constructor to prevent instantiation
    }
}
