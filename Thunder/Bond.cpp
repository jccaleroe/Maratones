//
// Created by juan on 15/04/17.
//

#include <iostream>

using namespace std;

int missions[25][25];

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int N;
    cin >> N;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            cin >> missions[i][j];


}