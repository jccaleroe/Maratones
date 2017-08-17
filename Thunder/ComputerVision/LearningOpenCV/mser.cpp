#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include "opencv2/features2d/features2d.hpp"

#include <iostream>

using namespace cv;
using namespace std;

//delta     variation       k   image
//3         4               2   1
//2         0.8             3   2
//1         0.15            3
//1         1.2*            3

int _delta = 5;   // it compares (sizei−sizei−delta)/sizei−delta. Default 5
int _min_area = 60;   // prune the area which smaller than minArea. Default 60
int _max_area = 14400;    // prune the area which bigger than maxArea. Default 14400
double _max_variation = 0.25; // prune the area have similar size to its children. Default 0.25
int k = 1;

int main(int argc, char *argv[]) {
    Mat img = imread(argv[1], 1);
    Mat img3 = img;
    cvtColor(img, img, COLOR_BGR2GRAY);
    Ptr<MSER> ms;
    vector<vector<Point> > msers;
    vector<Rect> bbox;

    ms = MSER::create(_delta, _min_area, _max_area, _max_variation);

    for (int i = 0; i < k; i++) {
        msers.clear();
        bbox.clear();
        ms->detectRegions(img, msers, bbox);

        for (int h = 0; h < bbox.size(); h++)
            rectangle(img, bbox[h], CV_FILLED, -1);
    }

    for (int h = 0; h < bbox.size(); h++)
        rectangle(img3, bbox[h], CV_RGB(0, 255, 0));

    //imshow("TextRecognition", img);
    imshow("TextRecognition3", img3);
    imwrite("output2.png", img3);
    waitKey(0);
}