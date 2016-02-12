package soccerLeague;
import java.sql.*;

public class MainMenu {
/*
* The Main Menu Generating Class and the class in the Project containing Main Method	
*/
	public static void main(String[] args) throws ClassNotFoundException {

		 try{
			 
			//Connecting Database
			ConnectDB dbConnect = new ConnectDB();
			Connection conn = dbConnect.startConnection();
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
			 
				MenuList.showMainMenu(); 
				userInput=UserInput.menuOption();
				try{
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
				catch(NumberFormatException ne)	{choice=-1;}
				
				// Switch construct
				switch (choice) {
				case 8: break mainLoop; 
				case 1:
					  ViewFixtures.mainMenu(conn); //Redirects to View Fixtures
					  break;
				case 2:
					  ViewResults.mainMenu(conn); //Redirects to View Results
					  break;
				case 3:
					  ViewTeamStats.mainMenu(conn); // Redirects to View Team Statistics 
					  break;
				case 4:
					  ViewTeamStandings.mainMenu(conn); // Redirects to Team Standings
					  break;
				case 5:
					  ViewPlayerStatistics.mainMenu(conn); // Redirects to Player Statistics
					  break;
				case 6:
					  InsertUpdateStatistics.mainMenu(conn); // Redirects to Insert Update Sub Menu
				      break;
				case 7:
					  ViewPeopleListings.mainMenu(conn); // Redirects to View People Listing i.e. Players, Managers and Referees
				default:
					  System.out.println("Invalid input. Please reselect\n\n");
				      break; 
				}
			 }
			 
			 System.out.println("Main Menu Ended");
			 dbConnect.closeConnection(); 

		 }catch(Exception e){
			 System.out.println("Error in connecting database"); }
		 
	 }
	
		
}