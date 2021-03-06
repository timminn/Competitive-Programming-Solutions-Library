The small bounds hint at this being an exponential dynamic programming problem. Since R and C are individually less than or equal to 16 
but their product is too large to be the parameter for an exponential DP, this suggests that the DP might proceed one row (or column) at a
time, where we only have to remember which pies were eaten on the previous row, in order to compute the DP table for the current row.

In particular, this is indeed the case: on row i, if we already know the exact set of pies on row i-1 that have been eaten, then we can 
determine which possible subsets of the pies on row i we are able to eat: any subset that has no pie in the same column as one of the 
eaten pies on the previous row, and has no two pies right next to each other in the row. So we can create a two-dimensional DP, where 
DP[i][mask] stores the maximum possible sum of tastiness obtainable if we eat some subset of pies in rows i or less and the subset of pies
in the ith row eaten is given by mask; and additionally stores the number of ways to achieve this maximum sum (modulo 10<sup>9</sup>+7, of course).

Now the base case is given by i=0 (or 1, depending on how you label the first row). In this row, all we have to do is enumerate all 
allowable subsets of the pies that we can eat, and, for each one, directly compute the total tastiness and the mask. 
Then we just set DP[0][mask] to (t, 1), where t is the total tastiness of this subset.

The transition is somewhat trickier to do. The idea of course is that for every valid bitmask for the current row, we want to look at all 
possible bitmasks in the previous row that are compatible with the one chosen for this row, in the sense defined earlier. 
We then combine all the DP values for compatible bitmasks in the previous row. To combine two (total, count) pairs is easy: 
if one has a greater total than the other, then the combined pair is just that pair itself. If the two have equal totals, 
then the combined pair has the same total, but its count is the sum of the two individual counts (reduced modulo 10<sup>9</sup>+7). 
But naive techniques for computing the transition may result in a complexity of 4<sup>C</sup> or 3<sup>C</sup>, both of which are too slow. One technique that 
works is to precompute a list of all bitmasks that have no two ones adjacent. There are Fc+2 of these, where Fi denotes a Fibonacci number.
Then, when performing a transition, consider only those bitmasks on both the current and previous rows. This gives a complexity of ϕ2C or 
about 2.6<sup>C</sup>, which is able to pass.
