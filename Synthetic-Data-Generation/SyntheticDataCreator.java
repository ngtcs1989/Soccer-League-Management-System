/*
package soccerleague.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;




public class SyntheticDataCreator{

    public static void main(String[] args) throws ParseException {
 
		
    	   	
    	int seasonID=1,matchID=1,managerID=1,refereeID=1,clubID=1,playerID=1,squadID=1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Random rn = new Random();
		//STEP 1 : Generate Clubs without Managers
		//Initializing few non fabricated data
		String[] clubNames ={"Atletico de Kolkata","Chennaiyin","Delhi Dynamos","Goa","Kerala Blasters","Mumbai City","NorthEast United","Pune City"};
		String[] dateInString = {"2014-08-08","2014-08-03","2014-07-31","2014-08-04","2014-08-15","2014-08-01","2014-05-07","2014-08-21"};
		String[] clubOwner ={"Sourav Ganguly","MS Dhoni","S.N. Sharma","Virat Kohli","Sachin Tendulkar","Ranbir Kapoor","John Abraham","Hrithik Roshan"};
		String[] chairman ={"Sanjiv Goenka","A Bachchan","S.N. Sharma","Venugopal Dhoot","Prasad V Potluri","Bimal Parekh","Shillong Lajong","Gaurav Modwel"};
		String[] nickname ={"Sanjiv Goenka","A Bachchan","S.N. Sharma","Venugopal Dhoot","Prasad V Potluri","Bimal Parekh","Shillong Lajong","Gaurav Modwel"};
		String[] mascot ={"Lion","Monster","Plane","Bull","Elephant","Train","Stars","Horse"};
		String[] currentHomeColor ={"Red","Yellow","Green","Brown","Black","White","Purpule","Blue"};
		String[] currentAwayColor ={"Black","White","Blue","Green","Red","Brown","Purpule","Yellow"};
		String[] Stadium ={"Salt Lake Stadium","Jawaharlal Nehru Stadium (Chennai)","Jawaharlal Nehru Stadium (Delhi)","Fatorda Stadium","Jawaharlal Nehru Stadium (Kochi)","DY Patil Stadium","Indira Gandhi Athletic Stadium","Shree Shiv Chhatrapati Sports Complex"};
		String[] position ={"Forward","Left-Midfield","Right-Midfield","Defender"};
		
		Club[] clubs = new Club[8];
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Clubs---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
		for(int i=0;i<clubs.length;i++)
		{
			clubs[i] =new Club();
			clubs[i].setClubID(clubID+i);
			clubs[i].setClubName(clubNames[i]);
			clubs[i].setFounded(sdf.parse(dateInString[i]));
			clubs[i].setClubOwner(clubOwner[i]);
			clubs[i].setChairman(chairman[i]);
			clubs[i].setNickName(nickname[i]);
			clubs[i].setMascot(mascot[i]);
			clubs[i].setCurrentHomeColor(currentHomeColor[i]);
			clubs[i].setCurrentAwayColor(currentAwayColor[i]);
			clubs[i].setStadium(Stadium[i]);
			//clubID++;

		// System.out.println(clubs[i].createSQL());
		printToFile(clubs[i].createSQL());
		}
		
		
		//Generate Players without Total Matches 
		String[] firstNameMasterList = {"JAMES","JOHN","ROBERT","MICHAEL","WILLIAM","DAVID","RICHARD","CHARLES","JOSEPH","THOMAS","CHRISTOPHER","DANIEL","PAUL","MARK","DONALD","GEORGE","KENNETH","STEVEN","EDWARD","BRIAN","RONALD","ANTHONY","KEVIN","JASON","MATTHEW","GARY","TIMOTHY","JOSE","LARRY","JEFFREY","FRANK","SCOTT","ERIC","STEPHEN","ANDREW","RAYMOND","GREGORY","JOSHUA","JERRY","DENNIS","WALTER","PATRICK","PETER","HAROLD","DOUGLAS","HENRY","CARL","ARTHUR","RYAN","ROGER","JOE","JUAN","JACK","ALBERT","JONATHAN","JUSTIN","TERRY","GERALD","KEITH","SAMUEL","WILLIE","RALPH","LAWRENCE","NICHOLAS","ROY","BENJAMIN","BRUCE","BRANDON","ADAM"};
		String[] lastNameMasterList	= {"SMITH","JOHNSON","WILLIAMS","BROWN","JONES","MILLER","DAVIS","GARCIA","RODRIGUEZ","WILSON","MARTINEZ","ANDERSON","TAYLOR","THOMAS","HERNANDEZ","MOORE","MARTIN","JACKSON","THOMPSON","WHITE","LOPEZ","LEE","GONZALEZ","HARRIS","CLARK","LEWIS","ROBINSON","WALKER","PEREZ","HALL","YOUNG","ALLEN","SANCHEZ","WRIGHT","KING","SCOTT","GREEN","BAKER","ADAMS","NELSON","HILL","RAMIREZ","CAMPBELL","MITCHELL","ROBERTS","CARTER","PHILLIPS","EVANS","TURNER","TORRES","PARKER","COLLINS","EDWARDS","STEWART","FLORES","MORRIS","NGUYEN","MURPHY","RIVERA","COOK","ROGERS","MORGAN","PETERSON","COOPER","REED","BAILEY","BELL","GOMEZ","KELLY"};
		String[] nationalityMasterList = {"Germany","Argnetina","Colombia","Netherlands","Belgium","Brazil","Uruguay","Spain","France","Switzerland","Portugal","Chile","Italy","Greece","CostaRica","Mexico","USA","England","Croatia","Algeria"};
		
		LeaguePlayer[] player= new LeaguePlayer[400];
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : League Player---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
		for(int i=0;i<player.length;i++)
		{
			player[i] =new LeaguePlayer();
			player[i].setPlayerID(playerID+i);
			//Random First Name
			player[i].setFirstName(firstNameMasterList[rn.nextInt(firstNameMasterList.length-1) + 0]);
			
			//Random Last Name
			player[i].setLastName(lastNameMasterList[rn.nextInt(lastNameMasterList.length-1) + 0]);
			
			//Random Age
			int maxAge=34,minAge=21;
			player[i].setAge(rn.nextInt(maxAge-minAge) + minAge);
		
			//Random Club Association
			int maxIndex=7,minIndex=0;
			player[i].setCurrentClubID(clubs[rn.nextInt(maxIndex-minIndex)+minIndex].getClubID());

			//Random Debut Date
			maxIndex=7;minIndex=0;
			player[i].setDebutDate(sdf.parse(dateInString[rn.nextInt(maxIndex-minIndex)+minIndex]));
			
			//Random Nationality
			maxIndex=nationalityMasterList.length-1; minIndex=0;
			player[i].setNationality(nationalityMasterList[rn.nextInt(maxIndex-minIndex)+minIndex]);
			
			
			player[i].setIsRetired(false);
			printToFile(player[i].createSQL());
			// System.out.println(player[i].createSQL());
			
			
		}

		//Generate Managers  without Club Assignment and Total Matches
		Manager[] manager= new Manager[8];
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Manager---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		for(int i=0;i<manager.length;i++)
		{
			manager[i] =new Manager();

			manager[i].setManagerID(managerID);
			//Random First Name
			manager[i].setFirstName(firstNameMasterList[rn.nextInt(firstNameMasterList.length-1) + 0]);
			
			//Random Last Name
			manager[i].setLastName(lastNameMasterList[rn.nextInt(lastNameMasterList.length-1) + 0]);
			
			//Random Age
			int maxAge=34,minAge=21;
			manager[i].setAge(rn.nextInt(maxAge-minAge) + minAge);
		
			// Club Association
			manager[i].setCurrentClubID(clubs[i].getClubID());
			
			//Random Nationality
			int maxIndex=nationalityMasterList.length-1; int minIndex=0;
			manager[i].setNationality(nationalityMasterList[rn.nextInt(maxIndex-minIndex)+minIndex]);

			//Random Debut Date
			int maxIndexYear=2014,minIndexYear=1999;
			int maxIndexMonth=12,minIndexMonth=1;
			int maxIndexDay=28,minIndexDay=1;
			String debut_date = String.valueOf(rn.nextInt(maxIndexYear-minIndexYear)+minIndexYear)+"-"+String.valueOf(rn.nextInt(maxIndexMonth-minIndexMonth)+minIndexMonth)+"-"+String.valueOf(rn.nextInt(maxIndexDay-minIndexDay)+minIndexDay);
			manager[i].setDebutDate(sdf.parse(debut_date));

			manager[i].setIsRetired(false);	
			managerID++;
			printToFile(manager[i].createSQL());
			// System.out.println(manager[i].createSQL());
		}	
		
		
		//Generate Referee without Total Matches
		Referee[] referee= new Referee[200];
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Referee---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
		for(int i=0;i<referee.length;i++)
		{
			referee[i] =new Referee();

			referee[i].setRefereeID(refereeID+i);
			//Random First Name
			referee[i].setFirstName(firstNameMasterList[rn.nextInt(firstNameMasterList.length-1) + 0]);
			
			//Random Last Name
			referee[i].setLastName(lastNameMasterList[rn.nextInt(lastNameMasterList.length-1) + 0]);
			
			//Random Age
			int maxAge=34,minAge=21;
			referee[i].setAge(rn.nextInt(maxAge-minAge) + minAge);
		
			//Random Nationality
			int maxIndex=nationalityMasterList.length-1; int minIndex=0;
			referee[i].setNationality(nationalityMasterList[rn.nextInt(maxIndex-minIndex)+minIndex]);

			//Random Debut Date
			int maxIndexYear=2014,minIndexYear=1999;
			int maxIndexMonth=12,minIndexMonth=1;
			int maxIndexDay=28,minIndexDay=1;
			String debut_date = String.valueOf(rn.nextInt(maxIndexYear-minIndexYear)+minIndexYear)+"-"+String.valueOf(rn.nextInt(maxIndexMonth-minIndexMonth)+minIndexMonth)+"-"+String.valueOf(rn.nextInt(maxIndexDay-minIndexDay)+minIndexDay);
			referee[i].setDebutDate(sdf.parse(debut_date));

			int maxMatches=34,minMatches=21;
			referee[i].setTotalMatches(rn.nextInt(maxMatches-minMatches) + minMatches);

			referee[i].setIsRetired(false);	
			//refereeID++;
			printToFile(referee[i].createSQL());
			// System.out.println(referee[i].createSQL());
		}	


		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Seasons---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		Season[] seasons= new Season[8];
		for(int i=0;i<seasons.length;i++)
		{
			seasons[i] =new Season();	
			seasons[i].setSeasonID(seasonID+i);
			String start_date = String.valueOf(2014+i)+"-"+String.valueOf(8)+"-"+String.valueOf(rn.nextInt(28)+1);
			//String start_date="gfvbf";
			String end_date = String.valueOf(2014+i)+"-"+String.valueOf(03)+"-"+String.valueOf(rn.nextInt(28)+1);
			seasons[i].setStartDate(sdf.parse(start_date));
			seasons[i].setEndDate(sdf.parse(start_date));
			seasons[i].setTotalClubs(0);
			//seasons[i].setTotalMatches(0);
			//seasonID++;
			printToFile(seasons[i].createSQL());
			// System.out.println(seasons[i].createSQL());
		}
		
		/*
		//Generate Squad
		int num_squads = clubs.length * seasons.length; 

		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Squads---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		Squad[] squad= new Squad[num_squads]; 
		for(int i=0;i< clubs.length;i++)
		{
			for(int j=0;j<seasons.length;j++)
			{
			squad[(i*seasons.length)+j] =new Squad();

			squad[(i*seasons.length)+j].setSquadID(squadID+(i*seasons.length)+j);	
			squad[(i*seasons.length)+j].setSeasonID(seasonID+j);
			squad[(i*seasons.length)+j].setClubID(clubID+i);	

			//squadID++;
			printToFile(squad[(i*seasons.length)+j].createSQL());
			// System.out.println(squad[(i*seasons.length)+j].createSQL());
			
			}
		}

		//Generate Squad Manager as well as assign Club to each Manager
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : SquadManager---------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		SquadManager[] squadManager = new SquadManager[squad.length]; 
		for(int i=0;i< squad.length;i++)
		{
			squadManager[i] =new SquadManager();
			squadManager[i].setSquadID(squadID+i);
			squadManager[i].setManagerID((managerID+i)%manager.length);

			//squadID++;
			
			printToFile(squadManager[i].createSQL());			
			// System.out.println(squadManager[i].createSQL());
		}	

		//Generate Squad Players as well as assign Club to each Manager
		SquadPlayers[] squadPlayers = new SquadPlayers[seasons.length*player.length]; 
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : SquadPlayers----------------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
        for(int i=0;i< player.length;i++)
		{
			//temp_clubID = temp_clubID/30; // assigning 1st 30 players to same clubId.  				                      
			for(int j=0;j< seasons.length;j++)
			{
				// Divide players among clubs
				//int maxClub=clubs.length-1, minClub=0;
				//int randomClubID = rn.nextInt(maxClub-minClub) + minClub;

				squadPlayers[(i*seasons.length)+j]  = new SquadPlayers(); 
				squadPlayers[(i*seasons.length)+j].setSquadID(squad[ ((i%clubs.length)*seasons.length) + j].getSquadID());

				squadPlayers[(i*seasons.length)+j].setPlayerID(player[i].getPlayerID());

				int maxJersey=100, minJersey=0;
				int randomJersey = rn.nextInt(maxJersey-minJersey) + minJersey;
				squadPlayers[(i*seasons.length)+j].setJerseyNo(randomJersey);

				int maxPosition=position.length-1, minPosition=0;
				int randomPosition = rn.nextInt(maxPosition-minPosition) + minPosition;
				squadPlayers[(i*seasons.length)+j].setPosition(position[randomPosition]);
			
				//  Derived attributes
				//squadPlayers[(i*seasons.length)+j].setMatchesPlayed();
				//squadPlayers[(i*seasons.length)+j].setGoalsScored();
				
				squadPlayers[(i*seasons.length)+j].setGoalsSaved(rn.nextInt(3));
                int totalshots = rn.nextInt(9);
				squadPlayers[(i*seasons.length)+j].setTotalShots(totalshots + 2);
				squadPlayers[(i*seasons.length)+j].setShotsOnTarget(rn.nextInt(totalshots+1));
				squadPlayers[(i*seasons.length)+j].setGoalAssist(rn.nextInt(3));
				//squadPlayers[(i*seasons.length)+j].setFoulsCommited();
				//squadPlayers[(i*seasons.length)+j].setFoulsSuffered();
				//squadPlayers[(i*seasons.length)+j].setYellowCards();
				//squadPlayers[(i*seasons.length)+j].setRedCards();
				// System.out.println(squadPlayers[(i*seasons.length)+j].createSQL());
				
				
				printToFile(squadPlayers[(i*seasons.length)+j].createSQL());			
			}
			//temp_clubID++;
			
		}	
		//Generate Fixtures
		//Generate Squad Players as well as assign Club to each Manager
		Fixtures[] fixtures = new Fixtures[squad.length*squad.length]; 
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Fixtures----------------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
		int num_fixtures =0;
		for(int i=0;i< squad.length;i++)
		{
			for(int j=0;j< squad.length;j++)
			{
				if ( (i==j) || (squad[i].getSeasonID() != squad[j].getSeasonID()) )
					continue;
				fixtures[num_fixtures] = new Fixtures();
				//fixtures[num_fixtures].setMatchID(matchID+(i*squad.length+j));
                                fixtures[num_fixtures].setMatchID(matchID+num_fixtures);
				fixtures[num_fixtures].setHomeSquadID(squad[i].getSquadID());
				fixtures[num_fixtures].setAwaySquadID(squad[j].getSquadID());
                		Date temp_date = seasons[squad[j].getSeasonID()-1].getStartDate();
    				temp_date.setTime(temp_date.getTime() + 5);
				fixtures[num_fixtures].setDate_time(temp_date);
				// System.out.println(fixtures[num_fixtures].createSQL());
				
				printToFile(fixtures[num_fixtures].createSQL());
				num_fixtures++;
			}
		}
		//Generate PastMatches
		PastMatches[] past_matches = new PastMatches[num_fixtures]; 
		for(int i=0;i< num_fixtures;i++)
		{
			past_matches[i] = new PastMatches();
			past_matches[i].setMatchID(fixtures[i].getMatchID());
			past_matches[i].setHalf1ExtraTime(rn.nextInt(5));
			past_matches[i].setHalf2ExtraTime(rn.nextInt(5));
			//past_matches[i].setGoalsFor();
			//past_matches[i].setGoalsAgainst();
			past_matches[i].setAttendance(rn.nextInt(5));
			past_matches[i].setRefereeID(referee[rn.nextInt(referee.length-1)].getRefereeID());
			//// System.out.println(past_matches[i].createSQL());
			//printToFile(past_matches[i].createSQL());
			
		}	
		//Generate LineUp
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Lineup----------------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
		LineUp[] lineup = new LineUp[num_fixtures*22];
		int k =0;
		for(int i=0;i< num_fixtures;i++)
		{
			int homeSquadID = fixtures[i].getHomeSquadID();
			int awaySquadID = fixtures[i].getAwaySquadID();
			int countHomeSquadID = 0;
			int countAwaySquadID = 0;
			for (int j=0; j < squadPlayers.length;j++)
			{
				if ((squadPlayers[j].getSquadID() == homeSquadID) && (countHomeSquadID < 11))
				{
					lineup[k] = new LineUp();
					lineup[k].setMatchID(fixtures[i].getMatchID());
					lineup[k].setPlayerID(squadPlayers[j].getPlayerID());
					lineup[k].setSquadID(squadPlayers[j].getSquadID());
					// System.out.println(lineup[k].createSQL());
					
					printToFile(lineup[k].createSQL());
					
					
					countHomeSquadID++;
					k++;
				}
				if ((squadPlayers[j].getSquadID() == awaySquadID) && (countAwaySquadID < 11))
				{
					lineup[k] = new LineUp();
					lineup[k].setMatchID(fixtures[i].getMatchID());
					lineup[k].setPlayerID(squadPlayers[j].getPlayerID());
					lineup[k].setSquadID(squadPlayers[j].getSquadID());
					// System.out.println(lineup[k].createSQL());
					
					printToFile(lineup[k].createSQL());
					countAwaySquadID++;
					k++;
				}
				if ((countAwaySquadID==11) && (countHomeSquadID==11))
					break;
			}
		}	
		
		
		//Generate Substitute
		printToFile("\n#--------------------------------------------------------------------------------------\n");
       // printToFile("#--------------Table : Substitute----------------------------------------------------------------");
       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		
		Substitute[] substitute = new Substitute[num_fixtures*8];
		k =0;
		for(int i=0;i< num_fixtures;i++)
		{
			int homeSquadID = fixtures[i].getHomeSquadID();
			int awaySquadID = fixtures[i].getAwaySquadID();
			int countHomeSquadID = 0;
			int countAwaySquadID = 0;
			for (int j=0; j < squadPlayers.length;j++)
			{
				if ((squadPlayers[j].getSquadID() == homeSquadID) && (countHomeSquadID < 4))
				{
					substitute[k] = new Substitute();
					substitute[k].setMatchID(fixtures[i].getMatchID());
					substitute[k].setPlayerID(squadPlayers[j].getPlayerID());
					substitute[k].setSquadID(squadPlayers[j].getSquadID());
					// System.out.println(substitute[k].createSQL());
					
					
					printToFile(substitute[k].createSQL());
					countHomeSquadID++;
					k++;
				}
				if ((squadPlayers[j].getSquadID() == awaySquadID) && (countAwaySquadID < 4))
				{
					substitute[k] = new Substitute();
					substitute[k].setMatchID(fixtures[i].getMatchID());
					substitute[k].setPlayerID(squadPlayers[j].getPlayerID());
					substitute[k].setSquadID(squadPlayers[j].getSquadID());
					// System.out.println(substitute[k].createSQL());
					
					printToFile(substitute[k].createSQL());
								
					countAwaySquadID++;
					k++;
				}
				if ((countAwaySquadID==2) && (countHomeSquadID==2))
					break;
			}
		}	
	
				//Generate Goals
				printToFile("\n#--------------------------------------------------------------------------------------\n");
				printToFile("#--------------Table : Goals----------------------------------------------------------------");
				printToFile("\n#--------------------------------------------------------------------------------------\n");
		
				Goals[] goals = new Goals[num_fixtures*4];
				int gid =0;
				String[] goaltypelist ={"Regular","Corner","Free-kick","penalty"};
				for(int i=0;i<num_fixtures;i++)
				{
					int homeGoals= rn.nextInt(4);
					int hs = fixtures[i].getHomeSquadID();
					int as = fixtures[i].getAwaySquadID();
					//int sid = squad[hs].getSeasonID();
		                        int mid = fixtures[i].getMatchID();
					past_matches[i].setGoalsFor(homeGoals);
					past_matches[i].setGoalsAgainst(4-homeGoals);
					k=0;
					for(int j=0;j<homeGoals;j++)
					{
						goals[gid] = new Goals();
						goals[gid].setGoalID(gid+1);
						goals[gid].setMatchID(mid);
						goals[gid].setTime_stamp(rn.nextInt(90));
						goals[gid].setGoalType(goaltypelist[rn.nextInt(goaltypelist.length-1)+0]);

						for (k=0; k < squadPlayers.length;k++)
						{
							if ( squadPlayers[k].getSquadID() == hs )
							{
								goals[gid].setPlayerID(squadPlayers[k].getPlayerID());
		                                                break;
							}
						}
						// System.out.println(goals[gid].createSQL());
						
												
						printToFile(goals[gid].createSQL());
						gid++;	
					}
					k=0;
					for(int j=0;j<4-homeGoals;j++)
					{
						goals[gid] = new Goals();
						goals[gid].setGoalID(gid+1);
						goals[gid].setMatchID(mid);
						goals[gid].setTime_stamp(rn.nextInt(90));
						goals[gid].setGoalType(goaltypelist[rn.nextInt(goaltypelist.length-1)+0]);
						//goals[gid].setPlayerID(); --> Player from awaysquadID
						for (k=0; k < squadPlayers.length;k++)
						{
							if ( squadPlayers[k].getSquadID() == as )
							{
								goals[gid].setPlayerID(squadPlayers[k].getPlayerID());
		                                                break;
							}
						}
						
						
						printToFile(goals[gid].createSQL());
						// System.out.println(goals[gid].createSQL());
						gid++;		
					}

				}	
		                for(int i=0;i< num_fixtures;i++)
				{
		                	// System.out.println(past_matches[i].createSQL());
		                }
				//Generate Fouls
		       // printToFile("\n#--------------------------------------------------------------------------------------\n");
		       // printToFile("#--------------Table : Fouls----------------------------------------------------------------");
		       // printToFile("\n#--------------------------------------------------------------------------------------\n");
						        
				Fouls[] fouls = new Fouls[num_fixtures*6];
				int fid =0;
				String[] cardtypelist ={"Red","Yellow","None"};
				for(int i=0;i<num_fixtures;i++)
				{
					int homeFouls= rn.nextInt(6);
					int hs = fixtures[i].getHomeSquadID();
					int as = fixtures[i].getAwaySquadID();
					int mid = fixtures[i].getMatchID();
					//int sid = squad[hs].getSeasonID();
					for(int j=0;j<homeFouls;j++)
					{
						fouls[fid] = new Fouls();
						fouls[fid].setFoulID(fid);
						fouls[fid].setMatchID(mid);
						//fouls.setPlayerID(); --> PlayerID from home squadID
						for (k=0; k < squadPlayers.length;k++)
						{
							if ( squadPlayers[k].getSquadID() == hs )
							{
								fouls[fid].setPlayerID(squadPlayers[k].getPlayerID());
		                                                break;
							}
						}
		                                fouls[fid].setTime_stamp(rn.nextInt(90));
						fouls[fid].setCard(cardtypelist[rn.nextInt(cardtypelist.length-1)+0]);
						
						
						printToFile(fouls[fid].createSQL());
						// System.out.println(fouls[fid].createSQL());
						fid++;	
					}
					for(int j=0;j<6-homeFouls;j++)
					{
						fouls[fid] = new Fouls();
						fouls[fid].setFoulID(fid);
						fouls[fid].setMatchID(mid);
						//fouls.setPlayerID(); -> PlayerID from AwaySquadID
						for (k=0; k < squadPlayers.length;k++)
						{
							if ( squadPlayers[k].getSquadID() == as )
							{
								fouls[fid].setPlayerID(squadPlayers[k].getPlayerID());
		                                                break;
							}
						}
		                                fouls[fid].setTime_stamp(rn.nextInt(90));
						fouls[fid].setCard(cardtypelist[rn.nextInt(cardtypelist.length-1)+0]);
						// System.out.println(fouls[fid].createSQL());
						
						
			            			
						printToFile(fouls[fid].createSQL());
						fid++;		
					}

				}	
		
				//Generate PlayerMatchStats
		        PlayerMatchStats[] player_match_stats = new PlayerMatchStats[lineup.length];
		       // printToFile("\n#--------------------------------------------------------------------------------------\n");
               // printToFile("#--------------Table : PlayerMatchStats------------------------------------------------------");
               // printToFile("\n#--------------------------------------------------------------------------------------\n");
	                
		        
		        for (int i=0; i< lineup.length;i++)
		        {
		            player_match_stats[i] = new PlayerMatchStats();    
		            player_match_stats[i].setMatchID(lineup[i].getMatchID());
		            player_match_stats[i].setPlayerID(lineup[i].getPlayerID());
		            player_match_stats[i].setOffSide(rn.nextInt(1));
		            player_match_stats[i].setGoalAssist(rn.nextInt(3));
		            
		            
		            
		           // printToFile(player_match_stats[i].createSQL());
		            // System.out.println(player_match_stats[i].createSQL());
		        }    

		        //Generate TeamMatchStats
		        TeamMatchStats[] team_match_stats = new TeamMatchStats[fixtures.length*2];
		       // printToFile("\n#--------------------------------------------------------------------------------------\n");
               // printToFile("#--------------Table : TeamMatchStats------------------------------------------------------");
               // printToFile("\n#--------------------------------------------------------------------------------------\n");
	    
		        
		        for (int i=0, kt=0; i< num_fixtures;i++ , kt=kt+2)
		        {
		            team_match_stats[kt] = new TeamMatchStats();    
		            team_match_stats[kt].setMatchID(fixtures[i].getMatchID());
		            team_match_stats[kt].setSquadID(fixtures[i].getHomeSquadID());
		            team_match_stats[kt].setTackles(rn.nextInt(10));
		            int possession_percent = rn.nextInt(100);
		            team_match_stats[kt].setPossessionPercent(possession_percent);

		            team_match_stats[kt+1] = new TeamMatchStats();    
		            team_match_stats[kt+1].setMatchID(fixtures[i].getMatchID());
		            team_match_stats[kt+1].setSquadID(fixtures[i].getAwaySquadID());
		            team_match_stats[kt+1].setTackles(rn.nextInt(10));
		            team_match_stats[kt+1].setPossessionPercent(100-possession_percent);

		            // System.out.println(team_match_stats[kt].createSQL());
		            // System.out.println(team_match_stats[kt+1].createSQL());
		            
		           // printToFile(team_match_stats[kt].createSQL());
		           // printToFile(team_match_stats[kt+1].createSQL());
		            
		        }    
		        
		     */   //Generate Standings
               // printToFile("\n#--------------------------------------------------------------------------------------\n");
               // printToFile("#--------------Table : Standings ----------------------------------------------------------");
               // printToFile("\n#--------------------------------------------------------------------------------------\n");

		        Standings[] standings = new Standings[clubs.length*seasons.length];
		        for (int i=0; i< seasons.length;i++)
		        {
		            for (int j=0; j< clubs.length;j++)
		            {
		                standings[(i*clubs.length)+j] = new Standings();    
		                standings[(i*clubs.length)+j].setSeasonID(seasons[i].getSeasonID());
		                standings[(i*clubs.length)+j].setClubID(clubs[j].getClubID());
		                System.out.println(standings[(i*clubs.length)+j].createSQL());
		                
		               
		               // printToFile(standings[(i*clubs.length)+j].createSQL());
		            }
		        }
				
			
	
	}
    
    
    
    public static void printToFile(String sql)
    {
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File("D:\\insertSyntheticData.sql"), true)); 
            writer.print("\n");
            writer.print(sql);
            writer.close();
        }
        catch (FileNotFoundException ex)  
        {
             System.out.println("ERROR");
        }
    }
    
    
    
    
    
    
}


