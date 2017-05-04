//
// Created by juan on 18/04/17.
//

#include <bits/stdc++.h>

using namespace std;

char m[30][30];
char m2[30][30];
int H, W, T;
int dg, sg;
int dx[] = {1, 0, -1, 0};
int dy[] = {0, 1, 0, -1};

bool isIn(int i, int j){
    return i >= 0 && i < H && j >= 0 && j < W;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    freopen("/home/juan/Documents/Maratones/Thunder/input.txt", "r", stdin);
    freopen("/home/juan/Documents/Maratones/Thunder/out.txt", "w", stdout);
    cin >> H >> W >> T;
    for (int i = 0; i < H; i++)
        for (int j = 0; j < W; j++) {
            cin >> m[i][j];
            m2[i][j] = m[i][j];
        }
    int aux1, aux2;
    char curr;
    for (int c = 0; c < T; c++){
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                dg = 0;
                sg = 0;
                curr = m[i][j];
                if (m[i][j] != '.'){
                    for (int x = 0; x < 4; x++){
                        aux1 = i+dy[x];
                        aux2 = j+dx[x];
                        if (isIn(aux1, aux2)){
                            if (m[aux1][aux2] != '.' && m[aux1][aux2] != curr){
                                dg++;
                            }
                        }
                    }
                    if (dg < 2 || dg > 3)
                        m2[i][j] = '.';
                }
                else{
                    for (int x = 0; x < 4; x++){
                        aux1 = i+dy[x];
                        aux2 = j+dx[x];
                        if (isIn(aux1, aux2)){
                            if (m[aux1][aux2] == '*')
                                dg++;
                            else if (m[aux1][aux2] == '#')
                                sg++;
                        }
                    }
                    if (sg == 1 && dg == 2)
                        m2[i][j] = '#';
                    else if (sg == 2 && dg == 1)
                        m2[i][j] = '*';
                }
            }
        }
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                m[i][j] = m2[i][j];

    }
    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            cout << m[i][j];
        }
        cout << endl;
    }
}