//
// Created by juan on 18/04/17.
//

#include <iostream>
#include <sstream>

using namespace std;

int X, A, B, na, nb;
int ta[50], tb[50];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    freopen("/home/juan/Documents/Maratones/Thunder/input.txt", "r", stdin);
    freopen("/home/juan/Documents/Maratones/Thunder/out.txt", "w", stdout);
    int r, aux;
    char trash;
    string line;
    while (getline(cin,line)){
        stringstream iss(line);
        iss>>X>>trash>>A>>trash>>B>>trash>>na>>trash>>nb;
        r = 0;
        for (int h = 0; h < na; h++)
            ta[h] = A;
        for (int h = 0; h < nb; h++)
            tb[h] = B;
        for (int i = 0; i < na; i++) {
            for (int j = 0; j < nb; j++) {
                aux = min(ta[i], min(tb[j], X));
                ta[i] -= aux;
                tb[j] -= aux;
                r += aux;
            }
        }
        cout << r << endl;
    }
}