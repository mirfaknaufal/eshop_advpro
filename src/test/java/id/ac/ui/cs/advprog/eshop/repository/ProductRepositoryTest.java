package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_ProductExists() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundProduct);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", foundProduct.getProductId());
        assertEquals("Sampo Cap Bambang", foundProduct.getProductName());
        assertEquals(100, foundProduct.getProductQuantity());
    }

    @Test
    void testFindById_ProductDoesNotExist() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testUpdate_ProductExists() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Updated Sampo");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result, "Updated product should not be null");
        assertEquals("Updated Sampo", result.getProductName(), "Product name should be updated");
        assertEquals(200, result.getProductQuantity(), "Product quantity should be updated");

        Product retrievedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(retrievedProduct);
        assertEquals("Updated Sampo", retrievedProduct.getProductName());
        assertEquals(200, retrievedProduct.getProductQuantity());
    }

    @Test
    void testUpdate_ProductDoesNotExist() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Updated Sampo");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNull(result, "Update should return null for non-existent product");
    }

    @Test
    void testUpdate_NoMatchingProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-matching-id");
        updatedProduct.setProductName("Updated Sampo");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNull(result, "Update should return null when no matching product exists");

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product retrievedProduct = productIterator.next();
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", retrievedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", retrievedProduct.getProductName());
        assertEquals(100, retrievedProduct.getProductQuantity());
    }

    @Test
    void testDelete_ProductExists() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        Product foundProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNull(foundProduct);
    }

    @Test
    void testDelete_ProductDoesNotExist() {
        productRepository.delete("non-existent-id");
    }

    @Test
    void testCreate_GeneratesProductIdIfNull() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId(), "Product ID should not be null");
        assertTrue(savedProduct.getProductId().matches("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$"),
                "Generated Product ID should match UUID format");

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product retrievedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), retrievedProduct.getProductId(),
                "The generated Product ID should match the one in the repository");
    }

    @Test
    void testCreate_ProductIdIsNull() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId(), "Product ID should not be null");
        assertTrue(savedProduct.getProductId().matches("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$"),
                "Generated Product ID should match UUID format");
    }

    @Test
    void testCreate_ProductIdIsEmpty() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId(), "Product ID should not be null");
        assertTrue(savedProduct.getProductId().matches("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$"),
                "Generated Product ID should match UUID format");
    }

    @Test
    void testCreate_ProductIdIsSet() {
        String predefinedId = "custom-product-id";
        Product product = new Product();
        product.setProductId(predefinedId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product savedProduct = productRepository.create(product);

        assertEquals(predefinedId, savedProduct.getProductId(), "Predefined Product ID should be retained");
    }
}