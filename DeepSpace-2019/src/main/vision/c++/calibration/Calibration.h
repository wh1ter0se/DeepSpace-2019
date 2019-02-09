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
    
    private:
    cv::VideoCapture cap;
    
    static void Update();
    static void Destroy();
    static gboolean delete_event(GtkWidget *widget, GdkEvent *event, gpointer pointer);
    
    //gtk widgets needed for the window
    GtkWidget *window; //the main window everything goes in
    GtkWidget *content_pane; //the box that displays all contents of window
    GtkWidget *slider_focal_distance; //the slider where focal distance is adjusted.
    GtkWidget *slider_error_correction; //the slider where error correction is adjusted.
    GtkWidget *exit_button; //the button that closes the window and disposes the object.
    
};

#endif
