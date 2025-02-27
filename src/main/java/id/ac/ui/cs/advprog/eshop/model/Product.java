package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.repository.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter

public class Product implements Identifiable {
    private String productId;
    private String productName;
    private int productQuantity;

    @Override
    public String getId() {
        return productId;
    }

    @Override
    public void setId(String id) {
        this.productId = id;
    }
}