#include<bits/stdc++.h> //modo dios
#define MOD 9000000 + 7  //usar macros hace la vida más fácil
#define TAM 100

using namespace std;

typedef long long ll;  //no escribir siempre long long
typedef pair<int,int> pii;  //no escribir siempre toda esa vaina


/*USADO EN EJEMPLO DE PRIORITY QUEUE*/
class Comparator {
public:
    bool operator()(const int &p1,const int &p2) {  //los parametros deben ser siempre const tipo &nombre  esto para que sea un paso por referencia constante
        // debe retornar true si p1 es más importante que p2 es decir si la pq retorna a p1 antes que a p2
        return p1 < p2;
    }
};

int main(){
    /*hacer que cin sea tan rápido como scanf*/
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    /*
    el siguiente código sirve para hacer pruebas en wl computador
    en codeblocks ir a Settings -> compiler y en la pestaña #defines escribir LOCAL
    o el nombre que se quiera poner, el código se puede mandar al juez así y no generará error
    */
    #ifdef LOCAL
    freopen("input.txt", "r", stdin);  //crear un archivo llamado input.txt donde esté el input (no hay necesidad de reescribir el input para cada prueba)
    //freopen("output.txt", "w", stdout);
    #endif

    /*truquitos de lectura de datos
      CASO 1: se dan T casos de prueba y luego otra cosa  */

      int T;
      cin>>T;
      while(T--){
        /**/
      }

    /*CASO 2: se da un string que puede contener espacios*/
    string s;
    getline(cin, s);

    /*CASO 3: se dan varios strings en una misma linea separados por espacios TOKENIZAR
      NOTA: si no hay bits usar #include<sstream> y #include<string>  */
      string str;
      getline(cin,s);
      stringstream p(str);

      while(!p.eof()){
            string token;
            p >> token;
            cout<<token<<endl;
      }


    /*ordenar un arreglo o un vector */
    vector<int> v;
    int arr[TAM];
    for(int i = 0; i < TAM; i++){
            v.push_back(i); //agregar elemento a vector
            arr[i] = i;
    }

    sort(v.begin(), v.end());
    sort(arr, arr + TAM);

    /*ordenar en reversa el vector*/
    sort(v.rbegin(), v.rend());

    /*maps, el equivalente de diccionarios, tiempo de acceso, borrado e insertado log(n)*/

    map<string, int> m;

    /*insertar es más fácil*/
    m["abc"] = 1;

    /*buscar si una llave está en el mapa toma tiempo log(n)*/
    if(m.count("abc") == 1) cout<<"existe la llave"<<endl;
    else cout<<"no existe"<<endl;

    /*estructuras de datos útiles en c++ si no hay bits la mayoria se incluyen facil como #include<queue> o #include<stack> etc*/
    stack<int> st;
    st.push(2);
    st.top(); //mirar el top de la pila pero no lo quita
    st.pop(); //no devuelve nada, para revisar el tope de la pila se usa top
    st.size();

    queue<int> q;
    q.push(3);
    q.front(); //devuelve el frente de la fila pero no lo quita
    q.pop();  //quita el frente pero no lo muestra
    q.size();

    priority_queue<int> pq;  /*no se puede hacer dijkstra decente sin esto :v pila de prioridad, regresa siempre el máximo pero se puede pasar una funcion de comparacion*/
    pq.push(3);
    pq.top();
    pq.pop();
    pq.size();

    /*priority queue que ordena de menor a mayor usando el struct comparator que se creo antes*/
    priority_queue<int> pq2(Comparator);



}
