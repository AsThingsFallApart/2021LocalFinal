

#include <bits/stdc++.h>

using namespace std;

typedef vector<int> vi;
typedef vector<vi> vvi;

vvi adj_list;
vvi jda_list;

int main() {
    int n, b;
    cin >> n >> b;
    
    for (int i = 0; i < n; i++) {
        vi v_1, v_2;
        adj_list.push_back(v_1);
        jda_list.push_back(v_2);
    }

    for (int i = 0; i < b; i++) {
        int a, b;
        cin >> a;
        a--;
        int len;
        cin >> len;
        for (int j = 0; j < len; j++) {
            cin >> b;
            b--;
            adj_list[a].push_back(b);
            jda_list[b].push_back(a);
        }
    }

    vi vis(n);
    vi q(n);
    int last = 0;
    int fptr = 0;
    int bptr = 0;
    for (int i = 0; i < n; i++) {
        if (vis[i] == 0) {
            vis[i] = 1;
            last = i;
            q[bptr++] = i;
            while (bptr > fptr) {
                int cur = q[fptr++];
                for (auto j : adj_list[cur])
                    if (vis[j] == 0)
                        vis[q[bptr++] = j] = 1;
            }
        }
    }
    fptr = 0;
    bptr = 0;
    vis[last] = 2;
    q[bptr++] = last;
    int amt = 0;
    while (bptr > fptr) {
        int cur = q[fptr++];
        amt++;
        for (auto j : adj_list[cur])
            if (vis[j] == 1)
                vis[q[bptr++] = j] = 2;
    }

    if (amt != n) {
        cout << 0 << endl;
        return 0;
    }
    
    fptr = 0;
    bptr = 0;
    vis[last] = 3;
    q[bptr++] = last;
    int ans = 0;
    while (bptr > fptr) {
        int cur = q[fptr++];
        ans++;
        for (auto j : jda_list[cur])
            if (vis[j] == 2)
                vis[q[bptr++] = j] = 3;
    }

    cout << ans << endl;

    return 0;
}
