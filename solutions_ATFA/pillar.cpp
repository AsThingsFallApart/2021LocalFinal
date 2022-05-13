#include <iostream>
#include <algorithm>
using namespace std;

struct building {
  int bottomLeftXCoord;
  int width;
  int height;
};

// compute the distance travelled by Lulu:
// from (0,0) to some building at (x, 0), up the building to (x,y),
// across the roof to (x + buildingLength, y), down the side to (x + buildingLength, y * 2).

int main() {

  int countBuildings = 0;
  cin >> countBuildings;

  struct building buildings[countBuildings];
  struct building buildingsSorted[countBuildings];

  int totalDistanceTravelled = 0;

  int i = 0;
  int j = 0;
  int distanceBetweenBuildings = 0;
  int runningXCoord = 0;
  int minimum = 9999;
  int tempWidth = 0;
  int tempHeight = 0;
  int index = 0;
  bool adjacentBuildings = false;

  // populate buildings array
  for(i = 0; i < countBuildings; i++) {
    cin >> buildings[i].bottomLeftXCoord;
    cin >> buildings[i].width;
    cin >> buildings[i].height;
  }

//  cout << "countBuildings: " << countBuildings << "\n";
//
//  for(i = 0; i < countBuildings; i++) {
//    cout << "\tbuildings[" << i << "].bottomLeftXCoord: " << buildings[i].bottomLeftXCoord << "\n";
//    cout << "\tbuildings[" << i << "].width: " << buildings[i].width << "\n";
//    cout << "\tbuildings[" << i << "].height: " << buildings[i].height << "\n\n";
//  }

  // insert sort buildings array based on 'bottomLeftXCoord'
  for(i = 0; i < countBuildings; i++) {
    for(j = 0; j < countBuildings; j++) {
      if(buildings[j].bottomLeftXCoord < minimum) {
        minimum = buildings[j].bottomLeftXCoord;
        tempWidth = buildings[j].width;
        tempHeight = buildings[j].height;
        index = j;
      }
    }
    buildings[index].bottomLeftXCoord = 9999;
    buildingsSorted[i].bottomLeftXCoord = minimum;
    buildingsSorted[i].width = tempWidth;
    buildingsSorted[i].height = tempHeight;
    minimum = 9999;
  }

//    for(i = 0; i < countBuildings; i++) {
//    cout << "\tbuildingsSorted[" << i << "].bottomLeftXCoord: " << buildingsSorted[i].bottomLeftXCoord << "\n";
//    cout << "\tbuildingsSorted[" << i << "].width: " << buildingsSorted[i].width << "\n";
//    cout << "\tbuildingsSorted[" << i << "].height: " << buildingsSorted[i].height << "\n\n";
//  }

  if(countBuildings == 1) {
    // distance to first building
    totalDistanceTravelled += buildingsSorted[0].bottomLeftXCoord;
//    cout << "totalDistanceTravelled: " << totalDistanceTravelled << "\n";

    // go up the left wall of the building
    totalDistanceTravelled += buildingsSorted[0].height;
//    cout << "totalDistanceTravelled: " << totalDistanceTravelled << "\n";

    // go across the roof
    totalDistanceTravelled += buildingsSorted[0].width;
//    cout << "totalDistanceTravelled: " << totalDistanceTravelled << "\n";

    // go down the right wall of the building
    totalDistanceTravelled += buildingsSorted[0].height;
//    cout << "totalDistanceTravelled: " << totalDistanceTravelled << "\n";

    // go the rest of the way to (100, 0)
    totalDistanceTravelled += 100 - (buildingsSorted[0].bottomLeftXCoord + buildingsSorted[0].width);

    cout << totalDistanceTravelled << "\n";
  }
  else {
    // by this point in the code,
    // it is guaranteed there is greater than one building

    // distance to first building
    totalDistanceTravelled += buildingsSorted[0].bottomLeftXCoord;

    // go up the left wall of the building
    totalDistanceTravelled += buildingsSorted[0].height;

    // go across the roof
    totalDistanceTravelled += buildingsSorted[0].width;


    for(i = 1; i < countBuildings; i++) {
      // calculate change in x between buildings
      distanceBetweenBuildings = buildingsSorted[i].bottomLeftXCoord - (buildingsSorted[i - 1].bottomLeftXCoord + buildingsSorted[i - 1].width);
      totalDistanceTravelled += distanceBetweenBuildings;

      // calculate change in y between buildings
      if(distanceBetweenBuildings > 0) {
        // go down the right wall of the previous building to y = 0
        // which means add height of old building to totalDistance
        totalDistanceTravelled += buildingsSorted[i - 1].height;

        // then go up left wall of current building
        totalDistanceTravelled += buildingsSorted[i].height;
      }
      else {
        // buildings are adjacent and distance should be absolute difference
        // of previous building's height and current building's height
        totalDistanceTravelled += abs(buildingsSorted[i].height - buildingsSorted[i - 1].height);
      }

      // whatever happens, go across the roof
      totalDistanceTravelled += buildingsSorted[i].width;
    }

    // after the loop, the only distances unaccounted for should be the
    // right wall of the last building and any distance between the right side
    // of the last building and (100, 0)
    totalDistanceTravelled += buildingsSorted[countBuildings -1].height;
    totalDistanceTravelled += 100 - (buildingsSorted[countBuildings - 1].bottomLeftXCoord + buildingsSorted[countBuildings - 1].width);

    cout << totalDistanceTravelled << "\n";
  }

  return 0;
}
