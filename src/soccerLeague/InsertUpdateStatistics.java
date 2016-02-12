package soccerLeague;
/*
 * This is the primary class which provides the functionality of data modification. For any data modification, Main Menu redirects the application flow to this class which itself
 * has sub menus for different data modification requirements 
 */
import java.sql.Connection;
 

public class InsertUpdateStatistics {

	public static void mainMenu(Connection conn)
	{
		
		 try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				MenuList.showInsertUpdateMenu();  
				userInput=UserInput.menuOption();
				try{
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
				catch(NumberFormatException ne){choice =-1;}
				
				// Switch construct
				switch (choice) {
				case 1:
					InsertUpdateClub.mainMenu(conn);  //SubMenu for ClubData Modification
					break;
				case 2:
					InsertUpdatePlayer.mainMenu(conn); //Sub Menu for Player Data Modification
					break;
				case 3:
					InsertUpdateManager.mainMenu(conn); // SubMenu for Manager Data Modification
				  	break;
				case 4:
					InsertUpdateReferee.mainMenu(conn); // SubMenu for Referee Data Modification
				case 5:
					InsertUpdateSeason.mainMenu(conn);  // SubMenu for Season Data Modification
					break;
				case 6:
					GenerateFixtures.process(conn); //SubMenu for Generating Fixtures
					break;
				case 7:
					InsertUpdateMatch.mainMenu(conn); // SubMenu for Match Data Modification 
					break;
				case 8:
					InsertUpdateLineup.mainMenu(conn); //SubMenu for LineUp Generation
					break;
				case 9:
					break mainLoop;
				default:
				  System.out.println("Invalid input. Please reselect");
				  System.out.println();
				  break; 
				}
			 
				
			 }
			 
			 System.out.println("Main Menu Ended");
			

		 }catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
			
		
		
	}
	
	
}
