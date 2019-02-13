#include "Settings.h"

/**
 * Settings source file. Simply defines methods for returning maxes and mins
 */


double Settings::Aspect_Ratio_Max() {
    return Settings::ASPECT_RATIO + Settings::ASPECT_RATIO_ERROR;
}

double Settings::Aspect_Ratio_Min() {
    return Settings::ASPECT_RATIO - Settings::ASPECT_RATIO_ERROR;
}

/**
 * Returns the opposite angle of the given angle in terms of the target.
 * For example, if an angle in range with the right angle is given, the value of the left angle
 * is returned.
 * @return The opposite angle of the given angle. Returns the given angle if no opposite can be found.
 */
int Settings::Opposite_Angle(int angle) {
    int left_high = Settings::LEFT_ANGLE + Settings::ANGLE_ERROR;
    int left_low  = Settings::LEFT_ANGLE - Settings::ANGLE_ERROR;

    int right_high = Settings::RIGHT_ANGLE + Settings::ANGLE_ERROR;
    int right_low  = Settings::RIGHT_ANGLE - Settings::ANGLE_ERROR;

    //check to see if the angle is in range of any set angles
    if(angle < left_high && angle > left_low)
        return Settings::RIGHT_ANGLE; //returns right if the angle is left
    
    else if(angle < right_high && angle > right_low)
        return Settings::LEFT_ANGLE; //returns left if the angle is right

    else 
        return angle; //returns the angle if the angle is not left or right
}

/**
 * Takes the given angle and returns the set angle it is in range with (LEFT_ANGLE or RIGHT_ANGLE)
 * @return The angle that is in range with the given int. Returns the given int if it is not in range with anything.
 */
int Settings::Closest_Angle(int angle) {
    int left_high = Settings::LEFT_ANGLE + Settings::ANGLE_ERROR;
    int left_low  = Settings::LEFT_ANGLE - Settings::ANGLE_ERROR;

    int right_high = Settings::RIGHT_ANGLE + Settings::ANGLE_ERROR;
    int right_low  = Settings::RIGHT_ANGLE - Settings::ANGLE_ERROR;

    if(angle < left_high && angle > left_low)
        return Settings::LEFT_ANGLE;
    else if(angle < right_high && angle > right_low)
        return Settings::RIGHT_ANGLE;
    else
        return angle;
}
