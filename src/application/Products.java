package application;

public class Products {
	
	int id_prod;
	String name_prod;
	int quantity_prod;
	double price_prod;
	String desc_prod;
	String id_cat;
	
	public Products(int id_prod, String name_prod, int quantity_prod, double price_prod, String desc_prod, String id_cat) {
		super();
		this.id_prod = id_prod;
		this.name_prod = name_prod;
		this.quantity_prod = quantity_prod;
		this.price_prod = price_prod;
		this.desc_prod = desc_prod;
		this.id_cat = id_cat;
	}
	
	public Products(String name_prod, int quantity_prod, double price_prod, String desc_prod, String id_cat) {
		this.name_prod = name_prod;
		this.quantity_prod = quantity_prod;
		this.price_prod = price_prod;
		this.desc_prod = desc_prod;
		this.id_cat = id_cat;
	}

	public int getId_prod() {
		return id_prod;
	}

	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}

	public String getName_prod() {
		return name_prod;
	}

	public void setName_prod(String name_prod) {
		this.name_prod = name_prod;
	}

	public int getQuantity_prod() {
		return quantity_prod;
	}

	public void setQuantity_prod(int quantity_prod) {
		this.quantity_prod = quantity_prod;
	}

	public double getPrice_prod() {
		return price_prod;
	}

	public void setPrice_prod(double price_prod) {
		this.price_prod = price_prod;
	}

	public String getDesc_prod() {
		return desc_prod;
	}

	public void setDesc_prod(String desc_prod) {
		this.desc_prod = desc_prod;
	}

	public String getId_cat() {
		return id_cat;
	}

	public void setId_cat(String id_cat) {
		this.id_cat = id_cat;
	}
	
	
	
}
