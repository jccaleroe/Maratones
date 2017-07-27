cmake_minimum_required(VERSION 3.6)
project(Thunder)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES other/TableDecoration.cpp KhanTopological.cpp Combinations.cpp MatrixChain.cpp other/Knapsack.cpp BridgeEdge.cpp)
add_executable(Thunder ${SOURCE_FILES} lcs.cpp)