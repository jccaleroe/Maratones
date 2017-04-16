//
// Created by juan on 16/04/17.
//

#include <iostream>

using namespace std;

int T, p, q, squared, aux, n, m;

bool check(){
    squared = p * p + q * q;
    for (int i = 0; i <= 141; i++) {
        if (i * i > squared)
            break;
        for (int j = 0; j <= 141; j++) {
            if (!((i == 0 && j == 0) || (i == 1 && j == 0) ||
                    (i == 0 && j == 1) || (i == abs(p) && j == abs(q)) || (i == abs(q) && j == abs(p)))) {
                m = i;
                n = j;
                aux = m * m + n * n;
                if (aux > squared)
                    break;
                if ((m*p + n*q) % aux == 0 && (m*q - n*p) % aux == 0)
                    return true;
                n *= -1;
                if ((m*p + n*q) % aux == 0 && (m*q - n*p) % aux == 0)
                    return true;
                n *= -1;
                m *= -1;
                if ((m*p + n*q) % aux == 0 && (m*q - n*p) % aux == 0)
                    return true;
                n *= -1;
                if ((m*p + n*q) % aux == 0 && (m*q - n*p) % aux == 0)
                    return true;
            }
        }
    }
    aux = p * p + q * q;
    if (p != 0 && q != 0) {
        m = -p;
        n = q;
        if ((m * p + n * q) % aux == 0 && (m * q - n * p) % aux == 0)
            return true;
        m = p;
        n = -q;
        if ((m * p + n * q) % aux == 0 && (m * q - n * p) % aux == 0)
            return true;
        m = q;
        n = p;
        if ((m*p + n*q) % aux == 0 && (m*q - n*p) % aux == 0)
            return true;
        m = -q;
        n = -p;
        if ((m*p + n*q) % aux == 0 && (m*q - n*p) % aux == 0)
            return true;
    }
    return false;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    cin >> T;
    for (int c = 0; c < T; c++){
        cin >> p >> q;
        if (check())
            cout << 'C' << endl;
        else
            cout << 'P' << endl;
    }
}