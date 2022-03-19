package model.dataTypes;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private double price;
	private int supplier_id;
	private int id;
	
	public Product(String t_name,String t_desc,double t_price,int t_id,int t_supp_id) {
		name = t_name;
		description = t_desc;
		id = t_id;
		price = t_price;
		supplier_id = t_supp_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public int getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean equals(Product p) {
		return id == p.getId();
	}
	
}
