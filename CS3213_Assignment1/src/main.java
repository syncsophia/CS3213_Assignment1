import java.util.*;


public class main {

	public static void main(String[] args) {
		Map<String, String> myMap = new HashMap<String, String>();
		
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		// do simple sorting
		String input = "this is a simple testing";
		ArrayList<String> temp = new ArrayList<String>();
		String[] splitList = new String[10];
		
		splitList = input.split(" ");
		
		for(int i = 0; i < splitList.length; i++)
		{
			System.out.println(splitList[i]);
			
			temp.add(splitList[i]);
		}
		
		temp.sort(null);
		
		System.out.println(temp.toString());

	}

}
