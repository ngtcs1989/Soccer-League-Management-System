package soccerLeague;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 *  This class provides the functionality of viewing Team performance statistics for each season. It Prints the results in a well formatted manner
 */
public class ViewTeamStats {
	public static void mainMenu(Connection conn)
	{
		//Display all Seasons
		System.out.println("\n");
		MenuList.printAllSeasons(conn);
		//Prompting user to choose the Season
		 System.out.print("\nEnter the Season No to view results: ");
		 System.out.println("\n");
		 String seasonId= UserInput.enterInput();
		 MenuList.showSeasonResults(conn, seasonId);
		
		// Selection of Match Id from Results
		 try{
		System.out.print("\nSelect Match ID whose Match Summary is Required: ");
		String matchId =UserInput.enterInput();
		// Validate Match ID
		Statement stmt;
		String query ="SELECT MatchID, HomeSquadID, AwaySquadID from Fixtures WHERE MatchID ='"+matchId+"'";
		stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    int rowCount = rs.last() ? rs.getRow() : 0;
		rs.beforeFirst();
		String homeSquadId="",awaySquadId="";
		if(rs.next())
		{
			homeSquadId=rs.getString("HomeSquadID");	
			awaySquadId=rs.getString("AwaySquadID");	
		}
		
		
		if(rowCount>0 )
		{
			String aClub="",hClub="",aGoals="",hGoals="",hPossn="",aPossn="";
			
			
			// Retrieve data from Team Match Stats(2 rows) if Match ID Valid 
			//Retreive Goals and Possession percent for Home Squad
			query ="SELECT Goals,Fouls,PossessionPercent from TeamMatchStats WHERE MatchID ='"+matchId+"' and SquadID='"+homeSquadId+"'";
			stmt = conn.createStatement();
			 rs = stmt.executeQuery(query);
			 if(rs.next())
			 {
				 hGoals=rs.getString("Goals");
				 hPossn=rs.getString("PossessionPercent");	
			 }
			//Retreive Goals and Possession percent for Away Squad
			 query ="SELECT Goals,PossessionPercent from TeamMatchStats WHERE MatchID ='"+matchId+"' and SquadID='"+awaySquadId+"'";
				stmt = conn.createStatement();
				 rs = stmt.executeQuery(query);
				 if(rs.next())
				 {
					 aGoals=rs.getString("Goals");	
					 aPossn=rs.getString("PossessionPercent");	
				 }
				
			 
			String[][] allAGoals=null,allHGoals=null; 
			//Retrieve all Home Goals
				 query ="SELECT  l.LastName , g.Time_Stamp  from Goals g, LeaguePlayer l, SquadPlayers sp where g.PlayerId=l.PlayerId and l.PlayerId=sp.PlayerId  and sp.squadId='"+homeSquadId+"' and matchId='"+matchId+"'";
				 stmt = conn.createStatement();
				 rs = stmt.executeQuery(query);
				 
				 rowCount = rs.last() ? rs.getRow() : 0;
				 rs.beforeFirst();
				 allHGoals=new String[rowCount][2];	 
				 int i=0;
				 while(rs.next())
				 {
					 allHGoals[i][0]=rs.getString("LastName");	
					 allHGoals[i][1]=rs.getString("Time_Stamp");	
				 i++;
				 }
				 	
				//Retrieve all Away Goals
				query ="SELECT l.LastName AS PName, g.Time_Stamp from Goals g, LeaguePlayer l, SquadPlayers sp where g.PlayerId=l.PlayerId and l.PlayerId=sp.PlayerId  and sp.squadId='"+awaySquadId+"' and matchId='"+matchId+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
						 
				 rowCount = rs.last() ? rs.getRow() : 0;
				 rs.beforeFirst();
				 allAGoals=new String[rowCount][2];	 
				 i=0;
				while(rs.next())
				 {
						 allAGoals[i][0]=rs.getString("PName");	
						 allAGoals[i][1]=rs.getString("Time_Stamp");	
						 i++;
				 }
				String[][] allAFouls=null,allHFouls=null; 	
				//Retrieve all Home Fouls
				query ="SELECT l.LastName AS PName, g.Time_Stamp  from Fouls g, LeaguePlayer l, SquadPlayers sp where g.PlayerId=l.PlayerId and l.PlayerId=sp.PlayerId  and sp.squadId='"+homeSquadId+"' and matchId='"+matchId+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
						 
				rowCount = rs.last() ? rs.getRow() : 0;
				rs.beforeFirst();
				allHFouls=new String[rowCount][2];	 
				i=0;
				while(rs.next())
				{
					 allHFouls[i][0]=rs.getString("PName");	
					 allHFouls[i][1]=rs.getString("Time_Stamp");	
					 i++;
				}
					
				//Retrieve all Away Fouls
				query ="SELECT  l.LastName AS PName, g.Time_Stamp  from Fouls g, LeaguePlayer l, SquadPlayers sp where g.PlayerId=l.PlayerId and l.PlayerId=sp.PlayerId  and sp.squadId='"+awaySquadId+"' and matchId='"+matchId+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
								 
				rowCount = rs.last() ? rs.getRow() : 0;
				rs.beforeFirst();
				allAFouls=new String[rowCount][2];	 
			    i=0;
				while(rs.next())
				{
					allAFouls[i][0]=rs.getString("PName");	
					allAFouls[i][1]=rs.getString("Time_Stamp");	
					i++;
				}	
				
				//Retrieve Home Club Name 
				query ="SELECT ClubName from Club Natural Join Squad where SquadID='"+homeSquadId+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				rs.next();
				hClub = rs.getString("ClubName");
				
				//Retrieve Away Club Name 
				query ="SELECT ClubName from Club Natural Join Squad where SquadID='"+awaySquadId+"'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				rs.next();
				aClub = rs.getString("ClubName");
				
				//Printing the Match Summary in a well formatted manner 
				System.out.println("=================================================================================================");
				System.out.println("                                      Match Summary                                              ");
				System.out.println("=================================================================================================");				
				System.out.format("%20s%20s%8s%-20s","",hClub,"   VS   ",aClub);
				System.out.println("");
				System.out.format("%20s%20s%8s%-20s","",hGoals,"   --   ",aGoals);
				System.out.println("\n");
				System.out.format("%20s%20s%8s%-20s","Possession:",hPossn+"%","",aPossn+"%");
				System.out.println("");
				i =0;
				while(i < allHGoals.length && i < allAGoals.length )
				{
					if(i==0)
					{
						System.out.format("%20s%20s%8s%-20s","Goals:",allHGoals[i][0] +" "+allHGoals[i][1]+"'" ,"",allAGoals[i][0] +" "+allAGoals[i][1]+"'");
						System.out.println("");
					}
					else
					{
						System.out.format("%20s%20s%8s%-20s","",allHGoals[i][0] +" "+allHGoals[i][1]+"'","",allAGoals[i][0] +" "+allAGoals[i][1]+"'");
						System.out.println("");
					}
					i++;
				}
				while(i< allHGoals.length)
				{
					if(i==0)
					{
						System.out.format("%20s%20s%8s%-20s","Goals:",allHGoals[i][0] +" "+allHGoals[i][1]+"'" ,"","");
						System.out.println("");
					}
					else
					{
						System.out.format("%20s%20s%8s%-20s","",allHGoals[i][0] +" "+allHGoals[i][1]+"'","","");
						System.out.println("");
					}
					i++;
				}
				while(i< allAGoals.length)
				{
					if(i==0)
					{
						System.out.format("%20s%20s%8s%-20s","Goals:","" ,"",allAGoals[i][0] +" "+allAGoals[i][1]+"'");
						System.out.println("");
					}
					else
					{
						System.out.format("%20s%20s%8s%-20s","","","",allAGoals[i][0] +" "+allAGoals[i][1]+"'");
						System.out.println("");
					}
					i++;
					
				}
				// Retrieve all Fouls
				i=0;
				System.out.println("");
				while(i < allHFouls.length && i < allAFouls.length )
				{
					if(i==0)
					{
						System.out.format("%20s%20s%8s%-20s","Fouls:",allHFouls[i][0] +" "+allHFouls[i][1]+"'" ,"",allAFouls[i][0] +" "+allAFouls[i][1]+"'");
						System.out.println("");
					}
					else
					{
						System.out.format("%20s%20s%8s%-20s","",allHFouls[i][0] +" "+allHFouls[i][1]+"'","",allAFouls[i][0] +" "+allAFouls[i][1]+"'");
						System.out.println("");
					}
					i++;
				}
				while(i< allHFouls.length)
				{
					if(i==0)
					{
						System.out.format("%20s%20s%8s%-20s","Fouls:",allHFouls[i][0] +" "+allHFouls[i][1]+"'" ,"","");
						System.out.println("");
					}
					else
					{
						System.out.format("%20s%20s%8s%-20s","",allHFouls[i][0] +" "+allHFouls[i][1]+"'","","");
						System.out.println("");
					}
					i++;
				}
				while(i< allAFouls.length)
				{
					if(i==0)
					{
						System.out.format("%20s%20s%8s%-20s","Fouls:","" ,"",allAFouls[i][0] +" "+allAFouls[i][1]+"'");
						System.out.println("");
					}
					else
					{
						System.out.format("%20s%20s%8s%-20s","","","",allAFouls[i][0] +" "+allAFouls[i][1]+"'");
						System.out.println("");
					}
					i++;
				}
				System.out.println("=================================================================================================\n\n");
				
				
				
				
				
						 
		}
		else
		{
			System.out.println("Invalid Input");
		}
		 
	 }catch (SQLException se)
	 {
		se.printStackTrace();
		 System.out.println("Invlid Input. Try again");
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
	}
}
