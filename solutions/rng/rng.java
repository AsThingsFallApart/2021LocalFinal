// Arup Guha
// 9/10/2021
// Alternate Solution to 2021 UCF Locals Problem: Really Nerdy Game
// Note: This took a while, I probably started it 5 days ago. Travis and I worked separately but ultimately
//       we had to work together to discover various things about this problem. His initial strategy didn't
//       work on typical cases and I used a book chapter on Markov Chains to solve typical cases. Travis then
//       discovered that my solution didn't work on any case where the game definitively ends. With his help,
//       I fixed my solution to detect those cases and solve them separately (via regular matrix multiplication)
//       and he solved those cases with a DP.

import java.util.*;

public class rng {

	final public static int MOD = 10007;
	
	public static int[] inv;
	public static int startRow;
	public static int origN;
	public static int d;
	public static int numW;
	public static int numL;
	public static int[] winS;
	public static int[] lossS;
	public static boolean[] transition;
	
	public static void main(String[] args) {
	
		// mod inverse table.
		inv = new int[MOD];
		for (int i=1; i<MOD; i++) inv[i] = modExp(i, MOD-2);
		
		// Get basic input.
		Scanner stdin = new Scanner(System.in);
		origN = stdin.nextInt();
		d = stdin.nextInt();
		numW = stdin.nextInt();
		numL = stdin.nextInt();
		transition = new boolean[origN];
		Arrays.fill(transition, true);
		
		// Read in winning states.
		winS = new int[numW];
		for (int i=0; i<numW; i++) {
			winS[i] = stdin.nextInt()-1;
			transition[winS[i]] = false;
		}
		
		// And losing ones.
		lossS = new int[numL];
		for (int i=0; i<numL; i++) {
			lossS[i] = stdin.nextInt()-1;
			transition[lossS[i]] = false;
		}
			
		// Screen out no win.
		if (numW == 0) 
			System.out.println("0");
			
		// Screen out no loss.
		else if (numL == 0)
			System.out.println("1");
			
		// Can either win or lose here.
		else 
			System.out.println(solve());
	}
	
	public static int solve() {
		
		// Our state matrix will be this size.
		int n = origN - numW - numL + 2;
			
		// Map from the original state to the new state for the transition matrix.
		int[] map = new int[origN];
			
		// first store all transitory states.
		int k = 0;
		for (int i=0; i<origN; i++)
			if (transition[i])
				map[i] = k++;
					
		// All of the win states are next bundled into one.
		for (int i=0; i<numW; i++)
			map[winS[i]] = k;
				
		// All of the loss states are after the win state, bundled into one.
		for (int i=0; i<numL; i++)
			map[lossS[i]] = k+1;
				
		// This is the top left "quadrant" of the transition matrix including only transitory states.
		int[][] qMatrix = new int[n-2][n-2];
			
		// This is top right matrix which stores all transitions from transitory states to final states.
		int[][] rMatrix = new int[n-2][2];
			
		// Loop through all possible transitions.
		for (int i=0; i<origN; i++) {
			for (int j=0; j<origN; j++) {
				
				// Actual states we are referring to in our new matrix.
				int mapS = map[i];
				int mapE = map[j];
					
				// In the qMatrix.
				if (mapS < k && mapE < k) {
					int add = numWays(i, j, d, origN);
					qMatrix[mapS][mapE] += add;
				}
					
				// In the rMatrix.
				else if (mapS < k) {
					int add = numWays(i, j, d, origN);
					rMatrix[mapS][mapE-k] += add;
				}
			}
		}

		// Get everything set up for linear algebra stuff.
		multAllElems(rMatrix, inv[d]);
		multAllElems(qMatrix, inv[d]);		
		
		// Clearly hacky...I didn't realize I needed m until now...
		int[][] m = new int[n][n];
		for (int i=0; i<n-2; i++) {
			for (int j=0; j<n-2; j++)
				m[i][j] = qMatrix[i][j];
			for (int j=n-2; j<n; j++)
				m[i][j] = rMatrix[i][j-(n-2)];
		}
		
		// Identity matrix here.
		for (int i=n-2; i<n; i++) m[i][i] = 1;
		
		// I simulate n steps so that nSteps[i] stores the probability of being in state i after n steps.
		int[] nSteps = simulate(m, n);
		
		// We check if the probability of being in all transitory states is 0.
		boolean noTrans = true;
		for (int i=0; i<n-2; i++)
			if (nSteps[i] != 0)
				noTrans = false;
			
		// If so, this is our answer.
		if (noTrans) return nSteps[n-2];
		
		// This is what we have to do for the linear algebra solution...
		makeIminusQ(qMatrix);
			
		// Starting state is always 0.
		startRow = 0;
			
		// Get (I - Q)^-1
		int[][] inv = inverse(qMatrix);
			
		// This matrix stores the probability of moving from any transitory state to any terminal state.
		int[][] res = mult(inv, rMatrix);
			
		// Ta da! So we want the probability of going from startRow to winning (column 0).
		return res[startRow][0];		
	}
	
	// Simulates the process for n steps and returns the probabilities of being in each state after n steps.
	public static int[] simulate(int[][] m, int n) {
		
		// Starting state.
		int[][] probs = new int[1][n];
		probs[0][0] = 1;
		
		// Run it n times!
		for (int i=0; i<n; i++)
			probs = mult(probs, m);
		return probs[0];
	}
	
