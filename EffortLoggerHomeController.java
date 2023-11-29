package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.FileReader;

public class EffortLoggerHomeController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
    private int seconds;
    private Timeline timer;
    String startTimer;
    String endTimer;
    String startDate;
    String endDate;
    
    @FXML
    private ComboBox<String> deliverable;

    @FXML
    private ComboBox<String> effortCategory;

    @FXML
    private ComboBox<String> lifeCycle;

    @FXML
    private ComboBox<String> project;

    @FXML
    private Button returnButton;
    
    @FXML
    private Label timerLabel;
    
    @FXML
    private Text dateDisplay;
    
    @FXML
    private Button viewHistoricalProjectsButton;
    
    @Override
    public void initialize(URL locaiton, ResourceBundle resources) {
    	String currentDate = "" + LocalDate.now();
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
    	
    	lifeCycle.getItems().addAll("Planning", 
    								"Information Gathering", 
    								"Information Understanding", 
    								"Verifying", 
    								"Outlining", 
    								"Drafting", 
    								"Finalizing", 
    								"Team Meeting",
    								"Coach Meeting", 
    								"Stakeholder Meeting");
    	
    	project.getItems().addAll("Business Project", 
    							  "Development Project");
    	dateDisplay.setText(currentDate);
    }

    @FXML
    public void ReturnEffortLogger(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void viewPastHistoricalProjects(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("ViewHistoricalProjects.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    void startTimer(ActionEvent event) {
    	// Initialize the timer and set up the timerLabel
        seconds = 0;
        updateTimerLabel();

        // Create a timeline that triggers an event every second
        timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> updateTimer()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        startTimer = "" + LocalTime.now();
    }

    @FXML
    void stopTimer(ActionEvent event) {
    	// Stop the timer
    	endTimer = "" + LocalTime.now();
        if (timer != null) {
            timer.stop();
        }
        
    	String deliv = deliverable.getValue();
    	String category = effortCategory.getValue();
    	String cycle = lifeCycle.getValue();
    	String proj = project.getValue();
    	String timerString = timerLabel.getText();
    	
    	System.out.println("Deliverable: " + deliv + 
    						"\nEffort Category: " + category + 
    						"\nLife Cycle: " + cycle + 
    						"\nProject: " + proj + 
    						"\n" + timerString + 
    						"\nDate Started" + LocalDate.now() + 
    						"\n" + startTimer +
    						"\n" + endTimer);
    	CreateEmployeeID createProjectID = new CreateEmployeeID("HistoricalProjects.txt");
    	int projectID = createProjectID.createProjectID();
    	FileReadWrite data = new FileReadWrite("HistoricalProjects.txt");
    	try {
    		BufferedReader dataRead = new BufferedReader(new FileReader("currentUser.txt"));
    		String line = dataRead.readLine();
    		String[] dataParse = line.split(";");
    		String employeeID = dataParse[0];
        	data.addHistoricalProjectDetails(projectID, employeeID, proj, deliv, category, cycle);
        	dataRead.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private void updateTimer() {
        // Update the timer value and label text
        seconds++;
        updateTimerLabel();
    }
    
    private void updateTimerLabel() {
        timerLabel.setText("Time: " + formatTime(seconds));
    }
    
    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}
