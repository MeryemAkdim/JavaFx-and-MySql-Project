package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ProdController {
	
	@FXML
    private ComboBox<String> CatId;
	
	@FXML
    private TextField ProPrice;

    @FXML
    private TextField ProQuantity;

    @FXML
    private TextField ProdDesc;

    @FXML
    private TextField ProdName;

    @FXML
    private TableView<Products> PodTable;
    
    @FXML
    private TextField txt_search;


    @FXML
    private TableColumn<Products, String> cdesc_prod;

    @FXML
    private TableColumn<Products, String> cid_cat;

    @FXML
    private TableColumn<Products, String> cid_prod;

    @FXML
    private TableColumn<Products, String> cname_prod;

    @FXML
    private TableColumn<Products, String> cprice_prod;

    @FXML
    private TableColumn<Products, String> cquntity_prod;

    @FXML
    void mouseSelect(MouseEvent event) {

    	Products p = PodTable.getSelectionModel().getSelectedItem();
    	
    	ProdName.setText(p.name_prod);
    	ProQuantity.setText(p.quantity_prod+"");
    	ProPrice.setText(p.price_prod+" ");
    	ProdDesc.setText(p.desc_prod);
    	CatId.setPromptText(p.id_cat);
    }
    
    ObservableList<Products> data;
   // ObservableList<String> datac = FXCollections.observableArrayList();
    
    @FXML
    void initialize() {
    	
    	data=FXCollections.observableArrayList();
    	
    	cid_prod.setCellValueFactory(new PropertyValueFactory<>("id_prod"));
    	cname_prod.setCellValueFactory(new PropertyValueFactory<>("name_prod"));
    	cquntity_prod.setCellValueFactory(new PropertyValueFactory<>("quantity_prod"));
    	cprice_prod.setCellValueFactory(new PropertyValueFactory<>("price_prod"));
    	cdesc_prod.setCellValueFactory(new PropertyValueFactory<>("desc_prod"));
    	cid_cat.setCellValueFactory(new PropertyValueFactory<>("id_cat"));

    	 	
    	PodTable.setItems(data);
    	getAll();
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT id_cat FROM `category`" );
			ResultSet rst = ps.executeQuery();
			
			while (rst.next()) {
				CatId.getItems().add(rst.getString("id_cat"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    }
    
   
    
    public void getAll() {
    	data.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `products`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_prod = rs.getInt("id_prod");
				String name_prod = rs.getString("name_prod");
				int quantity_prod = rs.getInt("quantity_prod");
				double price_prod = rs.getDouble("price_prod");
				String desc_prod = rs.getString("desc_prod");
				String id_cat = rs.getString("id_cat");
				data.add(new Products(id_prod, name_prod, quantity_prod, price_prod, desc_prod, id_cat));
				
			}
		} catch (Exception e) {
			
		}
    }
    
    @FXML
    void AddProd(ActionEvent event) {

    	String name_prod = ProdName.getText();
    	int quantity_prod = Integer.valueOf(ProQuantity.getText());
    	double price_prod = Double.valueOf(ProPrice.getText());
    	String desc_prod = ProdDesc.getText();
    	//String id_cat = CatId.getItems().toString();
    	String id_cat = CatId.getValue();
    	
    	Products p = new Products(name_prod, quantity_prod, price_prod, desc_prod,id_cat );
    	ProdName.setText("");
    	ProQuantity.setText("");
    	ProPrice.setText("");
    	ProdDesc.setText("");
    	CatId.setPromptText(""); 
    	
    	data.clear();
    	insertProd(p);
    	getAll();
    	//dataBill.add(b);
    	
    }
    
    public static void insertProd(Products p) {
		try {
			
			Connection connection = MysqlConnection.getDBConnection();
			
			
			String sql="INSERT INTO `products` (`name_prod`, `quantity_prod`, `price_prod`,`desc_prod`, `id_cat`) VALUES (?,?,?,?,?)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, p.name_prod);
			ps.setInt(2, p.quantity_prod);
			ps.setDouble(3, p.price_prod);
			ps.setString(4, p.desc_prod);
			ps.setString(5, p.id_cat);
			
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    void UpdateProd(ActionEvent event) {

    	try {
    		Connection connection = MysqlConnection.getDBConnection();
			
			String name_prod = ProdName.getText();
			int quantity_prod = Integer.valueOf(ProQuantity.getText());
			double price_prod = Double.valueOf(ProPrice.getText());
			String desc_prod = ProdDesc.getText();
			String id_cat = CatId.getValue();
			Products p=PodTable.getSelectionModel().getSelectedItem();
			
			String sql="UPDATE `products` SET `name_prod` =?,`quantity_prod`=?,`price_prod`=?,`desc_prod`=?,`id_cat`=?  WHERE `products`.`id_prod` = ?"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ps.setString(1, name_prod);
			ps.setInt(2, quantity_prod);
			ps.setDouble(3, price_prod);
			ps.setString(4, desc_prod);
			ps.setString(5, id_cat);
			ps.setInt(6, p.id_prod);
			ps.execute();
			
			
			ProdName.setText("");
	    	ProQuantity.setText("");
	    	ProPrice.setText("");
	    	ProdDesc.setText("");
	    	CatId.setPromptText("");
			
	    	getAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    		
    }
    
    @FXML
    void DeleteProd(ActionEvent event) {

    	int index=PodTable.getSelectionModel().getSelectedIndex();
    	
    	if(index>=0) {
    		
    		Products p=PodTable.getSelectionModel().getSelectedItem();
    		
    		delete(p.id_prod);
    		data.remove(index);
    	}
    	
    }
    
    public void delete(int id_prod) {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to delete?");
    	Optional<ButtonType> action = alert.showAndWait();
    	if(action.get() == ButtonType.OK) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM `products` WHERE `products`.`id_prod` = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, id_prod);

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
		
	}
    
    @FXML
    void Refreshbtb(ActionEvent event) {
    	getAll();
    }
    
    @FXML
    private RadioButton RbCat;

    @FXML
    private RadioButton RbId;

    @FXML
    private RadioButton RbName;

    @FXML
    private RadioButton RbPrice;

    @FXML
    private RadioButton RbQuantity;

    
    
    @FXML
    void Search(KeyEvent event) {

    	data.clear();
    	
    	if(RbId.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `products` WHERE `id_prod` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_prod = rs.getInt("id_prod");
    				String name_prod = rs.getString("name_prod");
    				int quantity_prod = rs.getInt("quantity_prod");
    				double price_prod = rs.getDouble("price_prod");
    				String desc_prod = rs.getString("desc_prod");
    				String id_cat = rs.getString("id_cat");
    				this.data.add(new Products(id_prod,name_prod, quantity_prod, price_prod, desc_prod,id_cat ));
    			}
    			
    			PodTable.setItems(data);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbName.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `products` WHERE `name_prod` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_prod = rs.getInt("id_prod");
    				String name_prod = rs.getString("name_prod");
    				int quantity_prod = rs.getInt("quantity_prod");
    				double price_prod = rs.getDouble("price_prod");
    				String desc_prod = rs.getString("desc_prod");
    				String id_cat = rs.getString("id_cat");
    				this.data.add(new Products(id_prod,name_prod, quantity_prod, price_prod, desc_prod,id_cat ));
    			}
    			
    			PodTable.setItems(data);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbQuantity.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `products` WHERE `quantity_prod` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_prod = rs.getInt("id_prod");
    				String name_prod = rs.getString("name_prod");
    				int quantity_prod = rs.getInt("quantity_prod");
    				double price_prod = rs.getDouble("price_prod");
    				String desc_prod = rs.getString("desc_prod");
    				String id_cat = rs.getString("id_cat");
    				this.data.add(new Products(id_prod,name_prod, quantity_prod, price_prod, desc_prod,id_cat ));
    			}
    			
    			PodTable.setItems(data);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbPrice.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `products` WHERE `price_prod` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_prod = rs.getInt("id_prod");
    				String name_prod = rs.getString("name_prod");
    				int quantity_prod = rs.getInt("quantity_prod");
    				double price_prod = rs.getDouble("price_prod");
    				String desc_prod = rs.getString("desc_prod");
    				String id_cat = rs.getString("id_cat");
    				this.data.add(new Products(id_prod,name_prod, quantity_prod, price_prod, desc_prod,id_cat ));
    			}
    			
    			PodTable.setItems(data);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbCat.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `products` WHERE `id_cat` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_prod = rs.getInt("id_prod");
    				String name_prod = rs.getString("name_prod");
    				int quantity_prod = rs.getInt("quantity_prod");
    				double price_prod = rs.getDouble("price_prod");
    				String desc_prod = rs.getString("desc_prod");
    				String id_cat = rs.getString("id_cat");
    				this.data.add(new Products(id_prod,name_prod, quantity_prod, price_prod, desc_prod,id_cat ));
    			}
    			
    			PodTable.setItems(data);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `products` WHERE `id_prod` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_prod = rs.getInt("id_prod");
    				String name_prod = rs.getString("name_prod");
    				int quantity_prod = rs.getInt("quantity_prod");
    				double price_prod = rs.getDouble("price_prod");
    				String desc_prod = rs.getString("desc_prod");
    				String id_cat = rs.getString("id_cat");
    				this.data.add(new Products(id_prod,name_prod, quantity_prod, price_prod, desc_prod,id_cat ));
    			}
    			
    			PodTable.setItems(data);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}
    }
    
}