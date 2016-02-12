package soccerLeague;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertUpdateReferee {


	public static void mainMenu(Connection conn)
	{

		try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				MenuList.showIURefereeSubMenu();
				userInput=UserInput.menuOption();
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;
				
				// Switch construct
				switch (choice) {
				case 3: break mainLoop;
				case 1:
					//Create New Referee. Enter new Referee information
					createReferee(conn);
				  break;
				case 2:
				  // Update existing Referee information
					updateReferee(conn);
				   break;
				default:
				  System.out.println("Invalid input. Please reselect");
				  System.out.println();
				  break; 
				}
			 
				
			 }
			 
			 System.out.println("Club SubwMenu Ended");
			

		 }catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	
	}
	
	public static void updateReferee(Connection conn) throws IOException
	{
		String refereeId;
		String newValue,input;
		String updateSql;
		
		//Prompting for the Referee ID which can be retrieved from the People Listing
		System.out.print ("Enter Referee ID of the Referee whose details you want to update: ");
		refereeId = UserInput.enterInput();
        // Prompting for the fieldname as Input which is to be updated				
		System.out.print("Enter the field you want to Update: ");
		String fieldName = UserInput.enterInput(); 
		System.out.print("Enter new value for "+fieldName+": ");
		newValue=UserInput.enterInput();
		//Checking if the update statement is valid
		boolean validUpdate=true;
		String message="";
		if (newValue.length()>30 && fieldName.equalsIgnoreCase("FirstName") )
		{message = "FirstName should have 30 or less characters";   validUpdate=false;}
		else if(newValue.length()>30 && fieldName.equalsIgnoreCase("LastName") )
		{message = "LastName should have 30 or less characters";   validUpdate=false;}
		else if (!Validator.isValidDate(newValue) && fieldName.equalsIgnoreCase("DebutDate"))
		{  message="Debut Date is invalid"; validUpdate=false;}
		else if (newValue.length()>30 && fieldName.equalsIgnoreCase("Nationality"))
		{message = "Nationality should have 30 or less characters";   validUpdate=false;}
		else if ((newValue.equals("0")||newValue.equals("1")) && fieldName.equalsIgnoreCase("isRetired"))
		{message = "isRetired is either 1 or 0";   validUpdate=false;}
		
		if(validUpdate)
		{   //If the input values are valid, proceed with building the Update statement and executing it 
			updateSql="UPDATE Referee SET "+fieldName+"='"+newValue+"'  where refereeId='" +refereeId+"'" ;
		    System.out.println("Please confirm if you want to update the new value as "+ newValue +" for "+fieldName+" for Referee ID :"+refereeId);
			MenuList.confirmInsertMenu();
			input=UserInput.menuOption();
			if(input.equals("Y"))
			{
				try
				{	Statement stmt = conn.createStatement();
				    stmt = conn.createStatement();	
					stmt.executeUpdate(updateSql);
					System.out.println("Record updated");
				}catch (SQLException e) {System.out.println("Error in input field name");}
			}
			else
			{
			 System.out.println("There has been error in the input. Please retry");
			 System.out.println(message);
			 }
	       	}

		else { System.out.println("Incorrect Input. Please retry");}
	}
	
	public static void createReferee(Connection conn)
	{
		try{
			String refereeFirstName;
			String refereeLastName;
			String refereeAge;
			String refereeNationality;
			String refereeDebutDate;
			String insertSql;
			
			System.out.print ("Enter Referee First Name: ");
			refereeFirstName = UserInput.enterInput();
					
			System.out.print ("Enter Referee Last Name: ");
			refereeLastName = UserInput.enterInput();
			
			System.out.print("Enter Referee Age: ");
			refereeAge =  UserInput.enterInput();
			
			System.out.print("Enter Referee Nationality: ");
			refereeNationality = UserInput.enterInput();
			
			System.out.print("Enter Referee Debut Date: ");
			refereeDebutDate = UserInput.enterInput();
			
			boolean validInsert=true;
			
			String message="";
			if(refereeFirstName.length()>30)
			{message = "Referee First Name should have 30 or less characters";   validInsert=false;}
			else if (refereeLastName.length()>30)
			{message = "Referee Last Name should have 30 or less characters";   validInsert=false;}
			else if (!Validator.isNumber(refereeAge))
			{message = "Referee Age is invalid";   validInsert=false;}
			else if (!Validator.isValidDate(refereeDebutDate))
			{  message="Referee Debut Date is invalid"; validInsert=false;}
			
			if(validInsert)
			{
			
				try{
					insertSql = "INSERT INTO Referee(FirstName,LastName,Age,Nationality,DebutDate,CurrentClubID,"
					+"TotalMatches,isRetired) values"+"('"+refereeFirstName+"','"
					+refereeLastName+"',"+refereeAge+",'"+refereeNationality+"','"
					+refereeDebutDate+"',"+"NULL"+","+"0"+","+"0"+");";
					Statement st = conn.createStatement();	
					st.executeUpdate(insertSql);
					System.out.println("Record successfully inserted");
				}
				catch(SQLException e){System.out.println("Error in database connection");}
			}
			
			else
			{
				System.out.println("There has been following error in the input");
				System.out.println(message);
				System.out.println("Please try again");
			}
		}catch(Exception e){
				 System.out.println("Error in database connection");}
	}
}