class Club
{
	private int clubID;
	private String clubName;
	private Date founded;
	private String clubOwner;
	private String chairman;
	private String nickName;
	private String mascot;
	private String currentAwayColor;
	private String currentHomeColor;
	private String stadium;
	
	public Club(){}
	
	public int getClubID() {
		return clubID;
	}


	public void setClubID(int clubID) {
		this.clubID = clubID;
	}


	public String getClubName() {
		return clubName;
	}


	public void setClubName(String clubName) {
		this.clubName = clubName;
	}


	public Date getFounded() {
		return founded;
	}


	public void setFounded(Date founded) {
		this.founded = founded;
	}


	public String getClubOwner() {
		return clubOwner;
	}


	public void setClubOwner(String clubOwner) {
		this.clubOwner = clubOwner;
	}


	public String getChairman() {
		return chairman;
	}


	public void setChairman(String chairman) {
		this.chairman = chairman;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getMascot() {
		return mascot;
	}


	public void setMascot(String mascot) {
		this.mascot = mascot;
	}


	public String getCurrentAwayColor() {
		return currentAwayColor;
	}


	public void setCurrentAwayColor(String currentAwayColor) {
		this.currentAwayColor = currentAwayColor;
	}


	public String getCurrentHomeColor() {
		return currentHomeColor;
	}


	public void setCurrentHomeColor(String currentHomeColor) {
		this.currentHomeColor = currentHomeColor;
	}


	public String getStadium() {
		return stadium;
	}


	public void setStadium(String stadium) {
		this.stadium = stadium;
	}



		
	
	public String createSQL(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return "INSERT INTO club(clubID, clubName, founded, clubOwner, chairman, nickName, mascot, currentAwayColor, currentHomeColor,stadium) "
					+ "values("+clubID+",'"+clubName+"','"+sdf.format(founded)+"','"+clubOwner+"','"+chairman+"','"+nickName+"','"+mascot+"','"+currentAwayColor+"','"+currentHomeColor+"','"+stadium+"');"; }
}



class Season
{
	private int seasonID;
	private Date startDate;
	private Date endDate;
	private int TotalClubs;
	private int WinnerClubID;
        private int TotalMatches;
	public Season(){}
	
