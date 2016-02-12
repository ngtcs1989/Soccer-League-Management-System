package soccerLeague;
import java.sql.*;

public class ViewFixtures{
	public static void mainMenu(Connection conn)
	{
		 try{
		     String userInput;	
			 int choice=-1;
			 Statement stmt = null;
			 mainLoop : while(true)
			 {
				//Displaying the Fixtures Menu 
				MenuList.viewFixtures();  
				userInput=UserInput.menuOption();
				choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;
				
				// Switch construct
				switch (choice) {
				case 2: break mainLoop;
				case 1:
				 MenuList.printAllSeasons(conn);
				  //Printing all the season and prompting the user to choose a particular season 	
				  System.out.print("Enter the season ID to view Fixtures: ");
				  String seasonId= UserInput.enterInput();
				  
				  
				  //Building the SQL query based on user input
				  String query = "select t1.MatchID, CONCAT(date_format(t1.Date_Time,'%b %d %Y %h:%i %p'),' IST') as Date_Time, c1.ClubName AS Home_Club, c2.ClubName AS Away_Club FROM"
				  		+ " (Select f.MatchID , f.HomeSquadID , f.AwaySquadID,sq1.ClubID as home, sq2.ClubID as away,f.Date_Time "
				  		+ "from Fixtures f, Seasons s, Squad sq1,Squad sq2 "
				  		+ "where (f.Date_Time between s.StartDate and s.EndDate) and s.SeasonID="
				  		+ seasonId + " and sq1.SeasonID = " + seasonId + " and sq2.SeasonID =" + seasonId 
				  		+ " and f.HomeSquadID=sq1.SquadID and f.AwaySquadID =sq2.SquadID) t1,Club c1,"
				  		+ "Club c2 where c1.ClubId = home AND c2.ClubId = away Order by t1.Date_Time";
				  System.out.println();
				  stmt = conn.createStatement();
				  //Checking the rowcount of the SQL Query
			      ResultSet rs = stmt.executeQuery(query);
			      int rowCount = rs.last() ? rs.getRow() : 0;
					 
			      
			      rs.beforeFirst();
				  if(rowCount>0)
				  {	  //Finally printing the Fixtures in an appropriate formatting style
					  System.out.format("%-10s%-30s%-25s%-25s","MatchId","Date","Home Club","Away Club");
			          System.out.println("");
			          System.out.println("--------------------------------------------------------------------------------");
					  while (rs.next()) {
					            String matchId = rs.getString("MatchID");
					            String dateTime = rs.getString("Date_Time");
					            String homeClub = rs.getString("Home_Club");
					            String awayClub = rs.getString("Away_Club");
					            System.out.format("%-10s%-30s%-25s%-25s",matchId,dateTime,homeClub,awayClub);
					            System.out.println("");
					        }
					        System.out.println();
				  }
				  else
				  {
					  System.out.println("No Data Found\n\n");
				  }
					        
				break;
				default:
					  System.out.println("Invalid input. Please reselect");
					  System.out.println();
					  break; 
				}
			 }
		 }catch (SQLException se)
		 {
			System.out.println("Invlid Input. Try again");
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		
	}
}