DROP DATABASE IF EXISTS soccerLeague2014;
create DATABASE soccerLeague2014;
use soccerLeague2014;

CREATE TABLE IF NOT EXISTS Club(
ClubId				INTEGER PRIMARY KEY,
ClubName			VARCHAR(50),
Founded 			DATE,
ClubOwner			VARCHAR(30),
Chairman			VARCHAR(30),
Nickname 			VARCHAR(20),
Mascot				VARCHAR(20),
CurrentAwayColor	VARCHAR(20),
CurrentHomeColor	VARCHAR(20),
Stadium				VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS LeaguePlayer(
PlayerID		INTEGER PRIMARY KEY,
FirstName 		VARCHAR(30),
LastName		VARCHAR(30),
Age				INTEGER,
Nationality 	VARCHAR(30),
DebutDate		DATE,
CurrentClubID	INTEGER REFERENCES Club(ClubId),
TotalMatches 	INTEGER,
isRetired		BOOL);


CREATE TABLE IF NOT EXISTS Manager(
ManagerID		INTEGER PRIMARY KEY,
FirstName		VARCHAR(30),
LastName		VARCHAR(30),
Age				INTEGER,
Nationality		VARCHAR(30),
DebutDate		DATE,
CurrentClubID	INTEGER	REFERENCES Club(ClubId),
TotalMatches	INTEGER,
isRetired		BOOLEAN
);

CREATE TABLE IF NOT EXISTS Referee(
RefereeID		INTEGER	PRIMARY KEY,
FirstName		VARCHAR(30),
LastName		VARCHAR(30),
Age				INTEGER,
Nationality		VARCHAR(30),
DebutDate		DATE,
TotalMatches	INTEGER,
isRetired		BOOLEAN
);

CREATE TABLE IF NOT EXISTS Seasons(
SeasonID		INTEGER	PRIMARY KEY,
StartDate		DATE,
EndDate			DATE,
TotalClubs		INTEGER,
TotalMatches	INTEGER,
WinnerClubID 	INTEGER	REFERENCES  Club(ClubId)
);

CREATE TABLE IF NOT EXISTS Squad(
SquadID 	INTEGER PRIMARY KEY,
SeasonID 	INTEGER REFERENCES Seasons(SeasonID),
ClubID 		INTEGER REFERENCES Club(ClubID)
);

CREATE TABLE IF NOT EXISTS SquadManager(
ManagerID 	INTEGER	REFERENCES Manager(ManagerID),
SquadID		INTEGER	REFERENCES Squad(SquadID),
Primary Key (ManagerID,SquadID)
);

CREATE TABLE IF NOT EXISTS SquadPlayers(
PlayerID		INTEGER	REFERENCES LeaguePlayer(PlayerID),
SquadID			INTEGER	REFERENCES Squad(SquadID),
JerseyNo		INTEGER,
Position		VARCHAR(20),
MatchesPlayed	INTEGER,
GoalsScored		INTEGER,
GoalsSaved		INTEGER,
TotalShots		INTEGER,
ShotonTarget	INTEGER,
GoalAssist		INTEGER,
FoulsCommitted	INTEGER,
FoulsSuffered	INTEGER,
YellowCards		INTEGER,
RedCards 		INTEGER,
PRIMARY KEY (PlayerID, SquadID)
);

CREATE TABLE IF NOT EXISTS Fixtures(
MatchID 		INTEGER PRIMARY KEY,
HomeSquadID		INTEGER REFERENCES Squad(SquadID),
AwaySquadID		INTEGER REFERENCES Squad(SquadID),
Date_Time  		DATETIME
);

CREATE TABLE IF NOT EXISTS PastMatches(
MatchID			INTEGER	REFERENCES Fixtures(MatchID) ,
Half1ExtraTime	INTEGER,
Half2ExtraTime	INTEGER,
GoalsFor		INTEGER,
GoalsAgainst	INTEGER,
Attendance		INTEGER,
RefereeID 		INTEGER REFERENCES Referee(RefereeID),
PRIMARY KEY (MatchID)
);

CREATE TABLE IF NOT EXISTS LineUp(
MatchID		INTEGER REFERENCES Fixtures(MatchID),
PlayerID	INTEGER REFERENCES LeaguePlayer(PlayerID),
SquadID		INTEGER REFERENCES Squad(SquadID),
PRIMARY KEY (MatchID,PlayerID)
);

CREATE TABLE IF NOT EXISTS Substitute(
MatchID		INTEGER REFERENCES Fixtures(MatchID),
PlayerID	INTEGER REFERENCES LeaguePlayer(PlayerID),
SquadID		INTEGER REFERENCES Squad(SquadID),
PRIMARY KEY (MatchID,PlayerID)
);

CREATE TABLE IF NOT EXISTS Goals(
GoalID		INTEGER	PRIMARY KEY,
MatchId		INTEGER	REFERENCES Fixtures(MatchId),
PlayerID	INTEGER	REFERENCES LeaguePlayer(PlayerID),
Time_Stamp	INTEGER,
GoalType	VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS Fouls(
FoulID		INTEGER PRIMARY KEY,
MatchID		INTEGER REFERENCES Fixtures(MatchID),
PlayerID	INTEGER	REFERENCES LeaguePlayer(PlayerID),
Time_Stamp	INTEGER,
Card		VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS PlayerMatchStats(
MatchID			INTEGER	REFERENCES Fixtures(MatchID),
PlayerID		INTEGER	REFERENCES LeaguePlayer(PlayerID),
GoalsScored		INTEGER,
GoalsSaved		INTEGER,
TotalShots		INTEGER,
ShotsOnTarget	INTEGER,
GoalAssist		INTEGER,
Offside			INTEGER,
FoulsCommitted	INTEGER,
FoulsSuffered	INTEGER,
YellowCards		INTEGER,
RedCards		INTEGER,
PRIMARY KEY (MatchID,PlayerID)
);

CREATE TABLE IF NOT EXISTS TeamMatchStats(
MatchID			INTEGER REFERENCES Fixtures(MatchID),
SquadID			INTEGER	REFERENCES SQUAD(SquadID),
Goals			INTEGER,
ShotsOnTarget	INTEGER,
Tackles			INTEGER,
Fouls			INTEGER,
PossessionPercent		INTEGER,
MatchResult		VARCHAR(10),
PRIMARY KEY (MatchID,SquadID)
);

CREATE TABLE IF NOT EXISTS Standings(
ClubRank		INTEGER,
SeasonID		INTEGER	REFERENCES Seasons(SeasonID),
ClubID			INTEGER	REFERENCES Club(ClubID),
AwayWins		INTEGER,
AwayLosses		INTEGER,
AwayDraws		INTEGER,
HomeWins		INTEGER,
HomeLosses		INTEGER,
HomeDraws		INTEGER,
TotalWin		INTEGER,
TotalLoss		INTEGER,
TotalDraws		INTEGER,
Points			INTEGER,
Primary Key (SeasonID, ClubId)
);
