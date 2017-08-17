#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/features2d/features2d.hpp>
#include "mser.h"
#include "utils.h"

using namespace cv;
using namespace std;

int _delta = 8;   // it compares (sizei−sizei−delta)/sizei−delta. Default 5
int _min_area = 60;   // prune the area which smaller than minArea. Default 60
int _max_area = 14400;    // prune the area which bigger than maxArea. Default 14400
double _max_variation = 0.22; // prune the area have similar size to its children. Default 0.25

int _delta2 = 2;
int _min_area2 = 40;
double _max_variation2 = 0.8;
int k = 3;

vector<vector<Point> > msers;
vector<Rect> bbox1, bbox2, regions;

vector<Rect> mser(char *file){
    regions.clear();
    Mat img = imread(file, 1);

    cvtColor(img, img, COLOR_BGR2GRAY);

    Ptr<MSER> ms = MSER::create(_delta, _min_area, _max_area, _max_variation);
    msers.clear();
    bbox1.clear();
    ms->detectRegions(img, msers, bbox1);

    bbox1 = filterIntersections(bbox1, 0.1, 6);

    ms = MSER::create(_delta2, _min_area2, _max_area, _max_variation2);

    for (int i = 0; i < k; i++) {
        msers.clear();
        bbox2.clear();
        ms->detectRegions(img, msers, bbox2);

        for (const auto &h : bbox2)
            rectangle(img, h, CV_FILLED, -1);
    }

    bbox2 = filterIntersections(bbox2, 0.1, 6);

    regions.insert(regions.end(), bbox1.begin(), bbox1.end());
    regions.insert(regions.end(), bbox2.begin(), bbox2.end());

    /*Mat img2 = imread(file, 1);
    Mat img3 = imread(file, 1);

    for (const auto &h : bbox1)
        rectangle(img3, h, CV_RGB(0, 255, 0));

    for (const auto &h : bbox2)
        rectangle(img2, h, CV_RGB(0, 255, 0));

    imshow("mserNormal", img3);
    imshow("mine", img2);*/
    return regions;
}