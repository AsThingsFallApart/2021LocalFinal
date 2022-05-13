
#include <bits/stdc++.h>

using namespace std;

#define ABS(x) (((x)<0)?-(x):(x))
#define EPS 1e-11
#define MAX(x,y) (((x)<(y))?(y):(x))

int eq(double a, double b) {
    double diff = ABS(a-b);
    if (diff < EPS)
        return 1;
    a = ABS(a);
    b = ABS(b);
    return (diff < EPS * MAX(a, b));
}

double comp(double v, double len) {
    // dist = t(v - t/2)
    // dist = tv - t*t/2
    // 0 = -(1/2)t^2 + (v)t - dist
    double a = -0.5;
    double b = v;
    double c = -len;
    double extra = b*b - 4 * a * c;
    if (extra < 0) return 2e9;
    return (b - sqrt(extra));
}

int check(double time, int len, vector<int> locs, double amt) {
    double vel = amt;
    for (int j = 1; j < locs.size(); j++) {
        double inc = comp(vel, locs[j] - locs[j - 1]);
        time -= inc;
        vel -= inc;
        vel += amt;
        if (time < 0 || vel < 0) return 0;
    }
    double inc = comp(vel, len - locs[locs.size() - 1]);
    time -= inc;
    return (time > 0);
    
}

int main() {
    int length;
    int boosts;
    int time;
    cin >> length >> boosts >> time;
    vector<int> arr(boosts);
    for (int i = 0; i < boosts; i++) {
        cin >> arr[i];
    }
    double lo = 0;
    double hi = 2e9;
    double ans = 2e9;

    while (!eq(lo, hi)) {
        double mid = (hi + lo) / 2;
        if (check(time, length, arr, mid)) {
            hi = mid;
            ans = mid;
        } else {
            lo = mid;
        }
    }
    
    printf("%.20lf\n", ans);
    return 0;
}
