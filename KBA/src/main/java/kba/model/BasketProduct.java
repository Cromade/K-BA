package kba.model;

import javafx.beans.property.*;

public class BasketProduct{

    private Product product;
    protected LongProperty quantity = new SimpleLongProperty();
    protected DoubleProperty total = new SimpleDoubleProperty();

    public BasketProduct(Product product) {
        this.product = product;
    }

    public void addQuantity(Long quantityToAdd) {
        if (quantity == null) {
            this.quantity.set(quantityToAdd);
        } else {
            this.quantity.set(quantity.getValue() + quantityToAdd);
        }
        total.setValue(quantity.longValue() * product.getPrice());
    }

    public void removeQuantity(Long quantityToRemove) {
        if (quantityToRemove.longValue() > quantity.longValue()) {
            return;
        }
        this.quantity.set(quantity.getValue() - quantityToRemove);
        total.setValue(quantity.longValue() * product.getPrice());
    }

    public Long getQuantity() {
        return quantity.get();
    }

    public LongProperty quantityProperty() {
        if (quantity == null) quantity = new SimpleLongProperty(this, "0");
        return quantity;
    }

    public Double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        if (total == null) total = new SimpleDoubleProperty(this, "0");
        return total;
    }

    public Product getProduct() {
        return product;
    }
}
