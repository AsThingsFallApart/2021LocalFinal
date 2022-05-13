
#include <bits/stdc++.h>

using namespace std;

vector<vector<int> > memo(21);

int rec(int p, int n);

int main() {
    int n, p;
    cin >> n >> p;
    if (p > 20) p = 20;
    
    for (int i = 0; i <= p; i++) {
        for (int j = 0; j <= n; j++)
            memo[i].push_back(-1);
        memo[i][1] = 1;
    }
    for (int j = 0; j <= n; j++) {
        memo[1][j] = j - 1;
    }
    
    cout << rec(p, n) << endl;

    return 0;
}

int rec(int p, int n) {
    if (n == 1) return 0;
    if (memo[p][n] != -1) return memo[p][n];
    int hi = n / 2 + 1;
    int lo = 1;
    memo[p][n] = n - 1;
    while (hi > lo) {
        int mid = (hi + lo) / 2;
        int a1 = rec(p - 1, mid);
        int a2 = rec(p, n - mid);
        memo[p][n] = min(memo[p][n], 1 + max(a1, a2));
        if (a1 < a2) {
            lo = mid + 1;
        } else if (a2 < a1) {
            hi = mid;
        } else {
            return memo[p][n];
        }
    }
    return memo[p][n];
}

