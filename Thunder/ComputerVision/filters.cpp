//
// Created by juan on 17/08/17.
//

#include <opencv2/opencv.hpp>
#include "filters.h"

using namespace cv;
using namespace std;

//drops rects thar are greater in alpha % in vertical or horizontal length according to the width or height of the image
vector<Rect> greatFilter(vector<Rect> &bbox, int &rows, int &cols, float alpha){
    double size = cols*rows;
    vector<Rect> tmp;
    for(auto &i : bbox)
        if (!(i.area()/size >= alpha || 1.0*i.width/cols >= alpha || 1.0*i.height/rows >= alpha))
            tmp.push_back(i);
    return tmp;
}

//joins two rects
Rect join(Rect &a, Rect &b){
   return a | b;
}

//return the intersection of two rects
Rect intersect(Rect &a, Rect &b){
    return a & b;
}

//return if one rect is contained in another.
bool containsAny(Rect &a, Rect &b){
    Rect tmp = intersect(a, b);
    return tmp.area() == a.area() || tmp.area() == b.area();
}

//returns how big is the intersection of two rects according to their union
double intersectRatio(Rect &a, Rect &b){
    Rect rect = intersect(a, b);
    return 1.0 * rect.area() / (a.area() + b.area() - rect.area());
}

//removes rects that have too many similar rects or which have another too similar
vector<Rect> filterIntersections(vector<Rect> &bbox, double delta, int max_children){
    double bravo = 0.8;
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

//if two rects are too similar, removes one of them
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