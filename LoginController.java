package application;

//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;

public class LoginController {
	
	String password = "";
	String username = "";
	
	FileReadWrite secretCodes = new FileReadWrite("NOTSecretCodes.txt");
	EncryptDecrypt usernameObject = new EncryptDecrypt();
	EncryptDecrypt passwordObject = new EncryptDecrypt();
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private RadioButton registerButton;
	
	@FXML
	private RadioButton loginButton;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private PasswordField passwordRegister;
    
    @FXML
    private TextField passwordTextLogin;

    @FXML
    private TextField passwordTextRegister;
    
    @FXML
    private Text passwordValid;
    
    @FXML
    private Text loginSuccessful;

    @FXML
    private TextField usernameLogin;

    @FXML
    private TextField usernameRegister;

    @FXML
    void loginInputs(MouseEvent event) throws IOException{
    	// getting input from the login
    	username = usernameLogin.getText();
    	if (!loginButton.isSelected()) {
    		password = passwordLogin.getText();
    	}
    	else {
    		password = passwordTextLogin.getText();
    	}
    	if (secretCodes.loginAuthentication(username, password)) {
    		root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    	}
    	else {
    		loginSuccessful.setText("Login Fail");
    	}
    	
    }

    @FXML
    void registerInputs(MouseEvent event) {
    	
    	//getting input from the register
    	username = usernameRegister.getText();
    	if (!registerButton.isSelected()) {
    		password = passwordRegister.getText();
    	}
    	else {
    		password = passwordTextRegister.getText();
    	}
    	// checking to see if the password meets requirements
    	if (!isStrongPassword(password)) {
    		passwordValid.setText("Registration failed. Password requirements not met.");
    		return;
    	}else {
    		secretCodes.writeEmployeeID();
    		secretCodes.writeUsername(usernameObject.encrypt(username));
    		secretCodes.writePassword(passwordObject.encrypt(password));
    		
    		/* Create Employee ID */
    		
    		passwordValid.setText("Registration Good!");
    	}
    	
    }
    
    @FXML
    void changeVisibilityRegister(ActionEvent event) {	// Change the visibility of the password on the Register Page
    	if(registerButton.isSelected()) {
    		passwordTextRegister.setText(passwordRegister.getText());
    		passwordTextRegister.setVisible(true);
    		passwordRegister.setVisible(false);
    		return;
    	}
    	passwordRegister.setText(passwordTextRegister.getText());
    	passwordRegister.setVisible(true);
    	passwordTextRegister.setVisible(false);
    }
    

    @FXML
    void changeVisibilityLogin(ActionEvent event) {	// Change the visibility of the password on the Login page
    	if(loginButton.isSelected()) {
    		passwordTextLogin.setText(passwordLogin.getText());
    		passwordTextLogin.setVisible(true);
    		passwordLogin.setVisible(false);
    		return;
    	}
    	passwordLogin.setText(passwordTextLogin.getText());
    	passwordLogin.setVisible(true);
    	passwordTextLogin.setVisible(false);
    }
    
    
    private boolean isStrongPassword(String password) {
        // Password should have at least one uppercase letter, one number, one special character and length is at least 8 characters long.
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        boolean hasLength = false;
        int length = 0;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
            length++;
        }
        if (length >= 8) {
        	hasLength = true;
        }

        return hasUppercase && hasNumber && hasSpecialChar && hasLength;
    }
}