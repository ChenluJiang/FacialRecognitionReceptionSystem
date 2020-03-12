package application;

import java.net.URL;
import java.sql.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author gaomingt
 *
 */
public class PiechartController implements Initializable {
	// connect fxml elements with the java file
	@FXML
	private DatePicker dpStartDate;
	@FXML
	private PieChart piechartFemale;
	@FXML
	private PieChart piechartMale;
	@FXML
	private Button btLeave;
	@FXML
	private Button btReturn;
	@FXML
	private DatePicker dpEndDate;

	// set default dates
	private Date startDate = java.sql.Date.valueOf("2000-10-10");
	private Date endDate = java.sql.Date.valueOf("2019-11-30");

	// the visit reasons for male and female
	private String operationFemale;
	 private String operationMale;
	JDBCController db = new JDBCController();

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 * 
	 * Initialize the pie charts with default dates
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Male data: " + db.get_malecategoryap(startDate, endDate));
		System.out.println("Female data: " + db.get_malecategoryap(startDate, endDate));
		System.out.println();
		// generate two pie charts when enter this scene
		updatePiechart();
	}

	/**
	 * Get data from the database and update the pie charts
	 */
	private void updatePiechart() {
		// generate data for the female pie chart
		ObservableList<Data> piechartDataFemale = FXCollections.observableArrayList();

		// store female visit reasons
		java.util.Iterator<Entry<Integer, Integer>> iterFemale = db.get_femalecategoryap(startDate, endDate).entrySet()
				.iterator();
		while (iterFemale.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iterFemale.next();
			if (entry.getKey() == 4)
				operationFemale = "pay tuition";
			else if (entry.getKey() == 2)
				operationFemale = "Submit assignments";
			else if (entry.getKey() == 0)
				operationFemale = "Making appointment";
			else if (entry.getKey() == 1)
				operationFemale = "Rent locker";
			else if (entry.getKey() == 3)
				operationFemale = "Report lost";
			else if (entry.getKey() == 5)
				operationFemale = "Complaint";
			else
				operationFemale = "Borrow stuff";
			piechartDataFemale.add(new PieChart.Data(operationFemale, entry.getValue()));
		}
		if (piechartDataFemale.isEmpty()) {
			piechartFemale.setTitle("No record.");
			piechartFemale.setData(piechartDataFemale);
		} else {
			piechartFemale.setTitle("Female\nFrom " + startDate + " to " + endDate);
			piechartFemale.setData(piechartDataFemale);
		}

		// generate data for the male pie chart
		ObservableList<Data> piechartDataMale = FXCollections.observableArrayList();

		// store male visit reasons
		java.util.Iterator<Entry<Integer, Integer>> iterMale = db.get_malecategoryap(startDate, endDate).entrySet()
				.iterator();
		while (iterMale.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iterMale.next();
			if (entry.getKey() == 4)
				operationMale = "pay tuition";
			else if (entry.getKey() == 2)
				operationMale = "Submit assignments";
			else if (entry.getKey() == 0)
				operationMale = "Making appointment";
			else if (entry.getKey() == 1)
				operationMale = "Rent locker";
			else if (entry.getKey() == 3)
				operationMale = "Report lost";
			else if (entry.getKey() == 5)
				operationMale = "Complaint";
			else
				operationMale = "Borrow stuff";
			piechartDataMale.add(new PieChart.Data(operationMale, entry.getValue()));
		}
		if (piechartDataMale.isEmpty()) {
			piechartMale.setTitle("No record.");
			piechartMale.setData(piechartDataMale);
		} else {
			piechartMale.setTitle("Male\nFrom " + startDate + " to " + endDate);
			piechartMale.setData(piechartDataMale);
		}
	}

	/**
	 * @param event get the action from the date picker and update startDate
	 */
	@FXML
	private void selectStartDate(ActionEvent event) {
		// set the start date 
		startDate = java.sql.Date.valueOf(dpStartDate.getValue());
		System.out.println("New Start Date -> " + dpStartDate.getValue());
		updatePiechart();
	}

	/**
	 * @param event get the action from the date picker and update endDate
	 */
	@FXML
	private void selectEndDate(ActionEvent event) {
		// set the end date 
		endDate = java.sql.Date.valueOf(dpEndDate.getValue());
		System.out.println("New End Date -> " + dpEndDate.getValue());
		updatePiechart();
	}

	/**
	 * @param event get the action from the date picker and change scene to the report page
	 */
	@FXML
	void handlebtReturn(ActionEvent event) {
		try {
			// get report scene
			AnchorPane root3 = (AnchorPane) FXMLLoader.load(getClass().getResource("Reportsscene.fxml"));
			Scene scene3 = new Scene(root3);
			Stage stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// change to the report scene
			stage3.hide();
			stage3.setScene(scene3);
			stage3.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param event get the action from the date picker and change scene to the login page
	 */
	@FXML
	void handlebtLeave(ActionEvent event) {
		try {
			// get Loginscene
			AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Loginscene.fxml"));
			Scene scene2 = new Scene(root2);
			Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// change to the login scene
			stage2.hide();
			stage2.setScene(scene2);
			stage2.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
