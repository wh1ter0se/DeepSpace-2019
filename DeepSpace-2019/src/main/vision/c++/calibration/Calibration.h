#ifndef CALIBRATION_H
#define CALIBRATION_H


#include <iostream>
#include "opencv2/opencv.hpp"
#include "gtk-2.0/gtk/gtk.h"


using namespace std;
using namespace cv;

class Calibration {
    public:
    static const bool USE_IMAGE = false; //when true uses image "target.jpg"

    //called every frame to update the output image
    static void Update(cv::VideoCapture cap);

    //called to process an image, returns the string to put on the label
    static string Process(cv::VideoCapture cap);

    private:
    static double GetAspectRatio(int width, int height);
    static int WhichIsGreater(int num1, int num2);
    static cv::Mat GetImage(cv::VideoCapture cap);
};

class Tuning {
    public:
    Tuning(cv::VideoCapture cap);

    //gtk widgets needed for the window
    
    
    private:
    cv::VideoCapture cap;

};

#endif
