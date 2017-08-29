#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
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

vector<vector<Point> > msers;
vector<Rect> bbox1, bbox2, regions;

void showmser(const char *s, Mat img, vector<Rect> &v){
    for (const auto &a : v)
        rectangle(img, a, CV_RGB(255, 0, 0));
    imshow(s, img);
}

void mserNomal(Mat &img){
    Ptr<MSER> ms = MSER::create(_delta, _min_area, _max_area, _max_variation);
    msers.clear();
    bbox1.clear();
    ms->detectRegions(img, msers, bbox1);
    //cout << "msers found: " << bbox1.size() << endl;
    bbox1 = filterWords(bbox1, 0.7);
    bbox1 = filterIntersections(bbox1, 0.05, 6);
    //cout << "msers after filters " << bbox1.size() << endl;
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

    tmp = filterWords(tmp, 0.8);
    tmp = filterIntersections(tmp, 0.05, 4);
    bbox2.insert(bbox2.end(), tmp.begin(), tmp.end());
}

vector<Rect> mser(Mat img){
    regions.clear();
    bbox2.clear();
    Mat img2 = img.clone();
    Mat img3 = img.clone();
    cvtColor(img, img, COLOR_BGR2GRAY);

    mserNomal(img);
    //mserMine(img, 2);
    //mserMine(img, 3);


    //showmser("2", img3, bbox2);
    //cout << "mine size: " << bbox2.size() << endl;

    //vector<Rect> tmp = modeClustering(bbox1, 0.62, 0.6, 2);
    //cout << "mode areas: " << tmp.size() << endl;
    //bbox1 = group(3, bbox1);
    //showmser("knn", img2, bbox1);

    bbox1 = modeClustering(bbox1, 0.64, 0.6, 2);
    //cout << "knn areas: " << bbox1.size() << endl;
    //for (auto &i : bbox1)
      //  cout << i.x << " " << i.y << " " << i.width << " " << i.height << endl;

    //vector<Rect> tmp = modeClustering(bbox1, 0.5, 0.5);
    //cout << tmp.size() << endl;
    //regions.insert(regions.end(), tmp.begin(), tmp.end());
    regions.insert(regions.end(), bbox1.begin(), bbox1.end());
    //regions.insert(regions.end(), bbox2.begin(), bbox2.end());
    //regions = filterIntersections(regions, 0.1, 6);

    return regions;
}