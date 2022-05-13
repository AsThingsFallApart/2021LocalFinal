
#include <bits/stdc++.h>
#define MAX 1000001
#define MOD 1000000007
#define all(x) (x).begin(), (x).end()

using namespace std;

typedef long long ll;
typedef vector<ll> vll;
typedef pair<int, int> pii;
typedef vector<pii> vpii;

vll fact, i_fact, inv;

void precomp() {
    inv.push_back(-1);
    inv.push_back(1);
    for (int i = 2; i <= MAX; i++) {
        ll res = ((MOD-(MOD/i))*(inv[MOD % i])) % MOD;
        inv.push_back(res);
    }
    fact.push_back(1);
    i_fact.push_back(1);
    for (int i = 0; i < MAX; i++) {
        fact.push_back((fact[i] * (i + 1)) % MOD);
        i_fact.push_back((i_fact[i] * inv[i + 1]) % MOD);
    }
}
vpii points;
vll inter[2];
vector<int> lowest_index[2];
vector<int> highest_index[2];
#define NEGATIVE 1
#define POSITIVE 0
ostream cnull(NULL);
#define cerr cnull

ll comp2(pii st, pii en, int type) {
    int dx = en.first - st.first;
    int dy = en.second - st.second;
    if (type == NEGATIVE)
        dy = -dy;
    if (dx < 0) return 0;
    if (dy < 0) return 0; // this
    ll ans = fact[dx + dy];
    ans *= i_fact[dx];
    ans %= MOD;
    ans *= i_fact[dy];
    return (ans % MOD);
}
void printPair(pii p) {
    cerr << "(" << p.first << ", " << p.second << ") ";
}

ll comp(int map, pii st, pii en, int type) {
    cerr << "\"" << inter[type][map] << "\"" << endl;
    if (inter[type][map] == 0) return 0;
//    cerr << map << " (" << st.first << ", " << st.second << ") (" << en.first << ", " << en.second << ") " << type << endl;
    ll front = comp2(st, points[lowest_index[type][map]], type);
    ll end = comp2(points[highest_index[type][map]], en, type);
    end *= front;
    end %= MOD;
    end *= inter[type][map];
    return end % MOD;
}



int main() {
    precomp();
    int n;
    cin >> n;
    points.resize(n);
    for (int i = 0; i < n; i++) {
        cin >> points[i].first;
        cin >> points[i].second;
    }
    for (int type = 0; type < 2; type++) {
        lowest_index[type].push_back(1);
        highest_index[type].push_back(1);
        inter[type].push_back(1);
    }
    for (int i = 1; i < (1<<n); i++) {
        int g = 0;
        while (!((1<<g)&i)) g++;
        if ((i&-i) != i) {
            pair<int, int> lip, hip, lin, hin;
            lip.first = g;
            lip.second = points[g].first + points[g].second;
            hip = lip;
            
            lin.first = g;
            lin.second = points[g].first - points[g].second;
            hin = lin;


            for (int j = g + 1; j < n; j++) {
                if (i&(1<<j)) {
                    pair<int, int> curNegPair;
                    curNegPair.first = j;
                    curNegPair.second = points[j].first - points[j].second;
                    
                    pair<int, int> curPosPair;
                    curPosPair.first = j;
                    curPosPair.second = points[j].first + points[j].second;

                    if (curPosPair.second < lip.second) lip = curPosPair;
                    if (curPosPair.second > hip.second) hip = curPosPair;
                    if (curNegPair.second < lin.second) lin = curNegPair;
                    if (curNegPair.second > hin.second) hin = curNegPair;
                }
            }

            lowest_index[POSITIVE].push_back(lip.first);
            highest_index[POSITIVE].push_back(hip.first);
            lowest_index[NEGATIVE].push_back(lin.first);
            highest_index[NEGATIVE].push_back(hin.first);

            printPair(lip);
            printPair(hip);
            cerr << endl;
            
            printPair(lin);
            printPair(hin);
            cerr << endl;

            for (int type = 0; type < 2; type++)
                inter[type].push_back(
                    comp(i^(1<<lowest_index[type][i]),
                    points[lowest_index[type][i]], 
                    points[highest_index[type][i]],
                    type));
        } else {
            for (int type = 0; type < 2; type++) {
                lowest_index[type].push_back(g);
                highest_index[type].push_back(g);
                inter[type].push_back(1);
            }
        }
    }

    for (int type = 0; type < 2; type++) {
        cerr << "Type " << type << endl;
        for (auto x : inter[type]) {
            cerr << x << endl;
        }
        cerr << endl;
    }

    int bc[(1<<n)];
    bc[0] = 0;
    for (int i = 1; i < (1<<n); i++) {
        bc[i] = 1 + bc[i^(i&-i)];
    }

    int c;
    cin >> c;
    while (c--) {
        pii st, en;
        cin >> st.first;
        cin >> st.second;
        cin >> en.first;
        cin >> en.second;
        
        if (st.first > en.first) {
            pii tmp = st;
            st = en;
            en = tmp;
        }
        ll ans = 0;
        int type = (st.second < en.second) ? POSITIVE : NEGATIVE;
        ans = comp2(st, en, type);
        cerr << " + " << ans << endl;

        for (int i = 1; i < (1<<n); i++) {
            if (bc[i]&1) {
                ans -= comp(i, st, en, type);
                cerr << i << " - " << comp(i, st, en, type) << " " << bc[i] << endl;
            } else {
                ans += comp(i, st, en, type);
                cerr << i << " + " << comp(i, st, en, type) << " " << bc[i] << endl;
            }
        }
        ans %= MOD;
        ans += MOD;
        cout << (ans % MOD) << endl;

    }
    return 0;
}
