addFirst(L, X, [X|L]).

addLast([H|T], X, [H|R]):- addLast(T, X, R).
addLast([], X,[X]).


concat([H|T], B, [H|R]):- concat(T, B, R).
concat([], A, A).

inv(A, R):- inv(A, [], R).
inv([], B, B).
inv([H|T], B, R):- R2 = [H|B], inv(T, R2, R).

divide([], [], [], _).
divide(A, L, R):- divide(A, L, R, 0).
divide([H|T], [H|L], R, 0):- divide(T, L, R, 1).
divide([H|T], L, [H|R], 1):- divide(T, L, R, 0).

merge([],A,A).
merge(A,[],A).
merge([H|T], [H2|T2], [H|C]):- H =< H2, merge(T, [H2|T2], C).
merge([H|T], [H2|T2], [H2|C]):- H > H2, merge([H|T], T2, C).

mergeSort([], []).
mergeSort([X], [X]).
mergeSort(A, B):- divide(A, L, R), mergeSort(L, LO), mergeSort(R, RO), merge(LO, RO, B).

preo([A, B, C], [B|D]):-preo(A, D), preo(C, D).
preo([], []).

preorden(A, L):- preorden(A, [], L).
preorden([], L, L).
preorden([I, R, D], LA, [R|LADI]):- preorden(D, LA, LAD), preorden(I, LAD, LADI).

inorden(A, L):- inorden(A, [], L).
inorden([], L, L).
inorden([I, R, D], LA, L):- inorden(D, LA, LAD), inorden(I, [R|LAD], L).

posorden(A, L):- posorden(A, [], L).
posorden([], L, L).
posorden([I, R, D], LA, L):- posorden(D, [R|LA], LAD), posorden(I, LAD, L).

max(A, B, A):- A >= B.
max(A, B, B):- B > A.

max(A, B, C, A):- A >= B, A >= C.
max(A, B, C, B):- B > A, B >= C.
max(A, B, C, C):- C > B, C > A.


height([A, _, C], R):- height(A, Tmp1), height(C, Tmp2), max(Tmp1, Tmp2, Tmp3), R is Tmp3 + 1.
height([], 0).

peso([], 0).
peso([I, R, D], R):- peso(I, R1), peso(D, R2), R is R1 + R2 + 1.


rol([_, B, _], R):- rol(A, B, R).
rol([A, B, C], ):-

rol([A, B, C], 0, R):-
rol([[], B, []], 1, R):-

maxt([A, B, C], R):- maxt(A, Tmp1), maxt(C, Tmp2), max(Tmp1, Tmp2, R).
maxt([[], B, []], B).














































