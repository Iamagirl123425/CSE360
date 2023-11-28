package application;

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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class EffortLoggerHomeController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	

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
    
    @Override
    public void initialize(URL locaiton, ResourceBundle resources) {
    	deliverable.getItems().addAll("Deliverable1", "Deliverable2", "Deliverable3");
    	effortCategory.getItems().addAll("EffortCategory1", "EffortCategory2", "EffortCategory3");
    	lifeCycle.getItems().addAll("LifeCycle1", "LifeCycle2", "LifeCycle3");
    	project.getItems().addAll("ProjectName1", "ProjectName2", "ProjectName3");
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
    void startTimer(ActionEvent event) {
    	
    }

    @FXML
    void stopTimer(ActionEvent event) {
    	String deliv = deliverable.getValue();
    	String category = effortCategory.getValue();
    	String cycle = lifeCycle.getValue();
    	String proj = project.getValue();
    	
    	System.out.println("Deliverable: " + deliv + "\nEffort Category: " + category + "\nLife Cycle: " + cycle + "\nProject: " + proj);
    }
}