	public int getSeasonID() {
		return seasonID;
	}


	public void setSeasonID(int seasonID) {
		this.seasonID = seasonID;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public int getTotalClubs() {
		return TotalClubs;
	}


	public void setTotalClubs(int totalClubs) {
		TotalClubs = totalClubs;
	}


	public int getWinnerClubID() {
		return WinnerClubID;
	}


	public void setWinnerClubID(int winnerClubID) {
		WinnerClubID = winnerClubID;
	}
        public int getTotalMatches() {
		return TotalMatches;
	}


	public void setTotalMatches(int totalMatches) {
		this.TotalMatches = totalMatches;
	}

	public String createSQL(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return "INSERT INTO seasons(seasonID,startDate,endDate,TotalClubs,TotalMatches,WinnerClubID) values("+seasonID+",'"+sdf.format(startDate)+"','"+sdf.format(endDate)+"',"+TotalClubs+","+TotalMatches+","+WinnerClubID+");"; }
}

class LeaguePlayer
{
	private int playerID;
	private String firstName;
	private String lastName;
	private int age;
	private String nationality;
	private Date debutDate;
	private int currentClubID;
	private int totalMatches;
	private Boolean isRetired;

	public LeaguePlayer(){}
	
	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public Date getDebutDate() {
		return debutDate;
	}


