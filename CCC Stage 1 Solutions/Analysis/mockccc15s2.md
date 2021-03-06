Approach 1 – Dilworth's Theorem
We found this to be a oddly challenging question for S2. Since this problem can be reduced to finding the width of a 
partially ordered set, we can simply apply Dilworth's theorem and use bipartite matching to find the longest antichain. 
Bipartite matching is solvable using max-flow, and we can use Dinic's blocking flow algorithm for optimal speed. To get even more marks 
we can condense several nodes of the same value into the same node, speeding up our algorithm slightly for cases where there are lots of
repeated values. You can read more about Dilworth's theorem, partially ordered sets, reduction from bipartite matching to max-flow from 
the following links:

1. Dilworth's theorem: [http://en.wikipedia.org/wiki/Dilworth%27s_theorem](http://en.wikipedia.org/wiki/Dilworth%27s_theorem)
2. Partially ordered sets: [http://en.wikipedia.org/wiki/Partially_ordered_set](http://en.wikipedia.org/wiki/Partially_ordered_set)
3. Maximum flow problems: [http://en.wikipedia.org/wiki/Maximum_flow_problem#Application](http://en.wikipedia.org/wiki/Maximum_flow_problem#Application)
4. Dinic's Algorithm: [http://www.slideshare.net/KuoE0/acmicpc-dinics-algorithm](http://www.slideshare.net/KuoE0/acmicpc-dinics-algorithm)
5. Dinic's Algorithm (slideshare): [http://www.slideshare.net/KuoE0/acmicpc-dinics-algorithm](http://www.slideshare.net/KuoE0/acmicpc-dinics-algorithm)
6. Max-flow min-cut related problems: [http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=maxFlow2](http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=maxFlow2)

Approach 2 – Greedy Algorithm
The above approach is only enough to get 40% of the points, but a cleverer observation is required to get more marks. 
Consider the second sample input sequence: ```{3, 2, 2, 1, 3, 3}```. We can sort it in descending order to get the sequence 
```{3, 3, 3, 2, 2, 1}```. Then, we can combine equivalent groups of values to pairs of (value, frequency) to get a list 
```{(3, 3), (2, 2), (1, 1)}```. After performing such a transformation on our input, it is now possible to apply the following greedy 
algorithm:

Loop through the list from beginning to end. For each pair that has not been crossed out:
Decrease its frequency by 1.
If its frequency is now 0, cross it off the list.
Repeat step 1 until the list is empty.
The number of iterations performed is the answer to the question. However, as removing from an array requires O(N) time in the worst 
case, the overall algorithm is O(N<sup>2</sup>). Again, this is too slow.

If we use a balanced binary search tree (an std::set or std::map in C++) to represent our list, crossing an element off the list 
can be done in O(log N) time. It is guaranteed that the sum of all frequencies in the pairs do not exceed the size of the input, 
so we iterate our outer loop no more than N times, for an overall running time of O(N log N). Using an actual linked list 
```(std::list in C++)``` will make splicing elements performable in constant time, making the overall running time just O(N). 
Either of these methods will be able to obtain 100% of the points. The proof for why this greedy algorithm works is left as an exercise 
for the reader.

Approach 3 – Magic
Just count how many times the mode appears, you nincompoop.

```cpp
#include <iostream>
using namespace std;

int N, x, ans, a[100005];

int main() {
  cin >> N;
  for (int i = 0; i < N; i++) {
    cin >> x;
    if (++a[x] > ans) ans = a[x];
  }
  cout << ans << endl;
  return 0;
}
```
