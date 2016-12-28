#include<bits/stdc++.h>
#define TAM 500000 + 7

using namespace std;

typedef long long ll;

char c[TAM];
int l[TAM];
int r[TAM];
int in[TAM];
ll balls[TAM];
queue<int> Q;

void reset(){
    memset(l, 0, sizeof l);
    memset(r, 0, sizeof r);
    memset(in, 0, sizeof in);
    memset(balls, 0, sizeof balls);
    c[0] = 'L';
}

void update(int u){
    if(balls[u] & 1){
        if(c[u] == 'L'){
            balls[l[u]] += (balls[u]/2) + 1;
            balls[r[u]] += (balls[u]/2);
            c[u] = 'R';
        }
        else{
            balls[r[u]] += (balls[u]/2) + 1;
            balls[l[u]] += (balls[u]/2);
            c[u] = 'L';
        }
    }
    else{
        balls[l[u]] += balls[u]/2;
        balls[r[u]] += balls[u]/2;
    }
}

void topo(vector<vector<int> > G, int u){
    while(!Q.empty()){
        u = Q.front();Q.pop();
        update(u);
        for(int i = 0; i <G[u].size(); i++){
            int v = G[u][i];
            in[v]--;
            if(in[v] == 0) Q.push(v);
        }
    }
}

int main(){
    ios_base::sync_with_stdio(0);
    cin.tie(0);
    #ifdef LOCAL
    freopen("in.txt","r", stdin);
    #endif
    ll n;
    int m;
    while(cin>>n>>m){
        reset();
        vector< vector<int> > G(m + 1);

        for(int i = 1; i <=m; i++){
            int u,v;
            cin>>c[i]>>u>>v;
            G[i].push_back(u);
            G[i].push_back(v);
            in[u] ++; in[v]++;
            l[i] = u;r[i] = v;
        }
        for(int i = 1; i <=m; i++){
            if(in[i] == 0)Q.push(i);
        }
        balls[1] = n;
        topo(G, 1);
        for(int i = 1; i <= m; i++) printf("%c",c[i]);
        printf("\n");
    }
}
