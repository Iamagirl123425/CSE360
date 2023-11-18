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

public class SessionSetupController implements Initializable {
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
	
	int loopCount = 1;
    
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
    	if(loopCount == loopPane) {
    		//send to Planning Poker Session
    	}
    	else {
    		
    	}
    }
}
