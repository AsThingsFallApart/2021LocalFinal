#include <iostream>
#include <vector>
using namespace std;

int main() {

  int first = 0;
  int second = 0;

  cin >> first >> second;
//  cout << first << " " << second << "\n";

  // generate third and fourth fib numbers to put into vector
  int third = first + second;
  if(third >= 10) {
    third = third % 10;
  }

  int fourth = second + third;
  if(fourth >= 10) {
    fourth = fourth % 10;
  }


  vector <int>fib = {first, second, third, fourth};

  int i = 3;
  int next = 0;
  while( (fib.at(0) != fib.at(i - 1)) || (fib.at(1) != fib.at(i)) ) {
    next = fib.at(i - 1) + fib.at(i);
    if(next >= 10) {
      next = next % 10;
    }

    fib.push_back(next);
    i++;
//
//    cout << "i: " << i << "\n";
//    cout << "vectorSize: " << fib.size() << "\n";
//    cout << "fib[0] = " << fib.at(0) << "\n";
//    cout << "fib[1] = " << fib.at(1) << "\n";
//    cout << "fib[i - 1] = " << fib.at(i - 1) << "\n";
//    cout << "fib[i] = " << fib.at(i) << "\n\n";
  }

  cout << fib.size() << "\n";

  return 0;
}
