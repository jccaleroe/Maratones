//
// Created by juan on 15/04/17.
//

#include <iostream>

using namespace std;

int main(){
    int n;
    cin >> n;
    cout << 2*n-1 << endl;
    for (int i = 1; i <= n; i++)
        cout << i << " ";
    for (int i = n; i >= 2; i--)
        cout << i << " ";
    return 0;
}