	public void setDebutDate(Date debutDate) {
		this.debutDate = debutDate;
	}


	public int getCurrentClubID() {
		return currentClubID;
	}


	public void setCurrentClubID(int currentClubID) {
		this.currentClubID = currentClubID;
	}


	public int getTotalMatches() {
		return totalMatches;
	}


	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}


	public Boolean getIsRetired() {
		return isRetired;
	}


	public void setIsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
	}


	
	public String createSQL(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		return "INSERT INTO LeaguePlayer(PlayerID,FirstName,LastName,Age,Nationality,DebutDate,CurrentClubID,TotalMatches,isRetired) values"
				+ "("+playerID+",'"+firstName+"','"+lastName+"',"+age+",'"+nationality+"','"+sdf.format(debutDate)+"',"+currentClubID+","+totalMatches+","+isRetired.toString()+");"; }
}


class Manager
{

	private int ManagerID;
	private String FirstName;
	private String LastName;
	private int Age;
	private String Nationality;
	private Date DebutDate;
	private int CurrentClubID;
	private Boolean isRetired;

	public Manager(){}
	
	public int getManagerID() {
		return ManagerID;
	}


	public void setManagerID(int managerID) {
		ManagerID = managerID;
	}


	public String getFirstName() {
		return FirstName;
	}


