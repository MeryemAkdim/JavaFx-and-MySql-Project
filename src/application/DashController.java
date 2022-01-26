package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DashController {

    @FXML
    private Label CatNbr;

    @FXML
    private Label CustNbr;

    @FXML
    private Label OrdNbr;

    @FXML
    private Label PrNbr;
    
    @FXML
    private AnchorPane slide;
    
    @FXML
    void initialize() {
    	
    	getOrdNbr();
    	getCustNbr();
    	getProdNbr();
    	getCatNbr();
    }
    
    public void getOrdNbr() {
    	
		try {
			Connection connection = MysqlConnection.getDBConnection();
	    	
	    	String sql="SELECT count(id_order) From `orders` "; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			OrdNbr.setText(rs.getString(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void getCustNbr() {
    	
		try {
			Connection connection = MysqlConnection.getDBConnection();
	    	
	    	String sql="SELECT count(id_cust) From `customers`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			CustNbr.setText(rs.getString(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void getProdNbr() {
    	
		try {
			Connection connection = MysqlConnection.getDBConnection();
	    	
	    	String sql="SELECT count(id_prod) From `products`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			PrNbr.setText(rs.getString(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void getCatNbr() {
    	
		try {
			Connection connection = MysqlConnection.getDBConnection();
	    	
	    	String sql="SELECT count(id_cat) From `category`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			CatNbr.setText(rs.getString(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    private Pane pan;
    
    @FXML
    private ColorPicker color;
        
    @FXML
    void Pick(ActionEvent event) {

    	Color mycolor = color.getValue();
    	slide.setBackground(new Background(new BackgroundFill(mycolor, null, null)));
    	//pan.setBackground(new Background(new BackgroundFill(mycolor, null, null)));
    	
    }

}

