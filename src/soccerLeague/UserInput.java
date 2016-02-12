package soccerLeague;
/*
 * It is a utility class whose functionality is to provide static methods for User Input 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {

	public static String menuOption() throws IOException
	{
		try {
			System.out.print("Enter option: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String userInput = br.readLine();
			return userInput;
		} catch (Exception e) {
			 System.out.println("Your Input is inappropriate");
		}
		
		return "";
	}
	
	
	public static String enterInput()
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String userInput = br.readLine();
			return userInput;
		} catch (Exception e) {
			 System.out.println("Your Input is inappropriate");
		}
		
		return "";
	}
}
