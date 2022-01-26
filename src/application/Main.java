package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	/*public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("application/ProgBar.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
    		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}*/
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		AnchorPane root=FXMLLoader.load(getClass().getResource("ProgBar.fxml"));
		stage.setScene(new Scene(root));
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
		
	}
	
	
	
}
