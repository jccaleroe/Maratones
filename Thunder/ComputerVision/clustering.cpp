//
// Created by juan on 25/08/17.
//

#include <opencv2/opencv.hpp>
#include "filters.h"
#include "clustering.h"

using namespace std;
using namespace cv;

vector<bool> visited;
vector<Rect> graph;
double avg;
double modeWidth, modeHeight, maxIntersec;

class Box{
public:
    double distance;

    explicit Box(const double &distance){
        this->distance = distance;
    }

    bool campareTo(Box Box){
        return distance > Box.distance;
    }
};

class MyComparator{
public:
    bool operator()(Box &a, Box &b){
        return a.campareTo(b);
    }
};

double getDistance(Rect &a, Rect&b){
    return sqrt(pow((a.x+a.width/2.0) - (b.x+b.width/2.0), 2) + pow((a.y+(a.height)/2.0) - (b.y+(b.height)/2.0), 2));
}

Rect dfs(int index){
    if (visited[index])
        return Rect(0,0,0,0);
    Rect u = graph[index], aux = graph[index], tmp;
    visited[index] = true;
    for (int i = 0; i < graph.size(); i++){
        if (!visited[i]) {
            if (getDistance(aux, graph[i]) <= avg) {
                tmp = dfs(i);
                if (!tmp.empty())
                    u = join(u, tmp);
            }
        }
    }
    return u;
}

vector<Rect> group(int k, vector<Rect> &bbox){
    graph = bbox;
    avg = 0;
    double aux;
    int minK;
    for (int i = 0; i < graph.size(); i++) {
        priority_queue<Box, vector<Box>, MyComparator> pq;
        for (int j = 0; j < graph.size(); j++){
            if (j != i && !containsAny(graph[i], graph[j]))
                pq.push(Box(getDistance(graph[i], graph[j])));
        }
        aux = 0;
        minK = min(k, static_cast<const int &>(pq.size()));
        for (int h = 0; h < minK; h++){
            aux += pq.top().distance;
            pq.pop();
        }
        if (minK > 0)
            aux /= k;
        avg += aux;
    }
    avg /= graph.size();
    //cout << "avg: " << avg << endl;
    visited.assign(graph.size(), false);
    vector<Rect> regions;
    Rect tmp;
    for (int i = 0; i < graph.size(); i++) {
        tmp = dfs(i);
        if (!tmp.empty())
            regions.push_back(tmp);
    }
    return regions;
}

int getMode(vector<Rect> &bbox){
    map<int, int> modes;
    for (auto &i : bbox)
        modes[i.width]++;
    int tmp = 0;
    int mode = 0;
    //cout << "modes:\n";
    for (auto &i : modes){
        //cout << i.first << " " << i.second << endl;
        if (i.second > tmp) {
            tmp = i.second;
            mode = i.first;
        }
    }
    //cout << endl;
    return mode;
}

Rect modeDFS(int index){
    if (visited[index])
        return Rect(0,0,0,0);
    Rect u = graph[index], aux = graph[index], tmp;
    visited[index] = true;
    int a, b, c, d, aux1 , aux2;
    for (int i = 0; i < graph.size(); i++){
        if (!visited[i]) {
            tmp = graph[i];
            a = min(aux.x + aux.width, tmp.x + tmp.width);
            b = max(aux.x, tmp.x);
            c = min(aux.y + aux.height, tmp.y + tmp.height);
            d = max(aux.y, tmp.y);
            aux1 = b-a;
            aux2 = d-c;
            if (aux1 <= modeWidth && (aux1 >= 0 || abs(aux1) < maxIntersec) && aux2 <= modeHeight && (aux2 >= 0 || abs(aux2) < maxIntersec)) {
                tmp = dfs(i);
                u = join(u, tmp);
            }
        }
    }
    return u;
}

vector<Rect> modeClustering(vector<Rect> &bbox, float alpha, float beta, float delta){
    int mode = getMode(bbox);
    //cout << "mode: " << mode << endl;
    modeWidth = alpha * mode;
    modeHeight = beta * mode;
    maxIntersec = delta * mode;
    graph = bbox;
    visited.assign(graph.size(), false);
    vector<Rect> regions;
    Rect tmp;
    for (int i = 0; i < graph.size(); i++) {
        tmp = modeDFS(i);
        if (!tmp.empty())
            regions.push_back(tmp);
    }
    return regions;
}