package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
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
        String productIdToDelete = "1";

        doNothing().when(productRepository).delete(productIdToDelete);

        productService.delete(productIdToDelete);

        verify(productRepository, times(1)).delete(productIdToDelete);
    }

    @Test
    void testDelete_ProductDoesNotExist_NoErrorThrown() {
        String nonExistentProductId = "999";

        doNothing().when(productRepository).delete(nonExistentProductId);

        productService.delete(nonExistentProductId);

        verify(productRepository, times(1)).delete(nonExistentProductId);
    }
}