package soccerLeague;
/*
 * This class contains all the actual menu data. The menu data has been centralized in a single class because there are instance 
    where multiple menu list or display list are 
 * required due to interrelated tables due to which this model provides a reusable way to utlise the menus 
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class MenuList {

	public static void showMainMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|                -- Main Menu --                       |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. View Fixtures                              |");
		System.out.println("|        2. View Results                               |");
		System.out.println("|        3. View Match Summary                         |");
		System.out.println("|        4. View Team Standings                        |");
		System.out.println("|        5. View Player Statistics                     |");
		System.out.println("|        6. Insert/Update Statistics                   |");
		System.out.println("|        7. People Listing                             |");
		System.out.println("|        8. Exit                                       |");
		System.out.println("========================================================");
		
	}
	
	public static void showPeopleMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|            -- People Listings Menu --                |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. List all Players in League                 |");
		System.out.println("|        2. List all Managers in League                |");
		System.out.println("|        3. List all Referees in League                |");
		System.out.println("|        4. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	
	public static void showInsertUpdateMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|             -- Insert/Update Menu --                 |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create/Update Clubs                        |");
		System.out.println("|        2. Create/Update Player                       |");
		System.out.println("|        3. Create/Update Manager                      |");
		System.out.println("|        4. Create/Update Referee                      |");
		System.out.println("|        5. Create/Update Season                       |");
		System.out.println("|        6. Generate Fixtures                          |");
		System.out.println("|        7. Insert/Update Match Statistics             |");
		System.out.println("|        8. Insert LineUp                              |");
		System.out.println("|        9. Exit                                       |");
		System.out.println("========================================================");
		
	}
	
	public static void showIUSeasonSubMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- Season UpdateInsert Menu --              |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create New Season                          |");
		System.out.println("|        2. Update Existing Season                     |");
		System.out.println("|        3. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}

	public static void showIUClubSubMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- Season UpdateInsert Menu --              |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create New Club                            |");
		System.out.println("|        2. Update Existing Club                       |");
		System.out.println("|        3. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	
	public static void showIUPlayerSubMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- Player UpdateInsert Menu --              |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create New Player                          |");
		System.out.println("|        2. Update Existing Player                     |");
		System.out.println("|        3. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	
	public static void showIUManagerSubMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- ManagerUpdateInsert Menu --              |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create New Manager                         |");
		System.out.println("|        2. Update Existing Manager                    |");
		System.out.println("|        3. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	
	public static void showIURefereeSubMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- RefereeUpdateInsert Menu --              |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create New Referee                         |");
		System.out.println("|        2. Update Existing Referee                    |");
		System.out.println("|        3. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	
	public static void showIUMatchSubMenu()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- Match	 UpdateInsert Menu --              |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Create New Match                           |");
		System.out.println("|        2. Enter/Update Goals For Match               |");
		System.out.println("|        3. Enter/Update Fouls For Match               |");
		System.out.println("|        4. Update Player Match Statistics             |");
		System.out.println("|        5. Update Team Match Statistics               |");
		System.out.println("|        6. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	
	public static void viewFixtures()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|              -- Fixtures Menu --                     |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Select Season for the Fixtures             |");
		System.out.println("|        2. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	public static void viewTeamStandings()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|           -- View Team Standings --                  |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Select Season for the Team Standings       |");
		System.out.println("|        2. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	public static void viewResults()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|               -- Results Menu --                     |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Select Season for the Results              |");
		System.out.println("|        2. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	public static void viewPlayerMatchStats()
	{
		System.out.println("========================================================");
		System.out.println("|                 Soccer League                        |");
		System.out.println("|          -- Player Match Statistics Menu --          |");
		System.out.println("========================================================");
		System.out.println("| Options:                                             |");
		System.out.println("|        1. Select details for Player Match Statistics |");
		System.out.println("|        2. Back to Previous Menu                      |");
		System.out.println("========================================================");
		
	}
	public static void viewTeamMatchStats()
	{
		System.out.println("=========================================================");
		System.out.println("|                 Soccer League                         |");
		System.out.println("|          -- Team Match Stats Menu --                  |");
		System.out.println("=========================================================");
		System.out.println("| Options:                                              |");
		System.out.println("|        1. Choose Team and Season for Match Statistics |");
		System.out.println("|        2. Back to Previous Menu                       |");
		System.out.println("=========================================================");
		
	}
	
	public static String[][] allClubs(Connection conn)
	{
		String clubList[][] =null;
		try {
			Statement s = conn.createStatement();
			 s.executeQuery("SELECT ClubID, ClubName FROM Club");
			 ResultSet rs = s.getResultSet();
			 int count=0;
			 int rowCount = rs.last() ? rs.getRow() : 0;
			  clubList= new String[rowCount][2];
			 rs.beforeFirst();
			 while(rs.next()){
				 clubList[count][0]=rs.getString(1);
				 clubList[count][1]=rs.getString(2);
				 count++;
			 }
			 rs.close();
			 s.close();
		} catch (SQLException e) {
			System.out.println("Database Connection Issue");
		}

		
		return clubList;
	}

	public static void showSeasonResults(Connection conn,String seasonId)
	{
		
		try {
			String query ="SELECT CONCAT(date_format(t2.Date_Time,'%b %d %Y %h:%i %p'),' IST') as Date_Time,T2.MatchID,c1.ClubName AS Home_Club,T2.GoalsFor,T2.GoalsAgainst,c2.ClubName AS Away_Club From(SELECT T1.Date_Time,T1.MatchID,s1.ClubID AS home,T1.GoalsFor,s2.ClubID as away,T1.GoalsAgainst FROM (SELECT f.Date_Time,f.MatchID,HomeSquadID,p.GoalsFor,AwaySquadID,p.GoalsAgainst  FROM Fixtures f NATURAL JOIN PastMatches p WHERE HomeSquadID IN (SELECT SquadID FROM Squad WHERE SeasonID ="+ seasonId +" )) T1,Squad s1, Squad s2 where s1.SquadID = T1.HomeSquadID AND s2.SquadID = T1.AwaySquadID) T2,Club c1, Club c2 where c1.ClubId = home AND c2.ClubId = away ORDER BY t2.Date_Time;";
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery(query);
			System.out.println("\n\nList Of Matches for Season "+ seasonId);
			System.out.println("--------------------------------------------------------------------------------------------------");
			    while (rs.next()) {
			        String dateTime = rs.getString("Date_Time");
			        String matchId = rs.getString("MatchID");
			        String homeClub = rs.getString("Home_Club");
			        String goalsFor = rs.getString("GoalsFor");
			        String goalsAgainst = rs.getString("GoalsAgainst");			          
			        String awayClub = rs.getString("Away_Club");
			        System.out.format("%-10s%-30s%20s%-8s%-20s","MatchId:",matchId,homeClub,"   Vs",awayClub);
			        System.out.println("");
			        System.out.format("%-5s%-30s%27s%3s%-10s","Date:",dateTime,goalsFor," - ",goalsAgainst);
			        System.out.println("\n");
			    }
			    System.out.println("\n");
		} catch (SQLException e) {
			System.out.println("Invalid Input");
		}
	}
	
	
	
	
	public static void printAllSeasons(Connection conn)
	{
		try {
			Statement s = conn.createStatement();
			 s.executeQuery("SELECT SeasonID, StartDate, EndDate FROM Seasons");
			 ResultSet rs = s.getResultSet();
			 rs.beforeFirst();
			 System.out.format("%-10s%-30s%-30s","Season No","StartDate","EndDate");
			 System.out.println("");
			 while(rs.next()){
			 System.out.format("%-10s%-30s%-30s",rs.getString("SeasonId"),rs.getString("StartDate"),rs.getString("EndDate"));	
			 System.out.println(""); 
			 }
			 rs.close();
			 s.close();
		} catch (SQLException e) {
			System.out.println("Database Connection Issue");
		}
		
	}
	
	
	public static void confirmInsertMenu()
	{
		System.out.println("Your input is valid, Are you sure to insert ? ");
		System.out.print("Enter Y to Confirm or any other input to reject :");
	}
	
	
	public static void showAllPlayers(Connection conn)
	{
		try {
			String query ="SELECT CONCAT(FirstName,\" \",LastName) AS Player,Age,Nationality,date_format(DebutDate,'%b %d %Y') AS DebutDate,ClubName,IFNULL(TotalMatches,0) AS TotalMatches,isRetired FROM LeaguePlayer,Club where CurrentClubID = ClubId Order BY FirstName,LastName;";
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("\n");  
			System.out.println("                                     Players List                                             "); 
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
		} catch (SQLException e) {
			System.out.println("Invalid Input");
		}
	}
	public static void showAllManagers(Connection conn)
	{
		try {
			String query ="SELECT CONCAT(FirstName,\" \",LastName) AS Manager,Age,Nationality,date_format(DebutDate,'%b %d %Y') AS DebutDate,ClubName,IFNULL(TotalMatches,0),isRetired FROM Manager,Club where CurrentClubID = ClubId Order BY FirstName,LastName;";
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("\n");  
			System.out.println("                                                   List of Managers                                             "); 
			System.out.println("================================================================================================================");
			System.out.format("%-25s%-5s%-15s%-20s%-25s%-18s%-10s","Manager Name","Age","Nationality","Debut Date","Club","Matches Managed","Status");
			System.out.println("");
			System.out.println("================================================================================================================");
			
			while (rs.next()) {
			        String managerName = rs.getString("Manager");
			        String age = rs.getString("Age");
			        String nationality = rs.getString("Nationality");
			        String debutDate = rs.getString("DebutDate");			          
			        String clubName = rs.getString("ClubName");
			        String totalMatches = rs.getString("TotalMatches");			          
			        String isRetired = rs.getString("isRetired");
			        String status = Boolean.valueOf(isRetired) ? "Retired" : "Active";
			        System.out.format("%-25s%-5s%-15s%-20s%-25s%-18s%-10s",managerName,age,nationality,debutDate,clubName,totalMatches,status);
			        System.out.println("");
			        
			    }
			    System.out.println("\n");
		} catch (SQLException e) {
			System.out.println("Invalid Input");
		}
	}
	public static void showAllReferees(Connection conn)
	{
		try {
			String query ="SELECT CONCAT(FirstName,\" \",LastName) AS Referee,Age,Nationality,date_format(DebutDate,'%b %d %Y') AS DebutDate,IFNULL(TotalMatches,0),isRetired FROM Referee Order BY FirstName,LastName;";
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("\n");  
			System.out.println("                                                 List of Referees                                               "); 
			System.out.println("================================================================================================================");
			System.out.format("%-25s%-5s%-15s%-20s%-18s%-10s","Name","Age","Nationality","Debut Date","Matches Refereed","Status");
			System.out.println("");
			System.out.println("================================================================================================================");
			
			while (rs.next()) {
			        String refereeName = rs.getString("Referee");
			        String age = rs.getString("Age");
			        String nationality = rs.getString("Nationality");
			        String debutDate = rs.getString("DebutDate");			          
			        String totalMatches = rs.getString("TotalMatches");			          
			        String isRetired = rs.getString("isRetired");
			        String status = Boolean.valueOf(isRetired) ? "Retired" : "Active";
			        System.out.format("%-25s%-5s%-15s%-20s%-18s%-10s",refereeName,age,nationality,debutDate,totalMatches,status);
			        System.out.println("");
			        
			    }
			    System.out.println("\n");
		} catch (SQLException e) {
			System.out.println("Invalid Input");
		}
	}	
}