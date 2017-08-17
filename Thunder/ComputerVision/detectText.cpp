//
// Created by juan on 14/08/17.
//

extern "C" {
#include "ccv.h"
#include "swtdetect.h"
}

#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>
#include "mser.h"
#include "extremal.h"
#include "utils.h"

using namespace std;
using namespace cv;

vector<Rect> words, bbox, extremals;

void detectText(char* file){
    words.clear();
    ccv_array_t *sbox = swt(file);
    bbox = mser(file);
    extremals = extremal(file);

    Mat img = imread(file, 1);
    //Mat img2 = imread(file, 1);
    //Mat img3 = imread(file, 1);


    if (sbox != nullptr) {
        for (int i = 0; i < sbox->rnum; i++) {
            auto *rect = (ccv_rect_t *) ccv_array_get(sbox, i);
            Rect h = Rect(rect->x, rect->y, rect->width, rect->height);
            //rectangle(img2, h, CV_RGB(0, 255, 0));
            words.push_back(h);
        }
        ccv_array_free(sbox);
    }

    cout << words.size() << endl;

    //imshow("swt", img2);

    extremals = filterIntersections(extremals, 0.3, 4);

    //for (const auto &a : extremals)
        //rectangle(img3, a, CV_RGB(0, 255, 0));
    //imshow("Extremals", img3);


    words.insert(words.end(), bbox.begin(), bbox.end());
    words.insert(words.end(), extremals.begin(), extremals.end());

    for (const auto &a : words)
        rectangle(img, a, CV_RGB(0, 255, 0));

    imshow("Words", img);
    waitKey(0);
}

int main(int argc, char* argv[]) {
    detectText(argv[1]);
}
