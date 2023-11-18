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

public class PlanningPokerController {
	
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
    	
    	root = FXMLLoader.load(getClass().getResource("SessionSetup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
