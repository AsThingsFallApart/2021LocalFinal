import java.util.*;

public class tutorial {

	final public static long MOD = 1000000007L;
	
	public static void main(String[] args) {
	
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		int maxDiff = stdin.nextInt();
		int maxSize = stdin.nextInt();
		
		int[] vals = new int[n];
		for (int i=0; i<n; i++)	
			vals[i] = stdin.nextInt();
		Arrays.sort(vals);
		
		// One way to split up a class of size 1.
		long[] dp = new long[n];
		dp[0] = 1;
		
		// Run DP.
		for (int i=1; i<n; i++) {
		
			// sz represents the size of the last tutorial.
			for (int sz=1; sz<=maxSize && i-sz+1 >= 0; sz++) {
			
				// Can't add another into this tutorial, gap is too big.
				if (vals[i-sz+1] + maxDiff < vals[i]) break;
				
				// How many ways to do the tutorials up to index i-sz. If i-sz == -1, that means this is our only group.
				long add = i-sz >= 0 ? dp[i-sz] : 1;
				
				// We can group this tutorial with all the ways to do the rest.
				dp[i] = (dp[i] + add)%MOD;
			}
		}
		
		// Ta da!
		System.out.println(dp[n-1]);
	}
}
