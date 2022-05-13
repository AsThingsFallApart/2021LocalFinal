#include <iostream>
using namespace std;

int main() {
  string s = "";
  cin >> s;

  int charCount[26];
  int i = 0;

  int oddFlag = 1;
  int evenFlag = 1;

  for(i = 0; i < 26; i++) {
    charCount[i] = 0;
  }

  for(char x : s) {
    switch(x) {
    case 'a':
      charCount[0]++;
      break;
    case 'b':
      charCount[1]++;
      break;
    case 'c':
      charCount[2]++;
      break;
    case 'd':
      charCount[3]++;
      break;
    case 'e':
      charCount[4]++;
      break;
    case 'f':
      charCount[5]++;
      break;
    case 'g':
      charCount[6]++;
      break;
    case 'h':
      charCount[7]++;
      break;
    case 'i':
      charCount[8]++;
      break;
    case 'j':
      charCount[9]++;
      break;
    case 'k':
      charCount[10]++;
      break;
    case 'l':
      charCount[11]++;
      break;
    case 'm':
      charCount[12]++;
      break;
    case 'n':
      charCount[13]++;
      break;
    case 'o':
      charCount[14]++;
      break;
    case 'p':
      charCount[15]++;
      break;
    case 'q':
      charCount[16]++;
      break;
    case 'r':
      charCount[17]++;
      break;
    case 's':
      charCount[18]++;
      break;
    case 't':
      charCount[19]++;
      break;
    case 'u':
      charCount[20]++;
      break;
    case 'v':
      charCount[21]++;
      break;
    case 'w':
      charCount[22]++;
      break;
    case 'x':
      charCount[23]++;
      break;
    case 'y':
      charCount[24]++;
      break;
    case 'z':
      charCount[25]++;
      break;
    }
  }

  for(i = 0; i < 26; i++) {
    if(charCount[i] != 0) {
      if(charCount[i] % 2 == 0) {
        oddFlag = 0;
      }
      if(charCount[i] % 2 != 0) {
        evenFlag = 0;
      }
    }
  }

  if((oddFlag == 0) && (evenFlag == 0)) {
    cout << "0/1" << "\n";
  }
  if(oddFlag) {
    cout << 1 << "\n";
  }
  if(evenFlag) {
    cout << 0 << "\n";
  }

  return 0;
}
