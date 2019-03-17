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
    this->sender = UDP("10.36.95.18", 3695); //init our UDP sender so we can talk to the RIO.

    this->cap = cv::VideoCapture(0); //creates a new video streaming utility
    cap.set(cv::CAP_PROP_FRAME_WIDTH, Settings::CAMERA_RESOLUTION_X);
    cap.set(cv::CAP_PROP_FRAME_HEIGHT, Settings::CAMERA_RESOLUTION_Y);

    if(Settings::DEBUG)
        cout << "Postprocessor starting in debugging mode." << endl;
    else
        cout << "Postprocessor starting in running mode." << endl;
}

/**
 * The main loop for the postprocessor. This takes the images and looks for the targets
 * within them. NOTE: this method loops infinitely and will only stop when PostProcessor::stop
 * is set to true.
 */
void PostProcessor::Loop() {
    bool use_last_target = false; //true when we want to use the last known target to compute data
    bool locked = false;
    PairData lastTarget;
    RightRect lastRightRect;
    
    int lock_frames = 0;
    int last_dist = 0;
    
    string last_msg = "";
        
    while(!stop) {
        cv::Mat img; //image we will be processing
        cv::Mat out; //color image we output
        bool readSuccess = cap.read(img); //reads an image to process from our video stream
        //img = cv::imread("target_3.jpg");
        img.copyTo(out);
        //bool readSuccess=true;

        PairData biggestTarget; //the biggest target we find, which is what we will return to the RIO.
        bool targetFound = false;

        vector<PairData> pairedRects; //rectangles that have found a pair in target
        vector<RotatedRect> unpairedRects; //rectangles that are invalid or have not found buddy
        
        if(readSuccess) { //only continue processing if we could actually read the image
            cv::cvtColor(img, img, cv::COLOR_BGR2GRAY);
            cv::inRange(img, cv::Scalar(254, 254 ,254), cv::Scalar(255,255,255), img);

            vector< vector <Point>> contours; //output array for edge detection
            cv::findContours(img, contours, cv::RETR_EXTERNAL, cv::CHAIN_APPROX_SIMPLE);

            for(int i=0; i<contours.size(); i++) {
                vector<Point> contour = contours[i]; //the contour we are processing
                cv::RotatedRect minRect = cv::minAreaRect(contour);

                if(Util::IsElgible(minRect)) { //the contour is elgible to be part of a target!
                    if(Settings::DEBUG) {
                        cv::Rect boxRect = minRect.boundingRect();
                        cv::rectangle(out, boxRect, cv::Scalar(255,0,0), 2);
                        cv::circle(out, cv::Point(minRect.center.x, minRect.center.y), 3, cv::Scalar(0,0,255), 4);
                    }

                    //try to find any pairs that might be there
                    bool pairFound = false;
                    for(int a=0; a<unpairedRects.size(); a++) {
                        cv::RotatedRect otherRect = unpairedRects[a];
                        if(Util::IsPair(minRect, otherRect)) { //ladies and gentlemen, we got em
                            PairData pair = PairData(otherRect, minRect);
                            pairedRects.push_back(pair); //adds our new pair to the array of pairs
                            if(pair.area() > biggestTarget.area() && !Settings::DOCK_USING_CLOSEST_TO_CENTER)
                                biggestTarget = pair;
                                
                            if(Settings::DOCK_USING_CLOSEST_TO_CENTER) {
                                if(pair.distanceFromCenter() < biggestTarget.distanceFromCenter() || !targetFound) {
                                    biggestTarget = pair;
                                    targetFound = true;
                                }
                            }
                        }
                    }
                    
                    if(!pairFound)
                        unpairedRects.push_back(minRect); //adds the rect to the array becuase there were no pairs found

                }
            }
            
            //now send to the RIO
            string sendToRIO = "";
            int target_x = -1;
            int target_y = -1;
            double target_dist = -1;
            double target_angle = 180;
            
            ////look for paired and unpaired targets. If we can find a paired and right rect, we use the right rect
            bool use_paired_rects = true; //when false will use unpaired rects only
            //if(unpairedRects.size() % 2 == 1 && pairedRects.size() > 0) { //there is at least one unpaired rect and one target
                //loop through paired rects and see if they are past MULTIPLE_TARGET_THRESHOLD
                //cout << " possible ";
                //cout.flush();
                //for(int i=0; i<pairedRects.size(); i++) {
                    //if(pairedRects[i].center().x < Settings::MULTIPLE_CONTOUR_IGNORE_THRESHOLD)
                        //use_paired_rects = true;
                //}
            //} else {
                //use_paired_rects = true;
                //cout << " NOT ";
                //cout.flush();
            //}
            
            if(pairedRects.size() > 0 && use_paired_rects) {
                //compute the data for the biggest target
                cv::Point target_center = biggestTarget.center();
                target_x = target_center.x; //grab center of target 
                target_y = target_center.y;
                target_dist = biggestTarget.distance();
                target_angle = biggestTarget.angle(target_dist);
                
                last_dist = target_dist;
                
                lastTarget = biggestTarget; //that way if we lose the left side we can still look at the right 
                use_last_target = true;
                
            }
                
            if(pairedRects.size() == 0) {
                //find the x, y, distance and angle of the right target
                bool targetFound = false;
                //only use right rects if there is a right rect 
                //if(unpairedRects.size() > pairedRects.size() * 2) {
                    for(int i=0; i<unpairedRects.size(); i++) {
                        RightRect target = RightRect(unpairedRects[i]);
                        if(target.isElgible()) {
                            cv::Point target_center = target.center();
                            //cout << "right rect" << endl;
                            cout.flush();
                            target_x = target_center.x;
                            target_y = target_center.y;
                            target_dist = target.distance();
                            target_angle = target.angle();
                        
                            last_dist = target_dist;
                        
                            targetFound = true;
                            lastRightRect = target;
                        
                            //if(target.distance() < Settings::MULTIPLE_CONTOUR_TARGET_LOCK) {
                                //target_x = -1;
                                //target_y = -1;
                                //target_dist = -1;
                                //target_angle = 180; //lock for colton to just drive
                                //locked = true;
                            
                                ////cout << "LOCKING. last distance: " << last_dist << endl;
                                ////cout.flush(); 
                            
                            //}
                    
                            if(Settings::DEBUG)
                                //draw a circle in the center of the contour we are using
                                cv::circle(out, cv::Point(target_x, target_y), 3, cv::Scalar(255,255,0), 5);
                                
                                //cv::Point robot_center = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, (Settings::KNOWN_HEIGHT / target.height()));
                                //robot_center           = Util::computeOffsets(robot_center.x, robot_center.y, Settings::CAMERA_OFFSET_RIGHTRECT_X, 0, (Settings::KNOWN_HEIGHT / target.height()));
                                //cv::circle(out, robot_center, 3, cv::Scalar(255, 0, 255), 5);
                            
                            break; //we found a good rectangle, we don't need anything else, so break the loop
                        }
                    //}
                }
            }
            
            if(target_x < 0) {//lock up because we cannot see a target
                locked = true;
                //cout << "LOCKING: NULL" << endl;
            }
            
            if(locked) {
                
                //cout << "LOCKED!" << endl; 
                
                if(target_x > -1) { //there is indeed a target in view
                    lock_frames++;
                    //cout << "GOOD FRAME: " << lock_frames << endl;
                    if(lock_frames >= Settings::CONSECUTIVE_FRAME_UNLOCK) {
                        lock_frames = 0;
                        locked = false;
                        //cout << "UNLOCKING" << endl;
                    }
                } else {
                    lock_frames = 0;
                }
                
                target_x = -1;
                target_y = -1;
                target_dist = -1;
                target_angle = 180;
                //cout << "NULLIFYING COORDS" << endl;
                
            }
            
            
            //cout << " distance: " << last_dist << endl;
            //cout.flush();
            
            //x, y, h, d : the string values for the values to send to the RIO
            string x = std::to_string(target_x);
            string y = std::to_string(target_y);
            string d = std::to_string((int) target_dist);
            string a = std::to_string((int) target_angle);
            sendToRIO = ":" + x + "," + y + "," + d + "," + a + ";";
            
            //cout << sendToRIO << endl;
            //cout.flush(); 
            
            //UDP send to RIO here 
            this->sender.Send(sendToRIO);

            if(Settings::DEBUG) {
                //puttext(img, text, point, font, scale, color)
                cv::drawContours(out, contours, -1, cv::Scalar(0,255,0), 3);
                cv::putText(out, sendToRIO, cv::Point(5,25), cv::FONT_HERSHEY_SIMPLEX, 0.5 , cv::Scalar(0,0,255), 2);

                for(int a=0; a<pairedRects.size(); a++) {
                    cv::circle(out, pairedRects[a].center(), 3 , cv::Scalar(255,255,0), 5);
                }
                
                //put a yellow circle around the box we are using to target
                cv::circle(out, biggestTarget.center(), 2, cv::Scalar(0, 255, 255), 4);
                
                //put a purple circle where the robot center should be
                cv::Point robot_center = Util::computeOffsets(Settings::CAMERA_RESOLUTION_X / 2, Settings::CAMERA_RESOLUTION_Y / 2, (double) (5.5 / biggestTarget.height()));
                cv::circle(out, robot_center, 3, cv::Scalar(255, 0, 255), 5);
                
                //cv::line(out, cv::Point(Settings::MULTIPLE_CONTOUR_IGNORE_THRESHOLD, 0), cv::Point(Settings::MULTIPLE_CONTOUR_IGNORE_THRESHOLD, 50), cv::Scalar(255, 0, 255), 3);
                
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
