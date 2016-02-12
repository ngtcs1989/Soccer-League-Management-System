package soccerLeague;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.sql.Statement;
public class InsertUpdateMatch {
	
	// File gets input from User with respect to match statistics, player match statistics and team match statistics.
	
	// <CREATE MATCH>
	// Updates following attributes:
	// PastMatches {MatchID, Half1ExtraTime, Half2ExtraTime, Attendance, RefereeID}
	// LineUp {MatchID, PlayerID. SquadID}
	// PlayerMatchStats {MatchID, PlayerID}
	// TeamMatchStats {MatchID, SquadID}
	
	// <ENTER GOALS FOR MATCH>
	// Updates following attributes:
	// Goals {GoalID, MatchId, PlayerID, Time_Stamp, GoalType}
	// PlayerMatchStats {GoalsScored}
	// TeamMatchStats {Goals}
	// PastMatches {GoalsFor, GoalsAgainst}
	
	// <ENTER FOULS FOR MATCH>
	// Updates following attributes:
	// Fouls {FoulID, MatchId, PlayerID, Time_Stamp, Card}
	// PlayerMatchStats {FoulsCommitted, RedCards, YellowCards}
	// TeamMatchStats {Fouls}
	
	// <ENTER PLAYER MATCH STATISTICS FOR A PARTICULAR MATCH, PARTICULAR PLAYER>
	// Updates following attributes:
	// PlayerMatchStats {GoalsSaved, TotalShots, ShotsOnTarget, GoalAssist, Offside, FoulsSuffered}
	// TeamMatchStats {ShotsOnTarget}
	
	// <ENTER TEAM MATCH STATISTICS FOR A PARTICULAR MATCH, HOME/AWAY SQUAD>
	// Updates following attributes:
	// TeamMatchStats {Tackles, PossessionPercent, MatchResult}
	
	public static void mainMenu(Connection conn)
	{

		try{
		     String userInput;	
			 int choice=-1;
			 mainLoop : while(true)
			 {
				MenuList.showIUMatchSubMenu();
				userInput=UserInput.menuOption();
				try{
					choice = Validator.isNumber(userInput) ? Integer.parseInt(userInput) : -1;}
					catch(NumberFormatException ne)
					{choice=-1;}
				
				// Switch construct
				switch (choice) {
				case 7: break mainLoop;
				case 1:
					 //Create New Match. Enter new Match information
					 createMatch(conn);  // <CREATE MATCH>
				     break;
				case 2:
					  // Update existing goals information
					  updateGoalsInMatch(conn);  // <ENTER GOALS FOR MATCH>
					  break;
				case 3:
					  // Update existing fouls information
					  updateFoulsInMatch(conn);  // <ENTER FOULS FOR MATCH>
					  break;
				case 4:
					  // Update player match statistics
					  selectUpdatePlayerMatchStats(conn);  // <ENTER PLAYER MATCH STATISTICS FOR A PARTICULAR MATCH, PARTICULAR PLAYER>
					  break;
				case 5:
					  // Update team match statistics
					  selectUpdateTeamMatchStats(conn);    // <ENTER TEAM MATCH STATISTICS FOR A PARTICULAR MATCH, HOME/AWAY SQUAD>
					  break;
				default:
				  System.out.println("Invalid input. Please reselect");
				  System.out.println();
				  break; 
				}
			 
				
			 }
			 
			 System.out.println("Club SubMenu Ended");
			

		 }catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	
	}
	
