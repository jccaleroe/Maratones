#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include "mser.h"
#include "filters.h"
#include "clustering.h"

using namespace cv;
using namespace std;

int _delta = 4;   // it compares (sizei−sizei−delta)/sizei−delta. Default 5
int _min_area = 20;   // prune the area which smaller than minArea. Default 60
int _max_area = 14400;    // prune the area which bigger than maxArea. Default 14400
double _max_variation = 0.22; // prune the area have similar size to its children. Default 0.25

int _delta2 = 2;
int _min_area2 = 20;
double _max_variation2 = 0.2;

int mserMode, mserMode2;
vector<vector<Point> > msers;
vector<Rect> bbox1, bbox2, regions;

int getMserMode(){
    return mserMode;
}

vector<Rect> getMser(Mat &img, bool setMode = false){
    Ptr<MSER> ms = MSER::create(_delta, _min_area, _max_area, _max_variation);
    msers.clear();
    vector<Rect> bbox;
    ms->detectRegions(img, msers, bbox);
    bbox = filterWords(bbox, 0.7);
    bbox = filterIntersections(bbox, 0.05, 6);
    if (setMode)
        mserMode = getMode(bbox);
    else
        mserMode2 = getMode(bbox);
    return bbox;
}

vector<Rect> mserKnn(Mat &img, int k, bool setMode = false){
    vector<Rect> bbox = getMser(img, setMode);
    bbox = group(k, bbox);
    return bbox;
}

vector<Rect> knnMode(Mat &img, int k){
    vector<Rect> bboxs = getMser(img), bbox, tmp;
    bbox = group(k, bboxs);
    tmp = modeClustering(bboxs, mserMode, 0.64, 0.64, 2.2);
    bbox.insert(bbox.end(), tmp.begin(), tmp.end());
    return bbox;
}

void insertRegions(vector<Rect> &bbox, vector<Rect> &regions, Mat &img){
    Mat tmp;
    vector<Rect> tmp2;
    for (auto &i : regions){
        //cout << img.cols << " " << img.rows << " " << i.x << " " << i.y << " " << i.width+i.x << " " << i.height + i.y << endl;

        tmp = img(Rect(i.x, i.y, min(i.width+i.x, img.cols)-i.x, min(i.height+i.y, img.rows)-i.y));
        tmp2 = knnMode(tmp, 3);
        for (auto &j : tmp2)
            if (j.width >= 2*mserMode2)
                bbox.emplace_back(j.x + i.x, j.y + i.y, j.width, j.height);
    }
}

vector<Rect> cropAndKnnMser(vector<Rect> &swts, vector<Rect> &extremals, Mat &img){
    cvtColor(img, img, COLOR_BGR2GRAY);
    vector<Rect> bbox;
    insertRegions(bbox, swts, img);
    insertRegions(bbox, extremals, img);
    bbox = filterWords(bbox, 0.7);
    bbox = filterIntersections(bbox, 0.01, 6);
    return bbox;
}

void mserMine(Mat img, int k){
    Ptr<MSER> ms = MSER::create(_delta2, _min_area2, _max_area, _max_variation2);
    vector<Rect> tmp;
    for (int i = 0; i < k; i++) {
        msers.clear();
        tmp.clear();
        ms->detectRegions(img, msers, tmp);
        if (i != 0)
            tmp = greatFilter(tmp, img.rows, img.cols, 0.3);

        for (auto &h : tmp)
            rectangle(img, h, CV_FILLED, -1);
    }

    tmp = filterWords(tmp, 0.7);
    tmp = filterIntersections(tmp, 0.05, 6);
    for (auto &i : tmp)
        if (i.width >= 2*mserMode)
            bbox2.push_back(i);
}

vector<Rect> mser(Mat img){
    regions.clear();
    bbox2.clear();
    cvtColor(img, img, COLOR_BGR2GRAY);

    bbox1 = mserKnn(img, 3, true);
    mserMine(img, 3);

    regions.insert(regions.end(), bbox1.begin(), bbox1.end());
    regions.insert(regions.end(), bbox2.begin(), bbox2.end());

    return regions;
}