

#include <opencv2/highgui/highgui.hpp>
#include "iostream"

using namespace cv;

int main(int argc, char** argv){
    std::cout<<  argv[1] << std::endl;
    Mat img = imread(argv[1], -1);
    if (img.empty() ) {
        std:: cout << "lol";
        return -1;
    }
    namedWindow("ExampleMe2", WINDOW_AUTOSIZE);
    imshow("ExampleMe2", img);
    waitKey(0);
    destroyWindow("ExampleMe2");
    return 0;
}