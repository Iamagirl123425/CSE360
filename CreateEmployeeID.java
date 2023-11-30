package application;
import java.io.*;

public class CreateEmployeeID {
	int baseEmployeeID = 100000;
	int baseProjectID = 10000;
	int baseSessionID = 1000;
	String globalFileName = "";
	
	public CreateEmployeeID (String fileName) {
		globalFileName = fileName;
	}
	
	public int createEmployeeID() {
		int ID = 0;
		try (BufferedReader fileReader = new BufferedReader(new FileReader(globalFileName))) {
			String line;
			while((line = fileReader.readLine()) != null) {
				String[] equalSignParse = line.split("=");
				
				if(equalSignParse.length == 2) {
					String IDString = equalSignParse[0];
					ID = Integer.parseInt(IDString);
				}
				if (ID == baseEmployeeID) {
					baseEmployeeID++;
				}
			}
		}
		catch (FileNotFoundException e) {		//just some exception handling
	        System.err.println("File not found: " + e.getMessage());
	    } 
		catch (IOException e) {
	        System.err.println("Error reading the file: " + e.getMessage());
	    }
		catch (NumberFormatException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
		return baseEmployeeID;
	}
	public int createProjectID() {
		int ID = 0;
		try (BufferedReader fileReader = new BufferedReader(new FileReader(globalFileName))) {
			String line;
			while((line = fileReader.readLine()) != null) {
				String[] equalSignParse = line.split("=");
				ID = Integer.parseInt(equalSignParse[0]);
				if (ID == baseProjectID) {
					baseProjectID++;
				}
			}
		}
		catch (FileNotFoundException e) {		//just some exception handling
	        System.err.println("File not found: " + e.getMessage());
	    } 
		catch (IOException e) {
	        System.err.println("Error reading the file: " + e.getMessage());
	    }
		catch (NumberFormatException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
		return baseProjectID;
	}
	public int createSessionID() {
		int ID = 0;
		try (BufferedReader fileReader = new BufferedReader(new FileReader(globalFileName))) {
			String line;
			while((line = fileReader.readLine()) != null) {
				String[] equalSignParse = line.split("=");
				
				if(equalSignParse.length == 2) {
					String IDString = equalSignParse[0];
					String unNeeded = equalSignParse[1];
					ID = Integer.parseInt(IDString);
				}
				if (ID == baseSessionID) {
					baseSessionID++;
				}
			}
		}
		catch (FileNotFoundException e) {		//just some exception handling
	        System.err.println("File not found: " + e.getMessage());
	    } 
		catch (IOException e) {
	        System.err.println("Error reading the file: " + e.getMessage());
	    }
		catch (NumberFormatException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
		return baseSessionID;
	}
}
