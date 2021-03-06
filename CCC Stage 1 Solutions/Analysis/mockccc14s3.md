The technique that is needed here is a stable sort, which sorts data but preserves the relative order of equivalent elements. 
Many languages support stable sort natively. In C++, std::stable_sort() is found in <algorithm>. In Java, Arrays.sort() uses 
merge sort, which is stable. In the implementation below, we store the rows as vectors so they can be swapped easily. 
To process the sorts, we define a special compare that only examines rows by the column that's being clicked at the time.

```cpp
// std::stable_sort()
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int R, C, N, col;
vector<int> rows[101];

bool comp(const vector<int> &v1,
          const vector<int> &v2) {
  return v1[col] < v2[col];
}

int main() {
  cin >> R >> C;
  for (int r = 0; r < R; r++) {
    for (int c = 0; c < C; c++) {
      cin >> col;
      rows[r].push_back(col);
    }
  }
  cin >> N;
  for (int i = 0; i < N; i++) {
    cin >> col;
    col--;
    stable_sort(rows, rows + R, comp);
  }
  for (int r = 0; r < R; r++) {
    for (int c = 0; c < C; c++) {
      if (c > 0) cout << " ";
      cout << rows[r][c];
    }
    cout << endl;
  }
  return 0;
}
```
Even if we did not know about std::stable_sort(), we can easily implement an O(N<sup>2</sup>) bubble sort, which is stable and will run in 
time since N ≤ 100.

```cpp
//Bubble Sort
#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int R, C, N, col;
vector<int> rows[101];

int main() {
  cin >> R >> C;
  for (int r = 0; r < R; r++) {
    for (int c = 0; c < C; c++) {
      cin >> col;
      rows[r].push_back(col);
    }
  }
  cin >> N;
  for (int i = 0; i < N; i++) {
    cin >> col;
    col--;
    //Bubble sort
    bool swapped;
    do {
      swapped = false;
      for (int a = 1; a < R; a++)
        if (rows[a-1][col] > rows[a][col]) {
          swap(rows[a-1], rows[a]);
          swapped = true;
        }
    } while (swapped);
  }
  for (int r = 0; r < R; r++) {
    for (int c = 0; c < C; c++) {
      if (c > 0) cout << " ";
      cout << rows[r][c];
    }
    cout << endl;
  }
  return 0;
}
```
