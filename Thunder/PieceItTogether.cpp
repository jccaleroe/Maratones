//
// Created by juan on 21/02/17.
//

#include <bits/stdc++.h>

using namespace std;

int n, m;
string s;
//vector< vector<char> > u(500, vector<char>(500));
char u[500][500];


int ll;
int yDir[4] = {1, 1, -1, -1};
int xDir[4] = {1, -1, 1, -1};
int yDir2[4] = {1, 0, -1, 0};
int xDir2[4] = {0, 1, 0, -1};

bool isIn(int i, int j){
    return i >= 0 && i < n && j >= 0 && j < m;
}

bool isComplete(){
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            if (u[i][j] != '.')
                return false;
    return true;
}

int checkWhite(){
    bool flag = false;
    for(int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (u[i][j] == 'W') {
                int cnt = 0, aux1, aux2, tmp1 = 0, tmp2 = 0;
                for (int c = 0; c < 4; c++) {
                    aux1 = i + yDir2[c];
                    aux2 = j + xDir2[c];
                    if (isIn(aux1, aux2) && u[aux1][aux2] == 'B') {
                        cnt += 1;
                        tmp1 = aux1;
                        tmp2 = aux2;
                    }
                }
                if (cnt == 1) {
                    if (i == tmp1) {
                        if (isIn(i + 1, tmp2) && u[i + 1][tmp2] == 'W') {
                            if (!isIn(i - 1, tmp2) || u[i - 1][tmp2] != 'W') {
                                u[tmp1][tmp2] = '.';
                                u[i + 1][tmp2] = '.';
                                u[i][j] = '.';
                                flag = true;
                            }
                        } else if (isIn(i - 1, tmp2) && u[i - 1][tmp2] == 'W') {
                            if (!isIn(i + 1, tmp2) || u[i + 1][tmp2] != 'W') {
                                u[tmp1][tmp2] = '.';
                                u[i - 1][tmp2] = '.';
                                u[i][j] = '.';
                                flag = true;
                            }
                        } else
                            return -1;
                    } else {
                        if (isIn(tmp1, j + 1) && u[tmp1][j + 1] == 'W') {
                            if (!isIn(tmp1, j - 1) || u[tmp1][j - 1] != 'W') {
                                u[tmp1][tmp2] = '.';
                                u[tmp1][j + 1] = '.';
                                u[i][j] = '.';
                                flag = true;
                            }
                        } else if (isIn(tmp1, j - 1) && u[tmp1][j - 1] == 'W') {
                            if (!isIn(tmp1, j + 1) || u[tmp1][j + 1] != 'W') {
                                u[tmp1][tmp2] = '.';
                                u[tmp1][j - 1] = '.';
                                u[i][j] = '.';
                                flag = true;
                            }
                        }
                        else
                            return -1;
                    }
                } else if (cnt == 0)
                    return -1;
            }
        }
    }
    if(flag)
        return 1;
    return 0;
}

bool pruning(){
    int aux1, aux2, tmp1 = 0, tmp2= 0;
    bool flag, flag2, flag3 = false;
    for(int i = 0; i < n; i++){
        for(int j = 0; j < m; j++){
            if(u[i][j] == 'B'){
                flag = flag2 = false;
                for(int c = 0; c < 4; c++){
                    aux1 = i+yDir[c];
                    aux2 = j+xDir[c];
                    if(isIn(aux1, j) && isIn(i, aux2) && u[aux1][j] == 'W' && u[i][aux2] == 'W'){
                        if(!flag) {
                            flag2 = true;
                            flag = true;
                        } else
                            flag2 = false;
                        if(flag2){
                            tmp1 = aux1;
                            tmp2 = aux2;
                        }
                    }
                }
                if(flag2){
                    u[tmp1][j] = '.';
                    u[i][tmp2] = '.';
                    u[i][j] = '.';
                    flag3 = true;
                }
            }
        }
    }
    return flag3;
}

bool search(){
    if (isComplete())
        return true;
    int aux1, aux2;
    for(int i = 0; i < n; i++){
        for(int j = 0; j < m; j++){
            if(u[i][j] == 'B'){
                for(int c = 0; c < 4; c++){
                    aux1 = i+yDir[c];
                    aux2 = j+xDir[c];
                    if(isIn(aux1, j) && isIn(i, aux2) && u[aux1][j] == 'W' && u[i][aux2] == 'W'){
                        u[aux1][j] = '.';
                        u[i][aux2] = '.';
                        u[i][j] = '.';
                        if(search())
                            return true;
                        u[aux1][j] = 'W';
                        u[i][aux2] = 'W';
                        u[i][j] = 'B';
                    }
                }
            }
        }
    }
    return false;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    int w, b, tmp;
    cin >> T;
    for(int c = 0; c < T; c++){
        cin >> n >> m;
        w = b = 0;
        for(int i = 0; i < n; i++) {
            cin >> s;
            for (int j = 0; j < m; j++) {
                u[i][j] = s[j];
                if (u[i][j] == 'W')
                    w++;
                else if (u[i][j] == 'B')
                    b++;
            }
        }
        if (w == b*2) {
            tmp = 1;
            while(tmp != 0 || pruning()){
                tmp = checkWhite();
                if ( tmp == -1){
                    cout << "NO" << endl;
                    break;
                }
            }
            if(tmp == -1)
                continue;
            //cout << (search() ? "YES" : "NO") << endl;
            cout << "YES" << endl;
        } else {
            cout << "NO" << endl;
        }
    }
}