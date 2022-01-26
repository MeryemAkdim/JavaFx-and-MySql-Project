package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Anch;

    @FXML
    private ProgressBar ProBar;

    @FXML
    private Label prog;

    @FXML
    void initialize() {
    	Task<?> task = taskWorker(10);
    	ProBar.progressProperty().unbind();
    	ProBar.progressProperty().bind(task.progressProperty());
    	
    	task.setOnSucceeded(e->{
    		Stage stage = (Stage) Anch.getScene().getWindow();
    		stage.close();
    		try {
        		Parent root=FXMLLoader.load(getClass().getResource("FxmlLog.fxml"));
        		Stage stage2 = new Stage();
        		Scene scene = new Scene(root);
        		stage2.setScene(scene);
        		//stage2.initStyle(StageStyle.UNDECORATED);
        		stage2.show();
        	}catch(Exception ex){
        	
        	}
    	});
    	
    		
    	Thread thrad = new Thread(task);
    	thrad.start();
    }
    
    private Task<?> taskWorker(int seconds) {
    	return new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				for(int i=0; i<seconds; i++) {
					updateProgress(i, seconds);
					Thread.sleep(1000);
				}
				
				return true;
			}
    		
    	};
    }

}
