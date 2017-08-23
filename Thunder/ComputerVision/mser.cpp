#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/features2d/features2d.hpp>
#include "mser.h"
#include "filters.h"

using namespace cv;
using namespace std;

int _delta = 4;   // it compares (sizei−sizei−delta)/sizei−delta. Default 5
int _min_area = 20;   // prune the area which smaller than minArea. Default 60
int _max_area = 14400;    // prune the area which bigger than maxArea. Default 14400
double _max_variation = 0.22; // prune the area have similar size to its children. Default 0.25

int _delta2 = 2;
int _min_area2 = 20;
double _max_variation2 = 0.2;
int k = 3;
int g = 4;

vector<vector<Point> > msers;
vector<Rect> bbox1, bbox2, regions;

void showMsers(const char* file){
    Mat img3 = imread(file, 1);
    for (const auto &h : bbox1)
        rectangle(img3, h, CV_RGB(0, 255, 0));
    imshow("mserNormal", img3);

    Mat img2 = imread(file, 1);
    for (const auto &h : bbox2)
        rectangle(img2, h, CV_RGB(0, 255, 0));
    imshow("mine", img2);
}

void mserNomal(Mat &img){
    Ptr<MSER> ms = MSER::create(_delta, _min_area, _max_area, _max_variation);
    msers.clear();
    bbox1.clear();
    ms->detectRegions(img, msers, bbox1);
    bbox1 = filterIntersections(bbox1, 0.1, 6);
}

void mserMine(Mat &img){
    Ptr<MSER> ms = MSER::create(_delta2, _min_area2, _max_area, _max_variation2);
    for (int i = 0; i < k; i++) {
        msers.clear();
        bbox2.clear();
        ms->detectRegions(img, msers, bbox2);

        if (i != 0)
            bbox2 = greatFilter(bbox2, img.rows, img.cols, 0.3);

        for (auto &h : bbox2)
            rectangle(img, h, CV_FILLED, -1);
    }

    bbox2 = filterWords(bbox2, 0.74);
    bbox2 = filterIntersections(bbox2, 0.1, 6);
}

vector<Rect> mser(Mat &img){
    regions.clear();
    cvtColor(img, img, COLOR_BGR2GRAY);

    //mserNomal(img);
    mserMine(img);

    regions.insert(regions.end(), bbox1.begin(), bbox1.end());
    regions.insert(regions.end(), bbox2.begin(), bbox2.end());
    //regions = filterIntersections(regions, 0.1, 6);

    //showMsers(file);
    return regions;
}

class Box{
public:
    Rect rect;
    double distance;
    Box(const Rect &rect, const double &distance){
        this->rect = rect;
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

void group(){
    priority_queue<Box, vector<Box>, MyComparator> pq;
    Rect base = Rect(0, 0, 0, 0);
    Rect a = Rect(0, 0, 1, 2);
    Rect b = Rect(0, 0, 2, 1);
    Rect c = Rect(0, 0, 3, 2);
    Rect d = Rect(0, 0, 2, 3);
    Rect e = Rect(-1, -1, 2, 2);
    pq.push(Box(a, getDistance(a, base)));
    pq.push(Box(b, getDistance(b, base)));
    pq.push(Box(c, getDistance(c, base)));
    pq.push(Box(d, getDistance(d, base)));
    pq.push(Box(e, getDistance(e, base)));
    while(!pq.empty()){
        Rect i = pq.top().rect;
        pq.pop();
        cout << i.x << " " << i.y << " " << i.width << " " << i.height << endl;
    }
}