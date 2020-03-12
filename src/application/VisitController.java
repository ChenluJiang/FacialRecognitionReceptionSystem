package application;

import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author gaomingt, Chenlu Jiang
 *
 */
public class VisitController {

	// connect fxml elements with the java file
	@FXML
	private TextArea taDetail;
	@FXML
	private DatePicker dpVisitDate;
	@FXML
	private Button btSubmit;
	@FXML
	private Button btReturn;
	@FXML
	private Label lblDate;
	@FXML
	private MenuButton mbt;
	@FXML
	private MenuItem mi1;
	@FXML
	private MenuItem mi2;
	@FXML
	private MenuItem mi3;
	@FXML
	private MenuItem mi4;
	@FXML
	private MenuItem mi5;
	@FXML
	private MenuItem mi6;
	@FXML
	private MenuItem mi7;

	// the visit reason
	private int choice;
	// will be set to the user id
	private int theID;
	private Date day;

	// get data from another controller and set the id
	public void setID(int id) {
		this.theID = id;
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectMake(ActionEvent event) {
		lblDate.setVisible(true);
		dpVisitDate.setVisible(true);
		mbt.setText(mi1.getText());
		// enable the user to enter details
		taDetail.setEditable(true);
		taDetail.setText("");
		// choose make appointment
		choice = 0;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectRent(ActionEvent event) {
		JDBCController db = new JDBCController();
		int i = db.getLockerId();
		lblDate.setVisible(false);
		dpVisitDate.setVisible(false);
		mbt.setText(mi2.getText());
		taDetail.setEditable(false);
		// show locker number and password
		taDetail.setText("Locker number: " + i);
		taDetail.appendText("\n" + db.getPassword(i));
		// choose rent locker
		choice = 1;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectSubmit(ActionEvent event) {
		lblDate.setVisible(false);
		dpVisitDate.setVisible(false);
		mbt.setText(mi3.getText());
		taDetail.setEditable(true);
		taDetail.setText("");
		// choose submit assignments
		choice = 2;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectReport(ActionEvent event) {
		lblDate.setVisible(false);
		dpVisitDate.setVisible(false);
		mbt.setText(mi4.getText());
		taDetail.setEditable(true);
		taDetail.setText("");
		// choose report lost
		choice = 3;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectPay(ActionEvent event) {
		lblDate.setVisible(false);
		dpVisitDate.setVisible(false);
		mbt.setText(mi5.getText());
		taDetail.setEditable(true);
		taDetail.setText("");
		// choose pay tuition
		choice = 4;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectComplain(ActionEvent event) {
		lblDate.setVisible(false);
		dpVisitDate.setVisible(false);
		mbt.setText(mi6.getText());
		taDetail.setEditable(true);
		taDetail.setText("");
		// choose complain
		choice = 5;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the menu button and update the layout of the scene 
	 */
	@FXML
	private void selectBorrow(ActionEvent event) {
		lblDate.setVisible(false);
		dpVisitDate.setVisible(false);
		mbt.setText(mi7.getText());
		taDetail.setEditable(true);
		taDetail.setText("");
		// choose borrow stuff
		choice = 6;
		btSubmit.setDisable(false);
	}

	/**
	 * @param event get the action from the date picker and update day
	 */
	@FXML
	private void selectStartDate(ActionEvent event) {
		// set the date
		day = java.sql.Date.valueOf(dpVisitDate.getValue());
	}

	/**
	 * @param event get the action from the submit button and insert data to the database
	 * and then change to the final page
	 */
	@FXML
	private void handlebtSubmit(ActionEvent event) {
		// instantiate the database
		JDBCController db = new JDBCController();
		// choose the visit reason
		try {
			switch (choice) {
			case 0:
				db.insertAppointment(day, theID, taDetail.getText());
				break;
			case 1:
				db.insert_general(theID, choice, "Rent locker");
				break;
			case 2:
				db.insert_general(theID, choice, taDetail.getText());
				break;
			case 3:
				db.insert_general(theID, choice, taDetail.getText());
				break;
			case 4:
				db.insert_general(theID, choice, taDetail.getText());
				break;
			case 5:
				db.insert_general(theID, choice, taDetail.getText());
				break;
			case 6:
				db.insert_general(theID, choice, taDetail.getText());
				break;
			}
			// get Finalscene
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Finalscene.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			FinalController visit = loader.getController();
			// transmit user id to the next scene
			visit.setID(theID);
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// change to the final scene
			stage.hide();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event get the action from the date picker and change scene to the login page
	 */
	@FXML
	private void handlebtReturn(ActionEvent event) {
		try {
			// get Loginscene
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Loginscene.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// change to the login scene
			stage.hide();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
