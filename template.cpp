#include<bits/stdc++.h>

using namespace std;

bool comparison(int a, int b){
    return a >= b;
}

int main(){
    priority_queue<int, comparison> q;
    q.push(10);
    q.push(4);
    q.push(8);
}
