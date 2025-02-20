package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");

        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("1", createdProduct.getProductId());
        assertEquals("Laptop", createdProduct.getProductName());

        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product1, product2);
        product1.setProductName("Laptop");
        product2.setProductName("Smartphone");
        product1.setProductId("1");
        product2.setProductId("2");

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getProductName());
        assertEquals("Smartphone", result.get(1).getProductName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        String productId = "1";
        Product product = new Product();

        product.setProductName("Laptop");
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(product);

        Product foundProduct = productService.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getProductId());
        assertEquals("Laptop", foundProduct.getProductName());

        verify(productRepository, times(1)).findById(productId);
    }
}