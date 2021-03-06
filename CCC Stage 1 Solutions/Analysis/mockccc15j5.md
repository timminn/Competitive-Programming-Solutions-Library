The problem can be restated as follows: given N points and M−1 line segments parallel to the axes, 
determine the number of points that lie on each line segment, and find the sum of these numbers across all of the segments.

The most naive solution would be to actually place the points in a 2D Boolean array and loop one row/column at a time to 
exactly simulate the Royal Guard's path, counting the number of buildings we encounter. Since the coordinates can get as large as 109, 
we know it's not feasible because a 109×109 Boolean array, even using optimized bitsets, will take 1018 bits ≈ 125 petabytes of memory. 
However, in times of desperation, one can try to limit the array size to around 10000×10000 and just let the bigger cases go out of 
bounds. Correctly implementing this method will receive between 10% and 30% of the marks, depending on the constant efficiency of the 
implementation.

A slightly smarter brute force would be to, for each line segment, loop over all of the N points and count the number of them that 
lie on the segment. A point (x, y) lies on a horizontal segment with endpoints (x1, y1) and (x2, y2) if and only if y = y1 = y2 and x 
lies between x1 and x2. Similarly, A point (x, y) lies on a vertical segment with endpoints (x1, y1) and (x2, y2) i
f and only if x = x1 = x2 and y lies between y1 and y2. Since there are N points to check for each of the M−1 segments, 
so the running time is O(NM). This straightforward idea will get 60% of the points.

60% Solution – Brute Force (C++)
```cpp
#include <algorithm>
#include <cstdio>
using namespace std;

int N, M, x1, y1, x2, y2;
int x[100005], y[100005];
long long ans = 0;

int main() {
  scanf("%d", &N);
  for (int i = 0; i < N; i++) scanf("%d%d", &x[i], &y[i]);
  scanf("%d%d%d", &M, &x2, &y2);
  for (int i = 0; i < M - 1; i++) {
    x1 = x2, y1 = y2;
    scanf("%d%d", &x2, &y2);
    for (int j = 0; j < N; j++) {
      if (x1 == x[j] && x2 == x[j] && min(y1, y2) <= y[j] && y[j] <= max(y1, y2)) ans++;
      if (y1 == y[j] && y2 == y[j] && min(x1, x2) <= x[j] && x[j] <= max(x1, x2)) ans++;
    }
  }
  printf("%Ld\n", ans);
  return 0;
}
```
To get full marks, we can introduce a technique called coordinate compression. Essentially, we want to reduce the magnitude of the x 
and y coordinates for all of the points while preserving their relative values. Consider only the 1D case where we have a list of 
numbers, say {1598, 2004, 4853, 2004, 1598, 9001}, and we would like to use smaller values to represent them. We can first remove 
repeated elements and sort them: {1598, 2004, 4853, 9001}. Now, we loop and assign the numbers 1, 2, 3, and 4 to the sorted values. 
Go back to the original array and replace the large values with the repeated mappings to get {1, 2, 3, 2, 1, 4}. We have successfully 
applied coordinate compression on our original array – their relative orders are preserved! Obviously, the magnitude of each number 
after compression is no greater than the size of the original list. This can be done in C++ with an std::map or the std::unique function. 
Applying coordinate compression to 2D coordinates is as easy as compressing all the x and y values separately. 
Note that we must consider all x- and y- coordinates in question – both of the building locations and the turning points.

Now that we have reduced the magnitude of all x- and y-coordinates from 109 down to 200000 (a max of 100000 building points and 
100000 turning points), we are now allowed to use x and y values as array indices. More specifically, we can store an array of lists, 
indexed by x coordinates. Construct an array h[] to store the list of horizontal, or x values at a given y-value. That is, h[p] will 
store a list of x values on the horizontal line y = p. Given the points (2, 1), (2, 5), (2, 7), h[2] should store the list {1, 5, 7}. 
Similarly, keep an array v where v[q] stores a list of x coordinates on the vertical line x = q.

Every time we want to count the number of points on a horizontal line segment at y = p, we access h[p] (a constant time operation) 
and count the number of values in that list that are between the y range of the line segment. Similarly, we use v[] for vertical line 
segments. How do we count the number of values? Well, we can just loop through linearly and count them. This is enough to get over 90% 
of the points.

