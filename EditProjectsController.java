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



public class EditProjectsController implements Initializable {
	
	Parent root;
	Stage stage;
	Scene scene;
	
	@FXML 
	private Button returnToPastProjects;
	
	@FXML
	private ComboBox<String> projectIDComboBox;
	
	@FXML
	private Button selectProjectIDButton;
	
	@FXML
	private ComboBox<String> projectName;
	
	@FXML
	private ComboBox<String> cycle;
	
	@FXML
	private ComboBox<String> effortCategory;
	
	@FXML
	private ComboBox<String> deliverable;
	
	@FXML
	private Text message;
	
	@FXML
	private Button submitChangesButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			BufferedReader dataRead = new BufferedReader(new FileReader("HistoricalProjects.txt"));
			String line;
			while((line = dataRead.readLine()) != null) {
				String[] dataParse = line.split("=");
				String projectID = dataParse[0];
				projectIDComboBox.getItems().addAll(projectID);
			}
			dataRead.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void returnToPastProjects(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ViewHistoricalProjects.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	public void selectProjectID(ActionEvent event) throws IOException {
		try {
			
	    	deliverable.getItems().addAll("Conceptual Design", 
					  "Detailed Design", 
					  "Test Cases",
					  "Solution",
					  "Reflection",
					  "Outline",
					  "Draft",
					  "Report",
					  "User Defined",
					  "Other");

	    	effortCategory.getItems().addAll("Plans", 
						 "Deliverables", 
						 "Interruptions",
						 "Defects",
						 "Others");

	    	cycle.getItems().addAll("Planning", 
					"Information Gathering", 
					"Information Understanding", 
					"Verifying", 
					"Outlining", 
					"Drafting", 
					"Finalizing", 
					"Team Meeting",
					"Coach Meeting", 
					"Stakeholder Meeting");
	    	projectName.getItems().addAll("Business Project", 
					  "Development Project");
			
			boolean found = false;
			BufferedReader dataRead = new BufferedReader(new FileReader("HistoricalProjects.txt"));
			String line;
			String[] details;
			String projectNameInput = "";
			String cycleInput = "";
			String effortCategoryInput = "";
			String deliverableInput = "";
			while((line = dataRead.readLine()) != null) {
				String[] dataParse = line.split("=");
				details = dataParse[1].split(";");
				
				if(dataParse[0].equals(projectIDComboBox.getValue())) {
					found = true;
					projectNameInput = details[1];
					cycleInput = details[4];
					effortCategoryInput = details[3];
					deliverableInput = details[2];
					break;
				}
			}
			if (found == true) {
				projectName.setValue(projectNameInput);
				cycle.setValue(cycleInput);
				effortCategory.setValue(effortCategoryInput);
				deliverable.setValue(deliverableInput);
			}
			else {
				message.setText("No project was found for this ID.");
			}
			dataRead.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void submitChanges(ActionEvent event) throws IOException {
		try {
			BufferedReader dataRead = new BufferedReader(new FileReader("HistoricalProjects.txt"));
			String line;
			StringBuilder stringToAdd = new StringBuilder();
			while((line = dataRead.readLine()) != null) {
				String[] dataParse = line.split("=");
				String[] details = dataParse[1].split(";");
				String projectID = dataParse[0];
				String employeeID = details[0];
				String projectNameInput = projectName.getValue();
				String deliverableInput = deliverable.getValue();
				String effortCategoryInput = effortCategory.getValue();
				String lifeCycleInput = cycle.getValue();
				String selectedID = projectIDComboBox.getValue();
				if(projectID.equals(selectedID)) {
					stringToAdd.append(selectedID + "=" + employeeID + ";" + projectNameInput + ";" + deliverableInput + ";" + effortCategoryInput + ";" + lifeCycleInput).append("\n");
				}
				else {
					stringToAdd.append(line).append("\n");
				}
			}
			BufferedWriter dataWrite = new BufferedWriter(new FileWriter("HistoricalProjects.txt"));
			dataWrite.write(stringToAdd.toString());
			dataRead.close();
			dataWrite.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		projectIDComboBox.getSelectionModel().clearSelection();
		projectName.getSelectionModel().clearSelection();
		cycle.getSelectionModel().clearSelection();
		effortCategory.getSelectionModel().clearSelection();
		deliverable.getSelectionModel().clearSelection();
		message.setText("Project has been edited.");
	}
}
