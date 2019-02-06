#include "Calibration.h"

/**
 * Source file for the vision calibration program. this is where stuff happens
 * written by: Brach Knutson
 */

void Calibration::Update(cv::VideoCapture cap) {
    cv::Mat img = GetImage(cap);
    cv::imshow("Output", img);
    cv::waitKey(5);
}

string Calibration::Process(cv::VideoCapture cap) {
    cv::Mat img = GetImage(cap);
    cv::Mat out; //output image with drawn contours
    cv::Mat bin;

    img.copyTo(out);

    cv::cvtColor(img, img, cv::COLOR_BGR2GRAY);
    cv::inRange(img, cv::Scalar(254,254,254), cv::Scalar(255,255,255), bin);

    vector <vector <Point> > contours;
    cv::findContours(bin, contours, cv::RETR_EXTERNAL, cv::CHAIN_APPROX_SIMPLE);
    cv::drawContours(out, contours, -1, cv::Scalar(0,255,0), 3);

    cv::imshow("Output", out);
    cv::waitKey(5);
    return "";
        
}

cv::Mat Calibration::GetImage(cv::VideoCapture cap) {
    cv::Mat img;
    if(Calibration::USE_IMAGE) { //means that we have to use target.jpg
        img = cv::imread("target.jpg");
    } else {
        cap.read(img);
    }

    return img;
}
