package qS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import java.util.Arrays;

public class quickSearch 
{	
	public static List<Integer> matchIndex = new ArrayList<Integer>();
	public static int matchCount = 0;
	public static int comparision = 0;
	public static long totalTime;
	public static boolean hasFound = false;
	// Pre-Processing, bad character table values...
	
	public static void preQsBc(char[] pattern, int patternSize, int[] qsBc)
	{
		for(int i = 0; i < qsBc.length; i++)
			qsBc[i] = patternSize + 1;			// assign the same value to the characters that is not part of pattern
		
		for(int i = 0; i < patternSize; i++)
			qsBc[pattern[i]] = patternSize - i;			// assign bad character table to pattern char values
	}

	// Quick Search 
	
	public static void qs(char[] text, char[] pattern)
	{
		long startTime = System.currentTimeMillis();
		int i;
		matchCount=0; 
		comparision = 0;
		matchIndex.clear();
		hasFound = false;
		int textSize = text.length;
		int patternSize = pattern.length;
		char[] shiftedText = Arrays.copyOfRange(text, 0, patternSize);
		int[] qsBc = new int[65536]; // size of an integer is 4 bytes = 16 bits = 2^16 = 65536
		
		// Pre-Processing
		preQsBc(pattern, patternSize, qsBc);
		// Searching 
		i = 0;		
		while(i <= textSize - patternSize)
		{
			int c = 0;
			for (int j = 0; j < patternSize; j++) 
			{
				if( pattern[j] == shiftedText[j])
				{
					c++;
					comparision++;
				}
				else
				{
					comparision++;
					break;
				}
					
			}
			if(c >= patternSize)
			{
				System.out.println("Found at " + i);
				matchIndex.add(i);
				matchCount++;
				hasFound = true;
			}
				
			
			/*if (Arrays.equals(pattern, shiftedText)) 
			{
				System.out.println("Found at " + i);
				matchIndex.add(i);
				matchCount++;
				hasFound = true;
			}*/
				// Shifting
				shiftedText = new char[patternSize];

				if(i + patternSize > textSize)
				{
					i += qsBc[text[textSize -1]];
					//comparision++;	
				}
					
				else
				{
					if(i+patternSize >= textSize)
						i += qsBc[text[textSize-1]];
					else
						i += qsBc[text[i + patternSize]];
					//comparision++;
				}
				
				if(i+patternSize >= textSize)
					shiftedText = Arrays.copyOfRange(text, textSize - patternSize, textSize);
				else
					shiftedText = Arrays.copyOfRange(text, i, i+patternSize);
		}
		
		if(matchCount == 0)
		{
			System.out.println("Couldn't Found");
			hasFound = false;
			
		}
		
		System.out.println("Match Count: " + matchCount);
		System.out.println("Comparision Count: " + comparision);
		
		long endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) 
	{
		
		String text = "amksadjasdjamkasasjk amk dsadsadasrweeeedsadasdassdasdasdsadasdasdsadjlsdhsjdalsjdsaldhlsajdhjsakhdjksahdkjsahdkjsahdkjsahdjksahdjsahdjkashkdashkjdas amk ee";
		String pattern = "amk";
		qs(text.toCharArray(), pattern.toCharArray());

		System.out.println("Run Time: " + totalTime + " ms");
	}

}
