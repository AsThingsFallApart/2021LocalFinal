// Arup Guha
// 8/18/2021
// Solution to 2021 UCF Locals Problem: Under Construction Forever

import java.util.*;

public class ucf {

	public static long MOD = 1000000007L;
	public static long[] fact;
	public static long[] inv;
	
	public static int n;
	public static pt[] pts;
	
	public static void main(String[] args) {
	
		// Precomp so we can calculate combos quickly.
		fact = new long[1000010];
		inv = new long[1000010];
		fact[0] = inv[0] = 1;
		
		for (int i=1; i<fact.length; i++) {
			fact[i] = (fact[i-1]*i)%MOD;
			inv[i] = modExp(fact[i], MOD-2);
		}
		
		Scanner stdin = new Scanner(System.in);
		
		// Read construction pts.
		n = stdin.nextInt();
		pts = new pt[n];
		for (int i=0; i<n; i++) {
			int x = stdin.nextInt();
			int y = stdin.nextInt();
			pts[i] = new pt(x, y);
		}
		
		// Get # of queries.
		int numQ = stdin.nextInt();
		for (int i=0; i<numQ; i++) {
			
			// Get this query.
			int x1 = stdin.nextInt();
			int y1 = stdin.nextInt();
			int x2 = stdin.nextInt();
			int y2 = stdin.nextInt();
			pt start = new pt(x1, y1);
			pt end = new pt(x2, y2);
			
			// Swap so sorted by x then y.
			if (x2 < x1 || (x2 == x1 && y2 < y1) ) {
				pt tmp = start;
				start = end;
				end = tmp;
			}
			
			// Ta da!
			System.out.println(solve(start, end));
		}
	}
	
	// Solve the query from start to end.
	public static long solve(pt start, pt end) {
		
		// Tells us if we move up in y or down.
		boolean dir = start.y < end.y;
		
		// Just store the obstructions we could face.
		ArrayList<pt> bad = new ArrayList<pt>();
		for (int i=0; i<n; i++)
			if (in(start, end, pts[i]))
				bad.add(pts[i]);
			
		// Sorts by x (small to big) and the y is either small to big or big to small depending on orientation of start and end.
		if (dir) {
			Collections.sort(bad, new Comparator<pt>() {
				public int compare(pt a, pt b) {
					if (a.x != b.x) return a.x-b.x;
					return a.y - b.y;
			}});
		}
		else {
			Collections.sort(bad, new Comparator<pt>() {
				public int compare(pt a, pt b) {
					if (a.x != b.x) return a.x-b.x;
					return b.y - a.y;
			}});			
		}
		
		long res = 0;
		int sz = bad.size();
		
		// Time to do inclusion exclusion.
		for (int i=0; i<(1<<sz); i++) {
			
			// Evalulate the # of ways to hit each bad point.
			long tmp = go(start, end, dir, bad, i);
			
			// Here we add.
			if (Integer.bitCount(i)%2 == 0)
				res = (res + tmp)%MOD;
			
			// Here we subtract.
			else
				res = (res - tmp + MOD)%MOD;
		}
		
		// Ta da!
		return res;
	}
	
	// Returns the # of ways to go from start to end in the direction dir stopping at each point in bad indicated by mask.
	public static long go(pt start, pt end, boolean dir, ArrayList<pt> bad, int mask) {
		
		// Special case.
		if (mask == 0) return eval(start, end);

		// Where we start.
		pt cur = start;
		long res = 1;
		
		// Go through each bit.
		for (int i=0; (1<<i) <= mask; i++) {

			// This bit is off.
			if ((mask & (1<<i)) == 0) continue;
			
			// This is the point to go to from cur.
			pt next = bad.get(i);
			
			// Contradictory directions, so answer is 0, this will only happen i y, because of how I sort.
			if (dir && cur.y > next.y) return 0;
			if (!dir && cur.y < next.y) return 0;
			
			// Multiply these in.
			res = (res*eval(cur, next))%MOD;
			
			// Update our current point.
			cur = next;
		}

		// Because of how I do it, it's guaranteed that this leg of the journey is okay.
		res = (res*eval(cur, end))%MOD;
		return res;
	}
	
	// Returns # of paths from a to b.
	public static long eval(pt a, pt b) {
		int dx = Math.abs(b.x - a.x), dy = Math.abs(b.y-a.y);
		return combo(dx+dy, dx);
	}
	
	// Returns true iff mid is in the box defined by start and end.
	public static boolean in(pt start, pt end, pt mid) {
		boolean inX = Math.min(start.x, end.x) <= mid.x && mid.x <= Math.max(start.x, end.x);
		boolean inY = Math.min(start.y, end.y) <= mid.y && mid.y <= Math.max(start.y, end.y);
		return inX && inY;
	}
	
	// Returns b to the power exp mod MOD. I just use this for mod inverse...
	public static long modExp(long b, long exp) {
		if (exp == 0) return 1;
		if (exp%2 == 0) {
			long tmp = modExp(b, exp/2);
			return (tmp*tmp)%MOD;
		}
		return (b*modExp(b, exp-1))%MOD;
	}
	
	// Returns C(x, y).
	public static long combo(int x, int y) {
		long tmp = (fact[x]*inv[y])%MOD;
		return (tmp*inv[x-y])%MOD;
	}
}

// I store this as a class so I can later custom sort this in different ways.
class pt {
	
	public int x;
	public int y;
	
	public pt(int myx, int myy) {
		x = myx;
		y = myy;
	}
	
	public String toString() {
		return "("+x+", "+y+")";
	}
}
