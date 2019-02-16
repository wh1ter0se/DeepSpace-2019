#ifndef SETTINGS_H
#define SETTINGS_H

class Settings {
    public:
    static const bool 
        DEBUG = true,
        USE_IMAGE = true;
                      
    //camera resolution (essential to pull the right image off the JeVois)
    static const int 
        CAMERA_RESOLUTION_X = 252,
        CAMERA_RESOLUTION_Y = 128;
        
    //offsets (inches)
    static const int 
        CAMERA_OFFSET_X = 12,
        CAMERA_OFFSET_Y = 3;
        
    //distance measurement settings
    static constexpr double 
        KNOWN_HEIGHT = 5.5,
        FOCAL_HEIGHT = 319.0,
        CALIBRATED_DISTANCE = 18,
        ERROR_CORRECTION = -0.14;
        
    //size settings (inches)
    static constexpr double
        ASPECT_RATIO = 0.34,
        ASPECT_RATIO_ERROR = 0.2;

    static const int 
        MIN_AREA = 1000;
    
    //angle settings (degrees)
    static const int
        LEFT_ANGLE = -14,
        RIGHT_ANGLE = -78,
        ANGLE_ERROR = 9;

    //distance settings (inches)
    static const int
        DISTANCE = 10,
        DISTANCE_ERROR = 5;


    static double Aspect_Ratio_Max();
    static double Aspect_Ratio_Min();

    static int Opposite_Angle(int angle);
    static int Closest_Angle(int angle);
};

#endif
