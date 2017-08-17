//
// Created by juan on 14/04/17.
//

#include <iostream>

char m[25][25];
int T, R, C;

using namespace std;

void fill(int a, int b, int c, int d, char l){
    for (int j = a; j <= b; j++)
        for (int i = c; i <= d; i++)
            if (m[i][j] == '?')
                m[i][j] = l;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    cin >> T;
    for (int c = 1; c <= T; c++) {
        cin >> R >> C;
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++)
                cin >> m[i][j];

        int p0 = 0, p3 = 0, p2;
        bool flag, flag2;
        char last;
        for (int j = 0; j < C; j++) {
            //p0 = 0;
            p2 = 0;
            p0 = p3;
            flag2 = false;
            for (int i = 0; i < R; i++) {
                if (m[i][j] != '?'){
                    flag2 = true;
                    last = m[i][j];
                    flag = false;
                    for (int h = i+1; h < R; h++){
                        if (m[h][j] != '?'){
                            fill(p0, j, p2, h-1, last);
                            p2 = h;
                            i = h-1;
                            flag = true;
                            break;
                        }
                    }
                    p3 = j+1;
                    if (!flag)
                        fill(p0, j, p2, R-1, last);
                }
            }
        }
        fill(p0, C-1, p2, R-1, last);

        cout << "Case #" << c << ":\n";
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                cout << m[i][j];
            }
            cout << endl;
        }
    }
}