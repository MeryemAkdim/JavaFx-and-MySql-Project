package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NavController {
	
	 @FXML
	    private Label CatNbr;

	    @FXML
	    private Label CustNbr;

	    @FXML
	    private Label OrdNbr;

	    @FXML
	    private Label PrNbr;

 /**********************************************/
    @FXML
    private AnchorPane slide;
    
    @FXML
    private BorderPane bp;


    @FXML
    void mouseSelect(MouseEvent event) {

    }
    
    /**************************************************************************/
    
    @FXML
    void Char(MouseEvent event) {
    	bp.setCenter(slide);
    }

    
    @FXML
    void cat(MouseEvent event) {

    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource("CatFrm.fxml"));
		} catch (IOException e) {
			Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	bp.setCenter(root);
    }

    @FXML
    void cust(MouseEvent event) {
    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource("CustFrm.fxml"));
		} catch (IOException e) {
			Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	bp.setCenter(root);
    	
    }

    @FXML
    void dash(MouseEvent event) {
    	
    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource("Dash.fxml"));
		} catch (IOException e) {
			Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	bp.setCenter(root);
    	
    }

    @FXML
    void exit(MouseEvent event) throws IOException {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to Logout?");
    	Optional<ButtonType> action = alert.showAndWait();
    	if(action.get() == ButtonType.OK) {
    		Parent root  =FXMLLoader.load(getClass().getResource("FxmlLog.fxml"));
        	Scene scene = new Scene(root);
    		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        	
        	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        	
        	window.setScene(scene);
        	window.show();
    	}
    	
    }

    @FXML
    void ord(MouseEvent event) {

    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource("OrdFrm.fxml"));
		} catch (IOException e) {
			Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	bp.setCenter(root);
    }

    @FXML
    void pro(MouseEvent event) {
    	
    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource("ProdFrm.fxml"));
		} catch (IOException e) {
			Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	bp.setCenter(root);
    }
    
    /*************************************************************/
    
    @FXML
    private ColorPicker color;
        
    @FXML
    void Pick(ActionEvent event) {

    	Color mycolor = color.getValue();
    	slide.setBackground(new Background(new BackgroundFill(mycolor, null, null)));
    }

    
    @FXML
    private BarChart<String, Integer> Char;
    
    @FXML
    private BarChart<String, Date> CharD;
    
    private void Charb() {
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT id_prod,COUNT(*) FROM `orders` GROUP BY id_prod";


			XYChart.Series<String, Integer> series = new XYChart.Series<>();
			
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
			}
			
			Char.getData().add(series);
			
			
		}catch(Exception e) {
			
		}
    }
    
   
    @FXML
    void initialize() {
    	
    	Charb();
    	//Chard();
    }
    
    

    
}
