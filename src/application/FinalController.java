package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The controller for the final scene
 * 
 * @author Chenlu Jiang
 *
 */
public class FinalController {
	
	private int theID;// use to store the student id

	 /**
	  * The method is used to received the student id from last scene
	  * 
	 * @param id int indicates student id
	 */
	public void setID(int id) {
	    	this.theID = id;
	    }
	
	//javafx controls for the scene
	@FXML private Button btContinue;
	@FXML private Button btLeave;
	
	/**
	 * The handle method for continue button, once clicked it will transfer to visit scene
	 * 
	 * @param event
	 */
	@FXML
	private void handlebtContinue(ActionEvent event) {
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Visitscene.fxml"));
			AnchorPane root=(AnchorPane)loader.load();
			VisitController visit=loader.getController();
			visit.setID(theID);
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        stage.hide();
	        stage.setScene(scene);
	        stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The handle method for leave button, once clicked it will transfer to login scene
	 * 
	 * @param event
	 */
	@FXML
	private void handlebtLeave(ActionEvent event) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Loginscene.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        stage.hide();
	        stage.setScene(scene);
	        stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
