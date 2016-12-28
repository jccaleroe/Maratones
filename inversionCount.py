import sys

global n
n = 0

def merge(A, p, q, r):
    L = A[p:q+1]+[float('inf')]
    R = A[q+1:r+1]+[float('inf')]
    i = 0
    j = 0
    global n
    for k in range(p, r+1):
        if L[i] <= R[j]:
            A[k] = L[i]
            i += 1
        else:
            A[k] = R[j]
            j += 1
            n = n + (q-p+1) - i

def mergeMain(A, p, r):
    if p < r:
        q = int((p+r) / 2.0)
        mergeMain(A, p, q)
        mergeMain(A, q+1, r)
        merge(A, p, q, r)

def merge_sort(A):
    mergeMain(A, 0, len(A)-1)


T = int(sys.stdin.readline())
for g in range(T):
    sys.stdin.readline()
    e = int(sys.stdin.readline())
    l = []
    for h in range(e):
        l.append(int(sys.stdin.readline()))
    merge_sort(l)
    print(n)
    n = 0
    