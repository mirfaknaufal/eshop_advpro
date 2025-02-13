<details open>
<summary>Module 1</summary>

# Reflection 1

## **1. Clean Code Principles Applied**

### **a. Use of Meaningful Naming**
- Class names (`ProductController`, `ProductService`, etc.) and method names (`create`, `update`, `delete`) are descriptive and follow standard naming conventions.
- Variable names like `productId`, `productName`, and `productQuantity` are clear and self-explanatory.


### **b. Consistent Formatting**
- Indentation, spacing, and braces are consistent throughout the code.
- Lombok annotations (`@Getter`, `@Setter`) reduce boilerplate code while maintaining readability.

### **c. Modularization**
- The application is divided into layers:
    - **Controller Layer**: Handles HTTP requests.
    - **Service Layer**: Implements business logic.
    - **Repository Layer**: Manages data storage.
    - **Model Layer**: Represents domain entities.
- This modular structure improves scalability and testability.

---

## **2. Secure Coding Practices Applied**

### **a. Input Validation**
- While not explicitly implemented yet, input validation should be added to ensure that user-provided data (e.g., `productName`, `productQuantity`) is valid and safe.

### **b. Use of HTTPS**
- Ensure that your application runs over HTTPS in production to encrypt data in transit.

### **c. Avoid Hardcoding Sensitive Information**
- There’s no evidence of hardcoded sensitive information (e.g., database credentials). If such information exists, it should be stored in environment variables or a secure configuration file.

### **d. CSRF Protection**
- Spring Security provides CSRF protection by default. If you’re using forms, ensure that CSRF tokens are included in POST requests.

### **e. Error Handling**
- The current implementation does not handle exceptions explicitly. For example, if a product is not found during an edit or delete operation, the application redirects to the product list without notifying the user.

---

## **3. Areas for Improvement**

### **a. Missing Input Validation**
- Currently, there is no validation for user input. For example:
    - What happens if the user submits an empty `productName`?
    - What happens if the user enters a negative or non-numeric value for `productQuantity`?

---

### **b. Missing Exception Handling**
- If a product is not found during an edit or delete operation, the application silently redirects to the product list without informing the user.

---

### **c. Logging**
- The application currently lacks comprehensive logging. Adding logs can help with debugging and monitoring.

---

### **d. Pagination for Large Data Sets**
- The `findAll` method retrieves all products at once. For large datasets, this can lead to performance issues.

---

</details>