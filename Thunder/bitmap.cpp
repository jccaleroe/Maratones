#include<bits/stdc++.h>

using namespace std;

bool matrix[182][182];
int N, M;

bool isIn(int r, int c){ return r >= 0 && c >= 0 && r < N && c < M;}

int main(){
    int T;
    cin >> T;
    while(T--){
        cin >> N >> M;
        bool seen[N][M];
        int answer[N][M];
        queue<pair<int, int>> q;
        int DR[] = {1, 0, -1, 0};
        int DC[] = {0, 1, 0, -1};
        string tmp;

        for(int i = 0; i < N; i++){
            cin >> tmp;
            for(int j = 0; j < M; j++){
                matrix[i][j] = tmp[j] - 48;
                seen[i][j] = false;
                if(matrix[i][j] == 1){
                    q.push(make_pair(i,j));
                    seen[i][j] = true;
                    answer[i][j] = 0;
                }
            }
        }
        while(!q.empty()){
            pair<int, int> u = q.front();
            q.pop();
            for(int i = 0; i < 4; i++){
                int tmp1 = DR[i] + u.first;
                int tmp2 = DC[i] + u.second;
                if(isIn(tmp1, tmp2) && seen[tmp1][tmp2] == false){
                    q.push(make_pair(tmp1, tmp2));
                    seen[tmp1][tmp2] = true;
                    answer[tmp1][tmp2] = answer[u.first][u.second] + 1;
                }
            }
        }
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++)
                cout << answer[i][j] << " ";
            cout << endl;
        }
    }
}
