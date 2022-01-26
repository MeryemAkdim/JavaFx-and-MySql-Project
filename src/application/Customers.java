package application;

public class Customers {

	int id_cust;
	String name_cust;
	String lastname_cust;
	String adress_cust;
	int phoneNbr_cust;
	
	public Customers(int id_cust, String name_cust, String lastname_cust, String adress_cust, int phoneNbr_cust) {
		super();
		this.id_cust = id_cust;
		this.name_cust = name_cust;
		this.lastname_cust = lastname_cust;
		this.adress_cust = adress_cust;
		this.phoneNbr_cust = phoneNbr_cust;
	}

	public Customers(String name_cust, String lastname_cust, String adress_cust, int phoneNbr_cust) {
		this.name_cust = name_cust;
		this.lastname_cust = lastname_cust;
		this.adress_cust = adress_cust;
		this.phoneNbr_cust = phoneNbr_cust;
	}

	public int getId_cust() {
		return id_cust;
	}

	public void setId_cust(int id_cust) {
		this.id_cust = id_cust;
	}

	public String getName_cust() {
		return name_cust;
	}

	public void setName_cust(String name_cust) {
		this.name_cust = name_cust;
	}

	public String getLastname_cust() {
		return lastname_cust;
	}

	public void setLastname_cust(String lastname_cust) {
		this.lastname_cust = lastname_cust;
	}

	public String getAdress_cust() {
		return adress_cust;
	}

	public void setAdress_cust(String adress_cust) {
		this.adress_cust = adress_cust;
	}

	public int getPhoneNbr_cust() {
		return phoneNbr_cust;
	}

	public void setPhoneNbr_cust(int phoneNbr_cust) {
		this.phoneNbr_cust = phoneNbr_cust;
	}

	
	
	
	
}
