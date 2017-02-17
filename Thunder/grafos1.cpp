/*-------------------------------------------------------------------------------graphs-------------------------------------------*/
#include<bits/stdc++.h>
#define TAM 8

using namespace std;

vector<vector<int> > grafo(TAM);
bool matrixG[TAM][TAM];
bool seen[TAM];
stack<int> s;
queue<int> q;


//dfs iterativo para adjacency list O(V + E)
void dfsL(int v){
	cout<<"visited "<<v<<endl;
	s.push(v);
	seen[v] = true;

	while(!s.empty()){

		v = s.top();
		s.pop();

		for(int i =  0; i < grafo[v].size(); i++){
			if(!seen[grafo[v][i]]){

				s.push(grafo[v][i]);
				cout<<"visited "<<grafo[v][i]<<endl;
				seen[grafo[v][i]] = true;
			}
		}
	}
}

//dfs iterativo para adjacency matrix O(|V| + |E|)
void dfsM(int v){
    cout<<"Visited "<<v<<endl;
    s.push(v);
    seen[v] = true;

    while(!s.empty()){
        v = s.top();
        s.pop();

        for(int i =0; i < TAM; i++){
            if(!seen[i] && matrixG[v][i]){
                s.push(i);
                cout<<"visited "<<i<<endl;
                seen[i] = true;
            }
        }
    }
}

void recursive_dfs(int v){
    seen[v] = true;
    cout<<"visited "<<v<<endl;
    for( int i = 0; i<grafo[v].size(); ++i ){
        if(!seen[grafo[v][i]]){
               recursive_dfs(grafo[v][i]);
        }
    }
}

//bfs O(|V| + |E|)
void bfsL(int v){
    q.push(v);

    cout<<"visited "<<v<<endl;
    seen[v] = true;
    while(!q.empty()){

        v = q.front();
        q.pop();

        for(int i = 0; i< grafo[v].size(); i++){
            if(!seen[grafo[v][i]]){
                q.push(grafo[v][i]);
                seen[grafo[v][i]] = true;
                cout<<"visited "<<grafo[v][i]<<endl;
            }
        }
    }
}

void edges(){
	for(int i =  0; i < 6; i++){
		cout<<"los vecinos de "<<i<<" son "<<endl;
		for(int j =  0; j < grafo[i].size(); j++)
			cout<<grafo[i][j]<<endl;
	}
}

int main(){

	//add an edge into the graph via adjacency list
	grafo[0].push_back(1);
	grafo[1].push_back(0);
	grafo[0].push_back(2);
	grafo[2].push_back(0);
	grafo[1].push_back(4);
	grafo[4].push_back(1);
	grafo[1].push_back(3);
	grafo[3].push_back(1);
	grafo[2].push_back(7);
	grafo[7].push_back(2);
	grafo[2].push_back(5);
	grafo[5].push_back(2);
	grafo[7].push_back(5);
	grafo[5].push_back(7);
	grafo[5].push_back(6);
	grafo[5].push_back(5);
	grafo[7].push_back(6);
	grafo[6].push_back(7);


	//add an edge to the graph via matrix

	matrixG[0][1] = true;
	matrixG[1][0] = true;
	matrixG[0][2] = true;
	matrixG[2][0] = true;
	matrixG[1][4] = true;
	matrixG[4][1] = true;
	matrixG[1][3] = true;
	matrixG[3][1] = true;



	dfsL(0);
	memset(seen, false, sizeof matrixG);  //inicializar visitados en false
	dfsM(2);
	memset(seen, false, sizeof matrixG);  //inicializar visitados en false
	recursive_dfs(3);
	memset(seen, false, sizeof matrixG);  //inicializar visitados en false
	bfsL(6);
}
