package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class HomePageControllerTest {

    @Test
    void homePage_returnsCorrectViewName() {
        HomePageController controller = new HomePageController();

        String viewName = controller.homePage();

        assertEquals("HomePage.html", viewName, "The view name should be 'HomePage.html'");
    }
}