<details>
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

# Reflection 2
### 1. Writing unit tests is an essential part of software development, and it can evoke a range of feelings depending on the complexity of the code and the testing framework being used. Initially, writing unit tests might feel tedious or time-consuming, especially when dealing with intricate logic or dependencies. However, as you become more familiar with testing frameworks (like JUnit, Mockito, etc.), the process becomes more intuitive and rewarding. Seeing your tests pass gives a sense of confidence in the correctness and reliability of your code.

---
### How Many Unit Tests Should Be Made for a Class?

The number of unit tests for a class depends on several factors:
1. **Functionality**: Each method in the class should ideally have at least one corresponding unit test. If a method has multiple logical branches (e.g., `if-else`, loops, exceptions), each branch should be tested separately.
2. **Code Complexity**: More complex methods with higher cyclomatic complexity require more tests to cover all possible execution paths.
3. **Edge Cases**: Tests should account for edge cases, invalid inputs, and boundary conditions.
4. **Behavioral Scenarios**: For classes that implement business logic, tests should verify expected behavior under various scenarios, including both normal and exceptional cases.

---

### Ensuring That Unit Tests Are Sufficient

To ensure that your unit tests are sufficient to verify your program, consider the following strategies:

1. **Test Coverage Metrics**:
  - Use tools like **JaCoCo**, **Cobertura**, or **SonarQube** to measure code coverage. These tools provide insights into how much of your code is exercised by your tests.
  - Common coverage metrics include:
    - **Line Coverage**: Percentage of lines executed during testing.
    - **Branch Coverage**: Percentage of decision points (e.g., `if` statements) covered.
    - **Method Coverage**: Percentage of methods invoked.
    - **Class Coverage**: Percentage of classes instantiated.

2. **Mutation Testing**:
  - Mutation testing tools (e.g., **PITest**) introduce small changes ("mutations") to your code and check whether your tests detect these changes. This helps identify gaps in your test suite.

3. **Boundary Value Analysis**:
  - Test inputs at the boundaries of acceptable ranges (e.g., minimum, maximum, null values).

4. **Integration and Functional Testing**:
  - While unit tests focus on individual components, integration tests ensure that components work together correctly. Functional tests validate the system's behavior from an end-user perspective.

5. **Review and Refactor**:
  - Regularly review your tests to ensure they remain relevant as the code evolves.
  - Refactor tests to improve readability and maintainability.

### Does 100% Code Coverage Mean No Bugs?

No, achieving **100% code coverage does not guarantee that your code is bug-free**. Here’s why:

1. **Logical Errors**:
  - Code coverage measures whether lines of code are executed, but it doesn’t verify whether the logic is correct. A test might execute a line of code but fail to detect incorrect behavior.

2. **Edge Cases**:
  - Even with full coverage, some edge cases might not be explicitly tested. For example, a test might cover a method but miss rare input combinations.

3. **External Dependencies**:
  - Code coverage doesn’t account for issues arising from external systems (e.g., databases, APIs). Mocking these dependencies in unit tests can mask potential problems.

4. **Non-Functional Requirements**:
  - Code coverage doesn’t address performance, security, usability, or scalability concerns.

5. **Human Error**:
  - Tests themselves can contain bugs or fail to assert the correct behavior.

---

### 2. 
When creating a new functional test suite to verify the number of items in the product list, duplicating the structure of `CreateProductFunctionalTest` introduces several clean code issues:

1. **Code Duplication**:
    - Repeating setup logic (e.g., WebDriver initialization, base URL construction) violates the DRY principle, making the code harder to maintain.

2. **Reduced Readability**:
    - Repetitive boilerplate code obscures the unique functionality being tested, making the tests harder to understand.

3. **Increased Maintenance Overhead**:
    - Changes to shared logic (e.g., setup procedures) require updates in multiple places, increasing effort and risk of inconsistencies.

4. **Violation of SRP**:
    - Embedding setup logic in each test class blurs responsibilities, reducing clarity and modularity.

---

### Things that can improve the code

1. **Extract Common Logic into a Base Class**:
    - Centralize shared setup procedures in a `BaseFunctionalTest` class to avoid duplication.

2. **Use Page Object Model (POM)**:
    - Encapsulate page-specific interactions (e.g., product list verification) in separate classes for better readability and reusability.

3. **Parameterize Tests**:
    - Use tools like JUnit 5's `@ParameterizedTest` to test multiple scenarios without duplicating code.

4. **Leverage Dependency Injection**:
    - Inject shared dependencies (e.g., WebDriver) to decouple test logic from setup logic.

---

### Benefits of those improvements

- Improved maintainability and scalability.
- Enhanced readability by focusing on unique test behavior.
- Adherence to clean code principles like DRY and SRP.

By centralizing shared logic and adopting reusable patterns, the new test suite can remain clean, modular, and professional.
</details>

<details>
<summary>Module 2</summary>

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
PMD detected an unused import which was `org.springframework.web.bind.annotation.*`. This is a wildcard import, I changed it from it into a more explicit imports to improve code clarity and maintainability. Wildcard imports offers convenience, but they can lead to ambiguity and namespace pollution. In large projects with other contributors, although wildcard imports saves space, using a more explicit imports would make the code more readable and predictable. In conclusion, using wildcard imports may saves space but using explicit imports would make the more readable and more predictable.

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
The current CI/CD implementation partially meets the definition of Continuous Integration (CI) and Continuous Deployment (CD). The `ci.yml` workflow automates testing on every push and pull request, ensuring code changes are validated through unit tests, which aligns with CI principles. However, while the Dockerfile prepares the application for deployment by building and packaging it into a containerized environment, there is no explicit pipeline or automation for deploying the application to production or staging environments, which is essential for true CD. Additionally, workflows like `pmd.yml` and `scorecard.yml` focus on code quality and security but do not contribute directly to deployment automation.

</details>

<details open>
<summary>Module 3</summary>

### 1. Explain what principles you apply to your project!
- Single Responsibility Principle: I split `ProductController` with `CarController`. Now they both are their own classes without one extends the other.
- Open Closed Principle: I can add more functions into classes like `ProductRepository` or `ProductController` without modifying other functions.
- Liskov Substitution Principle: `CarServiceImpl` and `ProductServiceImpl` both implements all methods in `CarService` or `ProductService` respectively.
- Interface Segregation Principle: Service classes offers needed methods.
- Dependency Inversion Principle: `CarController` depends on the CarService interface, not on a concrete implementation like `CarServiceImpl`.

### 2. Explain the advantages of applying SOLID principles to your project with examples.
With SOLID principle, my code is a lot more readable for me and other people by applying SRP. For example, `CarController` is on its own, and when I read the file name, I instantly knew that 'CarController' is the controller for Car page. Before I applied SOLID, `CarController` was inside of `ProductController`, other people wouldn't know where my 'CarController' is if it stayed.
With SOLID principle, improves robustness of my program. Adhering to SOLID principles reduces the likelihood of bugs and unexpected behavior. Not only that, following the principle makes debugging and spotting bugs a lot easier than ever. For example, following LSP ensures that subclasses behave consistently with their superclasses.

### 3. Explain the disadvantages of not applying SOLID principles to your project with examples.
Without applying SOLID principle, fixing bugs would be a pain in the head. Especially when you are not the one fixing your own bugs. For example, if someone wanted to fix your bug, and found out you didn't apply SRP, the person fixing your bug would need to look into your code more needed than necessary.
Without applying SOLID principle, changing a part of your code might break a lot of things. Without applying SOLID would also makes the entire code a lot harder to read.
</details>