	// Returns a times b.
	public static int[][] mult(int[][] a, int[][] b) {
		int[][] res = new int[a.length][b[0].length];
		for (int i=0; i<a.length; i++)
			for (int j=0; j<b[0].length; j++)
				for (int k=0; k<a[0].length; k++)
					res[i][j] = (res[i][j] + a[i][k]*b[k][j])%MOD;
		return res;
	}
	
	// returns a raised to the power pow using fast matrix exponentiation.
	public static int[][] exp(int[][] a, int pow) {
		
		// To be safe I return a copy.
		if (pow == 1) return copy(a);
		
		// Can do this faster.
		if (pow%2 == 0) {
			int[][] tmp = exp(a, pow/2);
			return mult(tmp, tmp);
		}
		
		// Usual slow way.
		int[][] tmp = exp(a, pow-1);
		return mult(a, tmp);
	}
	
	// Returns a copy of a.
	public static int[][] copy(int[][] a) {
		int[][] res = new int[a.length][a[0].length];
		for (int i=0; i<a.length; i++)
			for (int j=0; j<a[0].length; j++)
				res[i][j] = a[i][j];
		return res;
	}
	
	// Pre-condition: the inverse of q exists.
	// Post-condition: returns the inverse of q.
	public static int[][] inverse(int[][] q) {
		
		int sz = q.length;
		int[][] m = new int[sz][2*sz];
		
		// copy in my matrix.
		for (int i=0; i<sz; i++)
			for (int j=0; j<sz; j++)
				m[i][j] = q[i][j];
			
		// Make this the identity matrix.
		for (int i=0; i<sz; i++)
			m[i][i+sz] = 1;
		
		// First make lower triangle.
		for (int i=0; i<m.length; i++) {
			
			// Swap rows here.
			if (m[i][i] == 0) {
				int row = getRow(m, i);
				swapRows(m, i, row);
				
				// Update the starting row, if it got swapped.
				if (i == startRow) startRow = row;
				else if (row == startRow) startRow = i;
			}
			
			// Put a 1 in slot i,i.
			multRow(m[i], inv[m[i][i]]);
			
			// Fix all the future rows.
			for (int j=i+1; j<m.length; j++) {
				
				// Now go through update each row below.
				int mult = m[j][i];
				for (int k=i; k<m[i].length; k++)
					m[j][k] = (m[j][k] - mult*m[i][k] + MOD*MOD)%MOD;
			}	
		}
		
		// Next, make the top left triangle 0 also.
		for (int i=m.length-1; i>=0; i--) {
			
			// These (j) are the rows we are now adjusting.
			for (int j=0; j<i; j++) {
				
			// These are the updates on row j
				int mult = m[j][i];
				for (int k=i; k<m[i].length; k++)
					m[j][k] = (m[j][k] - mult*m[i][k] + MOD*MOD)%MOD;
			}
		}
		
		// Copy the inverse into a regular matrix and return.
		int[][] inv = new int[m.length][m.length];
		for (int i=0; i<m.length; i++)
			for (int j=0; j<m.length; j++)
				inv[i][j] = m[i][j+m.length];
		return inv;
	}
	
	// Returns a non-zero row of m strictly AFTER row.
	public static int getRow(int[][] m, int row) {
		
		for (int i=row+1; i<m.length; i++)
			if (m[i][row] != 0)
				return i;
			
		// I hope it never gets here =)
		return -1;
	}
	
	// Multiplies each item in row by factor (mod MOD)
	public static void multRow(int[] row, int factor) {
		for (int i=0; i<row.length; i++)
			row[i] = (row[i]*factor)%MOD;
	}
	
	// Swaps rows rX and rY in m.
	public static void swapRows(int[][] m, int rX, int rY) {
		int[] tmp = m[rX];
		m[rX] = m[rY];
		m[rY] = tmp;
	}
	
	// Changes qMatrix to be the identity minus q.
	public static void makeIminusQ(int[][] qMatrix) {
		for (int i=0; i<qMatrix.length; i++) 
			for (int j=0; j<qMatrix[i].length; j++) 
				if (i == j)
					qMatrix[i][j] = (1 - qMatrix[i][j] + MOD)%MOD;
				else
					qMatrix[i][j] = (MOD - qMatrix[i][j])%MOD;
	}
	
	// Multiplies all items in m by factor.
	public static void multAllElems(int[][] m, int factor) {
		for (int i=0; i<m.length; i++)
			for (int j=0; j<m[i].length; j++)
				m[i][j] = (m[i][j]*factor)%MOD;
	}
	
	// returns the # of ways to roll from s to e with dice labeled 1 to d and a board of origN sides.
	public static int numWays(int s, int e, int d, int origN) {
	
		// Number of steps for the shortest way.
		int numS = s < e ? e-s : origN - (s-e);
		
		// Full revolutions; everything gets hit at least this many times.
		int res = d/origN;
		
		// This means that the last trip around makes it.
		if (numS <= d%origN) res++;
		
		return res;
	}
	
	// Returns b to the e mod MOD.
	public static int modExp(int b, int e) {
		if (e == 1) return b;
		if (e%2 == 0) {
			int tmp = modExp(b, e/2);
			return (tmp*tmp)%MOD;
		}
		return (b*modExp(b,e-1))%MOD;
	}

}
