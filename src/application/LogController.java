package application;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogController {
	

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    @FXML
    private Button userlogin;
    
    @FXML
    private RadioButton RbAd;

    @FXML
    private RadioButton RbCs;


    @FXML
    void login(ActionEvent event) throws IOException {
    	
    	if(RbAd.isSelected()) {
    		if(username.getText().toString().equals("Admin") && password.getText().toString().equals("Admin") ) {
        		Parent root  =FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            	Scene scene = new Scene(root);
        		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            	
            	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            	
            	window.setScene(scene);
            	window.show();
        	}else if(username.getText().toString().equals("") && password.getText().toString().equals("")){
        		Alert alert  =new Alert(AlertType.INFORMATION);
            	//alert.setTitle("Incore");
            	alert.setHeaderText(null);
            	alert.setContentText("Please Enter Both User Name and Password");
            	alert.showAndWait();
        	}
        	else {
        		Alert alert  =new Alert(AlertType.ERROR);
            	//alert.setTitle("Incore");
            	alert.setHeaderText(null);
            	alert.setContentText("Incorrect User Name or Password");
            	alert.showAndWait();
        	}
    	}else if(RbCs.isSelected()) {
    		if(username.getText().toString().equals("Admin2") && password.getText().toString().equals("Admin2") ) {
        		Parent root  =FXMLLoader.load(getClass().getResource("BillForm.fxml"));
            	Scene scene = new Scene(root);
        		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            	
            	
            	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            	//window.initStyle(StageStyle.UNDECORATED);
            	window.setScene(scene);
            	window.show();
        	}else if(username.getText().toString().equals("") && password.getText().toString().equals("")){
        		Alert alert  =new Alert(AlertType.INFORMATION);
            	//alert.setTitle("Incore");
            	alert.setHeaderText(null);
            	alert.setContentText("Please Enter Both User Name and Password");
            	alert.showAndWait();
        	}
        	else {
        		Alert alert  =new Alert(AlertType.ERROR);
            	//alert.setTitle("Incore");
            	alert.setHeaderText(null);
            	alert.setContentText("Incorrect User Name or Password");
            	alert.showAndWait();
        	}
    	}
    	
    	
    }
    
    @FXML
    void exit(MouseEvent event) {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to delete?");
    	Optional<ButtonType> action = alert.showAndWait();
    	
    	if(action.get() == ButtonType.OK) {
    		System.exit(0);
    	}
    }


}
