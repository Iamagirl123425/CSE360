package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SessionSetupController implements Initializable {
	
	int loopCount = 2;
	int loopPane = 3;
	String playerStoryPoints = "";
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Text playerLabel;
	
	@FXML
	private Text userStoryLabel;
	
	@FXML
	private ComboBox<String> roleComboBox;
	
	@FXML
	private ComboBox<String> initialStoryPointComboBox;
	
	@FXML
	private Button submitButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roleComboBox.getItems().addAll(
				"Technician",
				"Developer",
				"Tester",
				"Scrum Master"
				);
		FileReadWrite inputNumOfPlayers = new FileReadWrite("numOfPlayers.txt");
		String maxValStoryPointString = inputNumOfPlayers.numOfPlayersRead(2);
		int maxValStoryPoint = Integer.parseInt(maxValStoryPointString);
		userStoryLabel.setText(inputNumOfPlayers.numOfPlayersRead(3));
		for(int i = 0; i <= maxValStoryPoint; i++) {
			String addToComboBox = Integer.toString(i);
			initialStoryPointComboBox.getItems().addAll(addToComboBox);
		}
	}
	
    @FXML
    void sessionSetupSubmit (ActionEvent event) throws IOException {
    	FileReadWrite inputNumOfPlayers = new FileReadWrite("numOfPlayers.txt");
    	String loopPaneString = inputNumOfPlayers.numOfPlayersRead(1);
    	int loopPane = Integer.parseInt(loopPaneString);
    	if(loopCount > loopPane) {
    		String loopCountString = Integer.toString(loopCount);
    		playerLabel.setText(loopCountString);
    		String chosenRole = roleComboBox.getValue();
    		String chosenStoryPoint = initialStoryPointComboBox.getValue();
    		FileReadWrite planningPokerSessionData = new FileReadWrite("CPPSPI.txt");
    		planningPokerSessionData.PlanningPokerSessionDataWrite(loopCountString, chosenRole, chosenStoryPoint);
    		root = FXMLLoader.load(getClass().getResource("PlanningPokerSession.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    	}
    	else {
    		String loopCountString = Integer.toString(loopCount);
    		playerLabel.setText(loopCountString);
    		String chosenRole = roleComboBox.getValue();
    		String chosenStoryPoint = initialStoryPointComboBox.getValue();
    		FileReadWrite planningPokerSessionData = new FileReadWrite("CPPSPI.txt");
    		planningPokerSessionData.PlanningPokerSessionDataWrite(loopCountString, chosenRole, chosenStoryPoint);
    		loopCount++;
    		
    		//reset values so user can enter data again
    		roleComboBox.getSelectionModel().clearSelection();
    		initialStoryPointComboBox.getSelectionModel().clearSelection();
    	}
    }
    
    @FXML
    void returnButton (ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("PlanningPokerSetNumberOfCharacters.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        try {
            BufferedWriter dataWrite = new BufferedWriter(new FileWriter("CPPSPI.txt"));
            dataWrite.close();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
}
