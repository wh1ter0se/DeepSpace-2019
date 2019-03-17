#include "Calibration.h"

/**
 * source file for the postprocessor for vision depth calibration.
 */


int PostProcessor::Update(cv::VideoCapture cap, double known_height, double focal_height, double error_correct, double known_distance) {

    cv::Mat img;
    cv::Mat out;

    img = Calibration::GetImage(cap);
    img.copyTo(out);

    cv::cvtColor(img, img, cv::COLOR_BGR2GRAY); //convert to grayscale so we can do edge detection
    
    vector <vector <Point> > contours;
    vector <vector <Point> > validContours;

    cv::findContours(img, contours, cv::RETR_EXTERNAL, cv::CHAIN_APPROX_SIMPLE);

    //find some elgible contours (for calibration, "elgible" is that it is angled and the area is at least 1500 px)
    for(int i=0; i<contours.size(); i++) {
        vector<Point> contour = contours[i];
        cv::RotatedRect minRect = cv::minAreaRect(contour);
        
        int rectArea = minRect.size.width * minRect.size.height;
        int rectAngle = minRect.angle;

        if(abs(rectAngle) > 4 && rectArea > 300) {
            cv::Rect box = cv::boundingRect(contour);
            cv::circle(out, minRect.center, 3, cv::Scalar(0,0,255), 4);
            cv::rectangle(out, box, cv::Scalar(255,0,0), 3);
            
            if(box.x > 25 && (252 - box.x > 25)) {
                validContours.push_back(contour);
                cv::circle(out, cv::Point(box.x,  box.y), 3, cv::Scalar(0,255,0), 4);
            }
        }
    }

    //if we got 2 valid contours then look for distance

    if(validContours.size() > 0 && validContours.size() < 3) {
        //find which one is left, and which one is right
        cv::RotatedRect leftRect = cv::minAreaRect(validContours[0]); //for distance we will only focus on left rect for efficency purposes
        double distance = 0;


        //calculate the distance between the target and the camera
        double target_height = 0;
        if(leftRect.size.height > leftRect.size.width) 
            target_height = (double) leftRect.size.height;
        else
            target_height = (double) leftRect.size.width;

        distance = (double) ((known_height * focal_height) / target_height);
        

        double error = known_distance - distance;
        error *= error_correct;
        distance += error;
        
        cout << "height: " << target_height << " " << "distance: " << distance << ", error: " << error << "\n";
        

        cv::imshow("Output", out);
        cv::waitKey(5);


        return (int) distance;
    }

    return -1;
}
