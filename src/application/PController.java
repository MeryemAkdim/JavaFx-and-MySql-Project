package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.io.InputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PController {

    @FXML
    private AnchorPane P;
    
    @FXML
    private TableColumn<Bill, String> BPname;

    @FXML
    private TableColumn<Bill,String> BPquantity;

    @FXML
    private TableColumn<Bill,String> BPrice;

    @FXML
    private TableColumn<Bill, String> BPtotal;

    @FXML
    private TableView<Bill> BillTable;

    @FXML
    private TableView<Products> PodTable;

    @FXML
    private TextField ProPrice;

    @FXML
    private TextField ProQuantity;

    @FXML
    private TextField ProdName;

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
    private Label tot;

    
    ObservableList<Products> data;
    ObservableList<Bill> dataBill;
    
    /*Stage thisStage;
    LogController controller;
    

	public PController(Stage stage, LogController logController) {
		thisStage=stage;
		controller=logController;
	}*/

	@FXML
    void initialize() {
    	
    	dataBill=FXCollections.observableArrayList();
    	
    	BPname.setCellValueFactory(new PropertyValueFactory<>("bname_prod"));
    	BPquantity.setCellValueFactory(new PropertyValueFactory<>("bquantity_prod"));
    	BPrice.setCellValueFactory(new PropertyValueFactory<>("bprice_prod"));
    	BPtotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    	
    	BillTable.setItems(dataBill);
    	getBillAll();
    	
    	data=FXCollections.observableArrayList();
    	
    	cid_prod.setCellValueFactory(new PropertyValueFactory<>("id_prod"));
    	cname_prod.setCellValueFactory(new PropertyValueFactory<>("name_prod"));
    	cquntity_prod.setCellValueFactory(new PropertyValueFactory<>("quantity_prod"));
    	cprice_prod.setCellValueFactory(new PropertyValueFactory<>("price_prod"));
    	cdesc_prod.setCellValueFactory(new PropertyValueFactory<>("desc_prod"));
    	cid_cat.setCellValueFactory(new PropertyValueFactory<>("id_cat"));

    	 	
    	PodTable.setItems(data);
    	getAll();
    	
    	getTotal();
    	
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
			// TODO: handle exception
		}
    }
    
    public void getTotal() {
    	
		try {
			Connection connection = MysqlConnection.getDBConnection();
	    	
	    	String sql="SELECT SUM(total) From `billing`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			tot.setText(rs.getString(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void getBillAll() {
    	dataBill.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `billing`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_bill = rs.getInt("id_bill");
				String bname_prod = rs.getString("bname_prod");
				int bquantity_prod = rs.getInt("bquantity_prod");
				double bprice_prod = rs.getDouble("bprice_prod");
				double total = rs.getDouble("total");
				dataBill.add(new Bill(id_bill,bname_prod, bquantity_prod, bprice_prod, total));
				
			}
		} catch (Exception e) {
			// TODO: handle exceptions
		}
    }
    
    @FXML
    void mouseSelect(MouseEvent event) {
    	
    	Products p = PodTable.getSelectionModel().getSelectedItem();
    	
    	ProdName.setText(p.name_prod);
    	ProPrice.setText(p.price_prod+" ");

    }
    
    @FXML
    void Add_To_Bill(ActionEvent event) {

    	String bname_prod = ProdName.getText() ;
    	int bquantity_prod = Integer.valueOf(ProQuantity.getText());
    	double bprice_prod = Double.valueOf(ProPrice.getText());
    	
    	Bill b = new Bill(bname_prod, bquantity_prod, bprice_prod);
    	ProdName.setText("");
    	ProQuantity.setText("");
    	ProPrice.setText("");
    	
    	dataBill.clear();
    	insertBill(b);
    	getBillAll();
    	//dataBill.add(b);
    	
    }
    
    public static void insertBill(Bill b) {
		try {
			
			Connection connection = MysqlConnection.getDBConnection();
			
			
			String sql="INSERT INTO `billing` (`bname_prod`, `bquantity_prod`, `bprice_prod`) VALUES (?,?,?)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setString(1, b.bname_prod);
			ps.setInt(2, b.bquantity_prod);
			ps.setDouble(3, b.bprice_prod);
			
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    void Reset(ActionEvent event) {

    	try {
			Connection connection = MysqlConnection.getDBConnection();
			String sql="DELETE FROM `billing` "; 
			PreparedStatement ps=connection.prepareStatement(sql);
			

			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	dataBill.clear();
    	//getBillAll();
    }
    
    @FXML
    void Calculate(ActionEvent event) {

    	try {
			Connection connection = MysqlConnection.getDBConnection();
	    	
	    	String sql="SELECT SUM(total) From `billing`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			tot.setText(rs.getString(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void onPrint(ActionEvent event) {
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			String DB_CONNECTION = "jdbc:mysql://localhost/inventery_system";
			Connection connection = DriverManager.getConnection(DB_CONNECTION, "root", "");
			
			InputStream input = this.getClass().getResourceAsStream("Blank_A4.jrxml");
			
			JasperDesign design = JRXmlLoader.load(input);

			JasperReport jr=JasperCompileManager.compileReport(design);
			JasperPrint jp=JasperFillManager.fillReport(jr,null,connection);
			JasperViewer.viewReport(jp,false);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    @FXML
    void Logout(MouseEvent event) throws IOException {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to Logout?");
    	Optional<ButtonType> action = alert.showAndWait();
    	if(action.get() == ButtonType.OK) {
    		Parent root  =FXMLLoader.load(getClass().getResource("FxmlLog.fxml"));
        	Scene scene = new Scene(root);
    		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        	
        	window.setScene(scene);
        	window.show();
    	}
    	
    }

}
