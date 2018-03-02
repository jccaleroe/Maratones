cmake_minimum_required(VERSION 3.6)
project(Thunder)

set(CMAKE_CXX_STANDARD 11)

#find_package(OpenCV REQUIRED)

#link_directories("ComputerVision/ccv/lib")

#include_directories(${OpenCV_INCLUDE_DIRS})
#include_directories("ComputerVision/ccv/lib")
#set(SOURCE_FILES ComputerVision/mser.cpp ComputerVision/swtdetect.c ComputerVision/detectText.cpp
#        ComputerVision/textdetection.cpp ComputerVision/filters.cpp ComputerVision/clustering.cpp)

set(SOURCE_FILES Lothric/template.cpp)

#link_libraries("ccv" "m" "jpeg" "png")

add_executable(Thunder ${SOURCE_FILES})

#target_link_libraries(Thunder ${OpenCV_LIBS})

#link_directories("/home/cluster/opencv/include")
#include_directories("/home/cluster/opencv/include")
#link_libraries("opencv_core" "opencv_text" "opencv_highgui" "opencv_imgproc" "opencv_imgcodecs")
