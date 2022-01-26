package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CustController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Add;

    @FXML
    private TextField CustLN;

    @FXML
    private TextField CustName;

    @FXML
    private TextField Ph;
    
    @FXML
    private TableView<Customers> CustTab;

    @FXML
    private TableColumn<Customers, String> cadd_cust;

    @FXML
    private TableColumn<Customers, String> cid_cust;

    @FXML
    private TableColumn<Customers, String> cname_cust;

    @FXML
    private TableColumn<Customers, String> cname_lastname;

    @FXML
    private TableColumn<Customers, String> cph_cust;
    
    ObservableList<Customers> Cdata;


    @FXML
    void AddCust(ActionEvent event) {

    	String name_cust = CustName.getText();
    	String lastname_cust = CustLN.getText();
    	String adress_cust = Add.getText();
    	int phoneNbr_cust= Integer.valueOf(Ph.getText());

    	
    	Customers c = new Customers(name_cust ,lastname_cust, adress_cust,  phoneNbr_cust) ;
    	CustName.setText("");
    	CustLN.setText("");
    	Add.setText("");
    	Ph.setText("");
    	
    	Cdata.clear();
    	insertCust(c);
    	getAll();
    }
    
    public static void insertCust(Customers c) {
		try {
			
			Connection connection = MysqlConnection.getDBConnection();
			
			
			String sql="INSERT INTO `customers` (`name_cust`, `lastname_cust`, `adress_cust`,`phoneNbr_cust`) VALUES (?,?,?,?)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, c.name_cust);
			ps.setString(2, c.lastname_cust);
			ps.setString(3, c.adress_cust);
			ps.setInt(4, c.phoneNbr_cust);
			//ps.setString(5, p.id_cat);
			
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    void DeleteCust(ActionEvent event) {
    	
    	int index=CustTab.getSelectionModel().getSelectedIndex();
    	
    	if(index>=0) {
    		
    		Customers c=CustTab.getSelectionModel().getSelectedItem();
    		
    		delete(c.id_cust);
    		Cdata.remove(index);
    	}
    }
    
    public void delete(int id_cust) {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to delete?");
    	Optional<ButtonType> action = alert.showAndWait();
    	
    	if(action.get() == ButtonType.OK) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM `customers` WHERE `customers`.`id_cust` = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, id_cust);

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
    void UpdateCust(ActionEvent event) {

    	try {
    		Connection connection = MysqlConnection.getDBConnection();
			
    		String name_cust = CustName.getText();
        	String lastname_cust = CustLN.getText();
        	String adress_cust = Add.getText();
        	int phoneNbr_cust= Integer.valueOf(Ph.getText());
        	
			Customers c =CustTab.getSelectionModel().getSelectedItem();
			
			String sql="UPDATE `customers` SET `name_cust` =?,`lastname_cust`=?,`adress_cust`=?,`phoneNbr_cust`=?  WHERE `customers`.`id_cust` = ?"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ps.setString(1, name_cust);
			ps.setString(2, lastname_cust);
			ps.setString(3, adress_cust);
			ps.setInt(4, phoneNbr_cust);
			ps.setInt(5, c.id_cust);

			ps.execute();
			
			
			CustName.setText("");
	    	CustLN.setText("");
	    	Add.setText("");
	    	Ph.setText("");
			
	    	getAll();
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }   	

    @FXML
    void mouseSelect(MouseEvent event) {

    	Customers c = CustTab.getSelectionModel().getSelectedItem();
    	
    	CustName.setText(c.name_cust);
    	CustLN.setText(c.lastname_cust);
    	Add.setText(c.adress_cust);
    	Ph.setText(c.phoneNbr_cust+"");
    }

    @FXML
    void initialize() {
       
    	Cdata=FXCollections.observableArrayList();

    	
    	cid_cust.setCellValueFactory(new PropertyValueFactory<>("id_cust"));
    	cname_cust.setCellValueFactory(new PropertyValueFactory<>("name_cust"));
    	cname_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname_cust"));
    	cadd_cust.setCellValueFactory(new PropertyValueFactory<>("adress_cust"));
    	cph_cust.setCellValueFactory(new PropertyValueFactory<>("phoneNbr_cust"));
    	
    	CustTab.setItems(Cdata);
    	getAll();
    }
    
    public void getAll() {
    	Cdata.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `customers`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_cust = rs.getInt("id_cust");
				String name_cust = rs.getString("name_cust");
				String lastname_cust = rs.getString("lastname_cust");
				String adress_cust = rs.getString("adress_cust");
				int phoneNbr_cust = rs.getInt("phoneNbr_cust");

				Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
				
			}
		} catch (Exception e) {
			
		}
    
    }
    
    @FXML
    private TextField txt_search;
   
    @FXML
    private RadioButton RbAdd;

    @FXML
    private RadioButton RbID;

    @FXML
    private RadioButton RbLN;

    @FXML
    private RadioButton RbNAme;

    @FXML
    private RadioButton RbPh;
    
    @FXML
    void Search(KeyEvent event) {

    	Cdata.clear();
    	if(RbID.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `customers` WHERE `id_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_cust = rs.getInt("id_cust");
    				String name_cust = rs.getString("name_cust");
    				String lastname_cust = rs.getString("lastname_cust");
    				String adress_cust = rs.getString("adress_cust");
    				int phoneNbr_cust = rs.getInt("phoneNbr_cust");
    				this.Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
    			}
    			
    			CustTab.setItems(Cdata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbNAme.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `customers` WHERE `name_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_cust = rs.getInt("id_cust");
    				String name_cust = rs.getString("name_cust");
    				String lastname_cust = rs.getString("lastname_cust");
    				String adress_cust = rs.getString("adress_cust");
    				int phoneNbr_cust = rs.getInt("phoneNbr_cust");
    				this.Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
    			}
    			
    			CustTab.setItems(Cdata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbLN.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `customers` WHERE `lastname_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_cust = rs.getInt("id_cust");
    				String name_cust = rs.getString("name_cust");
    				String lastname_cust = rs.getString("lastname_cust");
    				String adress_cust = rs.getString("adress_cust");
    				int phoneNbr_cust = rs.getInt("phoneNbr_cust");
    				this.Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
    			}
    			
    			CustTab.setItems(Cdata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbAdd.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `customers` WHERE `adress_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_cust = rs.getInt("id_cust");
    				String name_cust = rs.getString("name_cust");
    				String lastname_cust = rs.getString("lastname_cust");
    				String adress_cust = rs.getString("adress_cust");
    				int phoneNbr_cust = rs.getInt("phoneNbr_cust");
    				this.Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
    			}
    			
    			CustTab.setItems(Cdata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbPh.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `customers` WHERE `phoneNbr_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_cust = rs.getInt("id_cust");
    				String name_cust = rs.getString("name_cust");
    				String lastname_cust = rs.getString("lastname_cust");
    				String adress_cust = rs.getString("adress_cust");
    				int phoneNbr_cust = rs.getInt("phoneNbr_cust");
    				this.Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
    			}
    			
    			CustTab.setItems(Cdata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `customers` WHERE `id_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {	
    				int id_cust = rs.getInt("id_cust");
    				String name_cust = rs.getString("name_cust");
    				String lastname_cust = rs.getString("lastname_cust");
    				String adress_cust = rs.getString("adress_cust");
    				int phoneNbr_cust = rs.getInt("phoneNbr_cust");
    				this.Cdata.add(new Customers(id_cust ,name_cust, lastname_cust, adress_cust, phoneNbr_cust));
    			}
    			
    			CustTab.setItems(Cdata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}
    }
}