	public void setFirstName(String firstName) {
		FirstName = firstName;
	}


	public String getLastName() {
		return LastName;
	}


	public void setLastName(String lastName) {
		LastName = lastName;
	}


	public int getAge() {
		return Age;
	}


	public void setAge(int age) {
		Age = age;
	}


	public String getNationality() {
		return Nationality;
	}


	public void setNationality(String nationality) {
		Nationality = nationality;
	}


	public Date getDebutDate() {
		return DebutDate;
	}


	public void setDebutDate(Date debutDate) {
		DebutDate = debutDate;
	}


	public int getCurrentClubID() {
		return CurrentClubID;
	}


	public void setCurrentClubID(int currentClubID) {
		CurrentClubID = currentClubID;
	}


	public Boolean getIsRetired() {
		return isRetired;
	}


	public void setIsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
	}



	public String createSQL(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		return "INSERT INTO Manager(ManagerID,FirstName,LastName,Age,Nationality,DebutDate,CurrentClubID,isRetired) values"
				+ "("+ManagerID+",'"+FirstName+"','"+LastName+"',"+Age+",'"+Nationality+"','"+sdf.format(DebutDate)+"',"+CurrentClubID+","+isRetired.toString()+");"; }
}

class Referee
{

	private int RefereeID;
	private String FirstName;
	private String LastName;
	private int Age;
	private String Nationality;
	private Date DebutDate;
	private int TotalMatches;
	private Boolean isRetired;

