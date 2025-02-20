package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceUpdateTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdate_ProductExists_SuccessfullyUpdated() {
        Product existingProduct = new Product();
        existingProduct.setProductId("1");
        existingProduct.setProductName("Laptop");
        existingProduct.setProductQuantity(10);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("Gaming Laptop");
        updatedProduct.setProductQuantity(5);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        // Act: Call the update method in the service
        Product result = productService.update(updatedProduct);

        // Assert: Verify the product was updated successfully
        assertNotNull(result);
        assertEquals("Gaming Laptop", result.getProductName());
        assertEquals(5, result.getProductQuantity());

        // Verify interaction with the repository
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdate_ProductDoesNotExist_ReturnsNull() {
        // Arrange: Create a non-existent product
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("999");
        nonExistentProduct.setProductName("Non-existent Product");
        nonExistentProduct.setProductQuantity(1);

        when(productRepository.update(nonExistentProduct)).thenReturn(null);

        // Act: Attempt to update the non-existent product
        Product result = productService.update(nonExistentProduct);

        // Assert: Verify the update returns null
        assertNull(result);

        // Verify interaction with the repository
        verify(productRepository, times(1)).update(nonExistentProduct);
    }
}