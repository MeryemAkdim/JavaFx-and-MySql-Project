package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class OrdController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField CustID;

    @FXML
    private TableView<Orderes> OrdTab;

    @FXML
    private TextField ProdID;
    
    @FXML
    private DatePicker DateOrd;


    @FXML
    private TextField QuantityOrd;

    /*@FXML
    private TextField Total*/;

    @FXML
    private TextField Uprice;

    @FXML
    private TableColumn<Orderes, String> ccust_id;

    @FXML
    private TableColumn<Orderes, String> cid_ord;

    @FXML
    private TableColumn<Orderes, String> cord_date;

    @FXML
    private TableColumn<Orderes, String> cprice;

    @FXML
    private TableColumn<Orderes, String> cpro_id;

    @FXML
    private TableColumn<Orderes, String> cquantity_ord;

    @FXML
    private TableColumn<Orderes, String> ctot;
    
    ObservableList<Orderes> Ordata;

	//private TableView<Orderes> OrderTab;

    @FXML
    void AddOrd(ActionEvent event) {

    	
		int id_cust = Integer.valueOf(CustID.getText());
		int id_prod = Integer.valueOf(ProdID.getText());
		int quantity_order = Integer.valueOf(QuantityOrd.getText());
		double uprice = Double.valueOf(Uprice.getText());
		//String order_date = DateOrd.getAccessibleText();
		Date order_date = Date.valueOf(DateOrd.getValue());
		
		//double total = Double.valueOf(Total.getText());
    	
    	Orderes o = new Orderes( id_cust, id_prod, quantity_order,  uprice, order_date);
    	
    	CustID.setText("");
    	ProdID.setText("");
    	QuantityOrd.setText("");
    	Uprice.setText("");
    	DateOrd.setPromptText("");
    	//Total.setText("");
    	
    	Ordata.clear();
    	insertOrd(o);
    	getAll();
    }
    
    public static void insertOrd(Orderes o) {
		try {
			
			Connection connection = MysqlConnection.getDBConnection();
			
			
			String sql="INSERT INTO `orders` (`id_cust`, `id_prod`, `quantity_order`,`uprice`, `order_date`) VALUES (?,?,?,?,?)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ps.setInt(1, o.id_cust);
			ps.setInt(2, o.id_prod);
			ps.setInt(3, o.quantity_order);
			ps.setDouble(4, o.uprice);
			ps.setDate(5, o.order_date);
			//ps.setDouble(5, o.total);
			
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    void DeleteOrd(ActionEvent event) {

    	int index=OrdTab.getSelectionModel().getSelectedIndex();
    	
    	if(index>=0) {
    		
			Orderes o =  OrdTab.getSelectionModel().getSelectedItem();
    		
    		delete(o.id_order);
    		Ordata.remove(index);
    	}
    }
    
    public void delete(int id_order) {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to delete?");
    	Optional<ButtonType> action = alert.showAndWait();
    	
    	if(action.get() == ButtonType.OK) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM `orders` WHERE `orders`.`id_order` = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, id_order);

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
    void UpdateOrd(ActionEvent event) {

    	try {
    		Connection connection = MysqlConnection.getDBConnection();
			
    	//	int id_order = CustID.getInt("id_order");
			int id_cust = Integer.valueOf(CustID.getText());  
			int id_prod = Integer.valueOf(ProdID.getText());
			int quantity_order = Integer.valueOf(QuantityOrd.getText());
			double uprice = Double.valueOf(Uprice.getText());
			Date order_date = Date.valueOf(DateOrd.getValue());
			
			Orderes o =  OrdTab.getSelectionModel().getSelectedItem();
			
			String sql="UPDATE `orders` SET `id_cust` =?,`id_prod`=?,`quantity_order`=?,`uprice`=?,`order_date`=?  WHERE `orders`.`id_order` = ?"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ps.setInt(1, id_cust);
			ps.setInt(2, id_prod);
			ps.setInt(3, quantity_order);
			ps.setDouble(4, uprice);
			ps.setDate(5, order_date);
			ps.setInt(6, o.id_order);
			ps.execute();
			
			
			CustID.setText("");
	    	ProdID.setText("");
	    	QuantityOrd.setText("");
	    	Uprice.setText("");
	    	DateOrd.setPromptText("");
			
	    	getAll();
    	}catch(Exception e)	{
    		e.printStackTrace();
    	}
      }
    
    @FXML
    void mouseSelect(MouseEvent event) {

    	Orderes o = OrdTab.getSelectionModel().getSelectedItem();
    	
    	CustID.setText(o.id_cust+"");
    	ProdID.setText(o.id_prod+"");
    	QuantityOrd.setText(o.quantity_order+"");
    	Uprice.setText(o.uprice+"");
    	DateOrd.setPromptText(o.order_date+"");
    	
    }

    @FXML
    void initialize() {
       
    	Ordata=FXCollections.observableArrayList();
    	
    	//cid_prod.setCellValueFactory(new PropertyValueFactory<>("id_prod"));
    	cid_ord.setCellValueFactory(new PropertyValueFactory<>("id_order"));
    	ccust_id.setCellValueFactory(new PropertyValueFactory<>("id_cust"));
    	cpro_id.setCellValueFactory(new PropertyValueFactory<>("id_prod"));
    	cquantity_ord.setCellValueFactory(new PropertyValueFactory<>("quantity_order"));
    	cprice.setCellValueFactory(new PropertyValueFactory<>("uprice"));
    	cord_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));
    	ctot.setCellValueFactory(new PropertyValueFactory<>("total"));
    	 	
    	OrdTab.setItems(Ordata);
    	getAll();
    	
    }
    
    public void getAll() {
    	Ordata.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `orders`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_order = rs.getInt("id_order");
				int id_prod = rs.getInt("id_prod");
				int id_cust = rs.getInt("id_cust");
				int quantity_order = rs.getInt("quantity_order");
				double uprice = rs.getDouble("uprice");
				Date order_date = rs.getDate("order_date");
				double total = rs.getDouble("total");
				Ordata.add(new Orderes(id_order, id_cust, id_prod, quantity_order,  uprice, order_date, total));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    @FXML
    private TextField txt_search;
    
    @FXML
    private RadioButton RbCustID;

    @FXML
    private RadioButton RbOrdDate;

    @FXML
    private RadioButton RbOrdID;

    @FXML
    private RadioButton RbProID;
    
    @FXML
    void Search(KeyEvent event) {

    	Ordata.clear();
    	if(RbOrdID.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `orders` WHERE `id_order` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {		
    				this.Ordata.add(new Orderes(rs.getInt("id_order"), rs.getInt("id_cust"), rs.getInt("id_prod"), rs.getInt("quantity_order"),  rs.getDouble("uprice"), rs.getDate("order_date"), rs.getDouble("total")));
    			}
    			
    			OrdTab.setItems(Ordata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbCustID.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `orders` WHERE `id_cust` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {		
    				this.Ordata.add(new Orderes(rs.getInt("id_order"), rs.getInt("id_cust"), rs.getInt("id_prod"), rs.getInt("quantity_order"),  rs.getDouble("uprice"), rs.getDate("order_date"), rs.getDouble("total")));
    			}
    			
    			OrdTab.setItems(Ordata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbProID.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `orders` WHERE `id_prod` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {		
    				this.Ordata.add(new Orderes(rs.getInt("id_order"), rs.getInt("id_cust"), rs.getInt("id_prod"), rs.getInt("quantity_order"),  rs.getDouble("uprice"), rs.getDate("order_date"), rs.getDouble("total")));
    			}
    			
    			OrdTab.setItems(Ordata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else if(RbOrdDate.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `orders` WHERE `order_date` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {		
    				this.Ordata.add(new Orderes(rs.getInt("id_order"), rs.getInt("id_cust"), rs.getInt("id_prod"), rs.getInt("quantity_order"),  rs.getDouble("uprice"), rs.getDate("order_date"), rs.getDouble("total")));
    			}
    			
    			OrdTab.setItems(Ordata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}else {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `orders` WHERE `id_order` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {		
    				this.Ordata.add(new Orderes(rs.getInt("id_order"), rs.getInt("id_cust"), rs.getInt("id_prod"), rs.getInt("quantity_order"),  rs.getDouble("uprice"), rs.getDate("order_date"), rs.getDouble("total")));
    			}
    			
    			OrdTab.setItems(Ordata);
    			
    		}catch(Exception e) {
    			e.printStackTrace();

    		}
    	}
    }
    

}