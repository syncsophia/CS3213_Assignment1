import java.util.*;


public class main {

	public static Set<String> ignoreSet = new HashSet<String>();
	public static Set<String> inputSet = new HashSet<String>();
	public static Set<String> stringSet = new TreeSet<String>();

	public static void main(String[] args) {
		// initialization
		init();

		// Handle user input


		// process
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

			for(int k = 0; k < shifted.length; k++)
			{	
				System.out.print(StringArrayToString(shifted));
				if(NotInIgnoreSet(shifted[0]))
				{
					System.out.println(" - Stored~~");		// for debug purpose
					
					// convert string[] to string for storage
					stringSet.add( StringArrayToString(shifted) );
				}
				else
					System.out.println(" - Discard");		// for debug purpose

				// circular shifter
				shifted = CircularShifter(shifted);
			}
		}

		// Output stringSet in alphabetical order
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
		return result;
	}

	public static String StringArrayToString(String[] _input)
	{
		String result = "";
		boolean convertCase = false;
		for(int i = 0; i < _input.length; i++)
		{
			convertCase = false;
			if(NotInIgnoreSet( _input[i]) )
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
	public static boolean NotInIgnoreSet(String _input)
	{	
		if(ignoreSet.contains( _input ) || ignoreSet.contains( CaseInterchanger(_input) ))
			return false;

		return true;
	}

	
	// this function will toggle the case of the first char of input string
	public static String CaseInterchanger(String _input)
	{
		char target = _input.charAt(0);
		char result = 0;
		
		if(target > 'z' || target < 'A' || (target >'Z' && target < 'a') )
			return _input;
		
		
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
		inputSet.add("The Day after Tomorrow");
		inputSet.add("Fast and Furious");
		inputSet.add("Man of Steel");
		inputSet.add("1000 Night of Sadness");
		inputSet.add("   v for vendetta   ");
		inputSet.add("      ");
		inputSet.add("Fast and Furious");
		inputSet.add("Man of Steel");

		ignoreSet.add("is");
		ignoreSet.add("the");
		ignoreSet.add("of");
		ignoreSet.add("and");
		ignoreSet.add("as");
		ignoreSet.add("a");
		ignoreSet.add("after");
		ignoreSet.add("For");
	}


}
