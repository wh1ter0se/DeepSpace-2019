#include "Vision.h"

/**
 * Source file for PairData Class.
 * Written by Brach Knutson
 */

using namespace cv;

PairData::PairData(cv::RotatedRect rect1, cv::RotatedRect rect2) { 
    this->rect1 = rect1;
    this->rect2 = rect2;
}

cv::Point PairData::center() {
    int x = rect1.center.x + rect2.center.x;
    int y = rect1.center.y + rect2.center.y;
    x /= 2;
    y /= 2;

    return cv::Point(x, y);

}

int PairData::area() {
    int area1 = (int) (rect1.size.width * rect1.size.height);
    int area2 = (int) (rect2.size.width * rect2.size.height);
    
    return area1 + area2;
}

/**
 * Returns the height of the target by averaging the height of the two contours.
 */
int PairData::height() {
    int height1 = Util::WhichIsBigger(rect1.size.width, rect1.size.height);
    int height2 = Util::WhichIsBigger(rect2.size.width, rect2.size.height);
    
    int avg = height1 + height2;
    avg /= 2;
    return avg;
}

int PairData::distanceFromCenter() {
    double pixelsToInches = Settings::KNOWN_HEIGHT / this->height();
    cv::Point robot_center = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, pixelsToInches);
    return abs(robot_center.x - this->center().x);
}

double PairData::distance() {
    double target_dist = (double) ((Settings::KNOWN_HEIGHT * Settings::FOCAL_HEIGHT) / (double) this->height());
    double error = Settings::CALIBRATED_DISTANCE - target_dist;
    error *= (double) Settings::ERROR_CORRECTION;
    target_dist += error;
    return (int) target_dist;
}

double PairData::angle(double distance) {
    //compute angle to the target 
    double pixelsToInches = Settings::KNOWN_HEIGHT / this->height(); //get the amount of pixels per inch of the target

    cv::Point robot_center = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, pixelsToInches);
    int distance_between_target = this->center().x - robot_center.x;
    int distance_in_inches = distance_between_target * pixelsToInches;
                
    double target_angle = atan((double) distance_in_inches / distance);
    target_angle *= (180 / M_PI); //convert to degrees (NO radians allowed here!!);
}
