#ifndef VISION_H
#define VISION_H


#include <iostream>
#include "netdb.h"
#include "string.h"
#include "unistd.h"
#include "sys/socket.h"
#include "sys/types.h"
#include "netinet/in.h"
#include "arpa/inet.h"
#include "opencv2/opencv.hpp"
#include "Settings.h"

using namespace cv;
using namespace std;


/**
 * A UDP sender utility that sends and recieves information to and from the RIO.
 */
class UDP {
    public:
    UDP(){};
    UDP(string dest_ip, int port);
    void Send(string msg);
    string Recieve();
    void Close();

    private:
    int sock; //sock fd returned by socket() call
    sockaddr_in server_address; //address of the server
};


/**
 * The PostProcessor is the thing that takes the camera data and produces an output from it. 
 * The PostProcessor is written with the assumption that a JeVois camera is being used and 
 * the input image is already in binary.
 */
class PostProcessor {
    public:
    PostProcessor();
    void Loop(); //main loop that processes images and gives outputs
    void CleanUp(); //cleans up the postprocessor before disposal.
    bool Stop() { return stop; }

    private:
    bool stop = false;
    UDP sender;
    cv::VideoCapture cap;
};

/**
 * A collection of methods that simply makes PostProcessor code easier to read. 
 */
class Util {
    public:
    static bool IsElgible(cv::RotatedRect rect); //tests rotated rect to see if it is good for the target
    static bool IsPair(cv::RotatedRect rect1, cv::RotatedRect rect2);
    static double returnTrueDistanceScalar(cv::RotatedRect rectangle);
    static int distance(cv::Point point1, cv::Point point2);
    static int WhichIsBigger(int num1, int num2);
    static cv::Point computeOffsets(int x, int y, double pixelsToInches);
    static cv::Point computeOffsets(int x, int y, double offsetX, double offsetY, double pixelsToInches);
};

/**
 * Represents a recognized target as a pair of rotated rectangles.
 */
class PairData {
    public:
    PairData () {};
    PairData(cv::RotatedRect rect1, cv::RotatedRect rect2);
    cv::Point center();
    int area();
    int height();
    int distanceFromCenter();
    double distance();
    double angle(double distance);

    private:
    cv::RotatedRect rect1;
    cv::RotatedRect rect2;
};

/**
 * Represents the right rectangle of a target. Used only when we can only see that right rect.
 */
 
class RightRect {
    public:
    RightRect() {};
    RightRect(cv::RotatedRect rect);
    
    bool isElgible();
    cv::Point center();
    cv::Point target_center();
    int distance();
    int height();
    double angle();
    
    private:
    cv::RotatedRect rect;
};


#endif
