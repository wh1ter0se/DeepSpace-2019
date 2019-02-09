#include "Calibration.h"

/**
 * Source file for the vision calibration program. this is where stuff happens
 * written by: Brach Knutson
 */

void Calibration::Update(cv::VideoCapture cap) {
    cv::Mat img = GetImage(cap);
    cv::imshow("Output", img);
    cv::waitKey(5);
}

string Calibration::Process(cv::VideoCapture cap) {
    string returnMessage = ""; //the message that this method will return at the end.

    double known_distance = 0;
    double known_height = 0;

    //first grab the distance from target so we can do distance calculations
    string dist_string = "";
    cout<< "How far away from the target are you(inches)? ";
    cin >> dist_string;
    cout << "\n";

    //grab the known height of one contour
    string height_string = "";
    cout << "What is the height of ONE contour of the target(inches)? ";
    cin >> height_string;
    cout << "\n";

    //now attempt to convert those values into ints
    known_distance = std::stod(dist_string);
    known_height = std::stod(height_string);
    
    cout << "known dist: " << known_distance << "\n";
    cout << "known height: " << known_height << "\n";

    cv::Mat img = GetImage(cap); //this is where we grab the image from the camera
    cv::Mat out; //output image with drawn contours
    cv::Mat bin;

    img.copyTo(out);

    cv::cvtColor(img, img, cv::COLOR_BGR2GRAY);
    cv::inRange(img, cv::Scalar(254,254,254), cv::Scalar(255,255,255), bin);

    vector <vector <Point> > contours; //contours we find in image
    vector <vector <Point> > validContours; //valid contours we process as targets (there should be 2)
    cv::findContours(bin, contours, cv::RETR_EXTERNAL, cv::CHAIN_APPROX_SIMPLE);
    cv::drawContours(out, contours, -1, cv::Scalar(0,255,0), 3);

    //now find valid contours

    cout << contours.size() << " Contours found.\n";

    for(int i=0; i<contours.size(); i++) {
        vector<Point> contour = contours[i];
        cv::RotatedRect rect = cv::minAreaRect(contour); //get the rotated rectange
        int area = rect.size.width * rect.size.height;

        if(abs(rect.angle) > 4 && area > 1500) { 
            // the contour is angled meaning it could be a target. area must also be more than 1500 to eliminate noise
            validContours.push_back(contour);
            cv::Rect rect = cv::boundingRect(contour);
            cv::rectangle(out, rect, cv::Scalar(255,0,0), 3);
        } else 
            cout << "Invalid contour found!\n";
    }

    cout << validContours.size() << " Valid contours found.\n";

    //now analyze the contours for data
    if(validContours.size() == 2) {
        //this can ONLY be done if we have two contours to look at, no more, no less
        cv::RotatedRect leftRect; //the leftmost rectangle
        cv::RotatedRect rightRect; //the rightmost rectangle
        
        //some things we will find using this information
        int leftAngle = 0; //the angle of the leftmost contour
        int rightAngle = 0; //the angle of the rightmost contour
        int area = 0; //the averaged area of the two contours
        int distance = 0; //the distance between the two contours
        double focalLength = 0; //the focal distance of the target, used for depth perception
        double aspect_ratio = 0.0; //the averaged aspect ratio of the contours

        //find the left and the right rect first
        cv::RotatedRect rect1 = cv::minAreaRect(validContours[0]);
        cv::RotatedRect rect2 = cv::minAreaRect(validContours[1]);
        if(rect1.center.x < rect2.center.x) { //rect 1 x has less value meaning it left
            leftRect = rect1;
            rightRect = rect2;
        } else { //no actually rect1 is right of rect2
            leftRect = rect2;
            rightRect = rect1;
        }
        
        //quickly draw a point in the center of the thing
        int center_x = (rect1.center.x + rect2.center.x) / 2;
        int center_y = (rect2.center.x + rect2.center.y) / 2;
        cv::circle(out, cv::Point(center_x, center_y), 3, cv::Scalar(255,255,0), 4);
        
        //now look at the information of the contours and format into a string
        
        //angle
        leftAngle = leftRect.angle;
        rightAngle = rightRect.angle;

        //area
        int area1 = leftRect.size.width * leftRect.size.height;
        int area2 = rightRect.size.width * rightRect.size.height;
        area = (area1 + area2) / 2; //average the areas to get our actual area

        //aspect ratio
        double aspect_1 = GetAspectRatio(leftRect.size.width, leftRect.size.height);
        double aspect_2 = GetAspectRatio(rightRect.size.width, rightRect.size.height);
        aspect_ratio = (aspect_1 + aspect_2) / 2.0;

        //distance
        distance = rightRect.center.x - leftRect.center.x;

        //depth perception calculations
        //(pixels * distance) / height

        int targetHeight = WhichIsGreater(leftRect.size.width, leftRect.size.height); //gets the actual height of the target (since opencv gets it mixed up sometimes)

        focalLength = (double) (targetHeight * known_distance) / known_height; //calculate focal distance.

        //how package the entire thing into a string that we return 
        returnMessage = "CONTOUR FEATURES:\n";
        returnMessage += "Target Left Contour Angle  : " + std::to_string(leftAngle) + "\n";
        returnMessage += "Target Right Contour Angle : " + std::to_string(rightAngle) + "\n";
        returnMessage += "Contour Area               : " + std::to_string(area) + "\n";
        returnMessage += "Contour Distance(pixels)   : " + std::to_string(distance) + "\n";
        returnMessage += "Contour Aspect Ratio       : " + std::to_string(aspect_ratio) + "\n";

        returnMessage += "\nDEPTH PERCEPTION SETTINGS:\n";
        returnMessage += "Known Distance             : " + std::to_string(known_distance) + "\n";
        returnMessage += "Known Height               : " + std::to_string(known_height) + "\n";
        returnMessage += "Focal Length               : " + std::to_string(focalLength) + "\n";


    } else {
        returnMessage = "The operation could not be completed, \nsince there was no clear potential target.\nPlease make sure that the target is the only thing on the screen.";
    }

    cv::imshow("Output", out);
    cv::waitKey(5);
    return returnMessage;
        
}

cv::Mat Calibration::GetImage(cv::VideoCapture cap) {
    cv::Mat img;
    if(Calibration::USE_IMAGE) { //means that we have to use target.jpg
        img = cv::imread("target.jpg");
    } else {
        cap.read(img);
    }

    return img;
}

/**
 * return an aspect ratio of < 0 given the width and height.
 */
double Calibration::GetAspectRatio(int w, int h) {
    double width = (double) w;
    double height = (double) h;

    //we want the smallest aspect ratio because that is how the program works.
    if(h < w) {
        double temp = height;
        height = width;
        width = temp;
    }
    double AR = width / height;
    return AR;
}

int Calibration::WhichIsGreater(int num1, int num2) {
    if(num1 > num2)
        return num1;

    return num2;
}
