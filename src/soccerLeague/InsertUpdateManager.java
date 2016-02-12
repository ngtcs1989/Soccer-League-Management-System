package soccerLeague;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class InsertUpdateManager {


	public static void mainMenu(Connection conn)
	{

		try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				MenuList.showIUManagerSubMenu();
				userInput=UserInput.menuOption();
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;
				
				// Switch construct
				switch (choice) {
				case 3: break mainLoop;
				case 1:
					//Create New Manager. Enter new Manager information
					createManager(conn);
				  break;
				case 2:
				  // Update existing manager information
					updateManager(conn);
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
	
	public static void updateManager(Connection conn) throws IOException
	{	
		String managerId;
		String newValue,input;
		String updateSql;
		
		//Chossing the Manager through Magaer ID which can be obtained from People Listing 
		System.out.print ("Enter Manager ID of the manager whose details you want to update: ");
		managerId = UserInput.enterInput();
		
		//Asking update field
		System.out.print("Enter the field you want to Update: ");
		String fieldName = UserInput.enterInput(); 
		System.out.print("Enter new value for "+fieldName+": ");
		newValue=UserInput.enterInput();
		
		//Validating the field of Update statement
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
		
		//Building and executing the Update Statement based on the input values
		if(validUpdate)
		{
			updateSql="UPDATE Manager SET "+fieldName+"='"+newValue+"'  where managerId='" +managerId+"'" ;
		    System.out.println("Please confirm if you want to update the new value as "+ newValue +" for "+fieldName+" for Manager ID :"+managerId);
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
			
	
		
	
	
	public static void createManager(Connection conn)
	{
		try{
		String managerFirstName;
		String managerLastName;
		String managerAge;
		String managerNationality;
		String managerDebutDate;
		String insertSql;
		
		
		System.out.println ("Enter Manager First Name");
		managerFirstName = UserInput.enterInput();
				
		System.out.println ("Enter Manager Last Name");
		managerLastName = UserInput.enterInput();
		
		System.out.println ("Enter Manager Age");
		managerAge = UserInput.enterInput();
		
		System.out.println ("Enter Manager Nationality");
		managerNationality = UserInput.enterInput();
		
		System.out.println ("Enter Manager Debut Date");
		managerDebutDate = UserInput.enterInput();
		
		//Validation of Input Values
		boolean validInsert=true;
		String message="";
				
		if(managerFirstName.length()>30)
		{validInsert=false; message="Manager First Name should be less than 30 characters";}
		else if(managerLastName.length()>30)
		{validInsert=false; message="Manager Last Name should be less than 30 characters";}
		else if(Validator.isNumber(managerAge))
		{validInsert=false; message="Manager Age is invalid";}
		else if(managerNationality.length()>30)
		{validInsert=false; message="Manager Nationality should be less than 30 characters";}
		else if(Validator.isValidDate(managerDebutDate))
		{validInsert=false; message="Manager Debut Date is invalid";}
		
		if(validInsert)
		{
			insertSql = "INSERT INTO Manager(FirstName,LastName,Age,Nationality,DebutDate,CurrentClubID,"
					+"TotalMatches,isRetired) values"+"("+managerFirstName+"','"
					+managerLastName+"',"+managerAge+",'"+managerNationality+"','"
					+managerDebutDate+"',"+"NULL"+","+"0"+","+"0"+");";
			
			Statement st = conn.createStatement();	
			st.executeUpdate(insertSql);
		}
		
		else
		{
			System.out.println("There has been error in your input");
			System.out.println(message);
			System.out.println("Please, retry");
		}
		
	}catch(Exception e){
		 System.out.println("Error in connecting Database");
	 }
	}
}
