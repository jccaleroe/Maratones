//
// Created by juan on 13/03/17.
//

#include <iostream>
#include <queue>
#include <cstring>
#include <algorithm>

int N;
char people[1000][1000];
int houseP[1000];
int totalHouses = 0, currentHouse = 0;
bool seen[1000];
bool totalSeen[1000];

using namespace std;

bool compareYes(int a, int b){
    for(int i = 0; i < N; i++){
        if(people[a][i] != people[b][i])
            return false;
    }
    return true;
}

int bfs(int source){
    queue<int> q;
    q.push(source);
    memset(seen, 0, sizeof(bool)*N);
    //memset(seen2, 0, sizeof(bool)*N);
    seen[source] = 1;
    int u, housesVisited = 1;
    while(!q.empty()){
        u = q.front();
        q.pop();
        for(int i = 0; i < N; i++){
            if(people[u][i] == 'S'){
                if(totalSeen[i])
                    return -1;
                if(!seen[i]){
                    if(!compareYes(u, i))
                        return -1;
                    q.push(i);
                    seen[i] = 1;
                    housesVisited++;
                }
            }
        }
    }

    for(int i = 0; i < N; i++)
        totalSeen[i] = totalSeen[i] | seen[i];
    return housesVisited;
}

int main(){
    cin.tie(0);
    ios_base::sync_with_stdio(0);
    cin >> N;
    for(int i = 0; i < N; i++)
        for(int j = 0; j < N; j++)
            cin >> people[i][j];

    bool flag = true;
    for(int i = 0; i < N; i++){
        if(!totalSeen[i]){
            int tmp = bfs(i);
            if(tmp == -1){
                flag = false;
                break;
            }
            else{
                houseP[currentHouse] = tmp;
                currentHouse++;
                totalHouses++;
            }
        }
    }

    if(flag) {
        cout << totalHouses << endl;
        sort(houseP, houseP + totalHouses);
        for(int i = totalHouses-1; i > 0; i--)
            cout << houseP[i] << " ";
        cout << houseP[0];
        cout << endl;
    }
    else
        cout << -1 << endl;
}
