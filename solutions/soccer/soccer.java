//
// 2021 UCF Local Programming Contest (Final Round) -- soccer
// Ali Orooji
//

import java.util.*;
import java.lang.Math;

public class soccer
{
   public static final int COUNT = 5; // number of input values
   
   public static int[] values; // input values
   public static int[] perm;   // one permutation
   public static boolean[] used; // whether or not a value has been used
   
   /* ********************************************************* */
     
   public static void check_and_print()
   {
	   /* check to see if the permutation represents a valid answer
	      perm[1] = MP
		  perm[2] = W
		  perm[3] = D
		  perm[4] = L
		  perm[5] = Pts
	   */
	   
	   if ( perm[1] != (perm[2] + perm[3] + perm[4]) )
		   return;
	   
	   if ( perm[5] != ((3 * perm[2]) + perm[3]) )
		   return;
	   
	   // valid answer; print it and exit the program since we 
	   // found the answer
	   System.out.println(perm[1] + " " + perm[2] + " " + 
	                      perm[3] + " " + perm[4] + " " + perm[5]);
	   
	   System.exit(0);
   
   }// end of check_and_print()
 
   /* ********************************************************* */
     
   public static void gen_perm(int position)
   {
	   if (position > COUNT)
	   {
		   // we've generted a permutation
		   check_and_print();
		   return;
	   }
	   
	   /* Select an element to be used in "position" for the */
	   /* current permutation.                               */
	   /* Each element that has not been used in the current */
	   /* permutation will be used for "position".           */
	   for (int j = 1;  j <= COUNT;  ++j)
	   {
		   if (!used[j])
		   {
			   used[j] = true;
			   perm[position] = values[j];
			   gen_perm(position + 1);
			   used[j] = false;
		   }
		   
	   }// end for (j)
	   
   }// end of gen_perm()

   /* ********************************************************* */
   
   public static void main(String[] args)
   {
	  Scanner input = new Scanner(System.in);
	  
	  values = new int[COUNT + 1]; // [0] not used
	  perm   = new int[COUNT + 1]; // [0] not used
	  used   = new boolean[COUNT + 1]; // [0] not used
	  
	  // read the input values
	  for (int n = 1;  n <= COUNT;  ++n)
		  values[n] = input.nextInt();

	  // generate all the permutations and print the one that
	  // represents a valid answer
	  Arrays.fill(used, false); // Java does this but "explicit" is good!
	  gen_perm(1);

   }// end of main()
  
}// end of class soccer

   /* ********************************************************* */
