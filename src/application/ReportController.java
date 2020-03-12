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
 * @author gaomingt, Chenlu Jiang
 *
 */
public class ReportController {
	// connect fxml elements with the java file
	@FXML
	private Button btCategory;
	@FXML
	private Button btFrequency;
	@FXML
	private Button btLeave;

	/**
	 * @param event get the action from the date picker and change scene to the login page
	 */
	@FXML
	private void handlebtLeave(ActionEvent event) {
		try {
			// get Loginscene
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Loginscene.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.hide();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event get the action from the date picker and change scene to the category report page
	 */
	@FXML
	private void handlebtCategory(ActionEvent event) {
		try {
			// get category report scene
			AnchorPane root1 = (AnchorPane) FXMLLoader.load(getClass().getResource("Careportscene.fxml"));
			Scene scene1 = new Scene(root1);
			Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// change to the category report scene
			stage1.hide();
			stage1.setScene(scene1);
			stage1.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event get the action from the date picker and change scene to the frequency report page
	 */
	@FXML
	private void handlebtFrequency(ActionEvent event) {
		try {
			// get frequency report scene
			AnchorPane root4 = (AnchorPane) FXMLLoader.load(getClass().getResource("Freportscene.fxml"));
			Scene scene4 = new Scene(root4);
			Stage stage4 = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// change to frequency report scene
			stage4.hide();
			stage4.setScene(scene4);
			stage4.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
