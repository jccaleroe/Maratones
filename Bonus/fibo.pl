fibo(0,0).
fibo(1,1).
fibo(X,Y):- X > 1, fibo2(X, 2, 0, 1, Y).
fibo2(N, I, _, B, B):-  I > N.
fibo2(N, I, A, B, Y):- I2 is I+1, C is A + B, fibo2(N, I2, B, C, Y).

