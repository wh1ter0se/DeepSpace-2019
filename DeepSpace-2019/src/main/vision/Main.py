from __future__ import division

import cv2
import numpy
import time
import socket #for UDP

import Settings
import Util

if Settings.DEBUG: import UI # we only want to see UI if we are debugging

Stream = None
sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP

def Capture():
    #gets image using method in settings
    if Settings.IMAGE_CAPTURE_STREAM:
        ret, img = Stream.read()
        return img

    else:
        img = cv2.imread("target_3.jpg")
        return img


#compares the two passed contours and returns if they are in fact pairs
#@param Data1 the contourData to be compared with
#@param Data2 the contourData to compare with Data1
def CompareForPairing(Data1, Data2):
    #confirm that the angles are correct, fist find the leftmost contour
    LeftmostContour = None
    RightmostContour = None
    if Data1.x < Data2.x:
        LeftmostContour = Data1
        RightmostContour = Data2
    else:
        LeftmostContour = Data2
        RightmostContour = Data1

    LeftAngleHigh, LeftAngleLow = Settings.ReturnAngle_2() #get the left angle range
    RightAngleHigh, RightAngleLow = Settings.ReturnAngle_1() #get the right angle range
        
    angleTest1 = (LeftmostContour.angle < LeftAngleHigh) and (LeftmostContour.angle > LeftAngleLow)
    angleTest2 = (RightmostContour.angle < RightAngleHigh) and (RightmostContour.angle > RightAngleLow)
    Util.UIOutputMessage = "Angle 1:  " + str(angleTest1) + ", Angle 2: " + str(angleTest2)

    if angleTest1 and angleTest2:
        Util.UIOutputMessage = "Angle test passed."
        #get a pixel to inch scalar so we can measure true distance
        pixToInch_1 = Data1.GetScale()
        pixToInch_2 = Data2.GetScale()
        #average our scalar for maximum happiness
        pixelsPerInch = pixToInch_1 + pixToInch_2
        pixelsPerInch /= 2

        distance = Data1.x - Data2.x
        distance = abs(distance) #grab absolute value of distance (in pixels)
        distance *= pixelsPerInch #use our scalar to convert to inches
                
        dstHigh, dstLow = Settings.ReturnDistance() #get the max and min range we can have distance in
        if(distance > dstLow) and (distance < dstHigh):
            # distance is correct, we got a pair cheif
            if Settings.DEBUG: #update the UI with some information
                Util.TargetFound = True
                Util.UIOutputMessage = "All tests passed. (Angle and distance)"
                Util.ContourData_1 = Data1
                Util.ContourData_2 = Data2

            return True

    if Settings.DEBUG:
        Util.TargetFound = False
        
    return False


def Setup():
    global Stream
    Stream = cv2.VideoCapture(0)
    Stream.set(3, Settings.IMAGE_RESOLUTION_X) #sets the width of the camera resolution
    Stream.set(4, Settings.IMAGE_RESOLUTION_Y) #sets the height of the cam resolution
    print("Video stream instantiated")
    

def Loop():

        
    while not Util.ProgramQuit:
        StartTime = 0 #default loop start time is 0, just so the var is declared. Will only be used in debugging mode.
        
        if Settings.DEBUG:
            StartTime = time.clock() #keep record of when the loop started so we can calculate time
            UI.UpdateSettings()
            Util.UIOutputMessage = "No tests passed."
            
        if not Util.ProgramPause:
            #loopey boi

            paired = [] # PairData representing pairs of contours
            unpaired = [] # ContourData representing contours that have not been paired yet

            
            img = Capture()
            ConImage= img.copy()
            img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            Contours, _ = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE) #pull all contours out of image
            #cv2.drawContours(ConImage, Contours, -1, [0,255,0], 3) # draws the contours into the image

            #now process  the contours
            for contour in Contours:
                box = cv2.minAreaRect(contour) #get our bounding box, can be rotated.
                data = Util.ContourData(box) #create a data wrapper using the box

                if data.IsEligible():
                    #find any possible pairs
                    if Settings.DEBUG: #display bounding box around possible contour
                        points = cv2.cv.BoxPoints(box)
                        points = numpy.int0(points)
                        cv2.drawContours(ConImage, [points], -1, (0,0,255), 1)
                    
                    PixelScale = data.GetScale()
                    PairFound = False
                    for otherContour in unpaired:
                        Util.UIOutputMessage = "Testing contours for pairs"
                        if(CompareForPairing(data, otherContour)): #if the contours are pairs of each other, then add them to the array
                            pair = Util.PairData(data, otherContour)
                            paired.append(pair)
                            pairFound = True
                                            

                    if not PairFound:
                        unpaired.append(data)

            #update the util targets
            Util.Targets = paired

            #send the coords to the RIO
            #scale the image to 720p before sending
            UDPMessage = ""

            if len(paired) == 0:
                Util.TargetFound = False
                
            else: #the are more than 0 pairs
                LargestTarget = paired[0]
                for pair in paired:
                    centerX, centerY = pair.returnCenter()
                    if pair.area > LargestTarget.area: LargestTarget = pair
                    
                    if Settings.DEBUG:
                        cv2.circle(ConImage, (centerX, centerY), 3, (255,255,0), 5) #draw a point at the center of the target
                    
                scaledX = (LargestTarget.x / Settings.IMAGE_RESOLUTION_X) * 1280
                scaledY = (LargestTarget.y / Settings.IMAGE_RESOLUTION_Y) * 720
                scaledX = int(scaledX)
                scaledY = int(scaledY)
                
                UDPMessage = str(scaledX) + "," + str(scaledY)
                Util.UIOutputMessage = "Sending to RIO: " + UDPMessage
                UDPMessage = "-1,-1"
                
            sock.sendto(UDPMessage, (Settings.UDP_IP, Settings.UDP_PORT))

            if Settings.DEBUG: #Update the UI if we are in debugging mode.
                cv2.imshow("Contours", ConImage) #displays the contour image 
                cv2.waitKey(5)
            
                FinishTime = time.clock()
                Util.ProgramLoopTime = FinishTime - StartTime #record the loop time by subtracting finish from start
                Util.ProgramLoopTime *= 1000 #convert to ms
                Util.ProgramLoopTime = int(Util.ProgramLoopTime) #get rid of the decimal points so that the number doesn't bounce around on screen


if __name__ == '__main__': #START HERE:
    Setup()
    Loop()
    print("Exiting...")
    quit()
