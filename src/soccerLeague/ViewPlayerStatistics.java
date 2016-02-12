package soccerLeague;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * This class provides the functionality of viewing the Player Statistics
 */
public class ViewPlayerStatistics {
	public static void mainMenu(Connection conn)
	{
		
		 try{
		     String userInput;	
			 int choice=-1;
			 Statement stmt = null;
			 mainLoop : while(true)
			 { //Displaying the Menu Options
				MenuList.viewPlayerMatchStats();  
				userInput=UserInput.menuOption();
				try{ //Taking User Input
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
				catch(NumberFormatException nfe)
				{choice=-1;}
				// Switch construct
				switch (choice) {
				case 2: break mainLoop;
				case 1:
				  String playerId=null,squadId=null;					  
				  stmt = conn.createStatement();				      
				  System.out.print("Enter the First Name of the player whose stats you require: ");
				  String firstName = UserInput.enterInput();
				  System.out.print("Enter the player's Last Name: ");
				  String lastName = UserInput.enterInput();
				  //Building the Query based on User Input
				  String query1 = "SELECT PlayerID from LeaguePlayer where FirstName Like '"+firstName+"' AND LastName LIKE '"+lastName+"'";
				  //Validating if the User input corresponds to a valid player
				  boolean validPlayer=false;
				  ResultSet rs1 = stmt.executeQuery(query1);
				  if(rs1.next())
				  {
					  playerId =rs1.getString("PlayerId");
					  validPlayer=true;
				  }
				  //If user input was valid then proceed
				  if (validPlayer)
				  {   //Printing all the seasons
					  System.out.println("\nList Of Seasons:-");
					  MenuList.printAllSeasons(conn);	
					  System.out.println("\n");
					  System.out.print("Enter the season ID to view results: ");
					  String seasonId= UserInput.enterInput();   //Retrieving User Input for seasons
					  //Retrieving the Squad for which the player played in the Season provided
					  String query2 = "SELECT SquadID FROM Squad natural join SquadPlayers where SeasonID = "+ seasonId +" AND PlayerID = "+playerId;
					  ResultSet rs2 = stmt.executeQuery(query2);				  
					  rs2.next();
					  squadId = rs2.getString("SquadID");
					  // Displaying the list of Matches of the selected player
					  String query3 ="SELECT CONCAT(date_format(T2.Date_Time,'%b %d %Y %h:%i %p'),' IST') as Date_Time,T2.MatchID,c1.ClubName AS Home_Club,c2.ClubName AS Away_Club FROM (select T1.MatchID,t1.Date_Time, sq1.ClubID as home, sq2.ClubID as away FROM (Select f.MatchID , f.HomeSquadID , f.AwaySquadID,f.Date_Time from Fixtures f, Seasons s where (f.Date_Time between s.StartDate and s.EndDate) and (f.HomeSquadID ="+ squadId +" OR f.AwaySquadID ="+squadId+") and s.SeasonID=1) T1, Squad sq1,Squad Sq2 where sq1.SeasonID =1 and sq2.SeasonID=1 and T1.HomeSquadID=sq1.SquadID and T1.AwaySquadID =sq2.SquadID ORDER BY Date_Time)T2,Club c1, Club c2 where c1.ClubId = home AND c2.ClubId = away ORDER BY Date_Time";;
					  ResultSet rs3 = stmt.executeQuery(query3);
					  System.out.println("\n\nMatches played by "+firstName+" "+lastName+", club in season "+seasonId +" are as follows:-");
					  System.out.println("===================================================================================");
					  System.out.format("%-25s%-10s%-25s%-25s","Date","MatchID","Home Club","Away Club");
			          System.out.println("");
			          System.out.println("===================================================================================");
				        while (rs3.next()) {
				            String dateTime = rs3.getString("Date_Time");
				            String matchId = rs3.getString("MatchID");
				            String homeClub = rs3.getString("Home_Club");
				            String awayClub = rs3.getString("Away_Club");
				            System.out.format("%-25s%-10s%-25s%-25s",dateTime,matchId,homeClub,awayClub);
				            System.out.println("");
				        }
				        //Prompting the user to choose from the available MatchIDs
				        System.out.print("\n\nSelect one Match ID from above :");
				        String matchId= UserInput.enterInput();
				        String query4 = "SELECT GoalsScored,GoalsSaved,TotalShots,ShotsOnTarget,GoalAssist,Offside,FoulsCommitted,FoulsSuffered,YellowCards,RedCards from PlayerMatchStats where PlayerID= "+playerId+" AND MatchID ="+matchId;;
						ResultSet rs4 = stmt.executeQuery(query4);
						//Retrieving the Player statistics for the match id provided and displaying it in a well format manner
						System.out.println("=========================================");
						System.out.println(firstName+" "+lastName +"'s statistics");
						System.out.println("=========================================");
						while(rs4.next())
						{
							System.out.format("%-30s%-5s","Goals Scored:",rs4.getString("GoalsScored"));
							System.out.println("");
							System.out.format("%-30s%-5s","Goals Saved:",rs4.getString("GoalsSaved"));
							System.out.println("");
							System.out.format("%-30s%-5s","Total Shots:",rs4.getString("TotalShots"));
							System.out.println("");	
							System.out.format("%-30s%-5s","Shots On Target:",rs4.getString("ShotsOnTarget"));
							System.out.println("");						
							System.out.format("%-30s%-5s","Goal Assist:",rs4.getString("GoalAssist"));
							System.out.println("");
							System.out.format("%-30s%-5s","Offsides:",rs4.getString("Offside"));
							System.out.println("");						
							System.out.format("%-30s%-5s","Fouls Committed:",rs4.getString("FoulsCommitted"));
							System.out.println("");						
							System.out.format("%-30s%-5s","Fouls Suffered:",rs4.getString("FoulsSuffered"));
							System.out.println("");						
							System.out.format("%-30s%-5s","Yellow Cards:",rs4.getString("YellowCards"));
							System.out.println("");						
							System.out.format("%-30s%-5s","Red Cards:",rs4.getString("RedCards"));
							System.out.println("");						
						}
						System.out.println("\n");	
					  break;
				  }
				  else
				  {System.out.println("No such Player. Try again");}
				default:
					  System.out.println("Invalid input. Please reselect");
					  System.out.println();
					  break; 
				}
			 }
		 }catch (SQLException se)
		 {
			 System.out.println("Error in connecting Database");
		 }
		 catch (Exception e)
		 {
			System.out.println("Invalid Input. Please try again");
		 }
		
	}

}
