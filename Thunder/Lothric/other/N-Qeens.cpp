//
// Created by juan on 21/02/17.
//

#include <bits/stdc++.h>

using namespace std;

int N, iter = 1000;
int c1[300], c2[300], c3[300], c4[300];
int d1[300], d2[300], d3[300], d4[300];
int c1Fitness, c2Fitness, c3Fitness, c4Fitness;
double totalAttacks, c1Prob, c2Prob, c3Prob, c4Prob;

bool queenAttackQueen(int i1, int j1, int i2, int j2){
    return (j1 == j2 || abs(i1-i2) == abs(j1-j2));
}

void printArray(int *a){
    for(int i = 0; i < N; i++)
        cout << a[i] << " ";
    cout << endl;
}

void crossOver(int *a, int *b){
    int cross = rand()%N, tmp;
    //cout << "crossline " << cross << endl;
    for(int i = cross; i < N; i++){
        tmp = a[i];
        a[i] = b[i];
        b[i] = tmp;
    }
    double aux;
    for(int i = 0; i < N; i++){
        //aux = distribution(generator);
        aux = rand()%40 / 40.0;
        if (aux <= 0.003)
            a[i] = rand()%N;
        aux = rand()%40 / 40.0;
        if (aux <= 0.003)
            b[i] = rand()%N;
    }
    //cout << "descendants:" << endl;
    //printArray(a);
    //printArray(b);
}

void selection (int *d, int *e){
    double tmp1, tmp2, tmp3;
    int *a, *b, father;
    //tmp1 = distribution(generator);
    //tmp2 = distribution(generator);
    tmp1 = rand()%40 / 40.0;
    tmp2 = rand()%40 / 40.0;
    if(tmp1 <= c1Prob) {
        a = c1;
        tmp3 = c1Prob / 3;
        father = 1;
    }
    else if(tmp1 <= c1Prob + c2Prob) {
        a = c2;
        tmp3 = c2Prob / 3;
        father = 2;
    }
    else if(tmp1 <= c1Prob + c2Prob + c3Prob) {
        a = c3;
        tmp3 = c3Prob / 3;
        father = 3;
    }
    else {
        a = c4;
        tmp3 = c4Prob / 3;
        father = 4;
    }
    //cout << "Father choosen" << endl;
    //printArray(a);
    if(father != 1 && tmp2 <= c1Prob + tmp3)
        b = c1;
    else if(father != 2 && tmp2 <= c1Prob + c2Prob + tmp3)
        b = c2;
    else if(father != 3 && tmp2 <= c1Prob + c2Prob + c3Prob + tmp3)
        b = c3;
    else
        b = c4;
    //cout << "Mother choosen" << endl;
    //printArray(b);
    for(int i = 0; i < N; i++){
        d[i] = a[i];
        e[i] = b[i];
    }
    crossOver(d, e);
}

void populate(){
    for (int i = 0; i < N; i++) {
        c1[i] = rand() % N;
        c2[i] = rand() % N;
        c3[i] = rand() % N;
        c4[i] = rand() % N;
    }
}

void copyChilds(){
    for(int i = 0; i < N; i++){
        c1[i] = d1[i];
        c2[i] = d2[i];
        c3[i] = d3[i];
        c4[i] = d4[i];
    }
}

int* simulate(){
    srand (time(NULL));
    populate();
    for (int c = 0; c < iter; c++) {
//        printArray(c1);
//        printArray(c2);
//        printArray(c3);
//        printArray(c4);
//        cout << endl;
        c1Fitness = c2Fitness = c2Fitness = c4Fitness = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                c1Fitness += (queenAttackQueen(c1[i], i, c1[j], j) ? 1 : 0);
                c2Fitness += (queenAttackQueen(c2[i], i, c2[j], j) ? 1 : 0);
                c3Fitness += (queenAttackQueen(c3[i], i, c3[j], j) ? 1 : 0);
                c4Fitness += (queenAttackQueen(c4[i], i, c4[j], j) ? 1 : 0);
            }
        }
        if(c1Fitness == 0)
            return c1;
        if(c2Fitness == 0)
            return c2;
        if(c3Fitness == 0)
            return c3;
        if(c4Fitness == 0)
            return c4;
        totalAttacks = c1Fitness + c2Fitness + c3Fitness + c4Fitness;
        c1Prob = 1.0 - (c1Fitness / totalAttacks);
        c2Prob = 1.0 - (c2Fitness / totalAttacks);
        c3Prob = 1.0 - (c3Fitness / totalAttacks);
        c4Prob = 1.0 - (c4Fitness / totalAttacks);
        selection(d1, d2);
        selection(d3, d4);
        copyChilds();
        //cout << "Final result:" << endl;
//        printArray(c1);
//        printArray(c2);
//        printArray(c3);
//        printArray(c4);
//        cout << endl;
    }
    cout << "Nothing found :'/\n";
    return  NULL;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    int T;
    cin >> T;
    int *r;
    for(int c = 0; c < T; c++) {
        cin >> N;
        r = simulate();
        if (r != NULL){
            cout << N << endl;
        printArray(r);
        }
    }
}