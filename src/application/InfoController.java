package application;

//import necessary packages
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller for info scene
 * 
 * @author Chenlu Jiang
 *
 */
public class InfoController {
	

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//use to transform date
	private String operation; // indicates the operation student wants to do
	private int theID;//the to pass the id of the recognized student

	//javafx controls for the scene
	@FXML
	private Button leavebt;

	@FXML
	private Label lblDOB;

	@FXML
	private Label lblGender;

	@FXML
	private Label lblID;

	@FXML
	private TextArea taAnnoucement;

	@FXML
	private Label lblAndrewID;

	@FXML
	private Label lblAlerts;

	@FXML
	private Button dobt;

	@FXML
	private Label lblName;

	@FXML
	private Label lblVisitTime;

	@FXML
	private Label lblLastVisit;

	@FXML
	private Label lblProgram;

	@FXML
	private PieChart piechart;

	@FXML
	private ImageView imgPhoto;

	/**
	 * The method receives the students id as parameter to 
	 * get the student's information from database and fill it into gui controls,
	 * including name, gender, dobm program, andrewid, photo, visittime, lastvisit,
	 * annoucement and alert 
	 * 
	 * @param id the int that indicating the student id
	 */
	public void setID(int id) {

		JDBCController db = new JDBCController();
		lblID.setText(Integer.toString(id));
		lblName.setText(db.getName(id));
		lblGender.setText(db.getGender(id));
		lblDOB.setText(df.format(db.getDob(id)));
		lblAndrewID.setText(db.getAndrewid(id));
		lblProgram.setText(db.getProgram(id));
		lblLastVisit.setText(db.getLastvisit(id));
		lblVisitTime.setText(Integer.toString(db.getVisittimes(id)));
		taAnnoucement.setText(db.getAnnounce());
		lblAlerts.setText(db.get_alert());
		if (db.getPhoto(id) != null) {
			imgPhoto.setImage(new Image("file:" + db.getphoto(id)));
		} else {
			imgPhoto.setImage(new Image("file:resources/new_user.jpg"));
		}

		//the observableList for piechart 
		ObservableList<Data> piechartData = FXCollections.observableArrayList();
		
		// getcategory fill in id and set them as the perchart data
		java.util.Iterator<Entry<Integer, Integer>> iter = db.getcategory(id).entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iter.next();
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
			piechartData.add(new PieChart.Data(operation, entry.getValue()));
		}
		if (piechartData.isEmpty()) {
			piechart.setTitle("No record.");
			piechart.setData(piechartData);
		} else {
			piechart.setTitle("Visit Reason Distribution");
			piechart.setData(piechartData);
		}

		this.theID = id;// store the student id as primary field for passing to next scene

	}

	/**
	 * Handle method for button leave, once clicked it will transfer to login scene
	 * 
	 * @param event
	 */
	@FXML
	private void handleleavebt(ActionEvent event) {

		try {
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
	 * Handle method for button do, once clicked, it will transfer to visit scene
	 * 
	 * @param event
	 */
	@FXML
	private void handledobt(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Visitscene.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			VisitController visit = loader.getController();
			visit.setID(theID);
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.hide();
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
