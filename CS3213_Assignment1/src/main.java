import java.util.*;
import java.io.*;

public class main {

	public static String inputLine;
	public static Set<String> ignoreSet = new HashSet<String>();
	public static Set<String> inputSet = new HashSet<String>();
	public static Set<String> stringSet = new TreeSet<String>();
	
	public static Set<String> inputWordSet(Set<String> _inputSet) {		
		if (_inputSet.equals(inputSet)) {
			System.out.println("Titles:");
		}
		else if (_inputSet.equals(ignoreSet)) {
			System.out.println("Ignore:");
		}
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		try {
			while ( (inputLine = buffer.readLine()) != null) {
				if (inputLine.equals("continue~")) { 
					break; 
				}
				else {
					_inputSet.add(inputLine);
				}
			}
		} 
		catch (IOException ioe)
		{
			System.out.println("I/O exception thrown");
		}
		return _inputSet;
	}
	
	public static void main(String[] args) {
		// initialization
		init();

		// Handle user input
		System.out.println(
				"Welcome! Enter a list of titles first.\n"
				+ "Type \"continue~\" to continue.");
		
		inputSet = inputWordSet(inputSet);
		ignoreSet = inputWordSet(ignoreSet);
		

		// Main processing logic
		String currString = null;
		Iterator<String> inputItr = inputSet.iterator();

		while(inputItr.hasNext())
		{
			currString = inputItr.next();
			String[] original = currString.split(" ");

			/** 
			 this part of code will remove any empty string in the array after split
			 e.g. 'space' or null
			 **/
			ArrayList<String> magic = new ArrayList<String>();
			for(int i = 0; i < original.length; i++)
			{
				if(original[i].length() > 0)
					magic.add(original[i]);
			}
			String[] shifted = new String[magic.size()];
			magic.toArray(shifted);


			System.out.println("\n>>>>>" + currString + "<<<<<");		// for debug purpose

			/**
			 * This part of code will determine whether the first word is a keyword or not.
			 * if not a keyword - discard
			 * if is a keyword - store
			 */
			for(int k = 0; k < shifted.length; k++)
			{	
				System.out.print(StringArrayToString(shifted));
				if(isKeyword(shifted[0]))
				{
					System.out.println(" - Stored~~");		// for debug purpose

					// convert string[] to string for storage
					stringSet.add( StringArrayToString(shifted) );
				}
				else
					System.out.println(" - Discard");		// for debug purpose

				/**
				 * this part of code will circular shift the sequence of the input 
				 * according to number of words in the input 
				 */
				// circular shifter
				shifted = CircularShifter(shifted);
			}
		}

		/** 
		 * This part of code simply Output stringSet in alphabetical order
		 */
		Iterator<String> itr = stringSet.iterator();

		System.out.println("\nFinal output:");
		while(itr.hasNext())
			System.out.println(itr.next());
		
	}// end of main


	public static String[] CircularShifter(String[] _input)
	{
		String[] result = new String[_input.length];
		LinkedList<String> temp = new LinkedList<String>();
		for(int i = 0; i < _input.length; i++)
		{
			temp.add(_input[i]);
		}
		String firstWord = temp.pop();
		temp.addLast(firstWord);

		temp.toArray(result);
		temp = null;
		return result;
	}

	public static String StringArrayToString(String[] _input)
	{
		String result = "";
		boolean convertCase = false;
		for(int i = 0; i < _input.length; i++)
		{
			convertCase = false;
			
			if(isKeyword( _input[i]) )
			{
				if(_input[i].charAt(0) >= 'a' && _input[i].charAt(0) <= 'z')
					convertCase = true;
			}
			else
			{
				if (_input[i].charAt(0) >= 'A' && _input[i].charAt(0) <= 'Z')
					convertCase = true;
			}

			if(convertCase)
				result += CaseInterchanger(_input[i]) + " ";
			else
				result += _input[i] + " ";
		}
		return result;
	}

	// this function will ensure the checking is case insensitive
	public static boolean isKeyword(String _input)
	{	
		Iterator<String> itr = ignoreSet.iterator();
		String current;
		
		while(itr.hasNext())
		{
			current = itr.next();
			
			if(current.compareToIgnoreCase(_input) == 0 )
				return false;
		}
		return true;
	}

	// this function will toggle the case of the first char of input string
	// only interchange cases of alphabet
	public static String CaseInterchanger(String _input)
	{
		char target = _input.charAt(0);
		char result = 0;

		// checking for special characters other than alphabet
		// there is no changes if the input string is not alphabet
		if(target > 'z' || target < 'A' || (target >'Z' && target < 'a') )
			return _input;

		// this part will convert the first char of the input string 
		// from lower case to upper case and vice versa
		if(target >= 'a' && target <= 'z')
		{
			int value = target -'a';
			result = 'A';
			result += value;
		}
		else if(target >= 'A' && target <= 'Z')
		{
			int value = target -'A';
			result = 'a';
			result += value;
		}
		return new String(_input.replace(target, result));
	}

	public static void init()
	{
		// testing set of data
//		inputSet.add("The Day after Tomorrow");
//		inputSet.add("Fast and Furious");
//		inputSet.add("Man of Steel");
//		inputSet.add("1000 Night of Sadness");
//		inputSet.add("   v for vendetta   ");
//		inputSet.add("      ");
//		inputSet.add("Fast and Furious");
//		inputSet.add("Man of Steel");
//		inputSet.add("is a the after for");

//		ignoreSet.add("is");
//		ignoreSet.add("the");
//		ignoreSet.add("of");
//		ignoreSet.add("and");
//		ignoreSet.add("as");
//		ignoreSet.add("a");
//		ignoreSet.add("after");
//		ignoreSet.add("For");
	}


}
