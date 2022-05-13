// Arup Guha
// 9/6/2021
// Solution to 2021 UCF Locals Problem: k-gap Subsequence

import java.util.*;
import java.io.*;

public class kgap {

	public static void main(String[] args) throws Exception {
	
		// Read in the values and store all unique ones for coordinate compression.
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tok = new StringTokenizer(stdin.readLine());
		int n = Integer.parseInt(tok.nextToken());
		int k = Integer.parseInt(tok.nextToken());
		int[] vals = new int[n];
		TreeSet<Integer> set = new TreeSet<Integer>();
		tok = new StringTokenizer(stdin.readLine());
		for (int i=0; i<n; i++) {
			vals[i] = Integer.parseInt(tok.nextToken());
			set.add(vals[i]);
		}
		
		// Coordinate compressed values.
		TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
		int id = 0;
		while (set.size() > 0) map.put(set.pollFirst(), id++);
		
		// This is safe since # of unique values never exceeds n.
		IntTreeArray segtree = new IntTreeArray(n);
		
		int res = 1;
		
		// Go through the array.
		for (int i=0; i<n; i++) {
			
			int above = vals[i] + k - 1;
			int below = vals[i] - k + 1;
			
			Integer higherKey = map.higherKey(above);
			Integer lowerKey = map.lowerKey(below);
			
			// See if we can build off of a number higher than us.
			int cur = 1;
			if (higherKey != null) {
				int loc = map.get(higherKey);
				int prevAns = segtree.query(loc, n);
				cur = Math.max(cur, prevAns + 1);
			}
			
			// See if we can build off of a number lower than us.
			if (lowerKey != null) {
				int loc = map.get(lowerKey);
				int prevAns = segtree.query(0, loc);
				cur = Math.max(cur, prevAns + 1);
			}
			
			// See what answer we currently have here.
			int myID = map.get(vals[i]);
			int curAns = segtree.query(myID, myID);
			
			// Update if this new answer is better.
			if (cur > curAns) segtree.change(myID, myID, cur-curAns);
			
			// Update our global answer.
			res = Math.max(res, cur);
		}
		
		// Ta da!
		System.out.println(res);
	}
}

class IntTreeNode {

    public int delta;
    public int value;
    public int low;
    public int high;

    public IntTreeNode(int d, int v, int l, int h) {
        delta = d;
        value = v;
        low = l;
        high = h;
    }
}

class IntTreeArray {

    private IntTreeNode[] array;

	// Creates an interval tree from 0 to nextPow2(n)-1, inclusive.
	public IntTreeArray(int n) {

        int max = nextPow2(n)-1;
        array = new IntTreeNode[(max+1)<<1];
        array[1] = new IntTreeNode(0, 0, 0, max);

        for (int i=2; i<array.length; i = (i<<1)) {
            int cur = 0;
            int step = (array[i-1].high - array[i-1].low + 1)/2;
            for (int j=i; j<(i<<1); j++) {
                array[j] = new IntTreeNode(0, 0, cur, cur+step-1);
                cur += step;
            }
        }
	}

	// Returns k such that k is the smallest perfect power of 2 strictly greater than n.
	private static int nextPow2(int n) {
        return Integer.highestOneBit(n << 1);
	}

	// Propagates a change down to child nodes.
	private void prop(int node) {

		// Recursive case, push down.
		if (2*node < array.length) {
			array[2*node].delta += array[node].delta;	/*** can change ***/
			array[2*node+1].delta += array[node].delta;	/*** can change ***/
			array[node].delta = 0;
		}

		// I put this in, seems to make sense.
		else {
			array[node].value += array[node].delta; /*** Can change - set for sum now ***/
			array[node].delta = 0;
		}
	}

	// Pre-condition: delta is 0.
	private void update(int node) {
		if (2*node < array.length)
			array[node].value = Math.max(array[2*node].value+array[2*node].delta, array[2*node+1].value+array[2*node+1].delta) ; 
	}

	// Wrapper method - changes items in range[start, end] by adding extra to each.
	public void change(int start, int end, int extra) {
        change(1, start, end, extra);
	}

	// Changes the values in the range [start, end] starting at node adding extra.
	private void change(int node, int start, int end, int extra) {

		// Out of range.
		if (array[node].high < start || end < array[node].low) return;

		// Push down delta.
		prop(node);

		// Whole range must be updated.
		if (start <= array[node].low && array[node].high <= end) {
			array[node].delta += extra;		/*** Can change ***/
			update(node);
			return;
		}

		// Portions of children have to be changed instead.
		change(2*node, start, end, extra);
		change(2*node+1, start, end, extra);
		update(node);
	}

	// Another wrapper method.
	public int query(int start, int end) {
        return query(1, start, end);
	}

    // This one does the work.
	private int query(int node, int start, int end) {

		// Out of range.
		if (array[node].high < start || end < array[node].low) return  0; /*** Can change ***/

		// Whole range must be returned;
		if (start <= array[node].low && array[node].high <= end) {
			return array[node].value + array[node].delta;
		}

		// Get answers from both potions.
		prop(node);
		int leftSide = query(2*node, start, end);
		int rightSide = query(2*node+1, start, end);
		update(node);
		return Math.max(leftSide,rightSide);
	}
}
