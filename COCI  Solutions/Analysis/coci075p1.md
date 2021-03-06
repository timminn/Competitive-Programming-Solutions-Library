This is a pretty straightforward problem.

But to reduce the amount of hardcoding required:

Make a function f(a, b, c) that gives the right operator such that (a oper b) = c.

This can be implemented pretty simply:

```cpp
char f(int a, int b, int c)
{
	if (a+b == c) return '+';
	if (a-b == c) return '-';
	if (a == c*b) return '/'; //cross-multiplying is nice
	if (a*b == c) return '*';
	return 0; //nothing found
}
```

So, in our main program all we have to do is:

```cpp
int a, b, c;
scanf("%d %d %d", &a, &b, &c);
if (f(a,b,c))
	printf("%d%c%d=%d", a, f(a,b,c), b, c); //printf is more convenient here
else
	printf("%d=%d%c%d", a, b, f(b,c,a), c);
```
