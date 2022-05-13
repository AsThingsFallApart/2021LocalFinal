// Gabriel Pita
// 9/08/2021
// Solution to 2021 UCF Local Contest Problem: Caterpillar Walk

import java.util.Scanner;

public class pillar {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        // initialize height array for each x coordinate from 0 to 100 inclusive
        int[] heights = new int[101];
        for(int i =0; i < n; i++){
            int s = in.nextInt();
            int w = in.nextInt();
            int h = in.nextInt();
            // Save heights in an array for later use computing distance traveled vertically. It would be more efficient
            // to also save where each building ended, but it is unnecessary given the small input bounds.
            for(int x = s; x < s+w; x++){
                heights[x] = h;
            }

        }
        int totalDistanceTraveled = 0;
        // Add the distance travelled along the x-axis (horizontally) which is always constant
        totalDistanceTraveled += 100;
        for(int x = 0; x < 100; x++){
            // Add the change in height to distance traveled. Height array values for positions with no buildings is
            // 0 because int arrays are initialized to 0 in java.
            totalDistanceTraveled += Math.abs(heights[x]-heights[x+1]);
        }
        System.out.println(totalDistanceTraveled);
    }
}
