//
// 2021 UCF Local Programming Contest (Final Round) -- fib
// Ali Orooji
//

import java.util.*;
import java.lang.Math;

public class fib
{
	
   /* ********************************************************* */
	
   public static void main(String[] args)
   {
	  Scanner input = new Scanner(System.in);
	  
	  int[] seq = new int[100];
	  
	  seq[0] = input.nextInt();
	  seq[1] = input.nextInt();
	  
	  seq[2] = (seq[1] + seq[0]) % 10;
	  seq[3] = (seq[2] + seq[1]) % 10;
	  
	  int n = 4;
	  
	  /* keep finding the next number in the sequence until
	     the first two numbers are repeated */
	  while ( (seq[0] != seq[n - 2]) || (seq[1] != seq[n - 1]) )
	  {
		  seq[n] = (seq[n - 1] + seq[n - 2]) % 10;
		  ++n;
	  }// end while
	  
	  System.out.println(n);
	   
   }// end of main()
  
}// end of class fib

   /* ********************************************************* */