	public static void updateGoalsInMatch(Connection conn)
	{
		try{
			Statement stmt;
			ResultSet rs;
			String userInput;
			
			//////////////////  SELECT SEASON  ////////////////////////////////////////////
			Hashtable<String, Integer> seasonList = new Hashtable<String, Integer>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT SeasonID FROM Seasons");
			System.out.println("Seasons: ");
			while (rs.next()) 
			{
				System.out.println("=> "+rs.getInt("SeasonId"));
				seasonList.put(Integer.toString(rs.getInt("SeasonId")),rs.getInt("SeasonId"));
			}
			
			System.out.println("Select Season in which Goal is to be entered..");
			String season = UserInput.enterInput();
			Integer seasonID = seasonList.get(season);
			if (seasonID == null) {
			   System.out.println("Invalid Season Entered..");
			   return;
			}
			//////////////////////////////////////////////////////////////////////////////////
			
			//////////////////SELECT HOME & AWAY SQUAD FROM LIST OF CLUBS DISPLAYED  /////////////////
			    String selectQuery;
				Hashtable<String, Integer> squadList = new Hashtable<String, Integer>();
				selectQuery = "Select  Club.ClubName, Club.ClubID, Squad.SquadID from ("
						   +"(Select ClubName, ClubID from Club) as Club "
						   +"INNER JOIN "
						   +"(Select SquadID, ClubID from Squad where SeasonID ="+seasonID+") as Squad "
						   +"ON Club.ClubID = Squad.ClubID)";
				//System.out.println (selectQuery);
			
				rs = stmt.executeQuery(selectQuery);
				System.out.println("Squads: ");
				int count=1;
				while (rs.next()) 
				{
					System.out.println(count+". "+ rs.getString("ClubName"));
					squadList.put(Integer.toString(count), rs.getInt("SquadID"));
					count++;
				}
				
				System.out.println("Select Home Squad for the Match..");
				String homeSquad = UserInput.enterInput();
				Integer homeSquadID = squadList.get(homeSquad);
				if (homeSquadID == null) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				
				System.out.println("Select Away Squad for the Match..");
				String awaySquad = UserInput.enterInput();
				Integer awaySquadID = squadList.get(awaySquad);
				if ((awaySquadID == null) || (awaySquadID == homeSquadID)) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				
				//////////////////////////////////////////////////////////////////////////////////

				// Match ID corresponding to the home/away squads and season selected is looked up from PastMatches.
				selectQuery = "Select MatchID from Fixtures where HomeSquadID = "
					    + homeSquadID+ " AND AwaySquadID ="+ awaySquadID;
				System.out.println (selectQuery);
				rs = stmt.executeQuery(selectQuery);
				
				Integer matchID=0;
				if(rs.next()) 
				{
					matchID = rs.getInt("MatchID");
				}
				else
				{
					System.out.println("Error: Fixture Not Created..");
					return;
				}
				
				selectQuery = "Select MatchID from PastMatches where MatchID ="
					    + matchID;
				rs = stmt.executeQuery(selectQuery);
				if (rs.next() == true)
				{
					
					// Display All Players present in the lineup for the  match selected. Player corresponding to goal is selected from this list
					selectQuery = "Select  Player.PlayerID, Player.FirstName, Player.LastName from " 
						    +"(Select PlayerID,  FirstName , LastName from LeaguePlayer) as Player "
						    +"INNER JOIN " 
						    +"(Select PlayerID from LineUp where MatchID = "+matchID+") as LineUp "
						    +"ON LineUp.PlayerID = Player.PlayerID";
					rs = stmt.executeQuery(selectQuery);
					count =1;
					Hashtable<String, Integer> lineupList = new Hashtable<String, Integer>();
					while (rs.next()) 
					{
						System.out.println(count+". " + " "+ rs.getString("FirstName") + " "
								+ rs.getString("LastName")+"("+ rs.getInt("PlayerID")+")");
						lineupList.put(Integer.toString(count), rs.getInt("PlayerID"));
						count++;
					}
					System.out.println ("Select Lineup Player corresponding to the goal");
					
					String lineupPlayer = UserInput.enterInput();
					Integer lineupPlayerID = lineupList.get(lineupPlayer);
					if (lineupPlayerID == null) {
					   System.out.println("Invalid Player Entered..");
					   return;
					}
					
					////////   Enter attributes with respect to the goal  //////////////
					
					System.out.println ("Enter Time of Goal");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer timeOfGoal = Integer.parseInt(userInput);
					
					
					
					System.out.println ("Enter Goal type(regular,penalty,free-kick,corner)");
					String goalType = UserInput.enterInput().toUpperCase();
					if (!(goalType.equals("REGULAR") || goalType.equals("PENALTY") || goalType.equals("FREE-KICK") || goalType.equals("CORNER"))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					
					//// Goal entry is inserted into the Goals table
					
					String insertSql = "INSERT INTO Goals(MatchID,PlayerID,Time_stamp,GoalType) VALUES("+matchID
									    +","+lineupPlayerID+","+timeOfGoal+",'"+goalType+"')";
					//System.out.println (insertSql);
					stmt.executeUpdate(insertSql);
					
					String updateSql;
					
					///////////// Update GoalsFor, GoalsAgainst attributes in PastMatches table  ///////////////////////
					
					selectQuery = "Select SquadID from LineUp where MatchID ="
						    + matchID + " AND PlayerID =" +lineupPlayerID;
					//System.out.println (selectQuery);
					rs = stmt.executeQuery(selectQuery);
					int SquadID;
					if (rs.next() == true)
					{
						SquadID = rs.getInt("SquadID");
					}
					else
					{
						System.out.println("Error..");
						return;
					}
					selectQuery = "Select * from Fixtures where MatchID ="
						    + matchID + " AND HomeSquadID =" + SquadID;
					//System.out.println (selectQuery);
					rs = stmt.executeQuery(selectQuery);
					if (rs.next() == true)
					{
						//   Selected LineUp Player is From Home Squad. Update GoalsFor column in PastMatches 
						updateSql = "UPDATE PastMatches set GoalsFor = GoalsFor+1 " 
							    +"where MatchID="+matchID;
					}
					else
					{
						//   Selected LineUp Player is From Away Squad. Update GoalsFor column in PastMatches 
						updateSql = "UPDATE PastMatches set GoalsAgainst = GoalsAgainst+1 " 
							    +"where MatchID="+matchID;
					}
					
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					
					////////////////////////////////////////////////////////////////////////////////////////////////
					
					///////////// Update Goal attributes in PlayerMatchStats, TeamMatchStats table  ///////////////////////
					
					updateSql = "UPDATE PlayerMatchStats set GoalsScored = GoalsScored+1 " 
									    +"where MatchID="+matchID+" AND PlayerID="+lineupPlayerID;
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					
					updateSql = "UPDATE TeamMatchStats set Goals = Goals+1 "
								+"where MatchID="+matchID+" AND SquadID IN "
								+"(select SquadID from LineUp where MatchID="+matchID+" AND PlayerID="+lineupPlayerID+")";
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					
					/////////////////////////////////////////////////////////////////////////////////////////////////////
					
					System.out.println ("GOAL UPDATED SUCCESSFULLY");
					System.out.println ("TEAM MATCH STATS UPDATED SUCCESSFULLY");
					System.out.println ("PLAYER MATCH STATS UPDATED SUCCESSFULLY");
					
					rs.close();
					stmt.close();
				}
				else
				{
					System.out.println("Match profile not created..");
					return;
				}
				
				
		}catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
				
	}
	
	public static void updateFoulsInMatch(Connection conn)
	{
		try{
			Statement stmt;
			ResultSet rs;
			String userInput;
			
			//////////////////SELECT SEASON  ////////////////////////////////////////////
			Hashtable<String, Integer> seasonList = new Hashtable<String, Integer>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT SeasonID FROM Seasons");
			System.out.println("Seasons: ");
			while (rs.next()) 
			{
				System.out.println("=> "+rs.getInt("SeasonId"));
				seasonList.put(Integer.toString(rs.getInt("SeasonId")),rs.getInt("SeasonId"));
			}
			
			System.out.println("Select Season in which Foul is to entered..");
			String season = UserInput.enterInput();
			Integer seasonID = seasonList.get(season);
			if (seasonID == null) {
			   System.out.println("Invalid Season Entered..");
			   return;
			}
			//////////////////////////////////////////////////////////////////////////////////
			//////////////////SELECT HOME & AWAY SQUAD FROM LIST OF CLUBS DISPLAYED  /////////////////
			    String selectQuery;
				Hashtable<String, Integer> squadList = new Hashtable<String, Integer>();
				selectQuery = "Select  Club.ClubName, Club.ClubID, Squad.SquadID from ("
						   +"(Select ClubName, ClubID from Club) as Club "
						   +"INNER JOIN "
						   +"(Select SquadID, ClubID from Squad where SeasonID ="+seasonID+") as Squad "
						   +"ON Club.ClubID = Squad.ClubID)";
				//System.out.println (selectQuery);
				rs = stmt.executeQuery(selectQuery);
				System.out.println("Squads: ");
				int count=1;
				while (rs.next()) 
				{
					System.out.println(count+". "+ rs.getString("ClubName"));
					squadList.put(Integer.toString(count), rs.getInt("SquadID"));
					count++;
				}
				
				System.out.println("Select Home Squad for the Match..");
				String homeSquad = UserInput.enterInput();
				Integer homeSquadID = squadList.get(homeSquad);
				if (homeSquadID == null) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				//////////////////////////////////////////////////////////////////////////////////

				// Match ID corresponding to the home/away squads and season selected is looked up from PastMatches.
				System.out.println("Select Away Squad for the Match..");
				String awaySquad = UserInput.enterInput();
				Integer awaySquadID = squadList.get(awaySquad);
				if ((awaySquadID == null) || (awaySquadID == homeSquadID)) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				selectQuery = "Select MatchID from Fixtures where HomeSquadID = "
					    + homeSquadID+ " AND AwaySquadID ="+ awaySquadID;
				//System.out.println (selectQuery);
				rs = stmt.executeQuery(selectQuery);
				
				Integer matchID=0;
				if(rs.next()) 
				{
					matchID = rs.getInt("MatchID");
				}
				else
				{
					System.out.println("Error: Fixture Not Created..");
					return;
				}
				
				selectQuery = "Select MatchID from PastMatches where MatchID ="
					    + matchID;
				//System.out.println (selectQuery);
				rs = stmt.executeQuery(selectQuery);
				
				// Display All Players present in the lineup for the  match selected. Player corresponding to foul is selected from this list
				if (rs.next() == true)
				{
					selectQuery = "Select  Player.PlayerID, Player.FirstName, Player.LastName from " 
						    +"(Select PlayerID,  FirstName , LastName from LeaguePlayer) as Player "
						    +"INNER JOIN " 
						    +"(Select PlayerID from LineUp where MatchID = "+matchID+") as LineUp "
						    +"ON LineUp.PlayerID = Player.PlayerID";
					rs = stmt.executeQuery(selectQuery);
					count =1;
					Hashtable<String, Integer> lineupList = new Hashtable<String, Integer>();
					while (rs.next()) 
					{
						System.out.println(count+". " + " " + rs.getString("FirstName") 
								+ rs.getString("LastName")+"("+ rs.getInt("PlayerID")+")");
						lineupList.put(Integer.toString(count), rs.getInt("PlayerID"));
						count++;
					}
					System.out.println ("Select Lineup Player corresponding to the foul");
					
					String lineupPlayer = UserInput.enterInput();
					Integer lineupPlayerID = lineupList.get(lineupPlayer);
					if (lineupPlayerID == null) {
					   System.out.println("Invalid Player Entered..");
					   return;
					}
					
					////////   Enter attributes with respect to the foul  //////////////

					System.out.println ("Enter Time of Foul");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer timeOfFoul = Integer.parseInt(userInput);
					
					System.out.println ("Enter Card received(red,yellow)");
					String cardReceived = UserInput.enterInput().toUpperCase();
					if (!(cardReceived.equals("RED") || cardReceived.equals("YELLOW"))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					
					//// Foul entry is inserted into the Fouls table
					String insertSql = "INSERT INTO Fouls(MatchID,PlayerID,Time_stamp,Card) VALUES("+matchID
									    +","+lineupPlayerID+","+timeOfFoul+",'"+cardReceived+"')";
					//System.out.println (insertSql);
					stmt.executeUpdate(insertSql);
					//////////////////////////////////////////////////////////////////////////////////////////////
					
					///////////// Update Foul attributes in PlayerMatchStats, TeamMatchStats table  //////////////

					String updateSql = "UPDATE PlayerMatchStats set FoulsCommitted = FoulsCommitted+1 " 
						    +"where MatchID="+matchID+" AND PlayerID="+lineupPlayerID;
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					if (cardReceived.equals("RED"))
					{
						updateSql = "UPDATE PlayerMatchStats set RedCards = RedCards+1 " 
							    +"where MatchID="+matchID+" AND PlayerID="+lineupPlayerID;
					}
					else
					{
						updateSql = "UPDATE PlayerMatchStats set YellowCards = YellowCards+1 " 
							    +"where MatchID="+matchID+" AND PlayerID="+lineupPlayerID;
					}
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					
					updateSql = "UPDATE TeamMatchStats set Fouls = Fouls+1 "
							+"where MatchID="+matchID+" AND SquadID IN "
							+"(select SquadID from LineUp where MatchID="+matchID+" AND PlayerID="+lineupPlayerID+")";
				    //System.out.println (updateSql);
				    stmt.executeUpdate(updateSql);
				
					System.out.println ("FOUL UPDATED SUCCESSFULLY");
					System.out.println ("PLAYER MATCH STATS UPDATED SUCCESSFULLY");
					System.out.println ("TEAM MATCH STATS UPDATED SUCCESSFULLY");
					
					rs.close();
					stmt.close();
				}
				else
				{
					System.out.println("Match profile not created..");
					return;
				}
				
				
		}catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	}
	
	
	public static void selectUpdatePlayerMatchStats(Connection conn)
	{
		try{
			Statement stmt;
			ResultSet rs;
			String userInput;
			Hashtable<String, Integer> seasonList = new Hashtable<String, Integer>();
			stmt = conn.createStatement();
			
			//////////////////SELECT SEASON  ////////////////////////////////////////////

			rs = stmt.executeQuery("SELECT SeasonID FROM Seasons");
			System.out.println("Seasons: ");
			while (rs.next()) 
			{
				System.out.println("=> "+rs.getInt("SeasonId"));
				seasonList.put(Integer.toString(rs.getInt("SeasonId")),rs.getInt("SeasonId"));
			}
			
			System.out.println("Select Season ..");
			String season = UserInput.enterInput();
			Integer seasonID = seasonList.get(season);
			if (seasonID == null) {
			   System.out.println("Invalid Season Entered..");
			   return;
			}
			//////////////////////////////////////////////////////////////////////////////////
			
			//////////////////SELECT HOME & AWAY SQUAD FROM LIST OF CLUBS DISPLAYED  ////////////////
			    String selectQuery;
				Hashtable<String, Integer> squadList = new Hashtable<String, Integer>();
				selectQuery = "Select  Club.ClubName, Club.ClubID, Squad.SquadID from ("
						   +"(Select ClubName, ClubID from Club) as Club "
						   +"INNER JOIN "
						   +"(Select SquadID, ClubID from Squad where SeasonID ="+seasonID+") as Squad "
						   +"ON Club.ClubID = Squad.ClubID)";
				//System.out.println (selectQuery);
				rs = stmt.executeQuery(selectQuery);
				
				System.out.println("Squads: ");
				int count=1;
				while (rs.next()) 
				{
					System.out.println(count+". "+ rs.getString("ClubName"));
					squadList.put(Integer.toString(count), rs.getInt("SquadID"));
					count++;
				}
				
				System.out.println("Select Home Squad for the Match..");
				String homeSquad = UserInput.enterInput();
				Integer homeSquadID = squadList.get(homeSquad);
				if (homeSquadID == null) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				
				System.out.println("Select Away Squad for the Match..");
				String awaySquad = UserInput.enterInput();
				Integer awaySquadID = squadList.get(awaySquad);
				if ((awaySquadID == null) || (awaySquadID == homeSquadID)) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				//////////////////////////////////////////////////////////////////////////////////

				// Match ID corresponding to the home/away squads and season selected is looked up from PastMatches.
				rs = stmt.executeQuery("Select MatchID from Fixtures where HomeSquadID = "
									    + homeSquadID+ " AND AwaySquadID = "+ awaySquadID );
				Integer matchID=0;
				while (rs.next()) 
				{
					matchID = rs.getInt("MatchID");
				}
				
				rs = stmt.executeQuery("Select MatchID from PastMatches where MatchID ="
					    + matchID);
				if (rs.next() == true)
				{
					// Display All Players present in the lineup for the  match selected. Player whose match stats is to be entered is selected from this list
					rs = stmt.executeQuery("Select  Player.PlayerID, Player.FirstName, Player.LastName from " 
										    +"(Select PlayerID,  FirstName , LastName from LeaguePlayer) as Player "
										    +"INNER JOIN " 
										    +"(Select PlayerID from LineUp where MatchID = "+matchID+") as LineUp "
										    +"ON LineUp.PlayerID = Player.PlayerID");
					count =1;
					Hashtable<String, Integer> lineupList = new Hashtable<String, Integer>();
					while (rs.next()) 
					{
						System.out.println(count+". " + " " + rs.getString("FirstName") + " "
								+ rs.getString("LastName")+"("+ rs.getInt("PlayerID")+")");
						lineupList.put(Integer.toString(count), rs.getInt("PlayerID"));
						count++;
					}
					System.out.println ("Select Lineup Player whose match stats are to be updated..");
					
					String lineupPlayer = UserInput.enterInput();
					Integer lineupPlayerID = lineupList.get(lineupPlayer);
					if (lineupPlayerID == null) {
					   System.out.println("Invalid Player Entered..");
					   return;
					}
					////////   Enter attributes with respect to the player match statistics  //////////////
					System.out.println ("Enter Goals Saved");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer goalsSaved = Integer.parseInt(userInput);
					
					System.out.println ("Enter Total Shots");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer totalShots = Integer.parseInt(userInput);
					
					System.out.println ("Enter Shots On Target");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer shotsOnTarget = Integer.parseInt(userInput);
					
					System.out.println ("Enter Goals Assists");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer goalsAssists = Integer.parseInt(userInput);
					
					System.out.println ("Enter Offsides");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer offsides = Integer.parseInt(userInput);
					
					System.out.println ("Enter Fouls Suffered");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					Integer foulsSuffered = Integer.parseInt(userInput);
					// Update player match stats for the player,match selected
					String updateSql = "UPDATE PlayerMatchStats set GoalsSaved="+goalsSaved+", "
										+"TotalShots="+totalShots+", ShotsOnTarget="+shotsOnTarget+", GoalAssist="+goalsAssists+", Offside="+offsides+", " 
										+" FoulsSuffered="+foulsSuffered+" where playerID = "+lineupPlayerID+" AND matchID = "+matchID;
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					// update shotsONTarget in teamMatchStats for the squad to which player belongs
					updateSql = "UPDATE TeamMatchStats set ShotsOnTarget = ShotsOnTarget+ " + shotsOnTarget
							+" where MatchID="+matchID+" AND SquadID IN "
							+"(select SquadID from LineUp where MatchID="+matchID+" AND PlayerID="+lineupPlayerID+")";
				    //System.out.println (updateSql);
				    stmt.executeUpdate(updateSql);
					
					
					System.out.println("PLAYER MATCH STATS UPDATED SUCCESSFULLY");
					rs.close();
					stmt.close();
				}
				else
				{
					System.out.println("Match profile not created..");
					return;
				}
				
				
		}catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	}
	
	public static void selectUpdateTeamMatchStats(Connection conn)
	{
		try{
			Statement stmt;
			ResultSet rs;
			String userInput;
			Hashtable<String, Integer> seasonList = new Hashtable<String, Integer>();
			stmt = conn.createStatement();
			//////////////////SELECT SEASON  ////////////////////////////////////////////

			rs = stmt.executeQuery("SELECT SeasonID FROM Seasons");
			System.out.println("Seasons: ");
			while (rs.next()) 
			{
				System.out.println("=> "+rs.getInt("SeasonId"));
				seasonList.put(Integer.toString(rs.getInt("SeasonId")),rs.getInt("SeasonId"));
			}
			
			System.out.println("Select Season ..");
			String season = UserInput.enterInput();
			Integer seasonID = seasonList.get(season);
			if (seasonID == null) {
			   System.out.println("Invalid Season Entered..");
			   return;
			}
			//////////////////////////////////////////////////////////////////////////////////
			
			//////////////////SELECT HOME & AWAY SQUAD FROM LIST OF CLUBS DISPLAYED  /////////////////
			    String selectQuery;
				Hashtable<String, Integer> squadList = new Hashtable<String, Integer>();
				selectQuery = "Select  Club.ClubName, Club.ClubID, Squad.SquadID from ("
						   +"(Select ClubName, ClubID from Club) as Club "
						   +"INNER JOIN "
						   +"(Select SquadID, ClubID from Squad where SeasonID ="+seasonID+") as Squad "
						   +"ON Club.ClubID = Squad.ClubID)";
				//System.out.println (selectQuery);
				rs = stmt.executeQuery(selectQuery);
				
				System.out.println("Squads: ");
				int count=1;
				while (rs.next()) 
				{
					System.out.println(count+". "+ rs.getString("ClubName"));
					squadList.put(Integer.toString(count), rs.getInt("SquadID"));
					count++;
				}
				
				System.out.println("Select Home Squad for the Match..");
				String homeSquad = UserInput.enterInput();
				Integer homeSquadID = squadList.get(homeSquad);
				if (homeSquadID == null) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				
				System.out.println("Select Away Squad for the Match..");
				String awaySquad = UserInput.enterInput();
				Integer awaySquadID = squadList.get(awaySquad);
				if ((awaySquadID == null) || (awaySquadID == homeSquadID)) {
				   System.out.println("Invalid Squad Entered..");
				   return;
				}
				
				rs = stmt.executeQuery("Select MatchID from Fixtures where HomeSquadID = "
									    + homeSquadID+ " AND AwaySquadID = "+ awaySquadID );
				Integer matchID=0;
				while (rs.next()) 
				{
					matchID = rs.getInt("MatchID");
				}
				//////////////////////////////////////////////////////////////////////////////////

				// Match ID corresponding to the home/away squads and season selected is looked up from PastMatches
				rs = stmt.executeQuery("Select MatchID from PastMatches where MatchID ="
					    + matchID);
				if (rs.next() == true)
				{
					Integer tackles, homePossessionPercent; String homeMatchResult;
					
					System.out.println ("Enter Tackles for Home Squad");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					tackles = Integer.parseInt(userInput);
					
					System.out.println ("Enter Possession Percent for Home Squad");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					homePossessionPercent = Integer.parseInt(userInput);
					
					System.out.println ("Enter Match Result(WIN/LOSS/DRAW) for Home Squad");
					homeMatchResult = UserInput.enterInput().toUpperCase();
					if (!(homeMatchResult.equals("WIN") || homeMatchResult.equals("LOSS") || homeMatchResult.equals("DRAW"))) {
						   System.out.println("Invalid Opton Entered..");
						   return;
						}
					// Update TeamMatchStats attributes for both home and away squads
					String updateSql = "UPDATE TeamMatchStats set Tackles="+tackles+", "
										+"PossessionPercent="+homePossessionPercent+", MatchResult='"+homeMatchResult
										+"' where squadID = "+homeSquadID+" AND matchID = "+matchID;
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					
					Integer awayPossessionPercent; String awayMatchResult;
					
					System.out.println ("Enter Tackles for Away Squad");
					userInput = UserInput.enterInput();
					if (!(Validator.isNumber(userInput))) {
						   System.out.println("Invalid Option Entered..");
						   return;
						}
					tackles = Integer.parseInt(userInput);
				
					awayPossessionPercent = 100 - homePossessionPercent;
					
				    if (homeMatchResult.equals("DRAW") == true)
				    	awayMatchResult = "DRAW";
				    else if (homeMatchResult.equals("WIN") == true)
				    	awayMatchResult = "LOSS";
				    else
				    	awayMatchResult = "WIN";
				    
					updateSql = "UPDATE TeamMatchStats set Tackles="+tackles+", "
										+"PossessionPercent="+awayPossessionPercent+", MatchResult='"+awayMatchResult
										+"' where squadID = "+awaySquadID+" AND matchID = "+matchID;
					//System.out.println (updateSql);
					stmt.executeUpdate(updateSql);
					System.out.println("Team Match Stats UPDATED SUCCESSFULLY");
					rs.close();
					stmt.close();
				}
				else
				{
					System.out.println("Match profile not created..");
					return;
				}
				
				
		}catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
	}
	
	public static void selectUpdateSquadLineup(Integer squadID, Integer matchID, Connection conn)
	{
		// Lineup(11) players is selected from home/away squads of clubs already formed 
		
		System.out.println ("Select 11 players for Lineup from following list:");
		int count;
		try{
			Statement stmt;
			ResultSet rs;
			String selectQuery;
			stmt = conn.createStatement();
			Hashtable<String, Integer> lineupList = new Hashtable<String, Integer>();
			
			// display list of players available for selection for particular club, particular season
			selectQuery = "Select  Player.PlayerID, Player.FirstName, Player.LastName, Club.ClubID, Club.ClubName from " 
					+" (Select * from LeaguePlayer where PlayerID IN (Select PlayerID from SquadPlayers "
					+"where SquadID= "+squadID+" )) as Player "
				    +"INNER JOIN" 
				    +" (Select ClubID, ClubName from Club where ClubID IN" 
					+" (Select ClubID from Squad where SquadID= "+squadID+")) as Club"
					+" ON Club.ClubID = Player.CurrentClubID";
			//System.out.println (selectQuery);
			rs = stmt.executeQuery(selectQuery);
			count =1;
			while (rs.next()) 
			{
				System.out.println(count+". "+" " + rs.getString("FirstName") 
						+" "+ rs.getString("LastName") + "("+ rs.getInt("PlayerID")+")"+ " "
						+ rs.getString("ClubName")+"("+rs.getInt("ClubID")+")");
				lineupList.put(Integer.toString(count), rs.getInt("PlayerID"));
				count++;
			}
			count=1;
			while (count <= 11)
			{
				System.out.println ("Select Lineup Player: "+count+" for squad: "+ squadID);
				
				String lineupPlayer = UserInput.enterInput();
				Integer lineupPlayerID = lineupList.get(lineupPlayer);
				if (lineupPlayerID == null) {
				   System.out.println("Invalid Player Entered..");
				   continue;
				}
				
				String insertSql = "INSERT INTO LineUp(matchID,playerID,squadID) VALUES("+matchID+","+lineupPlayerID+","+squadID+")";
				//System.out.println (insertSql);
				stmt.executeUpdate(insertSql);
				// for each lineup player insert an entry in table PlayerMatchStats
				//insertSql = "INSERT INTO PlayerMatchStats(matchID,playerID) values ("+matchID+","+lineupPlayerID+")";
				insertSql = "INSERT INTO PlayerMatchStats(matchID,playerID,goalsScored,goalsSaved,totalShots,"
					    +"shotsOnTarget,goalAssist,offSide,foulsCommitted,foulsSuffered,YellowCards,RedCards) "
					    +"values("+matchID+","+lineupPlayerID+",0,0,0,0,0,0,0,0,0,0)" ;
				stmt.executeUpdate(insertSql);
				
				count++;
			}	
			
						
			rs.close();
			stmt.close();
		}catch(Exception e){
			 System.out.println("Error: "+e);
			 e.printStackTrace();
		 }
			
	}
	
	public static void createMatch(Connection conn)
	{
		try{
			Statement stmt;
			ResultSet rs;
			String selectQuery;
			Hashtable<String, Integer> seasonList = new Hashtable<String, Integer>();
			stmt = conn.createStatement();
		
			///////// Select Season //////////////////////////////////////////////////////////////////
			rs = stmt.executeQuery("SELECT SeasonID FROM Seasons");
			System.out.println("Seasons: ");
			while (rs.next()) 
			{
				System.out.println("=> "+rs.getInt("SeasonId"));
				seasonList.put(Integer.toString(rs.getInt("SeasonId")),rs.getInt("SeasonId"));
			}
		
			System.out.println("Select Season in which Match is to be created..");
			String season = UserInput.enterInput();
			Integer seasonID = seasonList.get(season);
			if (seasonID == null) {
				System.out.println("Invalid Season Entered..");
				return;
			}
			///////////////////////////////////////////////////////////////////////////////////////////
		
			//////////  Select Squads for the Match(Home, Away) ////////////////////////////////////
		
			Hashtable<String, Integer> squadList = new Hashtable<String, Integer>();
			selectQuery = "Select  Club.ClubName, Club.ClubID, Squad.SquadID from ("
					   +"(Select ClubName, ClubID from Club) as Club "
					   +"INNER JOIN "
					   +"(Select SquadID, ClubID from Squad where SeasonID ="+seasonID+") as Squad "
					   +"ON Club.ClubID = Squad.ClubID)";
			//System.out.println (selectQuery);
			rs = stmt.executeQuery(selectQuery);
			System.out.println("Squads: ");
			int count=1;
			while (rs.next()) 
			{
				System.out.println(count+". "+ rs.getString("ClubName"));
				squadList.put(Integer.toString(count), rs.getInt("SquadID"));
				count++;
			}
			
			System.out.println("Select Home Squad for the Match..");
			String homeSquad = UserInput.enterInput();
			Integer homeSquadID = squadList.get(homeSquad);
			if (homeSquadID == null) {
			   System.out.println("Invalid Squad Entered..");
			   return;
			}
			
			System.out.println("Select Away Squad for the Match..");
			String awaySquad = UserInput.enterInput();
			Integer awaySquadID = squadList.get(awaySquad);
			if ((awaySquadID == null) || (awaySquadID == homeSquadID)) {
			   System.out.println("Invalid Squad Entered..");
			   return;
			}
			
			// check whether match is present in Fixtures formed from squads.
			selectQuery = "Select MatchID from Fixtures where HomeSquadID = "
				    + homeSquadID+ " AND AwaySquadID ="+ awaySquadID;
			//System.out.println (selectQuery);
			rs = stmt.executeQuery(selectQuery);
			Integer matchID=0;
			if(rs.next()) 
			{
				matchID = rs.getInt("MatchID");
			}
			else
			{
				System.out.println("Error: Fixture Not Created..");
				return;
			}
			
			// check whether Match already exists
			selectQuery = "Select * from PastMatches where MatchID = "+ matchID;
			//System.out.println (selectQuery);
			rs = stmt.executeQuery(selectQuery);
			if (rs.next() == true)
			{
				System.out.println("Error: Match Profile Already Created..");
				return;
			}
		    ///////////////////////////////////////////////////////////////////////////////////////	
			
			// Match attributes are taken as input
			String userInput;
			
			System.out.println ("Enter Half1ExtraTime for Match");
			userInput = UserInput.enterInput();
			if (!(Validator.isNumber(userInput))) {
				   System.out.println("Invalid Option Entered..");
				   return;
				}
			
			Integer matchHalf1ExtraTime = Integer.parseInt(userInput);
			
			
			System.out.println ("Enter Half2ExtraTime for Match");
			userInput = UserInput.enterInput();
			if (!(Validator.isNumber(userInput))) {
				   System.out.println("Invalid Option Entered..");
				   return;
				}
			Integer matchHalf2ExtraTime = Integer.parseInt(userInput);
			
			System.out.println ("Enter Attendance for Match");
			userInput = UserInput.enterInput();
			if (!(Validator.isNumber(userInput))) {
				   System.out.println("Invalid Option Entered..");
				   return;
				}
			Integer matchAttendance = Integer.parseInt(userInput);
			
			///////////// Select Referee  from list of referees displayed ///////////////////////////////////////////////////
			System.out.println ("Referee List: ");
			Hashtable<String, Integer> refereeList = new Hashtable<String, Integer>();
			selectQuery = "Select RefereeID, FirstName, LastName from Referee";
			//System.out.println (selectQuery);
			rs = stmt.executeQuery(selectQuery);
			
			count=1;
			while (rs.next()) 
			{
				System.out.println(count+". "+ " "+ rs.getString("FirstName")+ " "+rs.getString("LastName")+ "("+rs.getInt("RefereeID")+")");
				refereeList.put(Integer.toString(count), rs.getInt("RefereeID"));
				
				count++;
			}
			
			System.out.println ("Select Referee for Match");
			String matchReferee = UserInput.enterInput();
			Integer matchRefereeID = refereeList.get(matchReferee);
			if (matchRefereeID == null){
			   System.out.println("Invalid Referee Entered..");
			   return;
			}
			////////////////////////////////////////////////////////////////////////////////////////
			
			/////////// CREATE MATCH ENTRY ///////////////////////////////////////////////////////////			
			String insertSql = "INSERT INTO PastMatches(MatchID,Half1ExtraTime,Half2ExtraTime,GoalsFor,GoalsAgainst,"
					+"Attendance,RefereeID) values"+"("+matchID+","+matchHalf1ExtraTime+","+matchHalf2ExtraTime
					+",0,0,"+matchAttendance+","+matchRefereeID+")";
			//System.out.println (insertSql);
			stmt.executeUpdate(insertSql);
			////////////////////////////////////////////////////////////////////////////////////////////
			
			// for home/away squad insert an entry in table TeamMatchStats
			insertSql = "INSERT INTO TeamMatchStats(MatchID,SquadID,Goals, ShotsOnTarget, Tackles, Fouls, PossessionPercent) values ("+matchID+","+homeSquadID+",0,0,0,0,0)";
			stmt.executeUpdate(insertSql);
			insertSql = "INSERT INTO TeamMatchStats(MatchID,SquadID,Goals, ShotsOnTarget, Tackles, Fouls, PossessionPercent) values ("+matchID+","+awaySquadID+",0,0,0,0,0)";
			stmt.executeUpdate(insertSql);
			
			/////////// SELECT SQUAD PLAYERS(Home/Away) ////////////////////////////////////////////////
			selectUpdateSquadLineup(homeSquadID, matchID, conn);
			selectUpdateSquadLineup(awaySquadID, matchID, conn);
			////////////////////////////////////////////////////////////////////////////////////////////
			
			System.out.println ("MATCH UPDATED SUCCESSFULLY");
			
			rs.close();
			stmt.close();
	}catch(Exception e){
		 System.out.println("Error: "+e);
		 e.printStackTrace();
	 }
	}
}
