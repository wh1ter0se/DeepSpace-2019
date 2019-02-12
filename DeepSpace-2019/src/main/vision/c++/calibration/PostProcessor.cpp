#include "Calibration.h"

/**
 * source file for the postprocessor for vision depth calibration.
 */


int PostProcessor::Update(cv::VideoCapture cap, int known_height, int focal_height, int error_correct, int known_distance) {

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

        if(abs(rectAngle) > 4 && rectArea > 1500) {
            cv::circle(out, minRect.center, 3, cv::Scalar(0,0,255), 4);
            cv::rectangle(out, cv::boundingRect(contour), cv::Scalar(255,0,0), 3);

            validContours.push_back(contour);
        }
    }

    //if we got 2 valid contours then look for distance

    if(validContours.size() > 0 && validContours.size() < 3) {
        //find which one is left, and which one is right
        cv::RotatedRect leftRect = cv::minAreaRect(validContours[0]); //for distance we will only focus on left rect for efficency purposes
        int distance = 0;


        //calculate the distance between the target and the camera
        int target_height = 0;
        if(leftRect.size.height > leftRect.size.width) 
            target_height = leftRect.size.height;
        else
            target_height = leftRect.size.width;

        distance = (known_height * focal_height) / target_height;
        

        int error = known_distance - distance;
        error *= error_correct;
        cout << "distance: " << distance << ", error: " << error << "\n";
        distance += error;
        

        cv::imshow("Output", out);
        cv::waitKey(5);


        return distance;
    }

    return -1;
}
