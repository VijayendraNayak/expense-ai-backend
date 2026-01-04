# 💰 Expense AI Backend

A **Spring Boot REST API** for expense tracking with in-memory storage - built to demonstrate clean architecture and SOLID principles.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 🚀 Features

### Core Functionality
- ✅ **CRUD Operations** - Create, Read, Update, Delete expenses
- ✅ **In-Memory Storage** - No database required (perfect for learning!)
- ✅ **RESTful API** - Industry-standard REST endpoints
- ✅ **Global Exception Handling** - Standardized error responses
- ✅ **Input Validation** - Automatic validation with Jakarta Bean Validation
- ✅ **Thread-Safe** - Concurrent operations using ConcurrentHashMap

### API Endpoints
- 📝 Create expense
- 📋 Get all expenses
- 🔍 Get expense by ID
- 👤 Filter by user
- 🏷️ Filter by category
- ✏️ Update expense
- 🗑️ Delete expense
- 🔢 Get expense count

---

## 🏗️ Architecture & Design

### SOLID Principles ⭐ 9/10
- **Single Responsibility** - Each class has one job
- **Open/Closed** - Interface-based design for extensibility
- **Liskov Substitution** - Implementations are interchangeable
- **Interface Segregation** - Focused interfaces and DTOs
- **Dependency Inversion** - Depends on abstractions

### Design Patterns
- 🎨 **Layered Architecture** (Controller → Service → Repository)
- 🏭 **Repository Pattern** - Data access abstraction
- 📦 **DTO Pattern** - Separate request/response objects
- 💉 **Dependency Injection** - Constructor injection
- 🛡️ **Exception Handling** - Centralized error handling
- 📋 **Builder Pattern** - Lombok integration

### Technology Stack
- **Java 17** - Modern Java features
- **Spring Boot 4.0.1** - Latest framework
- **Lombok** - Reduce boilerplate code
- **Jakarta Validation** - Bean validation
- **Gradle** - Build automation

---

## 📦 Project Structure

```
src/main/java/com/example/expense_ai_backend/
├── 📂 constant/          # Application constants
├── 📂 controller/        # REST controllers
├── 📂 dto/               # Data Transfer Objects
├── 📂 exception/         # Custom exceptions & handlers
├── 📂 model/             # Domain models
├── 📂 repository/        # Data access layer (in-memory)
└── 📂 service/           # Business logic layer
    └── 📂 Impl/          # Service implementations
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Gradle (included via wrapper)

### Run the Application

```bash
# Clone the repository
git clone https://github.com/VijayendraNayak/expense-ai-backend.git
cd expense-ai-backend

# Run the application
./gradlew bootRun
```

The application will start on **http://localhost:8061**

---

## 📝 API Usage Examples

### Create an Expense
```bash
curl -X POST http://localhost:8061/api/expense \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Coffee at Starbucks",
    "amount": 5.50,
    "category": "Food & Beverage"
  }'
```

**Response:**
```json
{
  "message": "Expense created successfully with ID: 1"
}
```

### Get All Expenses
```bash
curl http://localhost:8061/api/expense
```

**Response:**
```json
[
  {
    "id": 1,
    "description": "Coffee at Starbucks",
    "amount": 5.50,
    "category": "Food & Beverage",
    "date": "2026-01-04T10:30:00",
    "userId": "default-user"
  }
]
```

### Update an Expense
```bash
curl -X PUT http://localhost:8061/api/expense/1 \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Coffee at Starbucks (Updated)",
    "amount": 6.50,
    "category": "Food & Beverage"
  }'
```

### Delete an Expense
```bash
curl -X DELETE http://localhost:8061/api/expense/1
```

---

## 🎯 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/expense` | Create new expense |
| GET | `/api/expense` | Get all expenses |
| GET | `/api/expense/{id}` | Get expense by ID |
| GET | `/api/expense/user/{userId}` | Get expenses by user |
| GET | `/api/expense/category/{category}` | Get expenses by category |
| PUT | `/api/expense/{id}` | Update expense |
| DELETE | `/api/expense/{id}` | Delete expense |
| GET | `/api/expense/count` | Get total count |

