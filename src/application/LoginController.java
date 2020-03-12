package application;

//import necessary packages
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The controller for the Login scene
 * 
 * @author Chenlu Jiang
 *
 */
public class LoginController {
	
	//Three javafx controls for the scene
	@FXML private Button exitbt;
	@FXML private Button receptionbt;
	@FXML private Button adminbt;
	
	/**
	 * The handle method for Admin button, once clicked it will transfer to the report scene
	 * 
	 * @param event
	 */
	@FXML
	private void handleAdminButton(ActionEvent event) {
		try {
			AnchorPane root1 = (AnchorPane)FXMLLoader.load(getClass().getResource("Reportsscene.fxml"));
			Scene scene1 = new Scene(root1);
			Stage stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
	        stage1.hide();
	        stage1.setScene(scene1);
	        stage1.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The handle method for Reception button, once clicked it transfers to Facial recognize scene
	 * 
	 * @param event
	 */
	@FXML
	private void handleReceptionButton(ActionEvent event) {
		try {
//			CameraController controller=new CameraController();
//			controller.init();
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("FirstJFX.fxml"));

			FXMLLoader loader=new FXMLLoader(getClass().getResource("FirstJFX.fxml"));
			BorderPane root=(BorderPane)loader.load();
			FXController controller=loader.getController();
			controller.setRootElement(root);
			controller.init();
//			controller.startCamera();
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
	 * The handle method for exit button, once clicked the program stop
	 * 
	 * @param event
	 */
	@FXML
	private void exit(ActionEvent event) {
		
		Stage stage = (Stage)exitbt.getScene().getWindow();
        stage.close();
	}
	
	
	
}
