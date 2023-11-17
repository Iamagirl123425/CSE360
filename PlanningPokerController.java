package application;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

public class PlanningPokerController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField numOfPlayers;
	
	@FXML
	private TextField maxStoryPointVal;
	
	@FXML
	private TextField userStoryDescription;
	
	@FXML
	private Text playerLabel;
	
	@FXML
	private Text userStoryLabel;
	
	@FXML
	private ComboBox<String> roleComboBox;
	
	@FXML
	private ComboBox<String> initialStoryPointComboBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
//		userStoryLabel.setText("");
//		
//    	FileReadWrite inputNumOfPlayers = new FileReadWrite("numOfPlayers.txt");
//    	String maxStoryPointString = inputNumOfPlayers.numOfPlayersRead(2);
//    	int maxStoryPoint = Integer.parseInt(maxStoryPointString);
//        for(int j = 0; j <= maxStoryPoint; j++) {
//        	String storyPointDisplay = Integer.toString(j);
//        	initialStoryPointComboBox.getItems().addAll(storyPointDisplay);
//        }
	}

    @FXML
    void returnLandingPage(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void returnPlanningPokerHome (ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("PlanningPokerHome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void PlanningPokerCreateNumberOfCharacters (ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("PlanningPokerSetNumberOfCharacters.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void inputNumOfPlayers (ActionEvent event) throws IOException {
    	String numOfPlayersInput = numOfPlayers.getText();
    	String maxStoryPointValInput = maxStoryPointVal.getText();
    	String userStory = userStoryDescription.getText();
    	FileReadWrite inputNumOfPlayers = new FileReadWrite("numOfPlayers.txt");
    	inputNumOfPlayers.numOfPlayersWrite(numOfPlayersInput, maxStoryPointValInput, userStory);
    	
    	String loopPaneString = inputNumOfPlayers.numOfPlayersRead(1);
    	int loopPane = Integer.parseInt(loopPaneString);
    	String userStoryDescription = inputNumOfPlayers.numOfPlayersRead(3);
    	
    	root = FXMLLoader.load(getClass().getResource("SessionSetup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
		playerLabel.setText("Hello");
    }
}