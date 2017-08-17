#include<bits/stdc++.h>

using namespace std;

int p[1000001];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL
    int n, k;
    cin >> n;

    for(int i = 1; i <= n; i++)
        cin >> p[i];

    cin >> k;
    priority_queue<int> pq;
    priority_queue<int> q;

    for(int i = 1; i <= n; i++){
        pq.push(p[i]);
        if(i == k){
            cout << pq.top() << " ";
        }
        else if(i > k){
            q.push(p[i-k]);
            while(!q.empty() && pq.top() == q.top()){
                pq.pop();
                q.pop();
            }
            cout << pq.top() << " ";
        }
    }
}
