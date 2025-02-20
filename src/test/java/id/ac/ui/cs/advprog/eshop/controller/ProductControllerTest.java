package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = controller.createProductPage(model);

        assertEquals("CreateProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");

        String viewName = controller.createProductPost(product, model);

        assertEquals("redirect:list", viewName);
        verify(service, times(1)).create(product);
    }

    @Test
    void testProductListPage() {
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Laptop");

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Smartphone");

        List<Product> products = Arrays.asList(product1, product2);

        when(service.findAll()).thenReturn(products);

        String viewName = controller.productListPage(model);

        assertEquals("ProductList", viewName);
        verify(model, times(1)).addAttribute(eq("products"), eq(products));
        verify(service, times(1)).findAll();
    }

    @Test
    void testEditProductPage_ProductFound() {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Laptop");
        when(service.findById(productId)).thenReturn(product);

        String viewName = controller.editProductPage(productId, model);

        assertEquals("EditProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), eq(product));
        verify(service, times(1)).findById(productId);
    }

    @Test
    void testEditProductPage_ProductNotFound() {
        String productId = "999";
        when(service.findById(productId)).thenReturn(null);

        String viewName = controller.editProductPage(productId, model);

        assertEquals("redirect:/product/list", viewName);
        verify(service, times(1)).findById(productId);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");

        String viewName = controller.editProductPost(product, model);

        assertEquals("redirect:/product/list", viewName);
        verify(service, times(1)).update(product);
    }

    @Test
    void testDeleteProduct() {
        String productId = "1";

        String viewName = controller.deleteProduct(productId);

        assertEquals("redirect:/product/list", viewName);
        verify(service, times(1)).delete(productId);
    }
}