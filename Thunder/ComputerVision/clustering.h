//
// Created by juan on 25/08/17.
//

#ifndef THUNDER_CLUSTERING_H
#define THUNDER_CLUSTERING_H

#include <opencv2/opencv.hpp>

std::vector<cv::Rect> group(int k, std::vector<cv::Rect> &bbox);
std::vector<cv::Rect> modeClustering(std::vector<cv::Rect> &bbox, float alpha, float beta, float delta);

#endif //THUNDER_CLUSTERING_H
