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
public class PlanningPokerSessionController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	int loopPlayer = 2;
	
	@FXML
	private TextArea ConversationDisplay;
	
	@FXML
	private TextArea ConversationInput;
	
	@FXML
	private Text PlayerNumber;
	
	@FXML
	private Button ConversationSubmit;
	
	@FXML
	private TextArea StoryPointDisplay;
	
	@FXML
	private ComboBox<String> StoryPointInput;
	
	@FXML
	private Button StoryPointSubmit;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private Text ErrorMessage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FileReadWrite inputNumOfPlayers = new FileReadWrite("numOfPlayers.txt");
		String maxValStoryPointString = inputNumOfPlayers.numOfPlayersRead(2);
		int maxValStoryPoint = Integer.parseInt(maxValStoryPointString);
		for(int i = 0; i <= maxValStoryPoint; i++) {
			String addToComboBox = Integer.toString(i);
			StoryPointInput.getItems().addAll(addToComboBox);
		}
		FileReadWrite CPPSPI = new FileReadWrite("CPPSPI.txt");
		StoryPointDisplay.setText(CPPSPI.readCPPSPI());
	}
	
	@FXML
	public void submitStoryPoints() {
		String currentPlayerDisplay = Integer.toString(loopPlayer);
		String currentPlayerDisplayConversation = Integer.toString(loopPlayer - 1);
		ConversationDisplay.appendText("Player " + currentPlayerDisplayConversation + ": " + ConversationInput.getText() + "\n");
		
		FileReadWrite inputNumOfPlayers = new FileReadWrite("numOfPlayers.txt");
    	String loopPlayerNumberString = inputNumOfPlayers.numOfPlayersRead(1);
    	int loopPlayerNumber = Integer.parseInt(loopPlayerNumberString);
    	if(loopPlayer > loopPlayerNumber) {
    		loopPlayer = 1;
    	}
		
		FileReadWrite readCPPSPI = new FileReadWrite("CPPSPI.txt");
		StringBuilder stringToAdd = new StringBuilder();
		boolean found = false;
		try {
			BufferedReader dataRead = new BufferedReader(new FileReader("CPPSPI.txt"));
			String line;
			while ((line = dataRead.readLine()) != null) {
				String[] dataParse = line.split(";");
				if(dataParse[0].equals(currentPlayerDisplay)) {
					found = true;
					int newloopPlayer = loopPlayer + 1;
					System.out.println(newloopPlayer);
					stringToAdd.append(currentPlayerDisplay + ";" + dataParse[1] + ";" + StoryPointInput.getValue()).append("\n"); 
				}
				else {
					stringToAdd.append(line).append("\n");
				}
			}
			if (found == true) {
				BufferedWriter dataWrite = new BufferedWriter(new FileWriter("CPPSPI.txt"));
				dataWrite.write(stringToAdd.toString());
				dataWrite.close();
			}
			dataRead.close();
			String nextPlayerNumberDisplay = Integer.toString(loopPlayer);
			PlayerNumber.setText(nextPlayerNumberDisplay);
			StoryPointDisplay.setText(readCPPSPI.readCPPSPI());
			loopPlayer++;
			ConversationInput.clear();
			StoryPointInput.getSelectionModel().clearSelection();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void saveAndExit(ActionEvent event) throws IOException {
		try {
			BufferedReader dataRead = new BufferedReader(new FileReader("CPPSPI.txt"));
			FileReadWrite descriptionObj = new FileReadWrite("numOfPlayers.txt");
			FileReadWrite data = new FileReadWrite("HPPS.txt");
			CreateEmployeeID createID = new CreateEmployeeID("HPPS.txt");
			
			String line = dataRead.readLine();
			String[] dataParse = line.split(";");
			int sessionID = createID.createSessionID();
			int projectID = createID.createProjectID();
			String sessionIDString = Integer.toString(sessionID);
			String projectIDString = Integer.toString(projectID);
			String currentDate = "" + LocalDate.now();
			String description = descriptionObj.numOfPlayersRead(3);
			String storyPoint = dataParse[2];
			
			data.addHPPS(projectIDString, sessionIDString, currentDate, description, storyPoint);
			BufferedWriter dataWrite = new BufferedWriter(new FileWriter("CPPSPI.txt"));
			dataWrite.close();
			dataRead.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
}
