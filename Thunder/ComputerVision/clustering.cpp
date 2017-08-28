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
double modeWidth, modeHeight;

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
    return sqrt(pow((a.x+(a.width)/2.0) - (b.x+(b.width)/2.0), 2) + pow((a.y+(a.height)/2.0) - (b.y+(b.height)/2.0), 2));
}

Rect dfs(int index){
    if (visited[index])
        return Rect(0,0,0,0);
    Rect u = graph[index];
    visited[index] = true;
    Rect tmp;
    for (int i = 0; i < graph.size(); i++){
        if (!visited[i]) {
            if (getDistance(u, graph[i]) <= avg) {
                tmp = dfs(i);
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
    for (int i = 0; i < graph.size(); i++) {
        priority_queue<Box, vector<Box>, MyComparator> pq;
        for (int j = 0; j < graph.size(); j++){
            if (j != i)
                pq.push(Box(getDistance(graph[i], graph[j])));
        }
        aux = 0;
        for (int h = 0; h < k; h++){
            aux += pq.top().distance;
            pq.pop();
        }
        aux /= k;
        avg += aux;
    }
    avg /= graph.size();
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
    for (auto &i : modes){
        if (i.second > tmp) {
            tmp = i.second;
            mode = i.first;
        }
    }
    return mode;
}

Rect modeDFS(int index){
    if (visited[index])
        return Rect(0,0,0,0);
    Rect u = graph[index];
    visited[index] = true;
    Rect tmp;
    int a, b, c, d;
    for (int i = 0; i < graph.size(); i++){
        if (!visited[i]) {
            tmp = graph[i];

            a = min(u.x + u.width, tmp.x + tmp.width);
            b = max(u.x, tmp.x);
            c = min(u.y + u.height, tmp.y + tmp.height);
            d = max(u.y, tmp.y);


            if (abs(a-b) <= modeWidth && abs(c-d) <= modeHeight) {
                tmp = dfs(i);
                u = join(u, tmp);
            }
        }
    }
    return u;
}

vector<Rect> modeClustering(vector<Rect> &bbox, float alpha, float beta){
    int mode = getMode(bbox);
    cout << mode << endl;
    modeWidth = alpha * mode;
    modeHeight = beta * mode;
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