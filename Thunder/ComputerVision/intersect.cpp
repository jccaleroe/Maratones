//
// Created by juan on 17/08/17.
//

#include <opencv2/opencv.hpp>
#include "utils.h"

using namespace cv;
using namespace std;

const float alpha = 6;
const double beta = 1/6;

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
    cout << bbox.size() << endl;
    vector<Rect> tmp_bbox;
    if (bbox.size() == 0) return tmp_bbox;

    vector<int> tmp (bbox.size(), 0);

    for (int i = 0; i < bbox.size()-1; i++){
        for (int j = i+1; j < bbox.size(); j++) {
            if (intersectRatio(bbox[i], bbox[j]) > delta) {
                tmp[i] += 1;
                tmp[j] += 1;
            }
        }
    }

    for (int i = 0; i < bbox.size(); i++) {
        double ratio = bbox[i].width / bbox[i].height;
        if (tmp[i] < max_children && !(ratio > alpha || ratio < beta))
            tmp_bbox.push_back(bbox[i]);
    }

    return tmp_bbox;
}