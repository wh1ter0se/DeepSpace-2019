/**
 * The class where all the vision magic happens...
 * Written by Brach Knutson
 */

#include "Vision.h"

using namespace cv;
using namespace std;

/**
 * Constructs a new postprocessor.
 */
PostProcessor::PostProcessor() {
    this->cap = cv::VideoCapture(0); //creates a new video streaming utility
    cap.set(cv::CAP_PROP_FRAME_WIDTH, Settings::CAMERA_RESOLUTION_X);
    cap.set(cv::CAP_PROP_FRAME_HEIGHT, Settings::CAMERA_RESOLUTION_Y);

    if(Settings::DEBUG)
        cout << "Postprocessor starting in debugging mode.";
    else
        cout << "Postprocessor starting in running mode.";
}

/**
 * The main loop for the postprocessor. This takes the images and looks for the targets
 * within them. NOTE: this method loops infinitely and will only stop when PostProcessor::stop
 * is set to true.
 */
void PostProcessor::Loop() {
    while(!stop) {
        cv::Mat img; //image we will be processing
        cv::Mat out; //color image we output
        bool readSuccess = cap.read(img); //reads an image to process from our video stream
        //img = cv::imread("target_3.jpg");
        img.copyTo(out);
        //bool readSuccess=true;

        vector<PairData> pairedRects; //rectangles that have found a pair in target
        vector<RotatedRect> unpairedRects; //rectangles that are invalid or have not found buddy

        if(readSuccess) { //only continue processing if we could actually read the image
            cv::cvtColor(img, img, cv::COLOR_BGR2GRAY);
            cv::inRange(img, cv::Scalar(254, 254 ,254), cv::Scalar(255,255,255), img);

            vector< vector <Point>> contours; //output array for edge detection
            cv::findContours(img, contours, cv::RETR_EXTERNAL, cv::CHAIN_APPROX_SIMPLE);
            cv::drawContours(out, contours, -1, cv::Scalar(0,255,0), 3);

            for(int i=0; i<contours.size(); i++) {
                vector<Point> contour = contours[i]; //the contour we are processing
                cv::RotatedRect minRect = cv::minAreaRect(contour);

                if(Util::IsElgible(minRect)) { //the contour is elgible to be part of a target!
                    if(Settings::DEBUG) {
                        cv::Rect boxRect = minRect.boundingRect();
                        cv::rectangle(out, boxRect, cv::Scalar(255,0,0), 2);
                    }

                    //try to find any pairs that might be there
                    bool pairFound = false;
                    for(int a=0; a<unpairedRects.size(); a++) {
                        cv::RotatedRect otherRect = unpairedRects[a];
                        if(Util::IsPair(minRect, otherRect)) { //ladies and gentlemen, we got em
                            PairData pair = PairData(otherRect, minRect);
                            pairedRects.push_back(pair); //adds our new pair to the array of pairs
                        }
                    }
                    
                    if(!pairFound)
                        unpairedRects.push_back(minRect); //adds the rect to the array becuase there were no pairs found

                }
            }

            if(Settings::DEBUG) {
                for(int a=0; a<pairedRects.size(); a++) {
                    cv::circle(out, pairedRects[a].center(), 3 , cv::Scalar(255,255,0), 5);
                }

                cv::imshow("Output", out);
                cv::waitKey(5);
            }

        } else {
            cout << "COULD NOT GRAB FROM CAMERA!!";
        }
    }
}

/**
 * Cleans up all resources used by the PostProcessor instance. This method should be called
 * before the instance is disposed.
 */
void PostProcessor::CleanUp() {
    stop = true;
    cap.release();
    cv::destroyAllWindows();
}
