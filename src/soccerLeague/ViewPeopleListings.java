package soccerLeague;

import java.sql.Connection;
/*
 * This class provides the functionality of viewing Players, Managers and Referees of the Soccer League 
 */
public class ViewPeopleListings {
	public static void mainMenu(Connection conn)
	{
		String userInput;
		int choice;
		try {
			mainLoop : while(true)
			 {
				System.out.println("\n");
				MenuList.showPeopleMenu();
				System.out.print("\nEnter Your choice :");
				userInput=UserInput.enterInput();
				try{
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
				catch(NumberFormatException ne)
				{choice=-1;}
				
				// Switch construct
				switch (choice) {
					case 4: break mainLoop;
					case 1:
						MenuList.showAllPlayers(conn); //MenuList which provide option to view all League Players
						break;
					case 2:
						MenuList.showAllManagers(conn); //MenuList which provide option to view all Manager
						break;
					case 3:
						MenuList.showAllReferees(conn);//MenuList which provide option to view all Referees
						break;
					default:
						System.out.println("Invalid input. Please reselect");
						System.out.println();
						break; 
				}
			 }
		
		} 
		catch (Exception e) {
			System.out.println("Error in Database Connection");
		}
	}
}