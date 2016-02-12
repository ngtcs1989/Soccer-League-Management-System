package soccerLeague;

import java.sql.Connection;
/*
 * This class provides the functionality of displaying the Fixture Results for a given season
 */
public class ViewResults {
	

	
	public static void mainMenu(Connection conn)
	{
		
		 try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				//Taking User Input 
				MenuList.viewResults();  
				userInput=UserInput.menuOption();
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;
				
				// Switch construct
				switch (choice) {
				case 2: break mainLoop;
				case 1:
				  //Printing all seasons and prompting User to choose a season	
				  System.out.println("\n");	
				  MenuList.printAllSeasons(conn);	 
				  System.out.print("\nEnter the season ID to view results: ");
				  String seasonId= UserInput.enterInput();
				  MenuList.showSeasonResults(conn, seasonId);      //Retrieving the values from the MenuList's show Season Result method by providing the season id as parameter
				  break;
				default:
					  System.out.println("Invalid input. Please reselect");
					  System.out.println();
					  break; 
				}
			 }
		 }
		 catch (Exception e)
		 {
			System.out.println("Invalid Input");
		 }
		
	}

}
