package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        // Construct the base URL dynamically using the assigned server port
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProduct_SuccessfullyAddedToProductList(ChromeDriver driver) throws Exception {
        // Navigate to the "Create Product" page
        driver.get(baseUrl + "/product/create");

        // Locate form elements
        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        WebElement createButton = driver.findElement(By.id("createButton"));

        // Simulate user input
        String productName = "New Laptop";
        int productQuantity = 10;

        productNameInput.sendKeys(productName);
        productQuantityInput.sendKeys(String.valueOf(productQuantity));
        createButton.click();

        // Verify that the new product appears in the product list
        WebElement productListTable = driver.findElement(By.id("productList"));
        String productListText = productListTable.getText();

        assertTrue(productListText.contains(productName), "The product list should contain the newly added product.");
        assertTrue(productListText.contains(String.valueOf(productQuantity)), "The product list should contain the correct quantity.");
    }
}