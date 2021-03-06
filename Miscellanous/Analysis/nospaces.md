This problem is best solved using brute force. For each pair of adjacent digits in the input, we have a choice between either inserting 
a plus sign between them or not inserting a plus sign. After trying every single possibility and determining what sum results, 
choose the most probable one; if there is a tie, then choose the lowest.

There are still several approaches to the implementation. One of the simplest is to make a copy of the input string, 
actually insert plus signs in some of the locations, and then use the language eval() command, or something similar, 
to evaluate the expression. Another one is to split the resulting string at all the plus signs, giving a collection of addends; 
each of these can simply then be converted from a string to an integer, and all results added. After collecting all the results, 
we need to determine which ones occur most frequently. This can be done by sorting them, so that equal results occur consecutively 
in the sorted array; a slower but easier approach is to store the count of each result in a dictionary data structure. 
(A normal array will not work because the results may be very large numbers.)

## Implementation

The following code is by Brian Bi. It uses a bitmask in order to generate all 2<sup>(N-1)</sup> possibilities for where to insert or not to 
insert a plus sign (where N is the number of digits in the input). Each potential position for an insertion is assigned a bit, and 
when that bit is one, a plus sign will be inserted there, and when it is zero, a bit will not be inserted; since the counter ranges 
from 0 to 2N-1-1, each combination of bits will occur exactly once, so each combination of plus signs will occur exactly once. 
The bitmask loop actually constructs the string by inserting plus signs, then uses the eval() function to actually evaluate the sum. 
This function is recursive, where the base case is an expression without a plus sign; any case with a plus sign is handled by peeling 
off the first addend then recursively evaluating the rest of the expression, and adding. The stringstream object is used to convert 
strings to integers; note that we must use a 64-bit integer type to avoid overflow. A map is used to store the counts of results.

```cpp
#include <iostream>
#include <cstdio>
#include <string>
#include <sstream>
#include <map>
using namespace std;
int eval(string s)
{
    istringstream sin(s);
    int x; sin >> x;
    int blah=s.find_first_of("+");
    if (blah==string::npos)
        return x;
    else
        return x + eval(s.substr(blah+1));
}
int main()
{
    string s;
    cin >> s;
    int i,j;
    int N=s.length();
    string lol;
    map<int,int> M;
    for (i=0; i<(1<<(N-1)); i++)
    {
        lol="";
        for (j=0; j<N-1; j++)
        {
            lol+=s[j];
            if (i&(1<<j))
                lol+="+";
        }
        lol+=s[N-1];
        M[eval(lol)]++;
    }
    int num=M.size();
    int res=0;
    for (map<int,int>::iterator It=M.begin(); It!=M.end(); ++It)
        if (It->second>M[res])
            res=It->first;
    printf("%d%d\n",num,res);
    return 0;
}
```

## Complexity
There are 2N-1 possible expressions to generate, and each of these takes presumably O(N) time to construct and evaluate.
We must also insert each one into the dictionary, or sort, in each case taking on the order of (2<sup>N</sup>-1)(log (2<sup>N</sup>-1)) operations. 
So each part takes O(N·2N) time, which is therefore the overall complexity. Even though this solution takes exponential time, it is 
acceptable because the input size is so low.
