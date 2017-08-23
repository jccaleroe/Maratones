//
// Created by juan on 17/08/17.
//

#include <opencv2/opencv.hpp>
#include "filters.h"

using namespace cv;
using namespace std;

const double bravo = 0.8;

vector<Rect> greatFilter(vector<Rect> &bbox, int &rows, int &cols, float alpha){
    double size = cols*rows;
    vector<Rect> tmp;
    for(auto &i : bbox)
        if (!(i.area()/size >= alpha || 1.0*i.width/cols >= alpha || 1.0*i.height/rows >= alpha) )
            tmp.push_back(i);
    return tmp;
}

Rect intersect(Rect &a, Rect &b){
    int x = max(a.x, b.x);
    int y = max(a.y, b.y);
    int width = max(0, min(a.x + a.width, b.x + b.width) - x);
    int height = max(0, min(a.y + a.height, b.y + b.height) - y);
    return Rect(x, y, width, height);
}

double intersectRatio(Rect &a, Rect &b){
    Rect rect = intersect(a, b);
    return 1.0 * rect.area() / (a.area() + b.area() - rect.area());
}

vector<Rect> filterIntersections(vector<Rect> &bbox, double delta, int max_children){
    vector<Rect> tmp_bbox;
    if (bbox.empty()) return tmp_bbox;

    vector<int> tmp (bbox.size(), 0);

    for (int i = 0; i < bbox.size()-1; i++){
        for (int j = i+1; j < bbox.size(); j++) {
            double aux = intersectRatio(bbox[i], bbox[j]);
            if (aux >= bravo)
                tmp[j] += max_children;
            else if (aux > delta) {
                tmp[i] += 1;
                tmp[j] += 1;
            }
        }
    }

    for (int i = 0; i < bbox.size(); i++)
        if (tmp[i] < max_children)
            tmp_bbox.push_back(bbox[i]);

    return tmp_bbox;
}

vector<Rect> filterWords(vector<Rect> &bbox, double simility){
    vector<Rect> tmp_bbox;
    if (bbox.empty()) return tmp_bbox;

    vector<bool> tmp (bbox.size(), true);

    for (int i = 0; i < bbox.size()-1; i++)
        if (tmp[i])
            for (int j = i+1; j < bbox.size(); j++)
                if (intersectRatio(bbox[i], bbox[j]) >= simility)
                    tmp[j] = false;

    for (int i = 0; i < bbox.size(); i++)
        if (tmp[i])
            tmp_bbox.push_back(bbox[i]);

    return tmp_bbox;
}