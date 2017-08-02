#include<opencv2/highgui/highgui.hpp>
#include<opencv2/imgproc/imgproc.hpp>
#include<iostream>
#include "openVideo.h"

using namespace cv;

int	g_slider_position = 0;
int	g_run = 1, g_dontset = 0;	//start	out	in	single	step mode
VideoCapture g_cap;

void onTrackbarSlide(int pos, void*){
    g_cap.set( cv::CAP_PROP_POS_FRAMES, pos);
    if ( !g_dontset )
        g_run = 1;
    g_dontset = 0;
}

void openStreamVideo( VideoCapture & cap ){
    namedWindow("Camera", WINDOW_AUTOSIZE);
    Mat frame;
    for(;;) {
        cap.read(frame);
        if ( frame.empty() )
            break;
        imshow("Camera", frame);
        //if(waitKey(1) >= 0) break;
        //if ( waitKey(33) >= 0 ) break;
    }
}

void openVideo( VideoCapture & cap ){

    g_cap = cap;
    namedWindow("Example2_4", WINDOW_AUTOSIZE );

    int frames = (int) g_cap.get(CAP_PROP_FRAME_COUNT);
    int tmpw = (int) g_cap.get(CAP_PROP_FRAME_WIDTH);
    int tmph = (int) g_cap.get(CAP_PROP_FRAME_HEIGHT);

    std::cout << "Video has "	<< frames << " frames of dimensions("
         << tmpw << ", " << tmph	<< ").\n";

    createTrackbar("Position", "Example2_4", &g_slider_position, frames, onTrackbarSlide);
    Mat	frame;

    for(;;)	{
        if (g_run != 0 ){
            g_cap >> frame;
            if(frame.empty())
                break;
            int current_pos = (int)g_cap.get(CAP_PROP_POS_FRAMES);
            g_dontset = 1;
            setTrackbarPos("Position", "Example2_4", current_pos);
            imshow("Example2_4", frame);
            g_run = -1;
        }

        char c = (char) waitKey(10);
        if ( c == 's'){
            g_run = 1;
            std::cout << "Single step, run = " << g_run << std::endl;
        }
        if ( c == 'r'){
            g_run = -1;
            std::cout << "Run mode, run = " << g_run << std::endl;
        }
        if ( c == 27)
            break;
    }
}

//int	main( int argc,	char** argv	) {
//    g_cap.open(string(argv[1]));
//    openVideo(g_cap);
//}
