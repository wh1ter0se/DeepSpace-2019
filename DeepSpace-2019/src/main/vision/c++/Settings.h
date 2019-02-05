class Settings {
    public:
    static const bool 
        DEBUG = true,
        USE_IMAGE = true;
                      
    //camera resolution (essential to pull the right image off the JeVois)
    static const int 
        CAMERA_RESOLUTION_X = 252,
        CAMERA_RESOLUTION_Y = 128;

    //size settings (inches)
    static constexpr double
        ASPECT_RATIO = 0.34,
        ASPECT_RATIO_ERROR = 0.2;

    static const int 
        AREA = 500,
        AREA_ERROR = 1000;
    
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

    static int Area_Max();
    static int Area_Min();

    static int Opposite_Angle(int angle);
    static int Closest_Angle(int angle);
};
