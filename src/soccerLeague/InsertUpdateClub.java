package soccerLeague;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertUpdateClub {
/*
 * This class performs the role of Inserting and Updating Clubs
 */

	public static void mainMenu(Connection conn)
	{

		try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				//Displaying the sub menu 
				MenuList.showIUClubSubMenu();
				//Taking userInput 
				userInput=UserInput.menuOption();
				try{
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
				catch(NumberFormatException ne)	{choice=-1;}
				
				
				// Switch construct
				switch (choice) {
				case 3: break mainLoop;
				case 1:
				  //Create New Club. Enter new Club information
					insertClub(conn);
				  break;
				case 2:
				  // Update existing club information
					updateClub(conn);
				   break;
				default:
				  System.out.println("Invalid input. Please reselect");
				  System.out.println();
				  break; 
				}
			 }
			 System.out.println("Club Sub Menu Ended");
		 }catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	
	}
	
	
	static void insertClub(Connection conn)
	{
		
		//Input from User:
		System.out.println("Enter new Club Details");
		System.out.print("Enter Club Name :");
		String cName = UserInput.enterInput();
		System.out.print("Enter Founded Date (in YYYY-MM-DD format):");
		String cfDate = UserInput.enterInput();
		System.out.print("Enter Owner Name: ");
		String owner = UserInput.enterInput();
		System.out.print("Enter Chairman Name: ");
		String chairMan = UserInput.enterInput();
		System.out.print("Enter Team's NickName:");
		String nickName = UserInput.enterInput();
		System.out.print("Enter Team's Mascot Name:");
		String mascot = UserInput.enterInput();
		System.out.print("Enter Team's Home Color:");
		String homeColor = UserInput.enterInput();
		System.out.print("Enter Team's Away Color:");
		String awayColor = UserInput.enterInput();
		System.out.print("Enter Team's Stadium:");
		String stadium = UserInput.enterInput();
		
		//Validation of the Input Values
		boolean validInsert=true;
		String message="";
		if(cName.length()>30)
		{message = "ClubName should have 30 or less characters";   validInsert=false;}
		else if (!Validator.isValidDate(cfDate))
		{  message="Founded Date is invalid"; validInsert=false;}
		else if (owner.length()>30)
		{message = "Owner Name should have 30 or less characters";   validInsert=false;}
		else if (chairMan.length()>30)
		{message = "Chairman Name should have 30 or less characters";   validInsert=false;}
		else if (nickName.length()>30)
		{message = "NickName should have 30 or less characters";   validInsert=false;}
		else if (mascot.length()>30)
		{message = "Mascot Name should have 30 or less characters";   validInsert=false;}
		else if (homeColor.length()>30)
		{message = "Home Color should have 30 or less characters";   validInsert=false;}
		else if (awayColor.length()>30)
		{message = "Away Color should have 30 or less characters";   validInsert=false;}
		else if (stadium.length()>50)
		{message = "Stadium name should have 30 or less characters";   validInsert=false;}
		
		
		if(!validInsert)
		{   //In case the Insert statement was invalid
			System.out.println("There has been following error in the input");
			System.out.println(message);
			System.out.println("Please try again");
		}
		else
		{  //Confirming the Input
			MenuList.confirmInsertMenu();
			String cFlag=UserInput.enterInput();
			if(cFlag.equals("Y"))
			{
				
				//Getting New CLubID
				String clubId="0";
				
				try {
					Statement stmt = conn.createStatement();
					 stmt.executeQuery("SELECT max(ClubID)+1 FROM Club");
					 ResultSet rs = stmt.getResultSet();
					 if(rs.next())
					 {clubId=rs.getString(1);}
					 rs.close();
					 stmt.close();
				} catch (SQLException e) {System.out.println("Error in Database Connection");}
				
				//Inserting the new Club
				String insertSQL="INSERT INTO Club(ClubID,ClubName,Founded,ClubOwner,Chairman,Nickname,Mascot,CurrentAwayColor,CurrentHomeColor,Stadium) ";
				insertSQL=insertSQL+"("+clubId+",'"+cName+"','"+cfDate+"','"+owner+"','"+chairMan+"','"+nickName+"','"+mascot+"','"+awayColor+"','"+homeColor+"','"+stadium+"');";
				
				try
				{	Statement stmt = conn.createStatement();
					stmt = conn.createStatement();	
					stmt.executeUpdate(insertSQL);
					System.out.println("Record inserted successfully");
				}catch (SQLException e) {System.out.println("Error in Database Connection");}
				
			}
			else
			{
				System.out.println("INSERT declined by the User");
				
			}
			
		}
		
		
	}
	
	static void updateClub(Connection conn) throws IOException
	{	
		//Displaying available list of Clubs
		System.out.println("Below is the list of Club and their respective ClubIDs.");
		String clubList[][]=MenuList.allClubs(conn);
		System.out.println("ClubID        ClubName");
		for(int i=0;i<clubList.length;i++)
			System.out.println(clubList[i][0] +"            " +clubList[i][1]);
		System.out.print("\nEnter Club ID which you want to choose :");
		String input=UserInput.enterInput();
		//Validating Input
		boolean validInput=false;
		for(int i=0;i<clubList.length;i++)
		{
			
			if(clubList[i][0].equals(input))
				validInput=true;
		}
		
		String clubId=input;
		
		if(validInput)
		{	//Printing the list of fields which can be updated		
			System.out.println("Please choose the field which you want to update");
			System.out.println("1. Club Name");
			System.out.println("2. Founded");
			System.out.println("3. Club Owner");
			System.out.println("4. Chairman");
			System.out.println("5. Nickname");
			System.out.println("6. Mascot");
			System.out.println("7. Team Home Color");
			System.out.println("8. Team Away Color");
			System.out.println("9. Stadium");
			input=UserInput.menuOption();
			boolean innerValidInput=false;
			if(Validator.isNumber(input))
			{   //validating if the input club id was number 
				switch(Integer.parseInt(input))
				{ case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9:innerValidInput=true; }
			}
			
			//Validating the input field
			String fieldName="";
			if(innerValidInput)
			{
				switch(Integer.parseInt(input))
				{
				case 1: fieldName.equals("ClubName"); break;
				case 2: fieldName.equals("Founded"); break;
				case 3: fieldName.equals("ClubOwner"); break;
				case 4: fieldName.equals("Chairman"); break;
				case 5: fieldName.equals("Nickname"); break;
				case 6: fieldName.equals("Mascot"); break;
				case 7: fieldName.equals("CurrentAwayColor"); break;
				case 8: fieldName.equals("CurrentHomeColor"); break;
				case 9: fieldName.equals("Stadium"); break;
				}
			
			System.out.print("Enter new value for "+fieldName+": ");
			String newValue=UserInput.enterInput();
			//Validation of the SQL Statement
			boolean validInsert=true;
			String message="";
			if(newValue.length()>30 && fieldName.equals("ClubName") )
			{message = "ClubName should have 30 or less characters";   validInsert=false;}
			else if (!Validator.isValidDate(newValue) && fieldName.equals("Founded"))
			{  message="Founded Date is invalid"; validInsert=false;}
			else if (newValue.length()>30 && fieldName.equals("ClubOwner"))
			{message = "Owner Name should have 30 or less characters";   validInsert=false;}
			else if (newValue.length()>30 && fieldName.equals("Chairman"))
			{message = "Chairman Name should have 30 or less characters";   validInsert=false;}
			else if (newValue.length()>30 && fieldName.equals("Nickname"))
			{message = "NickName should have 30 or less characters";   validInsert=false;}
			else if (newValue.length()>30 && fieldName.equals("Mascot"))
			{message = "Mascot Name should have 30 or less characters";   validInsert=false;}
			else if (newValue.length()>30 && fieldName.equals("CurrentAwayColor"))
			{message = "Home Color should have 30 or less characters";   validInsert=false;}
			else if (newValue.length()>30 && fieldName.equals("CurrentHomeColor"))
			{message = "Away Color should have 30 or less characters";   validInsert=false;}
			else if (newValue.length()>50 && fieldName.equals("Stadium"))
			{message = "Stadium name should have 30 or less characters";   validInsert=false;}
				
			
			//Performing the Update
					if(validInsert)
					{
						String querySQL="UPDATE Club SET "+fieldName+"='"+newValue+"'  where ClubId=" +clubId ;
						System.out.println("Please confirm if you want to update the new value as "+ newValue +" for "+fieldName+" for Club ID :"+clubId);
						MenuList.confirmInsertMenu();
						input=UserInput.menuOption();
						if(input.equals("Y"))
						{
							try
							{	Statement stmt = conn.createStatement();
								stmt = conn.createStatement();	
								stmt.executeUpdate(querySQL);
								System.out.println("Record updated successfully");
							}catch (SQLException e) {System.out.println("Error in Database Connection");}
						}
						else
						{System.out.println("There has been error in the input. Please retry");
						System.out.println(message);}
						}
					}
					else
					{
						System.out.println("Incorrect Input. Please retry");
					}
		}	
		else
		{
			System.out.println("Your input was not a valid ClubID. Please retry");
		}
	}
	
	
	
	
}
