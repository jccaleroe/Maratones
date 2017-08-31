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

//class to keep the distance from a specific node to another
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

//class to be used as a comparator for some ordering algorithm
class MyComparator{
public:
    bool operator()(Box &a, Box &b){
        return a.campareTo(b);
    }
};

//return the distance from the centor of a rect to the center of another
double getDistance(Rect &a, Rect&b){
    return sqrt(pow((a.x+a.width/2.0) - (b.x+b.width/2.0), 2) + pow((a.y+(a.height)/2.0) - (b.y+(b.height)/2.0), 2));
}

//dfs to join rects that are close according to some max distance using getDistance()
Rect knndfs(int index){
    if (visited[index])
        return Rect(0,0,0,0);
    Rect u = graph[index], aux = graph[index], tmp;
    visited[index] = true;
    for (int i = 0; i < graph.size(); i++){
        if (!visited[i]) {
            if (getDistance(aux, graph[i]) <= avg) {
                tmp = knndfs(i);
                if (!tmp.empty())
                    u = join(u, tmp);
            }
        }
    }
    return u;
}

//gets an avg distance from de average distances of the average distance of each node's knn
// and calls knndfs with that average
vector<Rect> knnClustering(int k, vector<Rect> &bbox){
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
    visited.assign(graph.size(), false);
    vector<Rect> regions;
    Rect tmp;
    for (int i = 0; i < graph.size(); i++) {
        tmp = knndfs(i);
        if (!tmp.empty())
            regions.push_back(tmp);
    }
    return regions;
}

//returns the mode of the width of the rects
int getMode(vector<Rect> &bbox){
    map<int, int> modes;
    for (auto &i : bbox)
        modes[i.width]++;
    int tmp = 0, mode = 0;
    for (auto &i : modes){
        if (i.second > tmp) {
            tmp = i.second;
            mode = i.first;
        }
    }
    return mode;
}

//dfs to join rects that are close according to some vertical and horizontal distance from the closest sides of two rects.
//if they intersect but if one is too big from the other, they do not join.
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
            if (aux1 <= modeWidth && (aux1 >= 0 || abs(aux1) < maxIntersec) && aux2 <= modeHeight &&
                    (aux2 >= 0 || abs(aux2) < maxIntersec) && !containsAny(aux, tmp)) {
                tmp = modeDFS(i);
                u = join(u, tmp);
            }
        }
    }
    return u;
}

//gets the maximal vertical and horizontal distance to join rects in the modeDFS()
vector<Rect> modeClustering(vector<Rect> &bbox, int mode, float alpha, float beta, float delta){
    if (mode == 0)
        mode = getMode(bbox);
    modeWidth = alpha * mode;
    modeHeight = beta * mode;
    maxIntersec = delta * mode;
    graph = bbox;
    if (!graph.empty())
        visited.assign(graph.size(), false);
    else
        visited.clear();
    vector<Rect> regions;
    Rect tmp;
    for (int i = 0; i < graph.size(); i++) {
        tmp = modeDFS(i);
        if (!tmp.empty())
            regions.push_back(tmp);
    }
    return regions;
}