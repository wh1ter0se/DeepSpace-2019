#include "Vision.h"

/**
 * Source file for PairData Class.
 * Written by Brach Knutson
 */

using namespace cv;

/**
 * Creates a new PairData instance using the two given cv::RotatedRect objects.
 */
PairData::PairData(cv::RotatedRect rect1, cv::RotatedRect rect2) { 
    this->rect1 = rect1;
    this->rect2 = rect2;
}

/**
 * Calculates and returns the center of the target.
 * @return the center of the target in a cv::Point
 */
cv::Point PairData::center() {
    int x = rect1.center.x + rect2.center.x;
    int y = rect1.center.y + rect2.center.y;
    x /= 2;
    y /= 2;

    return cv::Point(x, y);

}

/**
 * Calculates and returns the  area of both rectangles of the target.
 * @returns the area of the rectangles.
 */
int PairData::area() {
    int area1 = (int) (rect1.size.width * rect1.size.height);
    int area2 = (int) (rect2.size.width * rect2.size.height);
    
    return area1 + area2;
}

/**
 * Returns the height of the target by averaging the height of the two contours.
 * @return the height of the rects.
 */
int PairData::height() {
    int height1 = Util::WhichIsBigger(rect1.size.width, rect1.size.height);
    int height2 = Util::WhichIsBigger(rect2.size.width, rect2.size.height);
    
    int avg = height1 + height2;
    avg /= 2;
    return avg;
}

/**
 * Calculates and returns the distance of the camera from the center of the robot in pixels.
 * @return the distance from the robot center in pixels
 */
int PairData::distanceFromCenter() {
    double pixelsToInches = Settings::KNOWN_HEIGHT / this->height(); //scalar to feed to computeOffsets
    cv::Point robot_center = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, pixelsToInches);
    return abs(robot_center.x - this->center().x);
}

/**
 * Calculates and returns the distance from the target in inches. 
 * @return the distance the robot needs to move to hit the target.
 */
double PairData::distance() {
    //calculate the distance in inches
    double target_dist = (double) ((Settings::KNOWN_HEIGHT * Settings::FOCAL_HEIGHT) / (double) this->height());
    
    //correct the distance (the farther away the distance is from where it was calibrated at, the less accurate it is)
    double error = Settings::CALIBRATED_DISTANCE - target_dist;
    error *= (double) Settings::ERROR_CORRECTION;

    //add the error correction factor
    target_dist += error;
    return target_dist;
}

/**
 * Calculates and returns the angle that the robot will need to turn in order to center itself with the target.
 * @return the angle the robot should turn to center the target
 */
double PairData::angle(double distance) {
    //compute angle to the target 
    double pixelsToInches = Settings::KNOWN_HEIGHT / this->height(); //get the amount of pixels per inch of the target

    //calculate the robot center so we can offset and get angle with
    cv::Point robot_center = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, pixelsToInches);
    int distance_between_target = this->center().x - robot_center.x;
    int distance_in_inches = distance_between_target * pixelsToInches;
                
    double target_angle = atan((double) distance_in_inches / distance); //use atan to get the actual angle
    target_angle *= (180 / M_PI); //convert to degrees (NO radians allowed here!!);
}
