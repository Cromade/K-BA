package kba.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Basket {

	private StringProperty name;
	private StringProperty groupName;
	private Group group;
	private DoubleProperty total;
	private Map<Product, Long> productList;
	private StringProperty status;
	
	public Basket(String name) {
		this.name = new SimpleStringProperty(name);
        this.groupName = new SimpleStringProperty("Aucun");
		this.group = null;
		this.total = new SimpleDoubleProperty(0.0);
		this.productList = new HashMap<Product, Long>();
		this.status = new SimpleStringProperty("En cours...");
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

    public String getGroupName() {
        return groupName.get();
    }

    public StringProperty groupNameProperty() {
        if (groupName == null) groupName = new SimpleStringProperty(this, "Aucun");
        return groupName;
    }
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
		this.groupName.setValue(group.getName());
	}

	public Double getTotal() {
		return total.get();
	}

	public DoubleProperty totalProperty() {
		if (total == null) total = new SimpleDoubleProperty(this, "total");
		return total;
	}
	
	public Map<Product, Long> getProductList() {
		return productList;
	}
	
	public void addProduct(Product product, int quantity) {
		if (productList != null) {
			if (productList.containsKey(product)) {
			    product.addQuantity(productList.get(product)+1);
				productList.put(product, productList.get(product)+1);
				total.setValue(product.getPrice()*quantity + total.getValue());
				return;
			}
		}
		product.addQuantity(quantity+0L);
		productList.put(product, quantity+0L);
		total.setValue(product.getPrice()*quantity + total.getValue());
	}
	
	public void removeProduct(Product product, int quantity) {
        product.removeQuantity(quantity+0L);
		productList.remove(product);
		total.setValue(total.getValue() - product.getPrice());
	}

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        if (status == null) status = new SimpleStringProperty(this, "En cours...");
        return status;
    }

    public void setValidated() {
        this.status.setValue("Validé !");
    }

    public void setInvalidated() {
	    this.status.setValue("En cours...");
    }
}
