// Gabriel Pita
// 9/08/2021
// Solution to 2021 UCF Local Contest Problem: Tutorial Groupings
import java.util.*;

public class tutorial2 {
    static int n, k, s;
    static int[] a;
    // "dp" is short for "Dynamic Programming"
    static int[][] dpPartialAnsByStudentIndexAndRunningGroupSize;
    static int mod = 1_000_000_007;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        k = in.nextInt();
        s = in.nextInt();
        a = new int[n];
        for(int i =0; i < n; i++){
            a[i] = in.nextInt();
        }
        // Sort student knowledge levels since tutorial groups require knowledge levels strictly increasing
        Arrays.sort(a);
        // 0 is NOT a valid answer, so 0 is used in this array to signify that the partial answer has not yet been
        // computed.
        dpPartialAnsByStudentIndexAndRunningGroupSize = new int[n][s+1];

        // Avoid stack overflow errors by filling out some memoized partial answers in reverse
        for(int studentIndex =n-1; studentIndex >= 1; studentIndex--){
            calculateDpAns(studentIndex, 1);
        }

        // Start the method call at student 1 since knowledge levels are compared with previous students.
        // Student 0 always goes into a new group, so there is only 1 way to handle student 0 and the computation can
        // be skipped.
        System.out.println(calculateDpAns(1, 1));
    }

    // Recursive dynamic programing method. Solutions for this method are saved in the
    // dpPartialAnsByStudentIndexAndRunningGroupSize array.
    static int calculateDpAns(int studentIndex, int currentGroupSize){
        if(studentIndex == n){
            // Base case: no students left.
            // Return 1 as there is only 1 way to group the 0 remaining students (do nothing).
            return 1;
        }
        // Check if partial answer is already memoized. If yes, return it immediately.
        if(dpPartialAnsByStudentIndexAndRunningGroupSize[studentIndex][currentGroupSize] != 0){
            return dpPartialAnsByStudentIndexAndRunningGroupSize[studentIndex][currentGroupSize];
        }
        //  If a new group is created, currentGroupSize must be set to 1 for the new running group just created with
        //  size 1.
        int numPossibilitiesCreatingNewGroup = calculateDpAns(studentIndex+1, 1);
        if(currentGroupSize == s){
            // No other student can de added to the running tutorial group, so the next student must be added to a new
            // group.
            return dpPartialAnsByStudentIndexAndRunningGroupSize[studentIndex][currentGroupSize] =
                    numPossibilitiesCreatingNewGroup;
        }

        int numPossibilitiesExtendingCurrentGroup;
        // Check if it is possible to extend the group without breaking the k restriction.
        // a[studentIndex-currentGroupSize] is the first student in the running group, which will have the minimum
        // knowledge level.
        if(a[studentIndex] - a[studentIndex-currentGroupSize] <= k){
            numPossibilitiesExtendingCurrentGroup = calculateDpAns(studentIndex+1, currentGroupSize+1);
        }
        else{
            // Extending the group is not allowed due to the k restriction
            numPossibilitiesExtendingCurrentGroup = 0;
        }
        // The combination of extending the current group or making a new group covers all the possible ways to process
        // the current student.
        int totalPossibilities = (numPossibilitiesCreatingNewGroup + numPossibilitiesExtendingCurrentGroup) % mod;
        return dpPartialAnsByStudentIndexAndRunningGroupSize[studentIndex][currentGroupSize] = totalPossibilities;
    }
}
