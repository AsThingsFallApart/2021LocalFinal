#include <bits/stdc++.h>
#include <iostream>

using namespace std;

int main() {
    string input;
    int value;
    bool is_odd = false;
    bool is_even = false;

    cin >> input;

    map<char, string> result;

    for (int i = 0; i < input.length(); i++) {
        char c = input.at(i);
        if (result.find(c) != result.end()) {
            if (result[c].compare("even") == 0) 
                result[c] = "odd";
            else 
                result[c] = "even";
        }
        else 
            result[c] = "odd";
    }

    map<char, string>::iterator it = result.begin();

    while (it != result.end()) {
        if (it->second.compare("even") == 0) 
            is_even = true;
        else
            is_odd = true;

        if (is_even & is_odd) {
            cout << "0/1";
            printf("\n");
            return 0;
        }  

        it++;
    }

    if (is_odd)
        cout << "1";
    else
        cout << "0";

    printf("\n");
    return 0;
}

 