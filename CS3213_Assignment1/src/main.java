import java.util.*;


public class main {

	public static void main(String[] args) {

		// initialization
		Set<String> ignoreSet = new HashSet<String>();
		Set<String> inputSet = new HashSet<String>();
		Set<String> stringSet = new HashSet<String>();

		// testing set of data
		inputSet.add("the star is blind");
		inputSet.add("love of blind");
		inputSet.add("the love of star");
		inputSet.add("blind birds in love");

		ignoreSet.add("a");
		ignoreSet.add("of");
		ignoreSet.add("the");
		ignoreSet.add("is");

		// process
		String[] currentList = new String[inputSet.size()];		
		inputSet.toArray(currentList);
		int inputSize = inputSet.size();

		for(int i = 0; i < inputSize; i++)
		{
			String currString = currentList[i];
			String[] choppedString = new String[currString.length()];
			choppedString = currString.split(" ");

			//for(int j = 0; j < choppedString.length; j++)
			//{
				if(ignoreSet.contains( choppedString[0]) )
				{
					// do nothing
				}
				else
				{
					stringSet.add(currString);
				}
			//}
				
				// shifter
				int j = 0;
				LinkedList<String> temp = new LinkedList<String>();
				while(j < choppedString.length)
				{
					temp.add(choppedString[j++]);
				}
				String firstWord = temp.pop();
				temp.addLast(firstWord);
				// end of shifter
				
				System.out.println(temp);
				
		}

		//System.out.println(stringSet.toString());



	}

}
