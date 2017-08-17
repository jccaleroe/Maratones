#include<bits/stdc++.h>

using namespace std;

int M, W;
int graph[98][98];
bool seen[98];
int parent[98];
int t;

bool bfs(){
    memset( seen, 0, sizeof( seen ) );
    memset( parent, 0, sizeof( parent ) );

    queue<int> q;
    q.push(0);
    seen[0] = 1;
    parent[0] = -1;

    while ( !q.empty() ){
        int u = q.front();
        q.pop();
        for ( int i = 0; i < M*2-2; i++){
            if ( !seen[i] && graph[u][i] > 0){
                parent[i] = u;
                if ( i == t ) return true;
                seen[i] = 1;
                q.push(i);
            }
        }
    }
    return false;
}

int fordFulkerson(int s){
    int u, v;
    int max_flow = 0;

    while ( bfs() ){
        //cout << "lol" << endl;
        int path_flow = INT_MAX;

        for ( v = t; v != s; v = parent[v] ){
            u = parent[v];
            path_flow = min(path_flow, graph[u][v]);
        }

        for ( v = t; v != s; v = parent[v] ){
            u = parent[v];
            graph[u][v] -= path_flow;
            graph[v][u] += path_flow;
        }
        max_flow += path_flow;
    }

    return max_flow;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    //freopen( "input.txt", "r", stdin );
    int tmp1, tmp2, tmp3;
    cin >> M >> W;
    while ( true ){
        if ( M == 0 && W == 0)
            break;

        for (int i = 0; i < M*2-2; i++)
            for (int j = 0; j < M*2-2; j++)
                graph[i][j] = 0;

        for ( int i = 0; i < M-2; i++){
            cin >> tmp1 >> tmp2;
            graph[(tmp1-1)*2-1][(tmp1-1)*2] = tmp2;
            //graph[(tmp1-1)*2][(tmp1-1)*2-1] = tmp2;
        }
        t = (M-1)*2-1;
        for ( int i = 0; i < W; i++){
            cin >> tmp1 >> tmp2 >> tmp3;
            if ( tmp1 == 1 ){
                if ( tmp2 == M ){
                    graph[0][t] = tmp3;
                    //graph[t][0] = tmp3;
                }
                else{
                    graph[0][(tmp2-1)*2-1] = tmp3;
                    //graph[(tmp2-1)*2][0] = tmp3;
                }
            }
            else if ( tmp2 == M ){
                graph[(tmp1-1)*2][t] = tmp3;
                //graph[t][(tmp1-1)*2-1] = tmp3;
            }
            else{
                graph[(tmp1-1)*2][(tmp2-1)*2-1] = tmp3;
                graph[(tmp2-1)*2][(tmp1-1)*2-1] = tmp3;
            }
        }
        cout << fordFulkerson(0) << endl;
        cin >> M >> W;
    }
}
