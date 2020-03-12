package application;

//import necessary packages
import java.io.IOException;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The controller for newuser scene
 * 
 * @author Chenlu Jiang
 * 
 */
public class NewuserController {
	
	private int theID;// indicating student if
	private JDBCController db = new JDBCController();// object contains method get data from database

	//javafx controls in the scene
    @FXML
    private TextField tfProgram;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAndrewID;

    @FXML
    private TextField tfGender;

    @FXML
    private Button btSubmit;

    /**
     * Handle method for submit button, once clicked, it will receive the information
     * user types in into database, and transfers to info scene
     * 
     * @param event
     */
    @FXML
    void submitHandle(ActionEvent event) {
    	
    	theID = db.insertnewuser(tfName.getText(), tfGender.getText(), 
    			tfProgram.getText(), Date.valueOf(dpDOB.getValue()), tfAndrewID.getText());

    	
    	try {
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("Infoscene.fxml"));
    		AnchorPane root=(AnchorPane)loader.load();
    		InfoController info=loader.getController();
    		info.setID(theID);
    		Scene scene = new Scene(root);
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.hide();
            stage.setScene(scene);
            stage.show();
    	} catch (IOException ex) {
            System.err.println(ex);
       }
    }
    
    //getter and setter for theID
	public int getTheID() {
		return theID;
	}

	public void setTheID(int theID) {
		this.theID = theID;
	}

}
