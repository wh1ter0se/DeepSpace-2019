#ifndef SETTINGS_H
#define SETTINGS_H

class Settings {
    public:
    static const bool 
        DEBUG = true,
        USE_IMAGE = true,
        DOCK_USING_CLOSEST_TO_CENTER = true;
                      
    //camera resolution (essential to pull the right image off the JeVois)
    static const int 
        CAMERA_RESOLUTION_X = 252,
        CAMERA_RESOLUTION_Y = 128;
        
    //offsets (inches)
    static const int 
        CAMERA_OFFSET_X = 9,
        CAMERA_OFFSET_Y = 4;
        
    static constexpr double
        CAMERA_OFFSET_RIGHTRECT_X = 0;
        
    //distance measurement settings
    static constexpr double 
        KNOWN_HEIGHT = 5.5,
        FOCAL_HEIGHT = 191.0,
        DIST_BETWEEN_RECTS = 12.0,
        CALIBRATED_DISTANCE = 36.0,
        ERROR_CORRECTION = 0.15;
        
    //size settings (inches)
    static constexpr double
        ASPECT_RATIO = 0.55,
        ASPECT_RATIO_ERROR = 0.35;

    static const int 
        MIN_AREA = 5,
        SINGLE_CONTOUR_MIN_AREA = 500; //minimum area for the right contour when we are using right to calculate data
    
    //angle settings (degrees)
    static const int
        LEFT_ANGLE = -76,
        RIGHT_ANGLE = -16,
        ANGLE_ERROR = 13;
        
    static constexpr double
        RIGHT_ANGLE_ONLY_DAMPER = 0.25;

    //distance settings (inches) 
    static const int
        DISTANCE = 10,
        DISTANCE_ERROR = 5;
        
    //special case settings
    static const int 
        MULTIPLE_CONTOUR_IGNORE_THRESHOLD = 150,
        MULTIPLE_CONTOUR_TARGET_LOCK = 42,
        CONSECUTIVE_FRAME_UNLOCK = 3;


    static double Aspect_Ratio_Max();
    static double Aspect_Ratio_Min();

    static int Opposite_Angle(int angle);
    static int Closest_Angle(int angle);
};

#endif
