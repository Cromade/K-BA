package kba.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Product {

    private Long id;
    private StringProperty name;
    private String manufacturer;
    private StringProperty description;
    private List<Category> categories;
    private DoubleProperty price;
    private ImageView productImg;

    public Product(Long id, String name, String manufacturer, String description, Double price) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.manufacturer = manufacturer;
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.categories = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this, "description");
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        boolean notFound = true;
        for (Category cat : categories) {
            if (cat.equals(category)) notFound = false;
        }
        if (notFound) categories.add(category);
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        if (price == null) price = new SimpleDoubleProperty(this, "0");
        return price;
    }

    public ImageView getProductImg() {
        return productImg;
    }

    public void setProductImg(Image productImg) {
        this.productImg = new ImageView(productImg);
        this.productImg.setFitHeight(60);
        this.productImg.setFitWidth(60);
    }

}