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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author gaomingt
 *
 */
public class BarchartController implements Initializable {
	
	// connect fxml elements with the java file
	@FXML
	private DatePicker dpStartDate;
	@FXML
	private BarChart barchart;
	@FXML
	private Button btLeave;
	@FXML
	private Button btReturn;
	@FXML
	private DatePicker dpEndDate;
	
	// set default dates
	private Date startDate = java.sql.Date.valueOf("2000-10-10");
	private Date endDate = java.sql.Date.valueOf("2019-11-30");

	// the visit reason
	private String operation;
	
	JDBCController db = new JDBCController();
	
	// instantiate XYData for the barchart
	Series<String, Integer> dataset = new XYChart.Series<>();

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 * 
	 * Initialize the bar chart with default dates
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("DATA: " + db.get_categoryap(startDate, endDate));
		System.out.println();
		// generate the bar chart when enter this scene
		updateBarchart();
	}

	/**
	 * Get data from the database and update the bar chart
	 */
	private void updateBarchart() {
		// clean the data set before updating the bar chart
		dataset.getData().clear();
		barchart.getData().clear();

		java.util.Iterator<Entry<Integer, Integer>> iter = db.get_categoryap(startDate, endDate).entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iter.next();
			System.out.println(entry.getValue());
			if (entry.getKey() == 4)
				operation = "pay tuition";
			else if (entry.getKey() == 2)
				operation = "Submit assignments";
			else if (entry.getKey() == 0)
				operation = "Making appointment";
			else if (entry.getKey() == 1)
				operation = "Rent locker";
			else if (entry.getKey() == 3)
				operation = "Report lost";
			else if (entry.getKey() == 5)
				operation = "Complaint";
			else
				operation = "Borrow stuff";
			dataset.getData().add(new XYChart.Data<>(operation, entry.getValue()));
		}
		if (dataset.equals(null)) {
			barchart.setTitle("No record.");
			barchart.getData().addAll(dataset);
		} else {
			barchart.setTitle("Visit by category\nFrom " + startDate + " to " + endDate);
			barchart.getData().addAll(dataset);
		}
	}

	
	/**
	 * @param event get the action from the date picker and update startDate
	 */
	@FXML
	private void selectStartDate(ActionEvent event) {
		// set the start date 
		startDate = java.sql.Date.valueOf(dpStartDate.getValue());

		System.out.println("DATA -> From " + startDate + " to " + endDate + ": " + db.get_categoryap(startDate, endDate));
		System.out.println();
		updateBarchart();
	}

	/**
	 * @param event get the action from the date picker and update endDate
	 */
	@FXML
	private void selectEndDate(ActionEvent event) {
		// set the end date
		endDate = java.sql.Date.valueOf(dpEndDate.getValue());

		System.out.println("DATA -> From " + startDate + " to " + endDate + ": " + db.get_categoryap(startDate, endDate));
		System.out.println();
		updateBarchart();
	}

	/**
	 * @param event get the action from the date picker and change scene to the report page
	 */
	@FXML
	void handlebtReturn(ActionEvent event) {
		try {
			// get Reportsscene
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
