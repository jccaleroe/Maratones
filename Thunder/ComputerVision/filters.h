//
// Created by juan on 17/08/17.
//

#ifndef THUNDER_UTILS_H
#define THUNDER_UTILS_H

#include <opencv2/opencv.hpp>

double intersectRatio(cv::Rect &a, cv::Rect &b);
std::vector<cv::Rect> filterIntersections(std::vector<cv::Rect> &bbox, double delta, int max_children);
std::vector<cv::Rect> filterWords(std::vector<cv::Rect> &bbox, double simility);
std::vector<cv::Rect> greatFilter(std::vector<cv::Rect> &bbox, int &rows, int &cols, float alpha);
cv::Rect join(cv::Rect &a, cv::Rect &b);
bool containsAny(cv::Rect &a, cv::Rect &b);
std::vector<cv::Rect> getPositives(std::vector<cv::Rect> &positives, std::vector<cv::Rect> &results, double simility);

#endif //THUNDER_UTILS_H
