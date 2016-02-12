package soccerLeague;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateFixtures {

	
	public static void process(Connection conn)
	{
		System.out.println("Following Season fixtures are not created :");
		//Display Season from Fixture Lock
		String sqlQuery="SELECT SeasonId from FixtureLock where LockFlag='N'";
		String sId="";
		try {
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			while (rs.next()) {
			         sId= rs.getString("SeasonId");
			        System.out.println(sId);}
			
		
		int countR=-1;
		boolean validSeasonId=false;
		System.out.println("Enter the Season ID whose fixture you want to create");
		//Going to previous menu
		//Validating the Season Id
		String query ="select count(*) AS COUNTR from Seasons where seasonId="+sId;
		rs = stmt.executeQuery(query);
		    while (rs.next()) 
		    {
		           countR = rs.getInt("COUNTR");
		        if (countR==1)
		        	validSeasonId=true;
		    }
		 
		if(validSeasonId)
		{
			CallableStatement callableStatement=null;
		    //Calling the Stored Procedure Generate_Fixtures  
			// Step-1: identify the stored procedure
		    String procedureCall = "{ call GENERATE_FIXTURES(?) }";
		    // Step-2: prepare the callable statement
		    callableStatement = conn.prepareCall(procedureCall);
		    // Step-3: Provide the input parameters
		    callableStatement.setInt(1, Integer.parseInt(sId));
		    // Step-4: execute the stored procedures: GENERATE_FIXTURES
		    callableStatement.execute();
		    System.out.println("Fixtures Generated");
		}
		else
		{
			System.out.println("Season ID was invalid");
		}
		  
	} catch (SQLException e) {
		System.out.println("Invalid Input");}
		
		
	} 
	
}
