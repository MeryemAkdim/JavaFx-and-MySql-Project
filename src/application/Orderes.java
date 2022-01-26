package application;

import java.sql.Date;

public class Orderes {

	int id_order;
	int id_cust;
	int id_prod;
	int quantity_order;
	double uprice;
	Date order_date;
	double total;
	
	public Orderes(int id_order, int id_cust, int id_prod, int quantity_order, double uprice, Date order_date, double total) {
		super();
		this.id_order = id_order;
		this.id_cust = id_cust;
		this.id_prod = id_prod;
		this.quantity_order = quantity_order;
		this.uprice = uprice;
		this.order_date = order_date;
		this.total = total;
	}

	public Orderes(int id_cust, int id_prod, int quantity_order, double uprice, Date order_date) {
		this.id_cust = id_cust;
		this.id_prod = id_prod;
		this.quantity_order = quantity_order;
		this.uprice = uprice;
		this.order_date = order_date;
		
	}

	public int getId_order() {
		return id_order;
	}

	public void setId_order(int id_order) {
		this.id_order = id_order;
	}

	public int getId_cust() {
		return id_cust;
	}

	public void setId_cust(int id_cust) {
		this.id_cust = id_cust;
	}

	public int getId_prod() {
		return id_prod;
	}

	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}

	public int getQuantity_order() {
		return quantity_order;
	}

	public void setQuantity_order(int quantity_order) {
		this.quantity_order = quantity_order;
	}

	public double getUprice() {
		return uprice;
	}

	public void setUprice(double uprice) {
		this.uprice = uprice;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
	
	
	
	
}
