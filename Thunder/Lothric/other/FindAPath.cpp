//
// Created by juan on 18/04/17.
//

#include <bits/stdc++.h>

using namespace std;

typedef pair<int, int> pi;

vector<vector<int> > v;
vector<vector<bool> > seen;

int H, W, T, n;
int dx[] = {1, 0, -1, 0};
int dy[] = {0, 1, 0, -1};

bool isIn(int i, int j){
    return i >= 0 && i < H && j >= 0 && j < W;
}

bool bfs(pi source){
    queue<pi> q;
    q.push(source);
    seen[source.first][source.second] = true;
    int aux1, aux2, a, b, curr;
    pi u;
    while(!q.empty()){
        u = q.front();
        q.pop();
        a = u.first;
        b = u.second;
        curr = v[a][b];
        for (int i = 0; i < 4; i++){
            aux1 = a+dy[i];
            aux2 = b+dx[i];
            if (isIn(aux1, aux2)){
                if (v[aux1][aux2] == curr +1 && v[aux1][aux2] == n)
                    return true;
                if (!seen[aux1][aux2] && v[aux1][aux2] == curr +1){
                    q.push(make_pair(aux1, aux2));
                    seen[aux1][aux2] = true;
                }
            }
        }
    }
    return false;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    freopen("/home/juan/Documents/Maratones/Thunder/input.txt", "r", stdin);
    freopen("/home/juan/Documents/Maratones/Thunder/out.txt", "w", stdout);
    cin >> T;
    pi source;
    for (int c = 0; c < T; c++){
        cin >> n >> H >> W;
        v.assign(H, vector<int>(W));
        seen.assign(H, vector<bool>(W, false));
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
                cin >> v[i][j];
                if (v[i][j] == 1)
                    source = make_pair(i, j);
            }
        if (bfs(source))
            cout << "true";
        else
            cout << "false";
        if (c != T-1)
            cout << endl;
    }
}