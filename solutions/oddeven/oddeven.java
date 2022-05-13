//
// 2021 UCF Local Programming Contest (Final Round) -- oddeven
// Ali Orooji
//

import java.util.*;
import java.lang.Math;

public class oddeven
{
	
   /* ********************************************************* */
	
   public static void main(String[] args)
   {
	  Scanner input = new Scanner(System.in);
	  
	  String str = input.next();
	  
	  // find the number of occurrences for each letter 
	  int[] count = new int[26];
	  Arrays.fill(count, 0); // Java does this but "explicit" is good!
	  for (int k = 0;  k < str.length();  ++k)
		  count[str.charAt(k) - 'a'] += 1;
	  
	  // check the number of occurrences for each letter 
	  boolean odd_exists  = false;
	  boolean even_exists = false;
	  for (int c = 0;  c < 26;  ++c)
	  {
		  if (count[c] >= 1)
		  {// this letter appears in the string
			  if ( (count[c] % 2) == 1 )
				  odd_exists = true;
			  else
				  even_exists = true;
		  }
		  
	  }// end for (c)
		  
	  if (odd_exists && even_exists)
		  System.out.println("0/1");
	  else if (odd_exists)
		  System.out.println("1");
	  else
		  System.out.println("0");
	   
   }// end of main()
  
}// end of class oddeven

   /* ********************************************************* */
