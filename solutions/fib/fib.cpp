#include <bits/stdc++.h>
#include <iostream>

using namespace std;

int main() {
    int f_num, s_num;
    cin >> f_num;
    cin >> s_num;

    int third_num = (f_num + s_num) % 10;
    int fourth_num = (s_num + third_num) % 10;
    int temp_num;
    int count = 4;

    if (third_num == f_num && fourth_num == s_num) {
        cout << count << '\n';
        return 0;
    }

    while (true) {
        count++;
        temp_num = (third_num + fourth_num) % 10;
        third_num = fourth_num;
        fourth_num = temp_num;

        if (third_num == f_num && fourth_num == s_num) {
            cout << count << '\n';
            return 0;
        }
    }

    return 0;
}
 