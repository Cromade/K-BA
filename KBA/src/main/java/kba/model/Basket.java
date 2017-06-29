package kba.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Basket {

	private StringProperty name;
	private StringProperty groupName;
	private Group group;
	private DoubleProperty total;
	private ObservableList<BasketProduct> productList;
	private StringProperty status;
	private boolean isFavourite;
	private ImageView proofImageView;
	private Image proofImage;

	public Basket() {
        this.name = new SimpleStringProperty("");
        this.groupName = new SimpleStringProperty("Aucun");
        this.group = null;
        this.total = new SimpleDoubleProperty(0.0);
        this.productList = FXCollections.observableArrayList();
        this.status = new SimpleStringProperty("En cours...");
        this.isFavourite = false;
        this.proofImage = null;
        this.proofImageView = null;
    }
	
	public Basket(String name) {
		this.name = new SimpleStringProperty(name);
        this.groupName = new SimpleStringProperty("Aucun");
		this.group = null;
		this.total = new SimpleDoubleProperty(0.0);
		this.productList = FXCollections.observableArrayList();
		this.status = new SimpleStringProperty("En cours...");
		this.isFavourite = false;
        this.proofImage = null;
        this.proofImageView = null;
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
	
	public ObservableList<BasketProduct> getProductList() {
		return productList;
	}
	
	public void addProduct(Product product, int quantity) {
	    BasketProduct basketProduct = new BasketProduct(product);
		if (productList != null) {
			for (BasketProduct bp : productList) {
                if (bp.getProduct().equals(basketProduct.getProduct())) {
                    bp.addQuantity(quantity+0L);
                    total.setValue((product.getPrice() * quantity) + total.getValue());
                    return;
                }
            }
		}
        basketProduct.addQuantity(quantity+0L);
		productList.add(basketProduct);
		total.setValue(product.getPrice()*quantity + total.getValue());
	}

    public void updateProduct(BasketProduct selectedProduct, int quantity) {
        for (BasketProduct bp : productList) {
            if (bp.getProduct().equals(selectedProduct.getProduct())) {
                bp.removeQuantity(quantity+0L);
                total.setValue(total.getValue() - bp.getTotal());
                return;
            }
        }
    }
	
	public void removeProduct(BasketProduct basketProduct, int quantity) {
        productList.remove(basketProduct);
        total.setValue(total.getValue() - basketProduct.getProduct().getPrice()*quantity);
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

    public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public boolean getIsFavourite() {
	    return isFavourite;
    }

    public Image getProofImage() {
        return proofImage;
    }

    public ImageView getProofImageView() {
        return proofImageView;
    }

    public void setProofImg(Image img) {
        this.proofImage = img;
        this.proofImageView = new ImageView(img);
        this.proofImageView.setFitHeight(395);
        this.proofImageView.setFitWidth(275);
    }

}
