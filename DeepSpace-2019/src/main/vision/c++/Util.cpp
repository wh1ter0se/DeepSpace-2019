#include "Vision.h"

/**
 * Utils file for 2019 DeepSpace vision
 * Written by: Brach Knutson
 */

using namespace std;

/** Tests a rotated rectangle to see if it could be part of a 2019 target. 
 * @return true if yes, false if no.
 */
bool Util::IsElgible(cv::RotatedRect rect) {
    bool ratioTest;
    bool areaTest;
    bool angleTest;

    double width = (double) rect.size.width;
    double height = (double) rect.size.height;

    //the aspect ratio test
    double aspect_ratio = 0.0;
    
    if(width > height) {
        aspect_ratio = height/width;
    } else {
        aspect_ratio = width/height; //make sure aspect ratio is consistent so we don't have to have two different settings 
    }
    double ARMax = Settings::Aspect_Ratio_Max();
    double ARMin = Settings::Aspect_Ratio_Min();
    ratioTest = (aspect_ratio < ARMax && aspect_ratio > ARMin);

    //the area test
    int area = (int) (width * height);
    areaTest = (area > Settings::MIN_AREA);
    

    //the angle test
    int left_angle_max = Settings::LEFT_ANGLE + Settings::ANGLE_ERROR;
    int left_angle_min = Settings::LEFT_ANGLE - Settings::ANGLE_ERROR;
    int right_angle_max = Settings::RIGHT_ANGLE + Settings::ANGLE_ERROR;
    int right_angle_min = Settings::RIGHT_ANGLE - Settings::ANGLE_ERROR;
    int angle = rect.angle;

    angleTest = (angle < left_angle_max && angle > left_angle_min)
              || (angle < right_angle_max && angle > right_angle_min); //true is angle is cool, false if no.
    
    return ratioTest && areaTest && angleTest; //returns true if both tests are true, otherwise returns false
}

/**
 * compares the two rects to see if they are pairs or not
 * @return true if the two rects are pairs, false if no
 */
bool Util::IsPair(cv::RotatedRect rect1, cv::RotatedRect rect2) {
    //run an angle test to see if they are opposites. using Settings:Opposite_angle, we can simply feed the angles into the method and see if they are different

    int rect1_opposite_angle = Settings::Opposite_Angle(rect1.angle);
    int rect2_opposite_angle = Settings::Opposite_Angle(rect2.angle);
    bool Opposite_Angles = (rect1_opposite_angle != rect2_opposite_angle); //if the angles are different, they are opposite, since the rects have already passed the test.
    //make sure that the contours are in the right places (i.e. Right on the right, left on left.)
    if(Opposite_Angles) {
        bool angles_are_correct = false; //will be true if the contours are on the correct sides of each other
        int distance_between_rects = rect1.center.x - rect2.center.x; //will be positive if rect1 is leftmost, negative if rect2 is leftmost

        if(distance_between_rects > 0) //positive, rect1 is the leftmost rect
            angles_are_correct = Settings::Closest_Angle(rect2.angle) == Settings::LEFT_ANGLE;
        else
            angles_are_correct = Settings::Closest_Angle(rect1.angle) == Settings::LEFT_ANGLE;

        //now we can move on to the distance testing
        if(angles_are_correct) {
            double pixels_to_inches = Util::returnTrueDistanceScalar(rect1);
                   pixels_to_inches += Util::returnTrueDistanceScalar(rect2);
            pixels_to_inches /= 2; //average the scalar for maximum happiness
            distance_between_rects = abs(distance_between_rects); //use our var from before to measure distance. Multiply by our scalar to measure the relationship
            double distance_in_inches = (double) (distance_between_rects * pixels_to_inches); //scale the distance to inches 
            //quickly define our range for distance here
            double distance_high = Settings::DISTANCE + Settings::DISTANCE_ERROR;
            double distance_low  = Settings::DISTANCE - Settings::DISTANCE_ERROR;
            if(distance_in_inches < distance_high && distance_in_inches > distance_low)
                return true;
        }
    }

    return false;
}


/**
 * Assuming the passed rotated rect is a possible target, returns a scalar converting pixels
 * to inches.
 * @param rectangle the cv::RotatedRect to use to calculate the scale
 * @return a scalar value for converting pixels to inches
 */
double Util::returnTrueDistanceScalar(cv::RotatedRect rectangle) {
    
    if(rectangle.size.width < rectangle.size.height) //the aspect ratio is correct, so just go on normally....
        return 5.5 / rectangle.size.height;
    else 
        return 5.5 / rectangle.size.width;
}

/**
 * Computes the distance between the two given points, point1 and point2
 * @return the horizontal distance between the two points.
 */
int Util::distance(cv::Point point1, cv::Point point2) {
    return abs(point1.x - point2.x);
}

/**
 * Compares the two given numbers and returns the biggest one.
 * @return the biggest number, either num1 or num2
 */
int Util::WhichIsBigger(int num1, int num2) {
    if(num1 > num2)
        return num1;
        
    return num2;
}

/**
 * Computes the horizontal and vertical offests of the given point (x and y), and the pixels to inches scalar.
 * Assumes that the camera offset settings in Settings.h are being used as the offsets.
 * @param x              The x-coordinate of the point to offset.
 * @param y              The y-coordinate of the point to offset.
 * @param pixelsToInches A pixels to inches scalar to use for offset.
 * @return the result of the offset in a cv::Point 
 */
cv::Point Util::computeOffsets(int x, int y, double pixelsToInches) {
    int offsetX = Settings::CAMERA_OFFSET_X / pixelsToInches; //get number of pixels to offset the center 
    int offsetY = Settings::CAMERA_OFFSET_Y * pixelsToInches; //y pixels to offset the thing by
    
    int new_x = x - offsetX;
    int new_y = y - offsetY;
    
    new_x += 15;
    return cv::Point(new_x, new_y);
}

/**
 * Computes the horizontal and vertical offsets of the given point(x and y), offsetX, offsetY, and 
 * the pixels to inches scalar. Uses the given offset values instead of the ones in Settings.h
 * @param x              The x-coordinate of the point to offset.
 * @param y              The y-coordinate of the point to offset.
 * @param offsetX        the amount of desired horizontal offset in inches.
 * @param offsetY        the amount of desired vertical offset in inches.
 * @param pixelsToInches a pixels to inches scalar to use for offset.
 * @return the result of the offset in a cv::Point
 */
cv::Point Util::computeOffsets(int x, int y, double offsetX, double offsetY, double pixelsToInches) {
    double offX = offsetX / (double)pixelsToInches; //get number of pixels to offset the center 
    double offY = offsetY * (double)pixelsToInches; //y pixels to offset the thing by
    
    int new_x = x - offX;
    int new_y = y - offY;
    
    return cv::Point(new_x, new_y);
}
