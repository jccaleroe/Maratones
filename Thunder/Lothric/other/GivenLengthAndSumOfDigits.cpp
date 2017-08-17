//
// Created by juan on 3/04/17.
//

#include <iostream>
#include <algorithm>

using namespace std;

int main(){
    int m, s, tmp;
    cin >> m >> s;
    tmp = s;
    string maxi = "", mini = "";

    if ( (m > 1 && s == 0) || (s > 9*m) ) {
        cout << "-1 -1\n";
        return 0;
    }

    for (int i = 0; i < m; i++){
        if (s >= 9){
            maxi += '9';
            s -= 9;
        }
        else {
            maxi += to_string(s);
            s -= s;
        }
    }
    s = tmp;
    for (int i = 1; i <= m; i++){
        tmp = s - 1;
        if (tmp >= 9){
            mini = '9' + mini;
            s -= 9;
        }
        else if (i == m){
            if (tmp == 0)
                mini = '1' + mini;
            else
                mini = to_string(tmp+1) + mini;
        }
        else {
            mini = to_string(tmp) + mini;
            s -= tmp;
        }
    }
    cout << mini << " " << maxi << endl;
}
