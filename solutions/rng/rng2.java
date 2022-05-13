
import java.util.*;

public class rng2 {
    
    public static int MOD = 10007;
    public static int[] invs;

    // Compute all the necessary mod inverses
    public static void precomp() {
        invs = new int[MOD];
        invs[1] = 1;
        for (int i = 2; i < MOD; i++) {
            invs[i] = (MOD-((MOD/i)*(invs[MOD%i]))%MOD);
        }
    }

    public static ArrayList<Integer> wins, losses;
    public static boolean[] isWin, isLoss;
    public static int n, d;

    // Check for finite number of exectutions
    public static boolean finite() {
        int[] probs = new int[n];
        int[] next = new int[n];
        probs[0] = 1;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(next, 0);
            for (int pos = 0; pos < n; pos++) {
                if (probs[pos] != 0) {
                    if (isWin[pos] || isLoss[pos]) {
                        if (isWin[pos]) ans += probs[pos];
                        continue;
                    }
                    if (pos + d >= n) return false;
                    for (int dVal = 1; dVal <= d; dVal++){ 
                        next[dVal + pos] += probs[pos] * invs[d];
                        next[dVal + pos] %= MOD;
                    }
                }
            }
            int[] tmp = next;
            next = probs;
            probs = tmp;
        }
        System.out.println(ans%MOD);

        return true;
    }

    // Get the full matrix
    public static int[][] makeMatrix() {
        int[][] ret = new int[n][n];
        for (int i = 0; i < n; i++) {
            if (isWin[i] || isLoss[i]) {
                ret[i][i] = 1;   
            } else {
                for (int j = 1; j <= d; j++)
                    ret[i][(j+i)%n] += invs[d];
                for (int j = 0; j < n; j++)
                    ret[i][j] %= MOD;
            }
        }
        int first = 0;
        int last = n - 1;
        for (int i = 0; i < n - 1; i++) {
            if (isWin[first] || isLoss[first]) {
                isWin[first] ^= isWin[last];
                isWin[last] ^= isWin[first];
                isWin[first] ^= isWin[last];
                isLoss[first] ^= isLoss[last];
                isLoss[last] ^= isLoss[first];
                isLoss[first] ^= isLoss[last];
                
                // swap row
                int[] tmp = ret[first];
                ret[first] = ret[last];
                ret[last] = tmp;

                // swap col
                for (int j = 0; j < n; j++) {
                    ret[j][first] ^= ret[j][last];
                    ret[j][last] ^= ret[j][first];
                    ret[j][first] ^= ret[j][last];
                }
                last--;
            } else {
                first++;
            }
        }
        return ret;
    }

    // Matrix copy
    public static int[][] copy(int[][] o) {
        int n = o.length;
        int m = o[0].length;
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) for(int j = 0; j < m; j++) ret[i][j] = o[i][j];
        return ret;
    }

    // DEBUG
    public static void printMatrix(int[][] o) {
        if (1 == 2) {
            for (int[] x : o)
                System.err.println(Arrays.toString(x));
            System.err.println();
        }
    }

    // Ewww
    public static boolean gaus(int[][] o) {
        int n = o.length;
        int m = o[0].length;
        int st = 0;
        for (int i = 0; i < n; i++) {
            for (int j = st; j < n; j++) {
                if (o[j][i] != 0) {
                    if (st == j) break;
                    int[] tmp = o[j];
                    o[j] = o[st];
                    o[st] = tmp;
                    break;
                }
            }
            if (o[st][i] == 0) {
                continue;
            }
            int mult = invs[o[st][i]];
            for (int j = 0; j < m; j++) {
                o[st][j] *= mult;
                o[st][j] %= MOD;
            }
            for (int j = 0; j < n; j++) {
                if (j == st) continue;
                if (o[j][i] == 0) continue;
                mult = o[j][i];
                for (int k = 0; k < m; k++) {
                    o[j][k] -= o[st][k] * mult;
                    o[j][k] %= MOD;
                    o[j][k] += MOD;
                    o[j][k] %= MOD;
                }
            }
            st++;
        }
        return (st == n);
    }

    public static int[][] matInv(int[][] o) {
        // Make extended
        int n = o.length;
        int[][] matrix = new int[n][n*2];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                matrix[i][j] = o[i][j];
                matrix[i][j+n] = (i==j)?1:0;
            }
        
        // Run gaus
        gaus(matrix);

        // extract partial
        int[][] partial = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                partial[i][j] = matrix[i][j+n];
            }
        }
        
        // Return the part that is inverted
        return partial;
    }

    // Matrix multiplication
    public static int[][] mult(int[][] a, int[][] b) {
        int n = a.length;
        int r = a[0].length;
        int m = b[0].length; 
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++)
                for (int k = 0; k < r; k++) {
                    ret[i][j] += a[i][k] * b[k][j];
                    ret[i][j] %= MOD;
                }
        return ret;
    }

    // Identity
    public static int[][] makeI(int n2) {
        int[][] ret = new int[n2][n2];
        for (int i = 0; i < n2; i++)
            ret[i][i] = 1;
        return ret;
    }

    // Q
    public static int[][] getQ(int[][] matrix) {
        int n2 = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (isWin[i] || isLoss[i]) break;
            n2++;
        }
        int[][] ret = new int[n2][n2];
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                ret[i][j] = matrix[i][j];
            }
        }
        return ret;
    }

    // matrix subtraction
    public static int[][] sub(int[][] a, int[][] b) {
        int n = a.length;
        int m = a[0].length;
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ret[i][j] = a[i][j] - b[i][j];
                if (ret[i][j] < 0) ret[i][j] += MOD;
            }
        }
        return ret;
    }

    // R
    public static int[][] getR(int[][] matrix, int n2) {
        int n = matrix.length;
        int r = n2;
        int c = n - n2;
        int[][] ret = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                ret[i][j] = matrix[i][n2+j];
            }
        }
        return ret;
    }

    public static void main(String[] Args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        d = sc.nextInt();
        int w = sc.nextInt();
        int l = sc.nextInt();

        // Corner cases
        if (w == 0) {
            System.out.println(0);
            return;
        }
        if (l == 0) {
            System.out.println(1);
            return;
        }

        // Read win loss states
        wins = new ArrayList<Integer>();
        losses = new ArrayList<Integer>();
        isWin = new boolean[n];
        isLoss = new boolean[n];
        for (int i = 0; i < w; i++) {
            int loc = sc.nextInt() - 1;
            isWin[loc] = true;
            wins.add(loc);
        }
        for (int i = 0; i < l; i++) {
            int loc = sc.nextInt() - 1;
            isLoss[loc] = true;
            losses.add(loc);
        }

        // Check if we can't loop around
        precomp();
        if (finite()) return;

        // Do the QRI method using absorbin markov chains
        int[][] matrix = makeMatrix();
        int[][] Q = getQ(matrix);
        int n2 = Q.length;
        int[][] In2 = makeI(n2);
        int[][] Q2 = matInv(sub(In2, Q));
        int[][] R = getR(matrix, n2);
        int[][] tmp = mult(Q2, R);

        // DEBUG
        printMatrix(tmp);

        // Extract the answer
        int ans = 0;
        for (int i = 0; i < n - n2; i++)
            if (isWin[i+n2])
                ans += tmp[0][i];
        System.out.println(ans % MOD);
    }
}
