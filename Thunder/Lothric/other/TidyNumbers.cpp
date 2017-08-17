//
// Created by juan on 8/04/17.
//

#include <iostream>

using namespace std;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    string s;
    cin >> T;
    int p;
    for (int C = 1; C <= T; C++){
        cin >> s;
        int p = s.size()-1;
        cout << "Case #" << C << ": ";
        for (int i = s.size()-1; i > 0; i--){
            if (s[i-1] > s[i]){
                s[i-1] = (char)(s[i-1] - 1);
                p = i-1;
            }
        }
        if (!(p == 0 && s[p] == '0'))
            for (int i = 0; i <= p; i++)
                cout << s[i];


        for(int i = p+1; i < s.size(); i++)
            cout << '9';

        cout << endl;
    }
}