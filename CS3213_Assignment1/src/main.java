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
			String[] shifted = choppedString;

			System.out.println(">>>>>" + currString + "<<<<<");
			
			for(int k = 0; k < choppedString.length; k++)
			{
				if(ignoreSet.contains( shifted[0])  )
				{
					// do nothing
				}
				else
				{
					String newKeyFoundString = "";
					for(int shiftedInd = 0; shiftedInd < shifted.length; shiftedInd++)
						newKeyFoundString+= shifted[shiftedInd] + " ";
					stringSet.add(newKeyFoundString);
				}
				
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
				temp.toArray(shifted);
			}
		}

		System.out.println("Final output: \n" + stringSet.toString());



	}

}
