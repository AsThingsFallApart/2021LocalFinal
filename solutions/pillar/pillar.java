//
// 2021 UCF Local Programming Contest (Final Round) -- pillar
// Ali Orooji
//

import java.util.*;
import java.lang.Math;

public class pillar
{
   public static final int MAX_NUM_OF_UNITS = 100; // number of units
   
   public static int[] unit_heights;
   
   /* ********************************************************* */
     
   public static void walk_and_print()
   {
	   int distance = MAX_NUM_OF_UNITS; // minimum travelled (if no walls)
	   
	   // add "Lulu" going up and down the walls
	   for (int k = 1;  k < MAX_NUM_OF_UNITS;  ++k)
	   {
		   distance += Math.abs(unit_heights[k] - unit_heights[k - 1]);
	   }
	   
	   System.out.println(distance);
   
   }// end of walk_and_print()
 
   /* ********************************************************* */
     
   public static void read_and_init(Scanner input)
   {
	   unit_heights = new int[MAX_NUM_OF_UNITS];
	   Arrays.fill(unit_heights, 0); // Java does this but "explicit" is good!
	   
	   // read each building and set the height for each unit
	   int num_of_bldg = input.nextInt();
	   for (int b = 1;  b <= num_of_bldg;  ++b)
	   {
		   int lower_left = input.nextInt();
		   int width  = input.nextInt();
		   int height = input.nextInt();
		   
		   // set the height for each unit
		   for (int k = lower_left;  k < (lower_left + width);  ++k)
			   unit_heights[k] = height;
		   
	   }// end for (b)
	   
   }// end of read_and_init()

   /* ********************************************************* */
   
   public static void main(String[] args)
   {
	  Scanner input = new Scanner(System.in);
	  
	  read_and_init(input);
	  walk_and_print();

   }// end of main()
  
}// end of class pillar_Ali

   /* ********************************************************* */