	public Referee(){}
	
	public int getRefereeID() {
		return RefereeID;
	}


	public void setRefereeID(int refereeID) {
		RefereeID = refereeID;
	}


	public String getFirstName() {
		return FirstName;
	}


	public void setFirstName(String firstName) {
		FirstName = firstName;
	}


	public String getLastName() {
		return LastName;
	}


	public void setLastName(String lastName) {
		LastName = lastName;
	}


	public int getAge() {
		return Age;
	}


	public void setAge(int age) {
		Age = age;
	}

	public Date getDebutDate() {
		return DebutDate;
	}


	public void setDebutDate(Date debutDate) {
		this.DebutDate = debutDate;
	}

	public String getNationality() {
		return Nationality;
	}


	public void setNationality(String nationality) {
		Nationality = nationality;
	}


	public int getTotalMatches() {
		return TotalMatches;
	}


	public void setTotalMatches(int totalMatches) {
		TotalMatches = totalMatches;
	}


	public Boolean getIsRetired() {
		return isRetired;
	}


	public void setIsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
	}


	public String createSQL(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		return "INSERT INTO Referee(RefereeID,FirstName,LastName,Age,Nationality,DebutDate,TotalMatches,isRetired) values"
				+ "("+RefereeID+",'"+FirstName+"','"+LastName+"',"+Age+",'"+Nationality+"','"+sdf.format(DebutDate)+"',"+TotalMatches+","+isRetired.toString()+");"; }
}


class Squad
{

	private int squadID;
	private int seasonID;
	private int clubID;
	
	public Squad(){}

	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}

	public int getSeasonID() {
		return seasonID;
	}

	public void setSeasonID(int seasonID) {
		this.seasonID = seasonID;
	}

	public int getClubID() {
		return clubID;
	}

	public void setClubID(int clubID) {
		this.clubID = clubID;
	}

	public String createSQL(){
		return "INSERT INTO squad(squadID,seasonID,clubID) VALUES("+squadID+","+seasonID+","+clubID+");";
	}
	
}


class SquadManager
{

	private int squadID;
	private int managerID;

	public SquadManager(){}
	
	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public String createSQL(){
		return "INSERT INTO squadManager(squadID,ManagerID) VALUES("+squadID+","+managerID+");";
	}
	
}


