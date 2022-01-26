package application;

public class Category {
	
	String id_cat;
	String name_cat;
	public Category(String id_cat, String name_cat) {
		super();
		this.id_cat = id_cat;
		this.name_cat = name_cat;
	}
	public String getId_cat() {
		return id_cat;
	}
	public void setId_cat(String id_cat) {
		this.id_cat = id_cat;
	}
	public String getName_cat() {
		return name_cat;
	}
	public void setName_cat(String name_cat) {
		this.name_cat = name_cat;
	}
	
	
}
