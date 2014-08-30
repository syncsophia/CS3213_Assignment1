import java.util.*;
import java.io.*;

public class main {

	public static enum EnumState{
		INIT, INPUT_TITLE_NEW, PROCESS, OUTPUT, IDLE, INSERT_TITLE, INSERT_IGNORE, DELETE_TITLE, DELETE_IGNORE
	};

	public static String inputLine;
	public static boolean isIgnoreSetResetted = true;
	public static Set<String> ignoreSet = new TreeSet<String>();
	public static Set<String> inputSet = new HashSet<String>();
	public static Set<String> stringSet = new TreeSet<String>();
	public static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

	public static boolean isQuit = false;
	public static boolean isBack = false;

	public static void main(String[] args) {
		EnumState currentState = EnumState.IDLE;
		EnumState nextState = EnumState.IDLE;

		do{
			if(isBack)
				currentState = EnumState.IDLE;
			else
				currentState = nextState;
			switch(currentState)
			{
			case INIT:
				initIgnoreList();
				nextState = EnumState.IDLE;
				break;
			case INPUT_TITLE_NEW:
				System.out.println("\n<<<<<< Entered 'NEW Title List' Screen >>>>>>");
				inputSet.clear();
				inputTitles();
				if(isIgnoreSetResetted){
					ignoreSet.clear();
					nextState = EnumState.INSERT_IGNORE;
				}
				else{
					if(ignoreSet.size() < 1)
					{
						System.out.println("The list of 'word to ignore' is empty. System will auto-initialized the list with PREDEFINED list now.");
						initIgnoreList();
					}
					System.out.println("Current list of 'Words to Ignore':");
					displayContent(ignoreSet);
					nextState = EnumState.PROCESS;
				}
				break;
			case PROCESS:
				processInput();
				if(stringSet.size() > 0)
					nextState = EnumState.OUTPUT;
				else
					nextState = EnumState.IDLE;
				break;
			case OUTPUT:
				System.out.println("\nCurrent list of 'Words to Ignore':");
				displayContent(ignoreSet);
				System.out.println("Final Output:");
				displayContent(stringSet);
				nextState = EnumState.IDLE;
				break;
			case INSERT_TITLE:
				if(inputSet.size() > 0)
				{
					System.out.println("\n<<<<<< Entered 'Insert new title into EXISTING Title List' Screen >>>>>>");
					System.out.println("\nCurrent Title List:");
					displayContent(inputSet);
					inputTitles();

					if(isIgnoreSetResetted){
						ignoreSet.clear();
						nextState = EnumState.INSERT_IGNORE;
						break;
					}
					else{
						if(ignoreSet.size() < 1)
						{
							System.out.println("The list of 'word to ignore' is empty. System will auto-initialized the list with PREDEFINED list now.");
							initIgnoreList();
						}
					}
				}
				nextState = EnumState.PROCESS;
				break;
			case INSERT_IGNORE:
				System.out.println("\n<<<<<< Entered insert NEW list of 'Word to Ignore' Screen >>>>>>");
				if(!isIgnoreSetResetted && ignoreSet.size() < 1)
				{
					System.out.println("The list of 'word to ignore' is empty. System will auto-initialized the list with PREDEFINED list now.");
					initIgnoreList();
					System.out.println("\nCurrent list of 'Words to Ignore':");
					displayContent(ignoreSet);
				}

				insertIgnoreKey();
				if(ignoreSet.size() > 0)
					isIgnoreSetResetted = false;
				nextState = EnumState.PROCESS;
				break;

			case DELETE_IGNORE:
				deleteIgnoreKey();
				nextState = EnumState.PROCESS;
				break;

			default:	
				isBack = false;
				//isIgnoreSetResetted = false;
				System.out.println("What do you want to do?");
				System.out.println("0. Initialize EXISTING List of 'word to ignore' with Predefined list.");
				System.out.println("1. Start with NEW Title List and Process with NEW List of 'words to ignore'.");
				System.out.println("2. Start with NEW Title List and Process with EXISTING List of 'words to ignore'");
				System.out.println("3. Insert New Title into EXISTING Title List and Process with NEW List of 'words to ignore'");
				System.out.println("4. Insert New Title into EXISTING Title List and Process with EXISTING List of 'words to ignore'");
				System.out.println("5. Insert New 'words to ignore' into EXISTING List of 'words to ignore'");
				System.out.println("6. Delete 1 entry from EXISTING List of 'words to ignore'");
				System.out.println("7. Process EXISTING Title List with EXISTING List of 'words to ignore'");
				System.out.println("8. Quit System");

				try {
					while ( (inputLine = buffer.readLine()) != null) {
						if (inputLine.equals("0")){
							initIgnoreList();
							System.out.println("\nCurrent list of 'Words to Ignore':");
							displayContent(ignoreSet);
							break; 
						}
						if (inputLine.equals("1")){
							isIgnoreSetResetted = true;
							nextState = EnumState.INPUT_TITLE_NEW;
							break; 
						}
						else if (inputLine.equals("2")) {
							nextState = EnumState.INPUT_TITLE_NEW;
							break; 
						}
						else if (inputLine.equals("3")) { 
							isIgnoreSetResetted = true;
							nextState = EnumState.INSERT_TITLE;
							break; 
						}
						else if (inputLine.equals("4")) {
							nextState = EnumState.INSERT_TITLE;
							break; 
						}
						else if (inputLine.equals("5")) {
							nextState = EnumState.INSERT_IGNORE;
							break; 
						}else if (inputLine.equals("6")) {
							nextState = EnumState.DELETE_IGNORE;
							break; 
						}else if (inputLine.equals("7")) {
							if(inputSet.size() > 0){
								System.out.println("\nCurrent list of 'Words to Ignore':");
								displayContent(ignoreSet);
							}
							nextState = EnumState.PROCESS;
							break; 
						}
						else if (inputLine.equals("8")) {
							isQuit = true;
							break; 
						}else{
							System.out.println("Invalid input. Please select appropriate selection(0-8)");
						}
					}
				} 
				catch (IOException ioe)
				{
					System.out.println("I/O exception thrown");
				}
				break;
			}
		}while(isQuit == false);

		System.out.println("Exiting this system...\nAll Process ended.");
	}// end of main loop


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
		return new String(result+_input.substring(1));
	}

	public static void initIgnoreList()
	{		
		ignoreSet.clear();

		ignoreSet.add("is");
		ignoreSet.add("the");
		ignoreSet.add("of");
		ignoreSet.add("and");
		ignoreSet.add("as");
		ignoreSet.add("a");
		ignoreSet.add("after");

		isIgnoreSetResetted = false;
	}

	public static void processInput()
	{
		stringSet.clear();

		if(inputSet.size() < 1){
			System.out.println("There is nothing in the Title List to process. \nPlease enter title by select '1' or '2' first.\n");
			return;
		}

		String currString = null;
		Iterator<String> inputItr = inputSet.iterator();

		while(inputItr.hasNext())
		{
			currString = inputItr.next();
			ArrayList<String>splited = new ArrayList<String>(spaceSplitter(currString));
			String[] shifted = new String[splited.size()];
			splited.toArray(shifted);


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

	}

	public static void displayContent(Set<String> _input)
	{
		Iterator<String> itr = _input.iterator();
		while(itr.hasNext())
			System.out.println(itr.next());
		System.out.println();
	}

	public static ArrayList<String> spaceSplitter(String _input)
	{
		String[] original = _input.split(" ");
		ArrayList<String> magic = new ArrayList<String>();
		for(int i = 0; i < original.length; i++)
		{
			if(original[i].length() > 0)
				magic.add(original[i]);
		}
		return magic;
	}

	public static void insertIgnoreKey() {

		System.out.println("Enter new 'Word to ignore' or type 'back' to return to main screen or type 'continue' to process");
		System.out.print("Enter new word to ignore:");
		try {
			while( (inputLine = buffer.readLine()) != null) 
			{
				if (inputLine.equalsIgnoreCase("back") ) {
					isBack = true;
					break;
				}
				else if (inputLine.equalsIgnoreCase("continue")){ 
					break; 
				}
				else
				{
					ArrayList<String> splited = new ArrayList<String>(spaceSplitter(inputLine));

					if(splited.isEmpty() || splited.size() > 1)
					{
						System.out.println("Invalid input detected. Please try again by enter single word only");
						return;
					}

					String temp = splited.get(0);
					System.out.println("Are you sure you want to insert '" + temp + "' into words to ignore?");
					System.out.println("(yes or no)");
					inputLine = buffer.readLine();

					if(inputLine != null && inputLine.equalsIgnoreCase("yes"))
					{
						ignoreSet.add(temp.toLowerCase());
						System.out.println("'" + temp + "' is added into list of words to ignore");
					}

					System.out.println("Current list of 'Words to Ignore':");
					displayContent(ignoreSet);

					System.out.println("Enter new 'Word to ignore' or type 'back' to return to main screen or type 'continue' to process");
					System.out.print("Enter new word to ignore:");
				}
			}
		} 
		catch (IOException ioe)
		{
			System.out.println("I/O exception thrown");
		}
	}


	public static void inputTitles() {
		System.out.println("Enter titles or type 'continue' to continue");
		System.out.print("Enter titles:");
		try {
			while ( (inputLine = buffer.readLine()) != null) {
				if (inputLine.equalsIgnoreCase("continue")) { 
					break; 
				}
				else if (inputLine.equalsIgnoreCase("quit")) { 
					isQuit = true;
					break; 
				}
				else {
					inputSet.add(inputLine.toLowerCase());
					System.out.println( "'" + inputLine + "' is added into the current list.[Current Size: " + inputSet.size()+ "]");
					System.out.println("\nEnter titles or type 'continue' to continue");
					System.out.print("Enter titles:");
				}
			}
		} 
		catch (IOException ioe)
		{
			System.out.println("I/O exception thrown");
		}
	}

	public static void deleteIgnoreKey() {
		String temp = "";
		if(ignoreSet.size() < 1)
		{
			System.out.println("The list of 'word to ignore' is empty. System will auto-initialized the list with PREDEFINED list now.");
			initIgnoreList();
		}
		System.out.println("\nCurrent list of 'Words to Ignore':");
		displayContent(ignoreSet);
		System.out.println("Enter Word to delete or type 'back' to return to main screen");
		System.out.print("Enter Word to delete:");
		try {
			while ( (inputLine = buffer.readLine()) != null) {
				temp = inputLine;
				if (inputLine.equalsIgnoreCase("back")) { 
					break; 
				}
				else {
					if(!isKeyword(inputLine))
					{
						System.out.println("Are you sure you want to delete '" + temp + "' from list of words to ignore?");
						System.out.println("(yes or no)");
						inputLine = buffer.readLine();

						if(inputLine != null && inputLine.equalsIgnoreCase("yes"))
						{
							ignoreSet.remove(temp.toLowerCase());
							System.out.println("'" + temp + "' is deleted from list of words to ignore");
							break;
						}
					}
					else
					{
						System.out.println( "'" + temp + "' is not found from list of words to ignore");
					}

					System.out.println("\nCurrent list of 'Words to Ignore':");
					displayContent(ignoreSet);
					System.out.println("Enter Word to delete or type 'back' to return to main screen");
					System.out.print("Enter Word to delete:");
				}
			}
		} 
		catch (IOException ioe)
		{
			System.out.println("I/O exception thrown");
		}
	}



}