# Exception Handling Documentation

## Overview
The application uses a **global exception handling** mechanism using Spring's `@RestControllerAdvice` to provide consistent error responses across all endpoints.

## Exception Hierarchy

### Custom Exceptions

1. **ResourceNotFoundException** - Thrown when a requested resource is not found
   - HTTP Status: `404 NOT FOUND`
   - Use Case: Expense not found by ID

2. **BadRequestException** - Thrown when the request is invalid
   - HTTP Status: `400 BAD REQUEST`
   - Use Case: Invalid input data

## Error Response Format

All errors return a standardized JSON response:

```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Expense not found with id: 123",
  "path": "/api/expense/123"
}
```

### ErrorResponseDTO Structure
```java
{
  timestamp: LocalDateTime,  // When the error occurred
  status: int,               // HTTP status code
  error: String,             // HTTP status phrase
  message: String,           // Detailed error message
  path: String               // Request URI that caused the error
}
```

## Exception Handling in GlobalExceptionHandler

### 1. ResourceNotFoundException Handler
**Trigger:** When `ResourceNotFoundException` is thrown  
**Status:** 404 NOT FOUND  
**Example:**
```java
throw new ResourceNotFoundException(AppConstant.EXPENSE_NOT_FOUND + id);
```

**Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Expense not found with id: 123",
  "path": "/api/expense/123"
}
```

---

### 2. BadRequestException Handler
**Trigger:** When `BadRequestException` is thrown  
**Status:** 400 BAD REQUEST  
**Example:**
```java
throw new BadRequestException(AppConstant.INVALID_EXPENSE_DATA);
```

**Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid expense data provided",
  "path": "/api/expense"
}
```

---

### 3. Validation Exception Handler (MethodArgumentNotValidException)
**Trigger:** When `@Valid` annotation fails on request body  
**Status:** 400 BAD REQUEST  
**Triggered By:** Invalid field values in DTOs

**Example Request:**
```json
{
  "description": "",
  "amount": -10.50,
  "category": ""
}
```

**Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: description - Description is required; amount - Amount must be greater than 0; category - Category is required; ",
  "path": "/api/expense"
}
```

---

### 4. Constraint Violation Exception Handler
**Trigger:** When `@NotNull`, `@Valid` constraints are violated  
**Status:** 400 BAD REQUEST  
**Example:** Missing required path variable

**Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Constraint violation: id must not be null",
  "path": "/api/expense/null"
}
```

---

### 5. Global Exception Handler
**Trigger:** All other unhandled exceptions  
**Status:** 500 INTERNAL SERVER ERROR  
**Example:** Unexpected runtime errors

**Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred: Division by zero",
  "path": "/api/expense"
}
```

---

## Validation Annotations in DTOs

### CreateExpenseRequestDTO & UpdateExpenseRequestDTO

```java
@NotBlank(message = "Description is required")
private String description;

@NotNull(message = "Amount is required")
@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
private BigDecimal amount;

@NotBlank(message = "Category is required")
private String category;
```

### Validation Rules:
- **description**: Cannot be null, empty, or whitespace
- **amount**: Cannot be null, must be >= 0.01
- **category**: Cannot be null, empty, or whitespace

---

## Usage Examples

### Service Layer - Throwing Exceptions
```java
@Override
public ExpenseResponseDTO getExpenseById(Long id) {
    Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                AppConstant.EXPENSE_NOT_FOUND + id
            ));
    return convertToDTO(expense);
}

@Override
public String deleteExpense(Long id) {
    if (!expenseRepository.existsById(id)) {
        throw new ResourceNotFoundException(AppConstant.EXPENSE_NOT_FOUND + id);
    }
    expenseRepository.deleteById(id);
    return AppConstant.EXPENSE_DELETED + id;
}
```

---

## Testing Exception Handling

### 1. Test 404 - Resource Not Found
```bash
curl -X GET http://localhost:8080/api/expense/999
```
**Expected Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Expense not found with id: 999",
  "path": "/api/expense/999"
}
```

---

### 2. Test 400 - Validation Error
```bash
curl -X POST http://localhost:8080/api/expense \
  -H "Content-Type: application/json" \
  -d '{
    "description": "",
    "amount": null,
    "category": ""
  }'
```
**Expected Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: description - Description is required; amount - Amount is required; category - Category is required; ",
  "path": "/api/expense"
}
```

---

### 3. Test 400 - Invalid Amount
```bash
curl -X POST http://localhost:8080/api/expense \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Test",
    "amount": -5.00,
    "category": "Food"
  }'
```
**Expected Response:**
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: amount - Amount must be greater than 0; ",
  "path": "/api/expense"
}
```

---

## Benefits of This Approach

✅ **Consistent Error Format** - All errors follow the same structure  
✅ **Better Client Experience** - Clear error messages with proper HTTP status codes  
✅ **Centralized Handling** - All exception handling in one place  
✅ **Validation Feedback** - Detailed validation messages for each field  
✅ **Debugging Friendly** - Includes timestamp and request path  
✅ **Maintainable** - Easy to add new exception types  
✅ **Production Ready** - Proper error responses for REST APIs  

---

## Adding New Custom Exceptions

To add a new exception type:

1. **Create Exception Class:**
```java
package com.example.expense_ai_backend.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
```

2. **Add Handler in GlobalExceptionHandler:**
```java
@ExceptionHandler(DuplicateResourceException.class)
public ResponseEntity<ErrorResponseDTO> handleDuplicateResourceException(
        DuplicateResourceException ex, HttpServletRequest request) {
    
    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );
    
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
}
```

3. **Use in Service:**
```java
if (expenseRepository.existsByDescription(description)) {
    throw new DuplicateResourceException("Expense already exists");
}
```