class SquadPlayers
{
	private int squadID;
	private int playerID;
	private int jerseyNo;
	private String position;
	private int matchesPlayed;
	private int goalsScored;
	private int goalsSaved;
	private int totalShots;
	private int shotsOnTarget;
	private int goalAssist;
	private int foulsCommited;
	private int foulsSuffered;
	private int YellowCards;
	private int RedCards;
	
	public SquadPlayers(){}
	
	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getJerseyNo() {
		return jerseyNo;
	}

	public void setJerseyNo(int jerseyNo) {
		this.jerseyNo = jerseyNo;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	public int getGoalsSaved() {
		return goalsSaved;
	}

	public void setGoalsSaved(int goalsSaved) {
		this.goalsSaved = goalsSaved;
	}

	public int getTotalShots() {
		return totalShots;
	}

	public void setTotalShots(int totalShots) {
		this.totalShots = totalShots;
	}

	public int getShotsOnTarget() {
		return shotsOnTarget;
	}

	public void setShotsOnTarget(int shotsOnTarget) {
		this.shotsOnTarget = shotsOnTarget;
	}

	public int getGoalAssist() {
		return goalAssist;
	}

	public void setGoalAssist(int goalAssist) {
		this.goalAssist = goalAssist;
	}

	public int getFoulsCommited() {
		return foulsCommited;
	}

	public void setFoulsCommited(int foulsCommited) {
		this.foulsCommited = foulsCommited;
	}

	public int getFoulsSuffered() {
		return foulsSuffered;
	}

	public void setFoulsSuffered(int foulsSuffered) {
		this.foulsSuffered = foulsSuffered;
	}

	public int getYellowCards() {
		return YellowCards;
	}

	public void setYellowCards(int yellowCards) {
		YellowCards = yellowCards;
	}

	public int getRedCards() {
		return RedCards;
	}

	public void setRedCards(int redCards) {
		RedCards = redCards;
	}

		
	public String createSQL(){
		return "INSERT INTO SquadPlayers(squadID,playerID,jerseyNo,position,matchesPlayed,goalsScored,goalsSaved,totalShots,ShotOnTarget,goalAssist,FoulsCommitted,FoulsSuffered,YellowCards,RedCards) "
				+ "VALUES("+squadID+","+playerID+","+jerseyNo+",'"+position+"',"+matchesPlayed+","+goalsScored+","+goalsSaved+","+totalShots+","+shotsOnTarget+","+goalAssist+","+foulsCommited+","+foulsSuffered+","+YellowCards+","+RedCards+");";
	}
	
}


class Fixtures
{
	private int matchID;
	private int homeSquadID;
	private int awaySquadID;
	private Date date_time;
	
	public Fixtures(){}
	
	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getHomeSquadID() {
		return homeSquadID;
	}

	public void setHomeSquadID(int homeSquadID) {
		this.homeSquadID = homeSquadID;
	}

	public int getAwaySquadID() {
		return awaySquadID;
	}

	public void setAwaySquadID(int awaySquadID) {
		this.awaySquadID = awaySquadID;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	
	public String createSQL(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		return "INSERT INTO Fixtures(matchID,homeSquadID,awaySquadID,date_time) VALUES("+matchID+","+homeSquadID+","+awaySquadID+",'"+sdf.format(date_time)+"');";
	}
	
}

class PastMatches
{

	private int matchID;
	private int half1ExtraTime;
	private int half2ExtraTime;
	private int goalsFor;
	private int goalsAgainst;
	private int attendance;
	private int refereeID;
	
	public PastMatches(){}
	
	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getHalf1ExtraTime() {
		return half1ExtraTime;
	}

	public void setHalf1ExtraTime(int half1ExtraTime) {
		this.half1ExtraTime = half1ExtraTime;
	}

	public int getHalf2ExtraTime() {
		return half2ExtraTime;
	}

	public void setHalf2ExtraTime(int half2ExtraTime) {
		this.half2ExtraTime = half2ExtraTime;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public int getRefereeID() {
		return refereeID;
	}

	public void setRefereeID(int refereeID) {
		this.refereeID = refereeID;
	}

	
	public String createSQL(){
		return "INSERT INTO PastMatches(matchID,half1ExtraTime,half2ExtraTime,goalsFor,goalsAgainst,attendance,refereeID) "
				+ "VALUES("+matchID+","+half1ExtraTime+","+half2ExtraTime+","+goalsFor+","+goalsAgainst+","+attendance+","+refereeID+");";
	}
	
}

class LineUp
{
	private int matchID;
	private int playerID;
	private int squadID;
	
	public LineUp(){}
	
	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}

	public String createSQL(){
		return "INSERT INTO LineUp(matchID,playerID,squadID) VALUES("+matchID+","+playerID+","+squadID+");";
	}
	
}

class Substitute
{
	private int matchID;
	private int playerID;
	private int squadID;
	
	public Substitute(){}
	
	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}

	public String createSQL(){
		return "INSERT INTO Substitute(matchID,playerID,squadID) VALUES("+matchID+","+playerID+","+squadID+");";
	}
	
}


class Goals
{
	private int goalID;
	private int matchID;
	private int playerID;
	private int time_stamp;
	private String goalType;
	
	public Goals(){}
	
	public int getGoalID() {
		return goalID;
	}

