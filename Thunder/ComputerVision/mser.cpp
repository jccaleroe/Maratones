#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include "mser.h"
#include "filters.h"
#include "clustering.h"

using namespace cv;
using namespace std;

//mser parameter used in getMser()
int _delta = 4;   // it compares (sizei−sizei−delta)/sizei−delta. Default 5
int _min_area = 20;   // prune the area which smaller than minArea. Default 60
int _max_area = 14400;    // prune the area which bigger than maxArea. Default 14400
double _max_variation = 0.22; // prune the area have similar size to its children. Default 0.25

//mser parameter used in kmser()
int _delta2 = 2;
int _min_area2 = 20;
double _max_variation2 = 0.2;

//modes found in getMser()
int mserMode, mserMode2;

vector<vector<Point> > msers;
vector<Rect> bbox1, bbox2, regions;

//call mser and filter the results
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

//calls knnClustering and modeClustering
vector<Rect> knnMode(Mat &img, int k, bool setMode = false){
    vector<Rect> bbox = getMser(img, setMode);
    vector<Rect> bboxs1 = knnClustering(k, bbox);
    int mode = setMode ? mserMode : mserMode2;
    vector<Rect> bboxs2 = modeClustering(bbox, mode, 0.64, 0.62, 2);
    bboxs1.insert(bboxs1.end(), bboxs2.begin(), bboxs2.end());
    return bboxs1;
}

//calls knnMode in some are of the image and filter rects smaller than the mode
void insertRegions(vector<Rect> &bbox, vector<Rect> &regions, Mat &img){
    Mat tmp;
    vector<Rect> tmp2;
    for (auto &i : regions){
        tmp = img(Rect(i.x, i.y, min(i.width+i.x, img.cols)-i.x, min(i.height+i.y, img.rows)-i.y));
        tmp2 = knnMode(tmp, 4);
        for (auto &j : tmp2)
            if (j.width >= 2*mserMode2)
                bbox.emplace_back(j.x + i.x, j.y + i.y, j.width, j.height);
    }
}

//calls insertRegions with the results of the swt and newmann
vector<Rect> cropAndKnnMser(vector<Rect> &swts, vector<Rect> &extremals, Mat &img){
    cvtColor(img, img, COLOR_BGR2GRAY);
    vector<Rect> bbox;
    insertRegions(bbox, swts, img);
    insertRegions(bbox, extremals, img);
    bbox = filterWords(bbox, 0.7);
    bbox = filterIntersections(bbox, 0.01, 6);
    return bbox;
}

//call mser k times filling with black the areas found in each iteration
void kmser(Mat img, int k){
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

//calls knnmode and kmser
vector<Rect> mser(Mat img){
    regions.clear();
    bbox2.clear();
    cvtColor(img, img, COLOR_BGR2GRAY);

    bbox1 = knnMode(img, 3, true);

    //kmser(img, 3);

    regions.insert(regions.end(), bbox1.begin(), bbox1.end());
    regions.insert(regions.end(), bbox2.begin(), bbox2.end());

    return regions;
}