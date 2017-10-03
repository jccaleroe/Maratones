//
// Created by juan on 28/09/17.
//

#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

template<typename Out>
void split(const string &s, char delim, Out result) {
    stringstream stream;
    stream.str(s);
    string item;
    while (getline(stream, item, delim))
        *(result++) = item;
}

vector<string> split(const string &s, char delim) {
    vector<string> elems;
    split(s, delim, back_inserter(elems));
    return elems;
}

bool validateNIT(vector<int> &v) {
    if (v.size() != 10)
        return false;
    long long aux = 41 * v[0] + 37 * v[1] + 29 * v[2] + 23 * v[3] + 19 * v[4] + 17 * v[5] + 13 * v[6] + 7 * v[7] + 3 * v[8];
    aux = aux % 11;
    if (aux >= 2)
        aux = 11 - aux;
    return aux == v[9];
}

vector<int> convertToArray(string& s){
    vector<int> v;
    for (char i : s)
        v.push_back(i - '0');
    return v;
}

int main(){
    freopen("/home/juan/Documents/Maratones/Thunder/tabula-Boletin90.csv", "r", stdin);
    string s;
    int aux = 4;
    long long ll;
    do{
        getline(cin, s);
        vector<string> v = split(s, ',');
        if (v[4] == "C.C. y/o NIT")
            aux = 4;
        else if (v[5] == "C.C. y/o NIT")
            aux = 5;
        else  if (v[5].empty())
            break;
        else {
            vector<int> v2 = convertToArray(v[aux]);
            if (!validateNIT(v2)) {
                ll = stoll(v[aux]);
                cout << ll << endl;
            }
        }
    }while (!s.empty());
}