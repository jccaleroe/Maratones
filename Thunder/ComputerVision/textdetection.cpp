/*
 * textdetection.cpp
 *
 * A demo program of the Extremal Region Filter algorithm described in
 * Neumann L., Matas J.: Real-Time Scene Text Localization and Recognition, CVPR 2012
 *
 * Created on: Sep 23, 2013
 *     Author: Lluis Gomez i Bigorda <lgomez AT cvc.uab.es>
 */

#include  "opencv2/text.hpp"
#include  "opencv2/highgui.hpp"
#include  "opencv2/imgproc.hpp"
#include "extremal.h"

using namespace std;
using namespace cv;
using namespace cv::text;


vector<Rect> extremal(const char* file){
    Mat src = imread(file);

    // Extract channels to be processed individually
    vector<Mat> channels;
    computeNMChannels(src, channels);

    auto cn = (int)channels.size();
    // Append negative channels to detect ER- (bright regions over dark background)
    for (int c = 0; c < cn-1; c++)
        channels.push_back(255-channels[c]);

    // Create ERFilter objects with the 1st and 2nd stage default classifiers
    Ptr<ERFilter> er_filter1 = createERFilterNM1(loadClassifierNM1("trained_classifierNM1.xml"),16,0.00015f,0.13f,0.2f,true,0.1f);
    Ptr<ERFilter> er_filter2 = createERFilterNM2(loadClassifierNM2("trained_classifierNM2.xml"),0.5);

    vector<vector<ERStat> > regions(channels.size());

    // Apply the default cascade classifier to each independent channel (could be done in parallel)
    for (int c=0; c<(int)channels.size(); c++) {
        er_filter1->run(channels[c], regions[c]);
        er_filter2->run(channels[c], regions[c]);
    }

    // Detect character groups
    vector< vector<Vec2i> > region_groups;
    vector<Rect> groups_boxes;
    erGrouping(src, channels, regions, region_groups, groups_boxes, ERGROUPING_ORIENTATION_HORIZ);

    er_filter1.release();
    er_filter2.release();
    regions.clear();

    return groups_boxes;
}
