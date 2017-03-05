//
// Created by juan on 24/02/17.
//

#include <iostream>

using namespace std;

int H, U, D, F, i;
double P, aux, aux2, d;

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    while(true){
        d = 0;
        i = 0;
        cin >> H >> U >> D >> F;
        P = F / 100.0;
        if(H == 0)
            break;
        aux2 = U*P;
        while(true){
            aux = i*aux2;
            d += (U- aux > 0 ? U - aux : 0);
            //d += U - aux;
            i++;
            if(d > H){
                cout << "success on day " << i << endl;
                break;
            }
            d -= D;
            if(d < 0){
                cout << "failure on day " << i << endl;
                break;
            }
        }
    }
}