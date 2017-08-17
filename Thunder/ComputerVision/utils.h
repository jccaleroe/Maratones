//
// Created by juan on 17/08/17.
//

#ifndef THUNDER_UTILS_H
#define THUNDER_UTILS_H

#include <opencv2/opencv.hpp>

cv::Rect intersect(cv::Rect &a, cv::Rect &b);
double intersectRatio(cv::Rect &a, cv::Rect &b);
std::vector<cv::Rect> filterIntersections(std::vector<cv::Rect> &bbox, double delta, int max_children);

#endif //THUNDER_UTILS_H
