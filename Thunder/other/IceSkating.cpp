//
// Created by juan on 28/02/17.
//

#include <bits/stdc++.h>

using namespace std;

vector< vector<int> > g(100, vector<int>());
int coor[100];
bool seen[100];

void bfs(int u){
    queue<int> q;
    seen[u] = 1;
    q.push(u);
    int node;
    while(!q.empty()){
        node = q.front();
        q.pop();
        for(int i = 0; i < g[node].size(); i++){
            if(!seen[g[node][i]]){
                seen[g[node][i]] = 1;
                q.push(g[node][i]);
            }
        }
    }
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int N;
    cin >> N;
    int x, y, x1, y1, u, r = 0;
    for(int c = 0; c < N; c++){
        cin >> x >> y;
        u = x + 1024*y;
        coor[c] = u;
        for(int i = 0; i < c; i++){
            x1 = coor[i] & 1023;
            y1 = coor[i] >> 10;
            if(x1 == x || y1 == y){
                g[c].push_back(i);
                g[i].push_back(c);
            }
        }
    }
    bfs(0);
    for(int i = 1; i < N; i++){
        if(!seen[i]) {
            r++;
            bfs(i);
        }
    }
    cout << r << endl;
}