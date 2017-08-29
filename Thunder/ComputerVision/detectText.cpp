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
#include "filters.h"
#include "extremal.h"

using namespace std;
using namespace cv;

vector<Rect> words, bbox, extremals;
string a, b, c, d;

void showImages(const char* file){
    //cout << words.size() << endl;
    Mat img = imread(file, 1);
    for (const auto &a : words)
        rectangle(img, a, CV_RGB(255, 0, 0));

    imshow("Words", img);
    waitKey(0);
}

void writeFile(int f){
    string tmp;
    tmp.append(c);
    tmp.append(to_string(f));
    tmp.append(d);
    freopen(tmp.c_str(), "w", stdout);
    for (auto &w : words)
        cout << w.x << "," << w.y << "," << w.x+w.width << "," << w.y << "," << w.x+w.width << "," << w.y+w.height
             << "," << w.x << "," << w.y+w.height << "," << 0.5 << "\n";
}

void swtHelper(const char* file){
    ccv_array_t *sbox = swt(file);
    //Mat img2 = imread(file, 1);

    if (sbox != nullptr) {
        for (int i = 0; i < sbox->rnum; i++) {
            auto *rect = (ccv_rect_t *) ccv_array_get(sbox, i);
            Rect h = Rect(rect->x, rect->y, rect->width, rect->height);
            //rectangle(img2, h, CV_RGB(0, 255, 0));
            words.push_back(h);
        }
        ccv_array_free(sbox);
    }
    //imshow("swt", img2);
}

void detectText(const char* file, int f){
    words.clear();
    Mat img = imread(file, 1);
    //swtHelper(file);

    extremals = extremal(file);
    extremals = filterIntersections(extremals, 0.1, 6);

    //bbox = mser(img);

    words.insert(words.end(), extremals.begin(), extremals.end());
    //words = greatFilter(words, img.rows, img.cols, 0.3);
    words.insert(words.end(), bbox.begin(), bbox.end());
    words = filterWords(words, 0.8);

    writeFile(f);

    showImages(file);
}

void icdar(){
    a = "images/img_", b = ".jpg", c = "res/res_img_", d = ".txt";
    string tmp;
    for (int i = 1; i <= 92; i++){
        tmp.clear();
        tmp.append(a);
        tmp.append(to_string(i));
        tmp.append(b);
        detectText(tmp.c_str(), i);
    }
}

int main(int argc, char* argv[]) {
    detectText(argv[1], 1);
    icdar();
}
