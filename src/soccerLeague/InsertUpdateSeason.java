package soccerLeague;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/*
 * This class provides the functionality of Inserting and Updating the Seasons
 */
public class InsertUpdateSeason {

	public static void mainMenu(Connection conn)
	{

		try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				MenuList.showIUSeasonSubMenu();
				userInput=UserInput.menuOption();
				try{
					choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
					catch(NumberFormatException ne)
					{choice=-1;}
				
				// Switch construct
				switch (choice) {
				case 3: break mainLoop;
				case 1:
				  /*Create New Season	
				 Enter Total Number of Clubs and then Enter choose clubs from the pool, If the selection is appropriate then a new entry will be done in Seasons Table
				 and Squads will be created for that season. Season will not be locked at this point of time.
				 */	
				 createSeason(conn);	
					
				  break;
				case 2:
				  /* Once Fixtures has been generated then no new team can be added or deleted. Otherwise the teams can be removed. In that scenario there will be delete
				   * from the Squad table.  
				   */
				updateSeason(conn);
				  break;
				default:
				  System.out.println("Invalid input. Please reselect");
				  System.out.println();
				  break; 
				}
			 
				
			 }
			 
			 System.out.println("Season SubMenu Ended");
			

		 }catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	
	}
	
	static void createSeason(Connection conn)
	{
	//Displaying the list of existing seasons	
	System.out.println("Below are the existing Seasons");	
	MenuList.printAllSeasons(conn);	
	//Prompting for Season selection
	System.out.print("Enter new season date :");
	String input=UserInput.enterInput();
	boolean validSeasonDate=false;
	String seasonStartDate=null,querySQL="";
	if(Validator.isValidDate(input)){
	//Validating if the input date for the new season is not existing in the database and is of future	
		try {
			String query ="select count(*) AS COUNTR from Seasons where EndDate>'"+input+"' and  YEAR('"+input+"')<>YEAR(EndDate)";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			    while (rs.next()) {
			        int countR = rs.getInt("COUNTR");
			        if (countR==0)
			        	validSeasonDate=true;
			    }
			    
			    System.out.println("\n");
		} catch (SQLException e) 
				{System.out.println("Invalid Input");}
	
	if(validSeasonDate)
	{    //If the seson date is valid, proceed with the club selection for the Season
		seasonStartDate=input;
		System.out.println("Choose the Clubs you want to insert :");
		System.out.println("Below is the list of Club and their respective ClubIDs.");
		String clubList[][]=MenuList.allClubs(conn);
		System.out.println("ClubID        ClubName");
		for(int i=0;i<clubList.length;i++)
			System.out.println(clubList[i][0] +"            " +clubList[i][1]);
		int totalClubs=0;
		try{
		System.out.print("Enter how many clubs you want to input: ");
		totalClubs=Integer.parseInt(UserInput.enterInput());
		System.out.print("\nEnter Club ID which you want to choose :");
		input=UserInput.enterInput();
	    
		//Storing the entered choices of Clubs
		Set<String> seasonClubs= new HashSet<String>();
		for(int i=1;i<totalClubs && totalClubs<=clubList.length;i++)
			{
			System.out.print("Enter Club ID which you want to choose :");
			input=UserInput.enterInput();
			seasonClubs.add(input);
			}
		
		//validating the input club Ids
		int validclubs=0;
		Iterator<String> itr = seasonClubs.iterator();
        String clubName="";
		while(itr.hasNext())
        {
        	clubName=(String) itr.next();
        	for(int i=0;i<clubList.length;i++)
    		{
    			if(clubList[i][0].equals(clubName))
    				validclubs++;
    				
    		}
        }
		Statement stmt = conn.createStatement();
		
		
		if(validclubs==seasonClubs.size())
		{   // If all the input values were correct then proceed with INSERT statement creation and its execution
			querySQL="INSERT INTO SEASONS(StartDate,EndDate,TotalClubs,TotalMatches) VALUES('"+seasonStartDate+"',NULL,'"+validclubs+"','"+validclubs*(validclubs-1)+"')";
			stmt = conn.createStatement();	
			stmt.executeUpdate(querySQL);
			
			//Create an entry into the fixture lock in unlock (default) mode so that in future Season can be altered for addition/deletion of different clubs 
			querySQL="INSERT INTO FixtureLock(SeasonID) SELECT SeasonID from Seasons where SeasonID NOT IN (Select SeasonID from FixtureLock);";
			stmt = conn.createStatement();	
			stmt.executeUpdate(querySQL);
			
			//Updating the Season End Date based on the Total matches which itself is calculated based on number of clubs
			querySQL="UPDATE SEASONS SET EndDate=DATE_ADD(StartDate,INTERVAL TotalMatches DAY)";
			stmt = conn.createStatement();	
			stmt.executeUpdate(querySQL);
			
			//Retrieving new SeasonID
			int newSeasonId=0;
			querySQL="Select MAX(SeasonID) AS SId from Seasons";
			ResultSet rs = stmt.executeQuery(querySQL);
			   if (rs.next()) {
				   newSeasonId=Integer.parseInt(rs.getString("SId"));
				   
			   }	   
			 //Generating Squads
			 for(String clubID : seasonClubs){
		            querySQL="INSERT INTO SQUAD(SeasonID,ClubId) Values('"+newSeasonId+"','"+clubID+"');";
		            stmt = conn.createStatement();	
					stmt.executeUpdate(querySQL);
			 	}
			 
		System.out.println("Seasons and Squads Created");
		
		}
		else {System.out.println("Invalid Input Please Try again");}
		}
		catch (Exception e){ System.out.println("Invalid Input Please Try again"); e.printStackTrace();}
	
	}
	else {System.out.println("Invalid Input"); }
	
	
	}
	else{System.out.println("Invalid new Season Date");}
	
	}
	
	

	@SuppressWarnings("resource")
	static void updateSeason(Connection conn)
	{
		
		try{
			String input="", querySQL=""; 
			int choice=-1;
		    String fieldName, newValue; 
			MenuList.printAllSeasons(conn);	
			System.out.print("Enter Season Number which you want to update :");
			String seasonId=UserInput.enterInput();
			boolean validSeasonId=false;
			int countR=0;
			//Validating the Season Id
			String query ="select count(*) AS COUNTR from Seasons where seasonId="+seasonId;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			    while (rs.next()) {
			           countR = rs.getInt("COUNTR");
			        if (countR==1)
			        	validSeasonId=true;
			    }
			    
			if(validSeasonId)
			{

				
				System.out.println("Enter 1 to update Season Date");
				System.out.println("Enter 2 to delete Season and Squads");
				input=UserInput.enterInput();
					try{
					choice = Validator.isNumber(input) ? Integer.parseInt(input) : -1;}
					catch(NumberFormatException ne){choice =-1;}
				
				switch(choice)
				{
				case 1:
				System.out.print("Enter field which you want to update :");
				fieldName=UserInput.enterInput();
				System.out.print("Enter New Value for the field :");
				newValue=UserInput.enterInput();
				querySQL="UPDATE Seasons set "+fieldName+"="+newValue+" where seasonId="+seasonId;
				stmt = conn.createStatement();	
				stmt.executeUpdate(querySQL);
				System.out.println("Season Updated");
				break;
				case 2:
				querySQL="DELETE FROM SEASONS where seasonId="+seasonId;
				stmt = conn.createStatement();	
				stmt.executeUpdate(querySQL);
				querySQL="DELETE FROM SQUAD where seasonId="+seasonId;
				stmt = conn.createStatement();	
				stmt.executeUpdate(querySQL);
				System.out.println("Seasons and Squads Deleted successfully");	
				break;
				default: System.out.println("You input option was incorrect");
				}
			
			}
			else
			{
				System.out.println("Invalid SeasonID. Try again");
			}
		}catch(Exception ex)
		{
			System.out.println("Invalid Input");
		}
		
		
		
	}
	
	
	
	
}
