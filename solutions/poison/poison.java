// Arup Guha
// 9/6/2021
// Solution to 2021 UCF Locals Problem: Food Poisoning

import java.util.*;

public class poison {

	public static int n;
	public static int p;
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		n = stdin.nextInt();
		p = stdin.nextInt();
		System.out.println(solve());
	}
	
	public static int solve() {
	
		// Base cases, easy to answer.
		if (n == 1) return 0;
		if (p == 1) return n-1;
		
		// Store all answers for p=1
		ArrayList<Integer> cur = new ArrayList<Integer>();
		for (int i=0; i<n; i++) cur.add(i+1);
		
		// Never have to go to more than level 17.
		for (int z=2; z<=p && z<=17; z++) {
		
			// Can't do any better for these.
			ArrayList<Integer> next = new ArrayList<Integer>();
			for (int i=0; i<=z-1&&i<n; i++) 
				next.add(cur.get(i));
			
			// next[i] stores most # of restaurants we can accommodate to solve in at most i subsets. (z = # of poisonings)
			for (int i=z; i<n; i++) {
				
				// This is the DP breakdown. The most # of restaurants you can do with z poisonings and i-1 subsets, plus
				// most # of restaurants you can do with z-1 poisonings and i-1 subsets.
				next.add(next.get(i-1) + cur.get(i-1));;
				if (next.get(i) > n) break;
			}
			
			// Reassign array.
			cur = next;
		}
		
		// Find first value (# of subsets) that covers at least n.
		int i = 1;
		while (cur.get(i) < n) i++;
		return i;
	}
}
