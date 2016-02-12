package soccerLeague;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * This class provides the functionality of viewing Team Ranking or Standing for each season. It Prints the results in a well formatted manner
 */
public class ViewTeamStandings {
	public static void mainMenu(Connection conn)
	{
		 try{
		     String userInput;	
			 int choice=-1;
			 Statement stmt = null;
			 mainLoop : while(true)
			 {
				//Printing the SubMenu first
				MenuList.viewTeamStandings();  
				System.out.println("");
				userInput=UserInput.menuOption();
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;
				
				// Switch construct
				switch (choice) {
				case 2: break mainLoop;
				case 1:
				  System.out.println("\n");
				  MenuList.printAllSeasons(conn);  //Displaying the list of seasons	
				  System.out.println("\n");
				  System.out.print("Enter the season ID to view Team Standings: "); //Taking user input for Season ID
				  String seasonId= UserInput.enterInput();				  
				  String query = "Select ClubRank,ClubName,AwayWins,AwayLosses,AwayDraws,HomeWins,HomeLosses,HomeDraws,TotalWin,TotalLoss,TotalDraws,Points from Standings Natural join Club where SeasonId="+ seasonId +" order by ClubRank;";	
				  stmt = conn.createStatement();
				  //Displaying the Team Standings in a Well Formatted manner
				  System.out.println("");
				  System.out.println("=============================================================================================================================================================================");
				  System.out.format("%-5s%-25s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-10s","Rank","Club","Away Wins", "Away Losses","Away Draws","Home Wins","Home Losses","Home Drwas","Total Wins","Total Losses","Total Drwas","Points");
		          System.out.println("");
		          System.out.println("=============================================================================================================================================================================");ResultSet rs = stmt.executeQuery(query);
			        while (rs.next()) {
			            String clubRank = rs.getString("ClubRank");
			            String clubName = rs.getString("ClubName");
			            String awayWins = rs.getString("AwayWins");
			            String awayLosses = rs.getString("AwayLosses");
			            String awayDraws = rs.getString("AwayDraws");
			            String homeWins = rs.getString("Homewins");
			            String homeLosses = rs.getString("HomeLosses");
			            String homeDraws = rs.getString("HomeDraws");
			            String totalWin = rs.getString("TotalWin");
			            String totalLoss = rs.getString("TotalLoss");
			            String totalDraws = rs.getString("TotalDraws");
			            String points = rs.getString("Points");
			            System.out.format("%-5s%-25s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-10s",clubRank,clubName,awayWins,awayLosses,awayDraws,homeWins,homeLosses,homeDraws,totalWin,totalLoss,totalDraws,points);
			            System.out.println("");
			        }
			        System.out.println("");
				  break;
				default:
					  System.out.println("Invalid input. Please reselect");
					  System.out.println();
					  break; 
				}
			 }
		 }catch (SQLException se)
		 {
			 se.printStackTrace();
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		
	}
}