	public void setGoalID(int goalID) {
		this.goalID = goalID;
	}

	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(int time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getGoalType() {
		return goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	
	
	public String createSQL(){
		return "INSERT INTO Goals(goalID,matchID,playerID,time_stamp,goalType) VALUES("+goalID+","+matchID+","+playerID+","+time_stamp+",'"+goalType+"');"; }
}

class Fouls
{
	private int foulID;
	private int matchID;
	private int playerID;
	private int time_stamp;
	private String card;
	
	public Fouls(){}
	
	public int getFoulID() {
		return foulID;
	}

	public void setFoulID(int foulID) {
		this.foulID = foulID;
	}

	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(int time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	
	
	public String createSQL(){
		return "INSERT INTO Fouls(foulID,matchID,playerID,time_stamp,card) VALUES("+foulID+","+matchID+","+playerID+","+time_stamp+",'"+card+"');"; 	}
}


class PlayerMatchStats
{
	private int matchID;
	private int playerID;
	private int squadID;
	private int goalsScored;
	private int goalsSaved;
	private int totalShots;
	private int shotsOnTarget;
	private int goalAssist;
	private int offSide;
	private int foulsCommited;
	private int foulsSuffered;
	private int YellowCards;
	private int RedCards;
	
	public PlayerMatchStats(){}
	
	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}
	
	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	public int getGoalsSaved() {
		return goalsSaved;
	}

	public void setGoalsSaved(int goalsSaved) {
		this.goalsSaved = goalsSaved;
	}

	public int getTotalShots() {
		return totalShots;
	}

	public void setTotalShots(int totalShots) {
		this.totalShots = totalShots;
	}

	public int getShotsOnTarget() {
		return shotsOnTarget;
	}

	public void setShotsOnTarget(int shotsOnTarget) {
		this.shotsOnTarget = shotsOnTarget;
	}

	public int getGoalAssist() {
		return goalAssist;
	}

	public void setGoalAssist(int goalAssist) {
		this.goalAssist = goalAssist;
	}

	public int getOffSide() {
		return offSide;
	}

	public void setOffSide(int offSide) {
		this.offSide = offSide;
	}

	public int getFoulsCommited() {
		return foulsCommited;
	}

	public void setFoulsCommited(int foulsCommited) {
		this.foulsCommited = foulsCommited;
	}

	public int getFoulsSuffered() {
		return foulsSuffered;
	}

	public void setFoulsSuffered(int foulsSuffered) {
		this.foulsSuffered = foulsSuffered;
	}

	public int getYellowCards() {
		return YellowCards;
	}

	public void setYellowCards(int yellowCards) {
		YellowCards = yellowCards;
	}

	public int getRedCards() {
		return RedCards;
	}

	public void setRedCards(int redCards) {
		RedCards = redCards;
	}

	
	
	public String createSQL(){
		return "INSERT INTO PlayerMatchStats(matchID,playerID,goalsScored,goalsSaved,totalShots,shotsOnTarget,goalAssist,offSide,foulsCommitted,foulsSuffered,YellowCards,RedCards) "
				+ "VALUES("+matchID+","+playerID+",IFNULL("+goalsScored+",0),"+goalsSaved+","+totalShots+","+shotsOnTarget+","+goalAssist+","+offSide+",IFNULL("+foulsCommited+",0),"+foulsSuffered+",IFNULL("+YellowCards+",0),IFNULL("+RedCards+",0));";
	}
	
}


class TeamMatchStats
{
	private int matchID;
	private int squadID;
	private int goals;
	private int shotsOnTarget;
	private int tackles;
	private int fouls;
	private int possessionPercent;
	private String matchResult;
	
	public TeamMatchStats(){}
	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public int getSquadID() {
		return squadID;
	}

	public void setSquadID(int squadID) {
		this.squadID = squadID;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getShotsOnTarget() {
		return shotsOnTarget;
	}

	public void setShotsOnTarget(int shotsOnTarget) {
		this.shotsOnTarget = shotsOnTarget;
	}

	public int getTackles() {
		return tackles;
	}

	public void setTackles(int tackles) {
		this.tackles = tackles;
	}

	public int getFouls() {
		return fouls;
	}

	public void setFouls(int fouls) {
		this.fouls = fouls;
	}

	public int getPossessionPercent() {
		return possessionPercent;
	}

	public void setPossessionPercent(int possessionPercent) {
		this.possessionPercent = possessionPercent;
	}

	public String getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
	}

	
	
	public String createSQL(){
		return "INSERT INTO TeamMatchStats(matchID,squadID,goals,shotsOnTarget,tackles,fouls,possessionPercent,matchResult) "
				+ "VALUES("+matchID+","+squadID+",IFNULL("+goals+",0),IFNULL("+shotsOnTarget+",0),"+tackles+",IFNULL("+fouls+",0),"+possessionPercent+",'"+matchResult+"');";
	}
	
}


class Standings
{
	private int clubRank;
	private int seasonID;
	private int clubID;
	private int awayWins;
	private int awayLosses;
	private int awayDraws;
	private int homeWins;
	private int homeLosses;
	private int homeDraws;
	private int totalWin;
	private int totalLoss;
	private int totalDraws;
	private int points;
	
	public Standings(){}
	
	public int getClubRank() {
		return clubRank;
	}

	public void setClubRank(int clubRank) {
		this.clubRank = clubRank;
	}

	public int getSeasonID() {
		return seasonID;
	}

	public void setSeasonID(int seasonID) {
		this.seasonID = seasonID;
	}

	public int getClubID() {
		return clubID;
	}

	public void setClubID(int clubID) {
		this.clubID = clubID;
	}

	public int getAwayWins() {
		return awayWins;
	}

	public void setAwayWins(int awayWins) {
		this.awayWins = awayWins;
	}

	public int getAwayLosses() {
		return awayLosses;
	}

	public void setAwayLosses(int awayLosses) {
		this.awayLosses = awayLosses;
	}

	public int getAwayDraws() {
		return awayDraws;
	}

	public void setAwayDraws(int awayDraws) {
		this.awayDraws = awayDraws;
	}

	public int getHomeWins() {
		return homeWins;
	}

	public void setHomeWins(int homeWins) {
		this.homeWins = homeWins;
	}

	public int getHomeLosses() {
		return homeLosses;
	}

	public void setHomeLosses(int homeLosses) {
		this.homeLosses = homeLosses;
	}

	public int getHomeDraws() {
		return homeDraws;
	}

	public void setHomeDraws(int homeDraws) {
		this.homeDraws = homeDraws;
	}

	public int getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(int totalWin) {
		this.totalWin = totalWin;
	}

	public int getTotalLoss() {
		return totalLoss;
	}

	public void setTotalLoss(int totalLoss) {
		this.totalLoss = totalLoss;
	}

	public int getTotalDraws() {
		return totalDraws;
	}

	public void setTotalDraws(int totalDraws) {
		this.totalDraws = totalDraws;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

		
	
	public String createSQL(){
		return "INSERT INTO Standings(clubRank,seasonID,clubID,awayWins,awayLosses,awayDraws,homeWins,homeLosses,homeDraws,totalWin,totalLoss,totalDraws,points) "
				+ "VALUES("+clubRank+","+seasonID+","+clubID+","+awayWins+","+awayLosses+","+awayDraws+","+homeWins+","+homeLosses+","+homeDraws+","+totalWin+","+totalLoss+","+totalDraws+","+points+");";
	}
	
}

*/