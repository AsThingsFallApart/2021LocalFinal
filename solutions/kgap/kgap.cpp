
#include <bits/stdc++.h>

using namespace std;

int size;

int maxx(int p, int * v) {
    int ret = 0;
    p++;
    while (p < size) {
        if (v[p] > ret)
            ret = v[p];
        p += (p&-p);
    }
    return ret;
}

void update(int p, int amt, int * v) {
    p++;
    while (p) {
        if (v[p] < amt)
            v[p] = amt;
        p -= (p&-p);
    }
}

int main() {
    int n, k;
    ios_base::sync_with_stdio(false);  
    
    cin >> n >> k;

    size = n * 3 + 3;
    int * prefix = new int[size];
    int * suffix = new int[size];
    int * arr = new int[n];
    set<int> sorted_vs;
    int ans = 0;

    for (int i = 0; i < n; i++) {
        cin >> arr[i];
        sorted_vs.insert(arr[i]);
        sorted_vs.insert(arr[i] + k);
        sorted_vs.insert(arr[i] - k);
    }

    map<int, int> forward;
    map<int, int> reverse;
    int index = 0;
    int xedni = sorted_vs.size() - 1;
    for (auto x : sorted_vs) {
        reverse[x] = xedni--;
        forward[x] = index++;
    }
    
    for (int i = 0; i < n; i++) {
        int ind_off, ind_act_1, ind_act_2;

        ind_off = forward[arr[i] + k];
        ind_act_1 = forward[arr[i]];
        int poss_1 = maxx(ind_off, prefix);

        ind_off = reverse[arr[i] - k];
        ind_act_2 = reverse[arr[i]];
        int poss_2 = maxx(ind_off, suffix);

        int pp = max(poss_1, poss_2) + 1;
        if (pp > ans) ans = pp;
        update(ind_act_1, pp, prefix);
        update(ind_act_2, pp, suffix);
    }

    cout << ans << endl;

    delete[] prefix;
    delete[] suffix;

    return 0;
}
