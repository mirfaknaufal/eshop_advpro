package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ProductServiceDeleteTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete_ProductExists_SuccessfullyDeleted() {
        // Arrange: Identify a product to delete
        String productIdToDelete = "1";

        doNothing().when(productRepository).delete(productIdToDelete);

        // Act: Call the delete method in the service
        productService.delete(productIdToDelete);

        // Assert: Verify no exceptions were thrown
        verify(productRepository, times(1)).delete(productIdToDelete);
    }

    @Test
    void testDelete_ProductDoesNotExist_NoErrorThrown() {
        // Arrange: Identify a non-existent product ID
        String nonExistentProductId = "999";

        doNothing().when(productRepository).delete(nonExistentProductId);

        // Act: Attempt to delete the non-existent product
        productService.delete(nonExistentProductId);

        // Assert: Verify no exceptions were thrown
        verify(productRepository, times(1)).delete(nonExistentProductId);
    }
}