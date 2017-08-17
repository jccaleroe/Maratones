#include<bits/stdc++.h>

using namespace std;

vector< vector <int> > g(6, vector<int>());
stack<int> s;
bool seen[6];

void dfs (int u){
    seen[u] = true;
    for ( int i = 0; i < g[u].size(); i++)
        if ( !seen[ g[u][i] ] )
            dfs(g[u][i]);
    s.push(u);
}

int main(){
    g[2].push_back(3);
    g[3].push_back(1);
    g[4].push_back(1);
    g[4].push_back(0);
    g[5].push_back(0);
    g[5].push_back(2);

    for ( int i = 0; i < 6; i++)
        if ( !seen[i] )
            dfs(i);


    while (!s.empty()){
        int a = s.top();
        s.pop();
        cout << a << " ";
    }
    cout << endl;
}
