//
// Created by juan on 10/04/17.
//

#include <iostream>

using namespace std;

int main(){
    int n, m, s, f, ti, l, r, t = 0, f2;
    int rDir;
    char dir;
    cin >> n >> m >> s >> f;
    if (f > s) {
        rDir = 1;
        dir = 'R';
    }
    else if (s > f) {
        rDir = -1;
        dir = 'L';
    }
    else
        return 0;
    for (int i = 0; i < m; i++){
        cin >> ti >> l >> r;
        for (int j = 0; j < ti - t - 1; j++){
            s += rDir;
            cout << dir;
            //cout << s << " " << dir << endl;
            if (s == f)
                return 0;
        }
        f2 = s + rDir;
        if ((s >= l && s <= r) || (f2 >= l && f2 <= r)) {
            cout << 'X';
            //cout << s << " " << 'X' << endl;
        } else{
            s += rDir;
            cout << dir;
            //cout << s << " " << dir << endl;
            if (s == f)
                return 0;
        }
        t += ti - t;
    }
    for (int i = 0; i < abs(s-f); i++)
        cout << dir;
}