---

## 🛡️ Error Handling

All errors return a standardized JSON response:

```json
{
  "timestamp": "2026-01-04T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Expense not found with id: 999",
  "path": "/api/expense/999"
}
```

### HTTP Status Codes
- **200 OK** - Successful GET, PUT, DELETE
- **201 CREATED** - Successful POST
- **400 BAD REQUEST** - Validation errors
- **404 NOT FOUND** - Resource doesn't exist
- **500 INTERNAL SERVER ERROR** - Unexpected errors

---

## ✅ Input Validation

All requests are automatically validated:

```java
@NotBlank(message = "Description is required")
private String description;

@NotNull(message = "Amount is required")
@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
private BigDecimal amount;

@NotBlank(message = "Category is required")
private String category;
```

Invalid requests receive detailed error messages.

---

## 🧪 Testing

### Import Insomnia Collection
1. Open Insomnia
2. Import `insomnia-collection.json`
3. Update base URL to `http://localhost:8061`
4. Start testing!

### Available Test Documentation
- 📖 `INSOMNIA_TESTING_GUIDE.md` - Comprehensive testing guide
- 📋 `TESTING_QUICK_REFERENCE.md` - Quick test checklist
- 🚀 `INSOMNIA_SETUP.md` - Insomnia setup instructions

---

## 📚 Documentation

Detailed documentation available in the repository:

| Document | Description |
|----------|-------------|
| `API_DOCUMENTATION.md` | Complete API reference |
| `SOLID_PRINCIPLES_ANALYSIS.md` | SOLID principles breakdown |
| `EXCEPTION_HANDLING.md` | Error handling details |
| `INSOMNIA_TESTING_GUIDE.md` | API testing guide |
| `WHY_CUSTOM_EXCEPTIONS.md` | Exception strategy explained |

---

## 💡 Why In-Memory Storage?

This project uses **in-memory storage** (no database) to:
- ✅ **Focus on learning** architecture and design patterns
- ✅ **Simplify setup** - no database configuration needed
- ✅ **Fast prototyping** - quick to start and test
- ✅ **Easy to understand** - transparent data flow

> **Note:** All data is lost when the application restarts. Perfect for development and learning!

---

## 🔑 Key Highlights

### Clean Architecture
- **3-Layer Architecture** - Controller, Service, Repository
- **Separation of Concerns** - Each layer has one responsibility
- **Interface-Based Design** - Easy to extend and test

### Best Practices
- ✅ Constructor Injection (not field injection)
- ✅ DTO Pattern (separate request/response objects)
- ✅ Global Exception Handling
- ✅ Centralized Constants
- ✅ Validation Annotations
- ✅ Thread-Safe Operations

### Code Quality
- **SOLID Score:** 9/10
- **Clean Code** - Readable and maintainable
- **Self-Documenting** - Clear naming and structure
- **Production-Ready** - Enterprise-level architecture

---

## 🛠️ Building from Source

```bash
# Clean and build
./gradlew clean build

# Run tests
./gradlew test

# Build without tests
./gradlew clean build -x test

# Generate JAR file
./gradlew bootJar
```

---

## 📊 Project Stats

- **Language:** Java 17
- **Framework:** Spring Boot 4.0.1
- **Build Tool:** Gradle 9.2.1
- **Design Patterns:** 6+ patterns implemented
- **SOLID Compliance:** 9/10
- **API Endpoints:** 8 RESTful endpoints
- **Lines of Code:** ~1000+ lines

---

## 🤝 Contributing

This is a learning project, but contributions are welcome!

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Vijayendra Nayak**
- GitHub: [@VijayendraNayak](https://github.com/VijayendraNayak)

---

## 🌟 Acknowledgments

Built with ❤️ to demonstrate:
- Clean Architecture
- SOLID Principles
- Design Patterns
- Spring Boot Best Practices

---

## 📞 Support

For issues or questions:
1. Check the documentation files
2. Review the API testing guide
3. Open an issue on GitHub

---

**Happy Coding! 🚀**

