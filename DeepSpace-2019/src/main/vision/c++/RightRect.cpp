#include "Vision.h"

/**
 * Source file for RightRect class.
 * Written by: Brach Knutson
 */
 
using namespace std;
using namespace cv;

RightRect::RightRect(cv::RotatedRect rect) {
	this->rect = rect;
}

/**
 * Returns whether the rect is elgible to be used or not.
 */
bool RightRect::isElgible() {
	int area = this->rect.size.width * this->rect.size.height;
	bool area_is_good = (area > Settings::SINGLE_CONTOUR_MIN_AREA);
	
	//now make sure that the contour is a right side angle
	int angle_high = Settings::RIGHT_ANGLE + Settings::ANGLE_ERROR;
	int angle_low  = Settings::RIGHT_ANGLE - Settings::ANGLE_ERROR;
	int angle = this->rect.angle;
	
	bool angle_is_good = (angle > angle_low && angle < angle_high);
		
	return (area_is_good && angle_is_good);
}

/**
 * Returns the center of the Rect
 */
cv::Point RightRect::center() {
	return this->rect.center;
}

/**
 * Returns the center of the target using data using data from the object.
 */
 cv::Point RightRect::target_center() {
	 double target_x_offset = (double) (Settings::DIST_BETWEEN_RECTS / 2.0);
	 double pixels_to_inches = Settings::KNOWN_HEIGHT / Util::WhichIsBigger(this->rect.size.width, this->rect.size.height);
	 
	 int offset_pixels = target_x_offset * pixels_to_inches;
	 
	 int new_x = this->rect.center.x - offset_pixels;
	 return cv::Point(new_x, this->rect.center.y);
 }

/**
 * Returns the robot's distance from the Rect.
 */
int RightRect::distance() {
	double target_dist = (double) ((Settings::KNOWN_HEIGHT * Settings::FOCAL_HEIGHT) / (double) this->rect.size.height);
    double error = Settings::CALIBRATED_DISTANCE - target_dist;
    error *= (double) Settings::ERROR_CORRECTION;
    target_dist += error;
    
    return target_dist;
}

/**
 * Returns the height of the target in pixels
 */
int RightRect::height() {
	return Util::WhichIsBigger(this->rect.size.width, this->rect.size.height);
}

/**
 * Returns the angle that the robot must turn to center itself with the Rect.
 */
double RightRect::angle() {
	//get the pixels to inches scalar
	double pixels_to_inches = Settings::KNOWN_HEIGHT / Util::WhichIsBigger(this->rect.size.width, this->rect.size.height);
	
	//get the offsets for center and target
	
	cv::Point target_offset = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, pixels_to_inches);
	target_offset           = Util::computeOffsets(target_offset.x, target_offset.y, -5.0, 0.0, pixels_to_inches);
		
	int x_offset = target_center().x - target_offset.x;
	x_offset *= pixels_to_inches;
	
	double target_angle = atan((double) x_offset / (double) distance());
	target_angle *= (180 / M_PI); //convert to degrees (NO radians allowed here!!);
	
	target_angle *= (double) Settings::RIGHT_ANGLE_ONLY_DAMPER;
	
	return target_angle;
}
