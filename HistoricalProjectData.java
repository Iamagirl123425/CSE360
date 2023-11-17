package application;
import java.util.Scanner;
import java.io.*;

public class HistoricalProjectData {
	public void menu() 
	{
		int selection = 0;
		String HistoricalProjects = "HistoricalProjects.txt";
		String HPPS = "HistoricalPlanningPokerSessions.txt";
		String EmployeeInfo = "EmployeeInfo.txt";
		String SecretCodeFile = "NOTSecretCodes.txt";
		//check if selection is valid.
		boolean quit = false;
		
		while (quit == false)
		{
			//Scanner to collect user input
			Scanner sc = new Scanner(System.in);
			
			//Welcome Menu
			System.out.println();
			System.out.println("Welcome to Planning Poker!");
			System.out.println("What would you like to do?\n");
			System.out.println("1) View past historical projects of an employee\n"
					+ "2) Add a historical project to an employee's portfolio\n"
					+ "3) View data from past planning poker sessions\n"
					+ "4) Save data for a planning poker session\n"
					+ "5) Add a historical project\n"
					+ "6) View a project's details\n"
					+ "7) Quit\n"
			);
			
			//Collect user selection
			System.out.print("Please enter a number 1-7: ");
			if (sc.hasNextInt())
			{
				selection = sc.nextInt();
			}
			
			//Selection 1: View past historical projects of an employee
			//Use Case 2.4.2.3: An employee can view their past projects
			if (selection == 1) {
				
				//collect employee id
				System.out.print("Please provide the employee's id: ");
				int id = sc.nextInt();
				System.out.println();
				
				FileReadWrite viewEmployeeHistoricalProjectIDs = new FileReadWrite(EmployeeInfo);
				System.out.println("Employee ID: " + viewEmployeeHistoricalProjectIDs.employeeHistoricalProjects(id)[0]);
				for(int i = 1; i < viewEmployeeHistoricalProjectIDs.employeeHistoricalProjects(id).length; i++) {
					System.out.print("Project ID's: " + viewEmployeeHistoricalProjectIDs.employeeHistoricalProjects(id)[i] + ", ");
				}

			}
			//Selection 2: Add a historical project for an employee
			//This will probably be in different section of EffortLogger, but just added here to show
			//an example of selection 1
				else if (selection == 2) {
					System.out.print("Please provide the employee's id: ");
					int employeeID = sc.nextInt();
					
					System.out.print("Please provide the projectID: ");
					int projID = sc.nextInt();
					
					System.out.print("Employee's first name: ");
					sc.nextLine();
					String firstName = sc.nextLine();
					
					System.out.print("Employee's last name: ");
					String lastName = sc.nextLine();
					
					FileReadWrite addHistoricalProject = new FileReadWrite(EmployeeInfo);
					
					addHistoricalProject.addHistoricalProject(employeeID, projID, firstName, lastName);
				}
//				//Selection 3: View data from past planning poker sessions
//				//Use Case 2.4.2.2 Data from a previous poker session is accessible by an employee
				else if (selection == 3) {
					
					//collect employee id
					System.out.print("Please provide a session ID: ");
					int id = sc.nextInt();
					System.out.println();
					String idString = Integer.toString(id);
					
					FileReadWrite viewHPPS = new FileReadWrite(HPPS);
					String[] packagedContent = new String[viewHPPS.viewHPPS(idString).length];
					packagedContent = viewHPPS.viewHPPS(idString);
					for(int i = 0; i < packagedContent.length; i++) {
						System.out.println(packagedContent[i]);
					}
				}
//				//Selection 4: Save data for a planning poker session
//				//Use Case 2.4.2.1: Data from a completed poker session stored for future use
				else if (selection == 4) {
					
					FileReadWrite addHPPS = new FileReadWrite(HPPS);
					addHPPS.addHPPS("100000", "21234", "2122321", "bad", "4");		//scanner isn't working for some reason
				}
//				//Selection 5: Add a historical project
//				//Added here to show use case 2.4.2.3
				else if (selection == 5)
				{
					//collect data from user
					System.out.print("Please provide the project ID: ");
					int projectID = sc.nextInt();
					
					System.out.print("Please provide the project name: ");
					sc.nextLine();
					String projectName = sc.nextLine();
					
					System.out.print("Please provide the start date of the project: ");
					String startDate = sc.nextLine();
					
					System.out.print("Please provide the completion date of the project: ");
					String endDate = sc.nextLine();
					
					System.out.print("Please provide a description for the project: ");
					String description = sc.nextLine();
					FileReadWrite addHistoricalProjectDetails = new FileReadWrite(HistoricalProjects);
					addHistoricalProjectDetails.addHistoricalProjectDetails(projectID, projectName, startDate, endDate, description);
				}
				//View a project's details
				else if (selection == 6)
				{
					System.out.print("Please provide the project ID: ");
					int projectID = sc.nextInt();
					System.out.println();
					FileReadWrite viewProjectDetails = new FileReadWrite(HistoricalProjects);
					for (int i = 0; i < viewProjectDetails.viewProjectDetails(projectID).length; i++) {
						if(viewProjectDetails.viewProjectDetails(projectID)[i] == null) {
							System.out.println("No project found!");
							break;
						}
						System.out.println(viewProjectDetails.viewProjectDetails(projectID)[i]);
				}
//				//Selection 7: Quit
//				else if (selection == 7) {
//					System.out.println("Thank you for playing Planning Poker!");
//					sc.close();
//					quit = true;
//				}
//				//not a valid selection
//				else {
//					System.out.println("That is not a valid selection.\n");
				}
		}
		
	}
	
}