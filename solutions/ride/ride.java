// Arup Guha
// 8/18/2021
// Solution to 2021 UCF Locals Problem: Toboggan Ride

import java.util.*;

public class ride {

	public static int len;
	public static int n;
	public static double t;
	public static int[] locs;

	public static void main(String[] args) {
	
		// Read in input.
		Scanner stdin = new Scanner(System.in);
		len = stdin.nextInt();
		n = stdin.nextInt();
		t = stdin.nextDouble();
		locs = new int[n+1];
		
		// Store end of the race as the last boost...
		locs[n] = len;
		
		// Read in the rest.
		for (int i=0; i<n; i++)
			locs[i] = stdin.nextInt();

		// Run a binary search. These bounds are safe.
		double low = 0, high = 2000000000;
		for (int i=0; i<100; i++) {
		
			// Halfway point.
			double mid = (low+high)/2;
			
			// How long it took.
			double thisT = sim(mid);
			
			// Update either low or high based on if this was fast enough.
			if (thisT < t)
				high = mid;
			else
				low = mid;
		}
		
		// Ta da!
		System.out.println(low);
	}
	
	// Simulate the ride with this boost.
	public static double sim(double boost) {
	
		double curV = 0, curT = 0;
		
		// Go through each segment.
		for (int i=0; i<n; i++) {
		
			// Update velocity.
			curV += boost;
			int travel = locs[i+1] - locs[i];
			
			// Discriminant for quadratic - screen impossible case.
			double disc = curV*curV - 2*travel;
			if (disc < 0) return t+1;
			
			// Time for this leg.
			double thisT = curV - Math.sqrt(disc);
		
			// Update our time and velocity.
			curT += thisT;
			curV -= thisT;
		}
	
		// How long this took.
		return curT;
	}
}
