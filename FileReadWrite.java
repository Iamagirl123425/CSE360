package application;
import java.io.*;
import java.util.*;
import java.nio.file.Paths;

public class FileReadWrite {
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	FileInputStream fis = null;
	FileOutputStream fos = null;
	int input;
	EncryptDecrypt encryptions = new EncryptDecrypt();
	String globalFileName = "";
	
	public FileReadWrite(String fileName) {		//empty constructor (no parameters needed)
		globalFileName = fileName;
	}
	
	public void createEmptyFile() {			//if file does not exist yet, create one. If it does, then print that it already exists.
		try {
			File file = new File(globalFileName);
            if (file.createNewFile()) {
                System.out.println("File " + globalFileName + " has been created.");
            } 
            else {
                System.out.println("File " + globalFileName + " already exists.");
            }
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeEmployeeID () {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(globalFileName, true));		//create file if it doesn't already exist. If it does, be in append mode (that's what the true argument is for)
			CreateEmployeeID createID = new CreateEmployeeID(globalFileName);
			String writeID = String.valueOf(createID.createEmployeeID());
			writer.write(writeID);
			writer.write("=");
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeUsername(int[] codes) {		//write array into database
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(globalFileName, true));
			for (int i = 0; i < codes.length; i++) {
				String num = String.valueOf(codes[i]);			//in order for computer to write into database, data must be in String format. Not array or any data structures.
				if (i == codes.length - 1) {
					writer.write(num);
					writer.write(":");
				}
				else {
					writer.write(num);
					writer.write(";");
				}
			}
			writer.close();
			System.out.println("Username written to " + globalFileName);		//Just to confirm that the username came through to the database
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writePassword (int[] codes) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(globalFileName, true));
			for (int i = 0; i < codes.length; i++) {
				String num = String.valueOf(codes[i]);			//in order for computer to write into database, data must be in String format. Not array or any data structures.
				if (i == codes.length - 1) {
					writer.write(num);
					writer.newLine();
				}
				else {
					writer.write(num);
					writer.write(";");
				}
			}
			writer.close();
			System.out.println("Password appended to " + globalFileName);		//Appended because it should be coming after username
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean loginAuthentication(String searchUsername, String searchPassword) {
		boolean found = false;				//so far, the username and password have not been found, so let's start it as false
		try (BufferedReader reader = new BufferedReader(new FileReader(globalFileName))) {
			String line;
			while ((line = reader.readLine()) != null) {			//read next line
				
				String[] employeeIDParse = line.split("=");
				String[] parts = employeeIDParse[1].split(":");			//according to encryption data should be in the following format: "###;###;###;###:###;###;###;###"
															//splitting at colon since there's only one because it separates username from password
				if (parts.length == 2) {
					String[] parsedUsername = parts[0].split(";");	
					String[] parsedPassword = parts[1].split(";");
					
					int[] parsedIntUsername = new int[parsedUsername.length];		//parse at semicolon because those separate characters from each other within username and password in ASCII format
					int[] parsedIntPassword = new int[parsedPassword.length];
					for(int i = 0; i < parsedUsername.length; i++) {
						parsedIntUsername[i] = Integer.parseInt(parsedUsername[i]);		//putting them into another array
					}
					for(int j = 0; j < parsedPassword.length; j++) {
						parsedIntPassword[j] = Integer.parseInt(parsedPassword[j]);
					}
					
					int[] usernameASCII = new int[parsedIntUsername.length];		//decrypting through EncryptDecrypt class
					int[] passwordASCII = new int[parsedIntPassword.length];
					usernameASCII = encryptions.decrypt(parsedIntUsername);
					passwordASCII = encryptions.decrypt(parsedIntPassword);
					
					char[] usernameChar = new char[usernameASCII.length];			//putting decrypted data into array in CHAR format, NOT ASCII
					char[] passwordChar = new char[passwordASCII.length];
					for(int k = 0; k < usernameASCII.length; k++) {
						char character = (char)usernameASCII[k];
						usernameChar[k] = character;
					}
					for(int l = 0; l < passwordASCII.length; l++) {
						char character = (char)passwordASCII[l];
						passwordChar[l] = character;
					}
					
					String username = new String(usernameChar);			//finally username, putting characters into one String
					String password = new String(passwordChar);
					if (username.equals(searchUsername) && password.equals(searchPassword)) {
						found = true;
						break;
					}
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
		return found;
	}
	
	public String[] employeeHistoricalProjects(int inputID) {
		try {
			boolean found = false;
			String[] packagedArray = null;
			BufferedReader EmployeeInfoReader = new BufferedReader(new FileReader(globalFileName));
			String line = "";
			while ((line = EmployeeInfoReader.readLine()) != null) {
				String[] employeeIDParse = line.split("=");
				String employeeIDString = employeeIDParse[0];
				
				int employeeID = Integer.parseInt(employeeIDString);
				if(inputID == employeeID) {
					found = true;
					String[] employeeProjectsAndName = employeeIDParse[1].split(":");
					
					if(employeeProjectsAndName[1].contains(";") == true) {
						String[] employeeProjects = employeeProjectsAndName[1].split(";");
						packagedArray = new String[(employeeProjects.length + 1)];
						packagedArray[0] = employeeIDString;
						for(int i = 1; i < packagedArray.length; i++) {
							packagedArray[i] = employeeProjects[i - 1];
						}
						break;
					}
					else {
						String employeeProjects = employeeProjectsAndName[1];
						packagedArray = new String[2];
						packagedArray[0] = employeeIDString;
						packagedArray[1] = employeeProjects;
						break;
					}
				}
			}
			if(found == false) {
				System.out.println("There are no historical projects for this employee yet.");
				packagedArray = new String[1];
				packagedArray[0] = "There are no historical projects for this employee yet.";
			}
			EmployeeInfoReader.close();
			return packagedArray;
		} 
		catch (FileNotFoundException e) {
			System.out.println("File was not found!");
			e.printStackTrace();
			String[] packagedArray = new String[1];
			packagedArray[0] = "File not found!";
			return packagedArray;
		}
		catch (IOException e) {
			e.printStackTrace();
			String[] packagedArray = new String[1];
			packagedArray[0] = "IO Exception!";
			return packagedArray;
		}
	}
	public void addHistoricalProject(int inputID, int projID, String firstName, String lastName) {
		try {
			boolean found = false;
			BufferedWriter checkForFile = new BufferedWriter(new FileWriter(globalFileName, true));
			checkForFile.close();
			BufferedReader employeeInfoReader = new BufferedReader(new FileReader(globalFileName));
			StringBuilder content = new StringBuilder();
			String line;
			while((line = employeeInfoReader.readLine()) != null) {
				String[] employeeIDParse = line.split("=");
				
				int employeeIDSearch = Integer.parseInt(employeeIDParse[0]);
				if(inputID == employeeIDSearch) {
					found = true;
					String projIDString = Integer.toString(projID);
					line = line + ";" + projIDString;
				}
				content.append(line).append("\n");
			}
			employeeInfoReader.close();
			
			if (found == false) {
				BufferedWriter employeeInfoWriter = new BufferedWriter(new FileWriter(globalFileName, true));
				String employeeIDString = Integer.toString(inputID);
				employeeInfoWriter.write(employeeIDString);
				employeeInfoWriter.write("=");
				employeeInfoWriter.write(firstName);
				employeeInfoWriter.write(";");
				employeeInfoWriter.write(lastName);
				employeeInfoWriter.write(":");
				String projIDString = Integer.toString(projID);
				employeeInfoWriter.write(projIDString);
				employeeInfoWriter.newLine();
				employeeInfoWriter.close();
			}
			else {
				BufferedWriter EmployeeInfoWriter = new BufferedWriter(new FileWriter(globalFileName));
				EmployeeInfoWriter.write(content.toString());
				EmployeeInfoWriter.close();
				System.out.println("Project ID has been appended to Employee " + globalFileName);
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Your input is invalid.");
		}
	}
	
	public void addHistoricalProjectDetails(int projectID, String projectName, String startDate, String endDate, String description) {
		try {
			boolean found = false;
			BufferedWriter HistoricalProjectsWriter = new BufferedWriter(new FileWriter(globalFileName, true));
			BufferedReader historicalProjectsReader = new BufferedReader(new FileReader(globalFileName));
			String line;
			StringBuilder content = new StringBuilder();
			while((line = historicalProjectsReader.readLine()) != null) {
				String[] projectIDStringParse = line.split("=");
				String projIDString = projectIDStringParse[0];
				int projID = Integer.parseInt(projIDString);
				if(projID == projectID) {
					found = true;
					line = projIDString + "=" + projectName + ";" + startDate + ";" + endDate + ";" + description;
					content.append(line).append("\n");
//					System.out.println("A project with the same ID already exists. Would you like to override? Y/N");
//					String choice = sc.nextLine();
//					if (choice.equals("Y") || choice.equals("y")) {
//						System.out.println();
//						line = projIDString + "=" + projectName + ";" + startDate + ";" + endDate + ";" + description;
//						System.out.println("The project was successfully overridden.");
//						content.append(line).append("\n");
//					}
//					else if (choice.equals("N") || choice.equals("n")) {
//						HistoricalProjectsWriter.close();
//						System.out.println("No changes have been made to ProjectID " + projectID);
//						content.append(line).append("\n");
//					}
				}
				else {
					content.append(line).append("\n");
				}
			}
			historicalProjectsReader.close();
			if(found == false) {
				String projectIDString = Integer.toString(projectID);
				String contentToAdd = projectIDString + "=" + projectName + ";" + startDate + ";" + endDate + ";" + description;
				//content.append(contentToAdd);
				HistoricalProjectsWriter.write(contentToAdd);
				HistoricalProjectsWriter.newLine();
				System.out.println("The project was successfully added.");
			}
			else {
				BufferedWriter newHistoricalProjectsWriter = new BufferedWriter(new FileWriter(globalFileName));
				newHistoricalProjectsWriter.write(content.toString());
				newHistoricalProjectsWriter.close();
			}
			HistoricalProjectsWriter.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("File not found!");
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("IO Exception!");
		}
	}
	
	public String[] viewProjectDetails(int projectID) {
		String[] packagedContent = null;
		try {
			boolean found = false;

			BufferedReader HistoricalProjectsReader = new BufferedReader(new FileReader(globalFileName));
			String line;
			while((line = HistoricalProjectsReader.readLine()) != null) {
				String[] projectIDStringParse = line.split("=");
				
				String projectIDString = projectIDStringParse[0];
				int scannedProjectID = Integer.parseInt(projectIDString);
				
				String[] projectDetails = projectIDStringParse[1].split(";");
				
				if(projectID == scannedProjectID) {
					found = true;
					packagedContent = new String[5];
					packagedContent[0] = "Project ID: " + projectIDString;
					packagedContent[1] = "Project Name: " + projectDetails[0];
					packagedContent[2] = "Start Date: " + projectDetails[1];
					packagedContent[3] = "End Date: " + projectDetails[2];
					packagedContent[4] = "Description: " + projectDetails[3];
				}
			}
			if(found == false) {
				packagedContent = new String[1];
				packagedContent[0] = null;
			}
			HistoricalProjectsReader.close();
			return packagedContent;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File Not Found!");
			packagedContent = new String[1];
			packagedContent[0] = null;
			return packagedContent;
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Input invalid!");
			packagedContent = new String[1];
			packagedContent[0] = null;
			return packagedContent;
		}
	}
	
	public void addHPPS(String projectIDString, String sessionIDString, String date, String description, String storyPointsString) {
		boolean found = false;
		try {
			BufferedWriter HPPSWriterAppend = new BufferedWriter(new FileWriter(globalFileName, true));
		    String line;
		    BufferedReader HPPSReader = new BufferedReader(new FileReader(globalFileName));
		    StringBuilder content = new StringBuilder();

		    while ((line = HPPSReader.readLine()) != null) {
		        String[] projectIDStringParse = line.split("=");
		        String scannedProjectIDString = projectIDStringParse[0];
		        
		        if (scannedProjectIDString.equals(projectIDString)) {
		            found = true;
		            System.out.println("A Planning Poker session with the same ID already exists. Would you like to override? Y/N");
		            // Assume yes
		            String updatedContent = projectIDString + "=" + sessionIDString + ";" + date + ";" + description + ";" + storyPointsString;
		            content.append(updatedContent).append("\n");
		        }
		        else {
		            content.append(line).append("\n");
		        }
		    }
		    
		    HPPSReader.close(); // Close the reader

		    if (found) {
		        BufferedWriter HPPSWriter = new BufferedWriter(new FileWriter(globalFileName));
		        HPPSWriter.write(content.toString());
		        HPPSWriter.close();
		        System.out.println("Planning Poker session has been updated.");
		    }
		    else {
		        String updatedContent = projectIDString + "=" + sessionIDString + ";" + date + ";" + description + ";" + storyPointsString;
		        HPPSWriterAppend.write(updatedContent);
		        HPPSWriterAppend.newLine();
		        HPPSWriterAppend.close();
		        System.out.println("Planning Poker session has been added.");
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    System.out.println("File not found!");
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("File not readable!");
		}
	}
	public String[] viewHPPS(String inputID) {
		String[] packagedContent = null;
		try {
			boolean found = false;
			BufferedReader HPPSReader = new BufferedReader(new FileReader(globalFileName));
			String line;
			while((line = HPPSReader.readLine()) != null) {
				String[] projectIDStringParse = line.split("=");
				String projectIDString = projectIDStringParse[0];
				if(inputID.equals(projectIDString)) {
					found = true;
					packagedContent = new String[5];
					String[] projectDetails = projectIDStringParse[1].split(";");
					packagedContent[0] = "Session ID: " + projectDetails[0];
					packagedContent[1] = "Project ID: " + projectIDString;
					packagedContent[2] = "Date: " + projectDetails[1];
					packagedContent[3] = "Description: " + projectDetails[2];
					packagedContent[4] = "StoryPoints: " + projectDetails[3];
				}
			}
			if(found == false) {
				packagedContent = new String[1];
				packagedContent[0] = null;
			}
			HPPSReader.close();
			return packagedContent;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found!");
			packagedContent = new String[1];
			packagedContent[0] = null;
			return packagedContent;
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("File reading error!");
			packagedContent = new String[1];
			packagedContent[0] = null;
			return packagedContent;
		}
	}
	
	public void numOfPlayersWrite(String numOfPlayers, String storyPointRange, String userStory) {
		try {
			BufferedWriter numOfPlayersWriter = new BufferedWriter(new FileWriter(globalFileName));
			numOfPlayersWriter.write(numOfPlayers);
			numOfPlayersWriter.write(";");
			numOfPlayersWriter.write(storyPointRange);
			numOfPlayersWriter.write(";");
			numOfPlayersWriter.write(userStory);
			numOfPlayersWriter.newLine();
			numOfPlayersWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.print("Input Invalid!");
		}
	}
	
	public String numOfPlayersRead(int input) {
		try {
			BufferedReader numOfPlayersReader = new BufferedReader(new FileReader(globalFileName));
			String line = numOfPlayersReader.readLine();
			String[] numOfPlayersParse = line.split(";");
			String numOfPlayers = numOfPlayersParse[0];
			String maxStoryPoint = numOfPlayersParse[1];
			String userStory = numOfPlayersParse[2];
			numOfPlayersReader.close();		
			switch(input) {
			case 1:
				return numOfPlayers;
			case 2:
				return maxStoryPoint;
			case 3:
				return userStory;
			default:
				return "not needed here";
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.print("File Not Found!");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.print("File not readable!");
		}
		return "not needed here";
	}
}
