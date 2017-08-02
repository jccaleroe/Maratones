//
// Created by juan on 17/02/17.
//

#include	<opencv2/opencv.hpp>
#include "iostream"

using namespace cv;

void smooth(const Mat & image){
    namedWindow("Example2-5-in", WINDOW_AUTOSIZE);
    namedWindow("Example2-5-out", WINDOW_AUTOSIZE);
    imshow("Example2-5-in", image);
    Mat out;
    //	Do	the	smoothing
    //	(	Note:	Could	use	GaussianBlur(),	blur(),	medianBlur() or	bilateralFilter(). )
    GaussianBlur(image, out, Size(5,5), 3, 4);
    GaussianBlur(out, out, Size(5,5), 3, 4);
    imshow("Example2-5-out", out);
    waitKey(0);
}

void half(const Mat & img){
    Mat img2;
    namedWindow("Example2-5-in", WINDOW_AUTOSIZE);
    namedWindow("Example2-5-out", WINDOW_AUTOSIZE);
    imshow("Example2-5-in", img);
    pyrDown(img, img2);
    imshow("Example2-5-out", img2);
    waitKey(0);
}

void grayAndCanny( const Mat & img ){
    Mat img_gray, img_canny;
    namedWindow("Gray", WINDOW_AUTOSIZE);
    namedWindow("Canny", WINDOW_AUTOSIZE);
    cvtColor(img, img_gray, COLOR_BGR2GRAY);
    imshow("Gray", img_gray);
    Canny( img_gray, img_canny, 10, 100, 3, true);
    imshow("Canny", img_canny);
    waitKey(0);
}

int main(int argc, char** argv){
    std::cout<<  argv[1] << std::endl;
    Mat img = imread(argv[1], -1), img2;
    if (img.empty() ) {
        std:: cout << "Wrong argument";
        return -1;
    }
    //grayAndCanny(img);
    //half(img);
    smooth(img);
    return 0;
}