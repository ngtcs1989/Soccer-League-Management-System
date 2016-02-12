package soccerLeague;
/*
 * This class provides the functionality of Squad Preparation. It draws the list of players from the available players pool and 
 * based on user selection assigns them to a particular squad
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SquadPreparation {

	public static void mainMenu(Connection conn)
	{
		try{
		
		//Choosing the Season
			String input=""; 
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
		//Choosing the Club	
			System.out.println("Below is the list of Club and their respective ClubIDs.");
			String clubList[][]=MenuList.allClubs(conn);
			System.out.println("ClubID        ClubName");
			for(int i=0;i<clubList.length;i++)
				System.out.println(clubList[i][0] +"            " +clubList[i][1]);
			System.out.print("\nEnter Club ID which you want to choose :");
			input=UserInput.enterInput();
			//Validating the Input
			boolean validInput=false;
			for(int i=0;i<clubList.length;i++)
			{
				
				if(clubList[i][0].equals(input))
					validInput=true;
			}
			String clubId=input;
			
			
			if(validInput){
			//Fetch SquadID
				int squadId=0;
				query="SELECT SquadId from SQUAD where ClubID='"+clubId+"' and SeasonId='"+seasonId+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				    if (rs.next()) { squadId = rs.getInt("SquadId"); }
				
			//Display Players from available pool
				   
						query ="SELECT CONCAT(FirstName,\" \",LastName) AS Player,Age,Nationality,date_format(DebutDate,'%b %d %Y') AS DebutDate,ClubName,IFNULL(TotalMatches,0) AS TotalMatches,isRetired FROM LeaguePlayer,Club where CurrentClubID = ClubId Order BY FirstName,LastName;";
						stmt = conn.createStatement();
						stmt = conn.createStatement();
						rs = stmt.executeQuery(query);
						System.out.println("\n");  
						System.out.println("                         Available   Players List                                             "); 
						System.out.println("================================================================================================================");
						System.out.format("%-25s%-5s%-15s%-20s%-25s%-10s%-10s","Player","Age","Nationality","Debut Date","Club","Matches","Status");
						System.out.println("");
						System.out.println("================================================================================================================");
						
						while (rs.next()) {
						        String playerName = rs.getString("Player");
						        String age = rs.getString("Age");
						        String nationality = rs.getString("Nationality");
						        String debutDate = rs.getString("DebutDate");			          
						        String clubName = rs.getString("ClubName");
						        String totalMatches = rs.getString("TotalMatches");			          
						        String isRetired = rs.getString("isRetired");
						        String status = Boolean.valueOf(isRetired) ? "Retired" : "Active";
						        System.out.format("%-25s%-5s%-15s%-20s%-25s%-10s%-10s",playerName,age,nationality,debutDate,clubName,totalMatches,status);
						        System.out.println("");
						        
						    }
						    System.out.println("\n");
					 
				    
			//Choose Number of Players need and Enter Player ID. For each insert a Squad Player will be inserted. If issue process halted
			System.out.print("Choose number of players you need: ");
			int pCount=Integer.parseInt(UserInput.enterInput());			    
			String playerId="";
			for(int i=0;i<pCount;i++)
				{
					System.out.print("Enter Player ID: ");
					playerId=UserInput.enterInput();
					query="INSERT INTO SquadPlayers(PlayerId,SquadId) VALUES('"+playerId+"','"+squadId+"')";
					stmt.executeUpdate(query);
					System.out.println("Squad Player entered successfully");
				}
						    
			}
			else
			{System.out.println("Your input Club ID was invalid");}
		}
		System.out.println("Your input Season ID was invalid");
		
		
		}catch(Exception e){System.out.println("Invalid Input"); }
	}
	
}
