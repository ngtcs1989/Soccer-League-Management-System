package soccerLeague;
/*
 * This class provides the functionality of generating LineUps or the playing Team for a particular fixture 
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InsertUpdateLineup {
	public static void mainMenu(Connection conn)
	{

		try{
		     String homeSquadId,awaySquadId;	
			 int count=0;
			 mainLoop : while(true)
			 {
				//Prints all the seasons 
				MenuList.printAllSeasons(conn);
				//Prompts for season selection
				System.out.print("Select the season for your match: ");
				String seasonId = UserInput.enterInput();
		        String query = "SELECT MatchID,CONCAT(date_format(Date_Time,'%b %d %Y %h:%i %p'),' IST') as Date_Time,c1.ClubName AS Home_Club,C2.ClubName AS Away_Club FROM Fixtures,Squad s1,Squad s2,Club c1,Club C2 where Date_Time BETWEEN (Select StartDate FROM Seasons WHERE SeasonID ='"+ seasonId +"') AND (SELECT EndDate FROM Seasons Where SeasonID = '"+seasonId+"') AND s1.SquadID = HomeSquadID AND s2.SquadID = AwaySquadID AND s1.ClubID = c1.ClubID AND s2.ClubID = c2.ClubID AND MatchID NOT in (select MatchID FROM PastMatches) order by Date_Time;";
		        Statement s = conn.createStatement();
				s.executeQuery(query);
				ResultSet rs = s.getResultSet();
				int rowCount = rs.last() ? rs.getRow() : 0;
				rs.beforeFirst();
				//If valid season has been input then proceed
				if(rowCount >0)
				{
					while(rs.next())
					{	//Printing available Matches for the Seasons
						System.out.format("%-10s%-30s%-20s%-8s%20s",rs.getString("MatchID"),rs.getString("Date_Time"),rs.getString("Home_Club"),"   VS   ",rs.getString("Away_Club"));
						System.out.println("");
					}
					System.out.print("Select Matchid of the Match you want to insert a Lineup for from above: ");
					String matchId = UserInput.enterInput();
					
					query = "SELECT HomeSquadID,,AwaySquadID from Fixtures where MatchID = '"+matchId+"';";  
					s.executeQuery(query);
					rs = s.getResultSet();
					rowCount = rs.last() ? rs.getRow() : 0;
					rs.beforeFirst();
					if(rowCount >0)
					{
						rs.next();
						homeSquadId= rs.getString("HomeSquadID");
						awaySquadId = rs.getString("AwaySquadID");
					}
					else
					{
						System.out.println("Invalid MatchID");
						break;
					}
					//Printing Available Squad Players for the chosen match
					
					System.out.println("               Available Squad Players For Home Team                 ");
					System.out.println("=====================================================================");
					System.out.format("%-10s%-20s%-10s","Player ID","Player Name","Position");
					System.out.println("");
					System.out.println("=====================================================================");
					query = "SELECT PlayerID,CONCAT(FirstName,\" \",LastName) AS PName,Position FROM LeaguePlayer NATURAL JOIN SquadPlayers where SquadID = '"+homeSquadId+"';";
					s.executeQuery(query);
					rs = s.getResultSet();
					List<String> hSquad = new ArrayList<String>() ;
					List<String> hLineup = new ArrayList<String>() ;
					while(rs.next())
					{
						String playerID = rs.getString("PlayerID");
						hSquad.add(playerID);
						System.out.format("%-10s%-20s%-10s",playerID,rs.getString("PName"),rs.getString("Position"));
						System.out.println("");
					}
					//Choosing the LineUp for Home Team
					System.out.println("\nSelect Line Up for Home Team:-\n");
					count = 0;
					while (count <=11)
					{
						System.out.print("Enter Player ID of Player "+String.valueOf(count+1)+": ");
						String input = UserInput.enterInput();
						if(hSquad.contains(input))
						{
							if(hLineup.contains(input))
							{
								System.out.println("Player already selected, select another player");
							}
							else
							{
								hLineup.add(input);
								query = "INSERT INTO LineUp(MatchID,PlayerID,SquadID) VALUES('"+matchId+"','"+input+"','"+homeSquadId+"');";
								count++;
							}
						}
						else
						{
							System.out.println("Player Not a part of available pool of players, Please try again");
						}
					}
					
					
					
					// Displaying available player for Away Squad.
					System.out.println("               Available Squad Players For Away Team                 ");
					System.out.println("=====================================================================");
					System.out.format("%-10s%-20s%-10s","Player ID","Player Name","Position");
					System.out.println("");
					System.out.println("=====================================================================");
					query = "SELECT PlayerID,CONCAT(FirstName,\" \",LastName) AS PName,Position FROM LeaguePlayer NATURAL JOIN SquadPlayers where SquadID = '"+awaySquadId+"';";
					s.executeQuery(query);
					rs = s.getResultSet();
					List<String> aSquad = new ArrayList<String>() ;
					List<String> aLineup = new ArrayList<String>() ;
					while(rs.next())
					{
						String playerID = rs.getString("PlayerID");
						aSquad.add(playerID);
						System.out.format("%-10s%-20s%-10s",playerID,rs.getString("PName"),rs.getString("Position"));
						System.out.println("");
					}
					//Choosing the Lineup for Away Team
					System.out.println("\nSelect Line Up for Away Team:-\n");
					count = 0;
					//Taking userinput and building the LineUp
					while (count <=11)
					{
						System.out.print("Enter Player ID of Player "+String.valueOf(count+1)+": ");
						String input = UserInput.enterInput();
						if(aSquad.contains(input))
						{
							if(aLineup.contains(input))
							{
								System.out.println("Player already selected, select another player");
							}
							else
							{
								aLineup.add(input);
								query = "INSERT INTO LineUp(MatchID,PlayerID,SquadID) VALUES('"+matchId+"','"+input+"','"+awaySquadId+"');";
								count++;
							}
						}
						else
						{
							System.out.println("Player Not a part of available pool of players, Please try again");
						}
					}
					// Exit While loop 
				break mainLoop;
					
				}
				else
				{
					System.out.println("No Matches left to be played in the season");
					break;
				}
		     	
			 }
			 			

		 }catch(Exception se){
			 System.out.println("Error: "+se);
			 se.printStackTrace();
		 }
	}
}
