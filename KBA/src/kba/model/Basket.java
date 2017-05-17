package kba.model;

import java.util.HashMap;
import java.util.Map;

public class Basket {

	private String name;
	private Group group;
	private Double total;
	private Map<Product, Long> productList;
	
	public Basket(String name) {
		this.name = name;
		this.total = 0.0;
		this.productList = new HashMap<Product, Long>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Double getTotal() {
		return total;
	}
	
	public Map<Product, Long> getProductList() {
		return productList;
	}
	
	public void addProduct(Product product, int quantity) {
		if (productList != null) {
			if (productList.containsKey(product)) {
				productList.put(product, productList.get(product)+1);
				total += product.getPrice()*quantity;
				return;
			}
		}
		productList.put(product, 1L);
		total += product.getPrice()*quantity;
	}
	
	public void removeProduct(Product product, int quantity) {
		productList.remove(product);
		total -= product.getPrice();
	}
}
