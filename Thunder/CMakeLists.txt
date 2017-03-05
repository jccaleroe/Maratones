cmake_minimum_required(VERSION 3.6)
project(Thunder)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES EncryptedPassword.cpp)
add_executable(Thunder ${SOURCE_FILES})