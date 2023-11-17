package application;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class EffortLoggerEncryption {

	public static void main(String[] args) throws Exception {
/*		
		Scanner scan = new Scanner(System.in);
		FileReadWrite secretCodes = new FileReadWrite("NOTSecretCodes.txt");
		secretCodes.createEmptyFile();
		
		System.out.println("Input a username: ");
		String username = scan.nextLine();
		System.out.println("Input a password: ");
		String password = scan.nextLine();
		
		EncryptDecrypt usernameObj = new EncryptDecrypt();		//new objects for EncryptDecrypt class
		EncryptDecrypt passwordObj = new EncryptDecrypt();
		secretCodes.writeEmployeeID();
		secretCodes.writeUsername(usernameObj.encrypt(username));	//encrypt username, then write into database
		secretCodes.writePassword(passwordObj.encrypt(password));	//encrypt password, then write into database
		
		System.out.println("Search for Username?");
		String searchUsername = scan.nextLine();
		
		System.out.println("Search for Password?");
		String searchPassword = scan.nextLine();
		
		//fileStuff.readData(searchUsername, searchPassword);
		
		if(secretCodes.loginAuthentication(searchUsername, searchPassword)) {	//search for password
			System.out.println("Found!");
		}
		else {
			System.out.println("Not Found!");
		}
*/
		HistoricalProjectData historicalProjectData = new HistoricalProjectData();
		historicalProjectData.menu();
	}

}
