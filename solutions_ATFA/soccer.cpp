#include <iostream>
using namespace std;

// questionContext:
//  win = 3 points
//  tie = 1 point
//  loss = 0 points

// how to read a soccer standing table:
//      MP  W D L Pts
//  USA 11  5 4 2 19
//
// "USA has played 11 matches, won 5 of them, drew 4 matches, and lost 2, for a total of 19 points"

// matchesPlayed = matchesWon + matchesDrew + matchesLost
// totalPoints = matchesWon * 3 + matchesDrew * 1 + matchesLost * 0

int main() {

  int i = 0;
  int j = 0;
  int k = 0;
  int outOfOrder[5];
  int combos[4];
  int innerCombos[3];
  int matchesPlayed = 0;
  int matchesWon = 0;
  int matchesDrew = 0;
  int matchesLost = 0;
  int totalPoints = 0;
  int sum = 0;
  int sumGames = 0;
  int PtsFound = 0;
  int indexOne = 0;
  int indexTwo = 0;
  int indexThree = 0;
  int indexUnselected = 0;

  cin >> outOfOrder[0] >> outOfOrder[1] >> outOfOrder[2] >> outOfOrder[3] >> outOfOrder[4];

  for(i = 0; i < 5; i++) {
//    cout << "-------------------------------i: " << i << "---------------------------------\n";
    if(i == 0) {
      combos[0] = outOfOrder[1];
      combos[1] = outOfOrder[2];
      combos[2] = outOfOrder[3];
      combos[3] = outOfOrder[4];
    }

    if(i == 1) {
      combos[0] = outOfOrder[0];
      combos[1] = outOfOrder[2];
      combos[2] = outOfOrder[3];
      combos[3] = outOfOrder[4];
    }

    if(i == 2) {
      combos[0] = outOfOrder[1];
      combos[1] = outOfOrder[0];
      combos[2] = outOfOrder[3];
      combos[3] = outOfOrder[4];
    }

    if(i == 3) {
      combos[0] = outOfOrder[1];
      combos[1] = outOfOrder[2];
      combos[2] = outOfOrder[0];
      combos[3] = outOfOrder[4];
    }

    if(i == 4) {
      combos[0] = outOfOrder[1];
      combos[1] = outOfOrder[2];
      combos[2] = outOfOrder[3];
      combos[3] = outOfOrder[0];
    }

    for(j = 0; j < 4; j++) {
//      cout << "j: " << j << "\n";
//      cout << "combos[0]: " << combos[0] << "\n";
//      cout << "combos[1]: " << combos[1] << "\n";
//      cout << "combos[2]: " << combos[2] << "\n";
//      cout << "combos[3]: " << combos[3] << "\n\n";

      indexOne = j;
      indexTwo = j + 1;
      // simulate a looping-array
      if(indexTwo > 3) {
        indexTwo = indexTwo - 4;
      }
      indexThree = j + 2;
      if(indexThree > 3) {
        indexThree = indexThree - 4;
      }
      // index of the one element left out of the 4-element array
      // this index, by process of elimination, is 'gamesPlayed'
      indexUnselected = j + 3;
      if(indexUnselected > 3) {
        indexUnselected = indexUnselected - 4;
      }

      innerCombos[0] = combos[indexOne];
      innerCombos[1] = combos[indexTwo];
      innerCombos[2] = combos[indexThree];

      if(!(PtsFound)) {
        for(k = 0; k < 6; k++) {
//          cout << "k: " << k << "\n";
//          cout << "innerCombos[0]: " << innerCombos[0] << "\n";
//          cout << "innerCombos[1]: " << innerCombos[1] << "\n";
//          cout << "innerCombos[2]: " << innerCombos[2] << "\n";

          if(k == 0) {
            sum = innerCombos[0] * 3 + innerCombos[1] * 1 + innerCombos[2] * 0;
            sumGames = innerCombos[0] + innerCombos[1] + innerCombos[2];
//            cout << "outOfOrder[i]: " << outOfOrder[i] << "\n";
//            cout << "sum: " << sum << "\n";
//            cout << "combos[indexUnselected]: " << combos[indexUnselected] << "\n";
//            cout << "sumGames: " << sumGames << "\n\n";
//            if(outOfOrder[i] == sum) {
//              cout << "\t\tPOSSIBLE totalPoints VALUE" << "\n";
//            }
            if(outOfOrder[i] == sum && combos[indexUnselected] == sumGames) {
              totalPoints = outOfOrder[i];
              matchesWon = innerCombos[0];
              matchesDrew = innerCombos[1];
              matchesLost = innerCombos[2];
//              cout << "k: " << k << "\n";
//              cout << "\tmatchesWon: " << matchesWon << "\n";
//              cout << "\tmatchesDrew: " << matchesDrew << "\n";
//              cout << "\tmatchesLost: " << matchesLost << "\n";
//              cout << "\ttotalPoints: " << totalPoints << "\n";
              PtsFound = 1;
              break;
            }
          }

          if(k == 1) {
            sum = innerCombos[0] * 0 + innerCombos[1] * 3 + innerCombos[2] * 1;
            sumGames = innerCombos[0] + innerCombos[1] + innerCombos[2];
//            cout << "outOfOrder[i]: " << outOfOrder[i] << "\n";
//            cout << "sum: " << sum << "\n";
//            cout << "combos[indexUnselected]: " << combos[indexUnselected] << "\n";
//            cout << "sumGames: " << sumGames << "\n\n";
//            if(outOfOrder[i] == sum) {
//              cout << "\t\tPOSSIBLE totalPoints VALUE" << "\n";
//            }
            if(outOfOrder[i] == sum && combos[indexUnselected] == sumGames) {
              totalPoints = outOfOrder[i];
              matchesWon = innerCombos[1];
              matchesDrew = innerCombos[2];
              matchesLost = innerCombos[0];
//              cout << "k: " << k << "\n";
//              cout << "\tmatchesWon: " << matchesWon << "\n";
//              cout << "\tmatchesDrew: " << matchesDrew << "\n";
//              cout << "\tmatchesLost: " << matchesLost << "\n";
//              cout << "\ttotalPoints: " << totalPoints << "\n";
              PtsFound = 1;
              break;
            }
          }

          if(k == 2) {
            sum = innerCombos[0] * 1 + innerCombos[1] * 0 + innerCombos[2] * 3;
            sumGames = innerCombos[0] + innerCombos[1] + innerCombos[2];
//            cout << "outOfOrder[i]: " << outOfOrder[i] << "\n";
//            cout << "sum: " << sum << "\n";
//            cout << "combos[indexUnselected]: " << combos[indexUnselected] << "\n";
//            cout << "sumGames: " << sumGames << "\n\n";
//            if(outOfOrder[i] == sum) {
//              cout << "\t\tPOSSIBLE totalPoints VALUE" << "\n";
//            }
            if(outOfOrder[i] == sum && combos[indexUnselected] == sumGames) {
              totalPoints = outOfOrder[i];
              matchesWon = innerCombos[2];
              matchesDrew = innerCombos[0];
              matchesLost = innerCombos[1];
//              cout << "k: " << k << "\n";
//              cout << "\tmatchesWon: " << matchesWon << "\n";
//              cout << "\tmatchesDrew: " << matchesDrew << "\n";
//              cout << "\tmatchesLost: " << matchesLost << "\n";
//              cout << "\ttotalPoints: " << totalPoints << "\n";
              PtsFound = 1;
              break;
            }
          }

          if(k == 3) {
            sum = innerCombos[0] * 3 + innerCombos[1] * 0 + innerCombos[2] * 1;
            sumGames = innerCombos[0] + innerCombos[1] + innerCombos[2];
//            cout << "outOfOrder[i]: " << outOfOrder[i] << "\n";
//            cout << "sum: " << sum << "\n";
//            cout << "combos[indexUnselected]: " << combos[indexUnselected] << "\n";
//            cout << "sumGames: " << sumGames << "\n\n";
//            if(outOfOrder[i] == sum) {
//              cout << "\t\tPOSSIBLE totalPoints VALUE" << "\n";
//            }
            if(outOfOrder[i] == sum && combos[indexUnselected] == sumGames) {
              totalPoints = outOfOrder[i];
              matchesWon = innerCombos[0];
              matchesDrew = innerCombos[2];
              matchesLost = innerCombos[1];
//              cout << "k: " << k << "\n";
//              cout << "\tmatchesWon: " << matchesWon << "\n";
//              cout << "\tmatchesDrew: " << matchesDrew << "\n";
//              cout << "\tmatchesLost: " << matchesLost << "\n";
//              cout << "\ttotalPoints: " << totalPoints << "\n";
              PtsFound = 1;
              break;
            }
          }

          if(k == 4) {
            sum = innerCombos[0] * 0 + innerCombos[1] * 1 + innerCombos[2] * 3;
            sumGames = innerCombos[0] + innerCombos[1] + innerCombos[2];
//            cout << "outOfOrder[i]: " << outOfOrder[i] << "\n";
//            cout << "sum: " << sum << "\n";
//            cout << "combos[indexUnselected]: " << combos[indexUnselected] << "\n";
//            cout << "sumGames: " << sumGames << "\n\n";
//            if(outOfOrder[i] == sum) {
//              cout << "\t\tPOSSIBLE totalPoints VALUE" << "\n";
//            }
            if(outOfOrder[i] == sum && combos[indexUnselected] == sumGames) {
              totalPoints = outOfOrder[i];
              matchesWon = innerCombos[2];
              matchesDrew = innerCombos[1];
              matchesLost = innerCombos[0];
//              cout << "k: " << k << "\n";
//              cout << "\tmatchesWon: " << matchesWon << "\n";
//              cout << "\tmatchesDrew: " << matchesDrew << "\n";
//              cout << "\tmatchesLost: " << matchesLost << "\n";
//              cout << "\ttotalPoints: " << totalPoints << "\n";
              PtsFound = 1;
              break;
            }
          }

          if(k == 5) {
            sum = innerCombos[0] * 1 + innerCombos[1] * 3 + innerCombos[2] * 0;
            sumGames = innerCombos[0] + innerCombos[1] + innerCombos[2];
//            cout << "outOfOrder[i]: " << outOfOrder[i] << "\n";
//            cout << "sum: " << sum << "\n";
//            cout << "combos[indexUnselected]: " << combos[indexUnselected] << "\n";
//            cout << "sumGames: " << sumGames << "\n\n";
//            if(outOfOrder[i] == sum) {
//              cout << "\t\tPOSSIBLE totalPoints VALUE" << "\n";
//            }
            if(outOfOrder[i] == sum && combos[indexUnselected] == sumGames) {
              totalPoints = outOfOrder[i];
              matchesWon = innerCombos[1];
              matchesDrew = innerCombos[0];
              matchesLost = innerCombos[2];
//              cout << "k: " << k << "\n";
//              cout << "\tmatchesWon: " << matchesWon << "\n";
//              cout << "\tmatchesDrew: " << matchesDrew << "\n";
//              cout << "\tmatchesLost: " << matchesLost << "\n";
//              cout << "\ttotalPoints: " << totalPoints << "\n";
              PtsFound = 1;
              break;
            }
          }
        }
      }
    }
  }

  matchesPlayed = matchesWon + matchesDrew + matchesLost;

  cout << matchesPlayed << " " << matchesWon << " " << matchesDrew << " " << matchesLost << " " << totalPoints << "\n";

  return 0;
}
