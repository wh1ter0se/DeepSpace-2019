class Settings {
    public:
    static const bool DEBUG = true,
                      USE_IMAGE = true;

    //size settings (inches)
    static constexpr double ASPECT_RATIO = 0.34,
                        ASPECT_RATIO_ERROR = 0.2;
    
    //angle settings (degrees)
    static const int LEFT_ANGLE = -14,
                     RIGHT_ANGLE = -78,
                     ANGLE_ERROR = 10;

    //distance settings (inches)
    static const int DISTANCE = 10,
                     DISTANCE_ERROR = 2;


    static double Aspect_Ratio_Max();
    static double Aspect_Ratio_Min();

    static int Opposite_Angle(int angle);
    static int Closest_Angle(int angle);
};