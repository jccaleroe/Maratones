cmake_minimum_required(VERSION 3.6)
project(Thunder)

set(CMAKE_CXX_STANDARD 11)

find_package(OpenCV REQUIRED)
include_directories(${OpenCV_INCLUDE_DIRS})

set(SOURCE_FILES ComputerVision/LearningOpenCV/chap1/smoothImage.cpp)
#set(SOURCE_FILES ComputerVision/mser/textDetection.cpp)
add_executable(Thunder ${SOURCE_FILES})

target_link_libraries(Thunder ${OpenCV_LIBS})