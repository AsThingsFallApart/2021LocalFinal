#include <bits/stdc++.h>
#include <iostream>

using namespace std;

int main() {
    int inputs [5];
    cin >> inputs[0];
    cin >> inputs[1];
    cin >> inputs[2];
    cin >> inputs[3];
    cin >> inputs[4];

    sort(inputs, inputs+5, greater<int>());

    do {        
        if ( (inputs[0] == inputs[1] + inputs[2] + inputs[3]) &&
             (inputs[4] == 3 * inputs[1] + inputs[2]) )  {
                cout << inputs[0] << ' ' << inputs[1] << ' ' << inputs[2] << ' ' << inputs[3] << ' ' << inputs[4] << '\n';
                break;
        } 
	} while ( prev_permutation(inputs,inputs+5) );

    return 0;
}

 