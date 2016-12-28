esvacia([]).
pertenece(X, [X|_]).
pertenece(X, [_|T]):- pertenece(X,T).

sum([], 0).
sum([H|T], X):- sum(T, X1), X is H + X1.

size([], 0).
size([_|T], X):- size(T, X1), X is X1 + 1.

mean(L, X):- meanF(L, 0, 0, X).
meanF([], Sum, Size, X):- Size > 0, X is Sum/Size.
meanF([H|T], Sum, Size, X):- Sum1 is H + Sum, Size1 is Size + 1, meanF(T, Sum1, Size1, X).

append(L, X, Y):- Y = [L|X].

inv(L, R):- inv(L, [], R).
inv([H|T], C, R):- R1 = [H|C], inv(T, R1, R).
inv([], C, C).

divide(X, L, R):- divide(X, L, R, 0).
divide([], [], [], _).
divide([H|T], [H|L], R, 0):- divide(T, L, R, 1).
divide([H|T], L, [H|R], 1):- divide(T, L, R, 0).

fusion([], []).
fusion([X], [X]).
fusion(L, O):- divide(L, I, D), fusion(I, OI), fusion(D, OD), mezclar(OI, OD, O).
mezclar([], L, L).
mezclar(L, [], L).
mezclar([H1|L1], [H2|L2], [H1|M]):- H1 =< H2, mezclar(L1, [H2|L2], M).
mezclar([H1|L1], [H2|L2], [H2|M]):- H1 > H2, mezclar([H1|L1], L2, M).












