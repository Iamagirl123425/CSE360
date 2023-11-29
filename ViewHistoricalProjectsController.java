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
	@FXML
	private TextArea HPPSDisplay;
	
	@FXML
	private Button returnToEffortLogger;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			BufferedReader dataRead = new BufferedReader(new FileReader("HistoricalProjects.txt"));
			StringBuilder stringToAdd = new StringBuilder();
			String line;
			while((line = dataRead.readLine()) != null) {
				stringToAdd.append(line).append("\n");
			}
			HPPSDisplay.setText(stringToAdd.toString());
			dataRead.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
