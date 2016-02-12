package soccerLeague;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class InsertUpdatePlayer {


	public static void mainMenu(Connection conn)
	{

		try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				MenuList.showIUPlayerSubMenu();
				userInput=UserInput.menuOption();
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;
				
				// Switch construct
				switch (choice) {
				case 3: break mainLoop;
				case 1:
					//Create New Player. Enter new Player information
					createPlayer(conn);
				  break;
				case 2:
				  // Update existing player information
					updatePlayer(conn);
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
	
	public static void updatePlayer(Connection conn) throws IOException
	{
		String playerId;
		String newValue,input;
		String updateSql;
		
		//Prompting for Player ID which can be retrieved from the People Listing -> Players
		System.out.print ("Enter Player ID of the Player whose details you want to update: ");
		playerId = UserInput.enterInput();
		//Prompting for field which is to be updated		
		System.out.print("Enter the field you want to Update: ");
		String fieldName = UserInput.enterInput(); 
		System.out.print("Enter new value for "+fieldName+": ");
		newValue=UserInput.enterInput();
		
		//Validating the new value which will be updated for the provided field name
		boolean validUpdate=true;
		String message="";
		if(newValue.length()>30 && fieldName.equalsIgnoreCase("FirstName") )
		{message = "FirstName should have 30 or less characters";   validUpdate=false;}
		else if(newValue.length()>30 && fieldName.equalsIgnoreCase("LastName") )
		{message = "LastName should have 30 or less characters";   validUpdate=false;}
		else if (!Validator.isValidDate(newValue) && fieldName.equalsIgnoreCase("DebutDate"))
		{  message="Debut Date is invalid"; validUpdate=false;}
		else if (newValue.length()>30 && fieldName.equalsIgnoreCase("Nationality"))
		{message = "Nationality should have 30 or less characters";   validUpdate=false;}
		else if ((newValue.equals("0")||newValue.equals("1")) && fieldName.equalsIgnoreCase("isRetired"))
		{message = "isRetired should be either 1 or 0";   validUpdate=false;}
		
		if(validUpdate)
		{   // Building the Update Statement
			updateSql="UPDATE LeaguePlayer SET "+fieldName+"='"+newValue+"'  where playerId='" +playerId+"'" ;
		    System.out.println("Please confirm if you want to update the new value as "+ newValue +" for "+fieldName+" for Player ID :"+playerId);
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
	
	public static void createPlayer(Connection conn)
	{
		try{
		String playerFirstName;
		String playerLastName;
		String playerAge;
		String playerNationality;
		String playerDebutDate;
		String insertSql;
		//Asking the basic field for Input
		System.out.print ("Enter Player First Name: ");
		playerFirstName = UserInput.enterInput();
				
		System.out.print ("Enter Player Last Name: ");
		playerLastName = UserInput.enterInput();
		
		System.out.print("Enter Player Age: ");
		playerAge =  UserInput.enterInput();
		
		System.out.print("Enter Player Nationality: ");
		playerNationality = UserInput.enterInput();
		
		System.out.print("Enter Player Debut Date: ");
		playerDebutDate = UserInput.enterInput();
		
		
		boolean validInsert=true;
		//Valid the input values based on the table constraints in the database
		String message="";
		if(playerFirstName.length()>30)
		{message = "Player First Name should have 30 or less characters";   validInsert=false;}
		else if (playerLastName.length()>30)
		{message = "Player Last Name should have 30 or less characters";   validInsert=false;}
		else if (!Validator.isNumber(playerAge))
		{message = "Player Age is invalid";   validInsert=false;}
		else if (!Validator.isValidDate(playerDebutDate))
		{  message="Player Debut Date is invalid"; validInsert=false;}
		
		if(validInsert)
		{
			//Building the Insert statement if the validation is passed	
			try{
				insertSql = "INSERT INTO LeaguePlayer(FirstName,LastName,Age,Nationality,DebutDate,CurrentClubID,"
				+"TotalMatches,isRetired) values"+"('"+playerFirstName+"','"
				+playerLastName+"',"+playerAge+",'"+playerNationality+"','"
				+playerDebutDate+"',"+"NULL"+","+"0"+","+"0"+");";
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
		 System.out.println("Error in database connection"); 
	 }
	}
}
