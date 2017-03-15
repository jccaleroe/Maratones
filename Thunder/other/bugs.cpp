#include <bits/stdc++.h>

using namespace std;

vector< vector<int> > graph;
bool seen[2000];
bool colors[2000];

bool dfs (int source){
    if ( seen[source] )
        return true;

    stack<int> s;
    s.push(source);
    seen[source] = 1;
    int u, neighbor;
    bool color = 0;
    colors[source] = color;

    while ( !s.empty() ){
        u = s.top();
        s.pop();
        for ( int i = 0; i < graph[u].size(); i++ ){
            neighbor = graph[u][i];
            if ( !seen[neighbor] ){
                seen[neighbor] = 1;
                colors[neighbor] = !colors[u];
                s.push( neighbor );
            }
            else
                if ( colors[neighbor] == colors[u] )
                    return false;
        }
    }
    return true;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    #ifdef LOCAL
        freopen("input.txt", "r", stdin);
    #endif // LOCAL

    int T, I = 1, bugs, interactions, a, b;

    cin >> T;
    while (T > 0){
        T--;
        cin >> bugs >> interactions;
        graph.assign(bugs, vector<int>());

        for ( int i = 0; i < bugs; i++ ){
            seen[i] = 0;
            colors[i] = 0;
        }

        for ( int i = 0; i < interactions; i++ ){
            cin >> a >> b;
            graph[a-1].push_back(b-1);
            graph[b-1].push_back(a-1);
        }

        bool flag = true;
        for ( int i = 0; i < bugs; i++ ){
            if ( !dfs(i) ){
                flag = false;
                break;
            }
        }

        cout << "Scenario #" << I << ":\n";
        if ( flag )
            cout << "No suspicious bugs found!\n";
        else
            cout << "Suspicious bugs found!\n";
        I++;
    }
}
