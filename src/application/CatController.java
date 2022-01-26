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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CatController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private TextField CatId;

    @FXML
    private TextField CatName;


    @FXML
    private TableView<Category> CatTab;
    
    @FXML
    private TableColumn<Category, String> cid_cat;

    @FXML
    private TableColumn<Category, String> ccut_name;
    
    ObservableList<Category> data;


  
    @FXML
    void AddOrd(ActionEvent event) {

    	String id_cat = CatId.getText();
    	String name_cat = CatName.getText();
    	//String id_cat = CatId.getItems().toString();
    	
    	Category c = new Category(id_cat, name_cat);
    	CatId.setText("");
    	CatName.setText("");
    	
    	
    	data.clear();
    	insertCat(c);
    	getAll();
    }
    
    public static void insertCat(Category c) {
		try {
			
			Connection connection = MysqlConnection.getDBConnection();
			
			
			String sql="INSERT INTO `category` (`id_cat`, `name_cat`) VALUES (?,?)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ps.setString(1, c.id_cat);
			ps.setString(2, c.name_cat);				
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    void DeleteOrd(ActionEvent event) {

    	int index=CatTab.getSelectionModel().getSelectedIndex();
    	
    	if(index>=0) {
    		
    		Category c=CatTab.getSelectionModel().getSelectedItem();
    		
    		delete(c.id_cat);
    		data.remove(index);
    	}
    }
    
    public void delete(String id_cat) {
    	Alert alert  =new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure you want to delete?");
    	Optional<ButtonType> action = alert.showAndWait();
    	if(action.get() == ButtonType.OK) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM `category` WHERE `category`.`id_cat` = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setString(1, id_cat);

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    void UpdateOrd(ActionEvent event) {

    	try {
    		Connection connection = MysqlConnection.getDBConnection();
			
			String id_cat = CatId.getText();
			String name_cat = CatName.getText();
			Category c=CatTab.getSelectionModel().getSelectedItem();
			
			String sql="UPDATE `category` SET `id_cat`=?,`name_cat` =?  WHERE `category`.`id_cat` = ?"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ps.setString(1, id_cat);
			ps.setString(2, name_cat);
			ps.setString(3, c.id_cat);
			ps.execute();
			
			
	    	CatId.setText("");
			CatName.setText("");

			
	    	getAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void mouseSelect(MouseEvent event) {

		Category c=CatTab.getSelectionModel().getSelectedItem();
    	
    	CatName.setText(c.name_cat);
    	CatId.setText(c.id_cat);
    }

    @FXML
    void initialize() {
    	
    	data=FXCollections.observableArrayList();

    	cid_cat.setCellValueFactory(new PropertyValueFactory<>("id_cat"));
    	ccut_name.setCellValueFactory(new PropertyValueFactory<>("name_cat"));
    	
    	CatTab.setItems(data);
    	getAll();
    	
    }
    
    public void getAll() {
    	data.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `category`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {				
				String name_cat = rs.getString("name_cat");
				String id_cat = rs.getString("id_cat");
				data.add(new Category(id_cat,name_cat));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    @FXML
    void Refreshbtb(ActionEvent event) {
    	
    	getAll();
    }
    
    @FXML
    private TextField txt_search;
    
    @FXML
    private RadioButton RbId;

    @FXML
    private RadioButton RbName;
   
    
    @FXML
    void Search(KeyEvent event) {

    	data.clear();
    	if(RbId.isSelected()) {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `category` WHERE `id_cat` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {				
    				this.data.add(new Category(rs.getString(1), rs.getString(2)));
    			}
			
			CatTab.setItems(data);
			
		  }catch(Exception e) {
			e.printStackTrace();

		  }
    	}else {
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			
    			String sql="SELECT * FROM `category` WHERE `name_cat` like '%" +txt_search.getText()+"%'"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			//ps.setString(1, this.txt_search.getText());
    			
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {				
    				this.data.add(new Category(rs.getString(1), rs.getString(2)));
    			}
			
			CatTab.setItems(data);
			
		  }catch(Exception e) {
			e.printStackTrace();

		  }
    	}
    }

}