For a full solution, we must sort all of the lists in h[] and v[] and use binary search to find the lower and upper position of the 
range in our list. The inclusive difference between the two positions is the number of buildings on that line segment. 
In C++, binary search is built-in to two standard library functions: std::lower_bound and std::upper_bound.

There are M segments and each will require two binary searches. Each binary search is proportional to the logarithm of the 
magnitude of a compressed coordinate, so the worst case running time is theoretically O(M log(N + M)). 
The following is a slightly long-winded solution – first compressing the points using a map and then using binary search on the two 
arrays of lists (indexed by x and y respectively).

100% Solution - Coordinate Compression and Binary Search (C++)
```cpp
#include <algorithm>
#include <cstdio>
#include <map>
#include <vector>
using namespace std;

int N, M, bx[100005], by[100005], tx[100005], ty[100005];
map<int, int> xm, ym;
map<int, int>::iterator it;
vector<int> v[200005], h[200005];

int cnt(int x1, int y1, int x2, int y2) {
  if (x1 == x2) {
    if (y1 > y2) swap(y1, y2);
    return upper_bound(v[x1].begin(), v[x1].end(), y2) -
           lower_bound(v[x1].begin(), v[x1].end(), y1);
  }
  if (x1 > x2) swap(x1, x2);
  return upper_bound(h[y1].begin(), h[y1].end(), x2) -
         lower_bound(h[y1].begin(), h[y1].end(), x1);
}

int main() {
  scanf("%d", &N);
  for (int i = 0; i < N; i++) {
    scanf("%d%d", bx + i, by + i);
    xm[bx[i]] = ym[by[i]] = 0;
  }
  scanf("%d", &M);
  for (int i = 0; i < M; i++) {
    scanf("%d%d", tx + i, ty + i);
    xm[tx[i]] = ym[ty[i]] = 0;
  }
  it = xm.begin();
  for (int i = 0; it != xm.end(); it++) it->second = i++;
  it = ym.begin();
  for (int i = 0; it != ym.end(); it++) it->second = i++;
  for (int i = 0; i < N; i++) {
    h[ym[by[i]]].push_back(xm[bx[i]]);
    v[xm[bx[i]]].push_back(ym[by[i]]);
  }
  for (int i = 0; i <= 200001; i++) {
    sort(h[i].begin(), h[i].end());
    sort(v[i].begin(), v[i].end());
  }
  long long ans = 0;
  for (int i = 1; i < M; i++)
    ans += cnt(xm[tx[i-1]], ym[ty[i-1]], xm[tx[i]], ym[ty[i]]);
  printf("%Ld\n", ans);
  return 0;
}
```
Finally, we observe that we don't even need to index the coordinates by x- or y-, as long as we sort them by x, then by y when x's are 
equal for h[] (by y, then by x when y's are equal for v[]). This way, we can just binary search on the endpoints of the segment and 
achieve the same result. Theoretically, the complexity is still the same O(M log(N + M)). Since we don't have to access points by array 
indices, we eliminate the need to compress them in the first place! The slightly longer logarithmic time spent on binary searching
probably counterbalances the original linearithmic time to coordinate compress, making it even faster in practice. 
The following is an extremely concise official solution.

```cpp
#include <algorithm>
#include <cstdio>
#include <vector>
using namespace std;

int N, M, x, y, x2, y2;
vector< pair<int, int> > h, v;
long long ans = 0;

bool compy(const pair<int, int> & l, const pair<int, int> & r) {
  if (l.second != r.second) return l.second < r.second;
  return l.first < r.first;
}

int main() {
  scanf("%d", &N);
  for (int i = 0; i < N; i++) {
    scanf("%d%d", &x, &y);
    h.push_back(make_pair(x, y));
  }
  v = h;
  sort(h.begin(), h.end());
  sort(v.begin(), v.end(), compy);
  scanf("%d%d%d", &M, &x2, &y2);
  for (int i = 0; i < M - 1; i++) {
    x = x2, y = y2;
    scanf("%d%d", &x2, &y2);
    if (x == x2) {
      ans += upper_bound(h.begin(), h.end(), make_pair(x, max(y, y2))) -
             lower_bound(h.begin(), h.end(), make_pair(x, min(y, y2)));
    } else {
      ans += upper_bound(v.begin(), v.end(), make_pair(max(x, x2), y), compy) -
             lower_bound(v.begin(), v.end(), make_pair(min(x, x2), y), compy);
    }
  }
  printf("%Ld\n", ans);
  return 0;
}
```
