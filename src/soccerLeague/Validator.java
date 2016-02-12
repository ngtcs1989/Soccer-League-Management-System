package soccerLeague;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * This is again a utility class which helps in identifying if the User Input is valid or not
 */
		
public class Validator {

	public static boolean isNumber(String str)
	{  //Checks if a given string is numeric and greater than 0
		for (char c : str.toCharArray())
		{ if (!Character.isDigit(c)) return false; }
		
		if(Integer.parseInt(str)>0)
			return true;
	
	return true;
	}


  public static boolean isValidDate(String inputDate)
  {
	  //Check if a given String format Date input is valid in terms of MySQL default format i.e. YYYY-MM-DD
      String re1="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))[-:\\/.](?:[0]?[1-9]|[1][012])[-:\\/.](?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	
      Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	  Matcher m = p.matcher(inputDate);
	  return m.find();
  }














}
