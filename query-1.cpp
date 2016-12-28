//Queries and build takes O(log n)
//You are given a sequence A[1], A[2], ..., A[N] . ( |A[i]| ≤ 15007 , 1 ≤ N ≤ 50000 ).
//Query(x,y) = Max { a[i]+a[i+1]+...+a[j] ; x ≤ i ≤ j ≤ y }.
#include<bits/stdc++.h>

using namespace std;

int p[60000];

typedef struct {
	int pre, su, sum, maxs;
} node;

node solve(node a, node b){
	node result;
	result.sum = a.sum + b.sum;
	result.pre = max(a.pre, a.sum + b.pre);
	result.su = max(b.su, b.sum + a.su);
	result.maxs = max(result.pre,
			max(result.su,
				max(a.maxs,
					max(b.maxs, a.su + b.pre))));
	return result;

}

struct SegmentTree{
    SegmentTree *L, *R;
    node ans;
    int l, r;

    node query(int a, int b){
        if(a == l && b == r) return ans;
        if(b <= L->r) return L->query(a,b);
        if(a >= R->l) return R->query(a,b);

	node left = query(a, L->r);
	node right = query(R->l, b);

	return solve(left, right);
    }

    SegmentTree(int a, int b): l(a), r(b){
        if(a == b){
            ans.pre = ans.su = ans.sum = ans.maxs = p[a];
            L = R = nullptr;
        }
        else{
            int mid = (a + b)/2;
            L = new SegmentTree ( a, mid );
            R = new SegmentTree ( mid + 1, b );

	    ans= solve(L->ans, R->ans);
    	}
    }
};

int main(){
	int T;
	cin >> T;
	for(int i = 0; i < T; i++)
		cin >> p[i];
	SegmentTree *stree = new SegmentTree(0, T-1);
	int N;
	cin >> N;
	int I, J;
	while(N --){
		cin >> I >> J;
		node aux = stree -> query( I-1, J-1 );
		printf("%d\n", aux.maxs);
	}
}
