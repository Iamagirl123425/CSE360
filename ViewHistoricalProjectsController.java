package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewHistoricalProjectsController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextArea HPPSDisplay;
	
	@FXML
	private Button returnToEffortLogger;
	
	@FXML
	private Button EditButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			BufferedReader dataRead = new BufferedReader(new FileReader("HistoricalProjects.txt"));
			StringBuilder stringToAdd = new StringBuilder();
			String line;
			while((line = dataRead.readLine()) != null) {
				String[] dataParse = line.split("=");
				String[] details = dataParse[1].split(";");
				String projectID = dataParse[0];
				String employeeID = details[0];
				String projectName = details[1];
				String deliverable = details[2];
				String effortCategory = details[3];
				String lifeCycle = details[4];
				
				stringToAdd.append("Project ID: " + projectID + "\n" + 
								   "EmployeeID: " + employeeID + "\n" +
								   "Project Name: " + projectName + "\n" +
								   "Deliverable: " + deliverable + "\n" +
								   "Effort Category: " + effortCategory + "\n" +
								   "Life Cycle: " + lifeCycle + "\n" +
								   "----------------------------------------------------------------------------------------" + "\n");
			}
			HPPSDisplay.setText(stringToAdd.toString());
			dataRead.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void returnToEffortLogger(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("EffortLoggerHome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	public void editButton(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("EditProjects.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
}
