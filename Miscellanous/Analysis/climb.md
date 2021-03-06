Solution
Convince yourself that the following are true:

If the gap between two adjacent holds, or the bottommost hold and the ground, is longer than 2M metres, then it is impossible for Capba to 
ascend the rock.

If the gap between two adjacent holds, or the bottommost hold and the ground, is longer than M metres, but not longer than 2M metres, 
then Capba must use an extremely physics-defying leap to cross it.

Furthermore, one extremely physics-defying leap can only cross one such gap. Capba cannot use an extremely 
physics-defying leap to cross two gaps that are each longer than M metres, because then their combined length would be more than 2M 
metres (and an extremely physics-defying leap has length 2M metres).

Based on the two above properties, the minimum number of extremely physics-defying leaps Capba needs to ascend the rock is exactly the 
number of gaps that are longer than M metres (but not longer than 2M metres). Therefore, if this number is greater than E, Capba does 
not have enough energy to ascend the rock, but if it is less than or equal to E, then Capba does have enough energy.
The number of gaps that have length M metres or less is not relevant.

These observations make the problem quite easy. All we have to do is to read in all the heights, which, according to the problem 
statement, come pre-sorted, and find the difference between the height of each hold and the height of the previous one 
(or the height of the hold and zero, for the lowest hold). If any of these have height greater than 2M, we are screwed. If not, 
we count the number of differences greater than M, and make our decision accordingly.

## Implementation

```cpp
#include <cstdio>
using namespace std;

int main()
{
    int n,m,e,p=0,c;
    scanf("%d%d%d",&n,&m,&e);
    while (n--)
    {
        scanf("%d",&c);
        if (c-p>m)
            if ((e) && (c-p<=2*m))
                e--;
            else
            {
                printf("Unfair!\n");
                return(0);
            }
        p=c;
    }
    printf("Too easy!\n");
    return(0);
}
```

## Complexity
This solution runs in linear time, as it has only a single loop over the input values.
