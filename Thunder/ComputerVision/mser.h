//
// Created by juan on 14/08/17.
//

#ifndef THUNDER_MSER_H
#define THUNDER_MSER_H

#include <opencv2/opencv.hpp>

std::vector<cv::Rect> mser(cv::Mat img);
std::vector<cv::Rect> cropAndKnnMser(std::vector<cv::Rect> &swts, std::vector<cv::Rect> &msers, cv::Mat &img);
std::vector<cv::Rect> modeMser(std::vector<cv::Rect> &bbox, float alpha, float beta, float delta);

#endif //THUNDER_MSER_H
