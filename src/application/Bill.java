package application;

public class Bill {
	
	int id_bill;
	String bname_prod;
	int bquantity_prod;
	double bprice_prod;
	double total;
	
	public Bill(int id_bill, String bname_prod, int bquantity_prod, double bprice_prod, double total ) {
		super();
		this.id_bill = id_bill;
		this.bname_prod = bname_prod;
		this.bquantity_prod = bquantity_prod;
		this.bprice_prod = bprice_prod;
		this.total = total;
	}
	
	public Bill(String bname_prod, int bquantity_prod, double bprice_prod) {
		this.bname_prod = bname_prod;
		this.bquantity_prod = bquantity_prod;
		this.bprice_prod = bprice_prod;
		//this.total = total;
	}

	public int getId_bill() {
		return id_bill;
	}

	public void setId_bill(int id_bill) {
		this.id_bill = id_bill;
	}

	public String getBname_prod() {
		return bname_prod;
	}

	public void setBname_prod(String bname_prod) {
		this.bname_prod = bname_prod;
	}

	public int getBquantity_prod() {
		return bquantity_prod;
	}

	public void setBquantity_prod(int bquantity_prod) {
		this.bquantity_prod = bquantity_prod;
	}

	public double getBprice_prod() {
		return bprice_prod;
	}

	public void setBprice_prod(double bprice_prod) {
		this.bprice_prod = bprice_prod;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	
	
	
}
