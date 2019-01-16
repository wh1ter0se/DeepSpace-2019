import cv2
import numpy
import Settings
import Util

Stream = None

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
    targetAngle = Util.GetOppositeAngle(Data1.angle) #whats the angle we are looking for?
    angleHigh = targetAngle + Settings.ANGLE_ERROR #get our range for angles
    angleLow = targetAngle  - Settings.ANGLE_ERROR 
    if(Data2.angle > angleLow) and (Data2.angle < angleHigh): # it's in range, let's go to distance testing
        #get a pixel to inch scalar so we can measure true distance
        pixToInch_1 = Data1.GetScale()
        pixToInch_2 = Data2.GetScale()
        #average our scalar for maximum happiness
        pixelsPerInch = pixToInch_1 + pixToInch_2
        pixelsPerInch /= 2

        distance = Data1.x - Data2.x
        distance = abs(distance) #grab absolute value
        distance *= pixelsPerInch
                
        dstHigh, dstLow = Settings.ReturnDistance() #get the max and min range we can have distance in
        if(distance > dstLow) and (distance < dstHigh):
            # distance is correct, we got a pair
            return True
        
    return False






def Setup():
    global Stream
    Stream = cv2.VideoCapture(0)
    print("Video stream instantiated")
    

def Loop():

        
    while True:
        #loopey boi

        paired = [] # PairData representing pairs of contours
        unpaired = [] # ContourData representing contours that have not been paired yet

        img = Capture()
        ConImage= img.copy()

        upper = numpy.array( [255,255,255] )
        lower = numpy.array( [240,240,240] )
        img = cv2.inRange(img, lower, upper)
        _, Contours, _ = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE) #pull all contours out of image
        cv2.drawContours(ConImage, Contours, -1, [0,255,0], 3) # draws the contours into the image

        #now process  the contours
        for contour in Contours:
            box = cv2.minAreaRect(contour) #get our bounding box, can be rotated.
            data = Util.ContourData(box) #create a data wrapper using the box

            if data.IsEligible():
                #find any possible pairs
                PixelScale = data.GetScale()
                PairFound = False
                for otherContour in unpaired:
                    if(CompareForPairing(data, otherContour)):
                        pair = Util.PairData(data, otherContour)
                        paired.append(pair)
                        pairFound = True
                                        

                if not PairFound:
                    unpaired.append(data)

        for pair in paired:
            centerX, centerY = pair.returnCenter()
            cv2.circle(ConImage, (centerX, centerY), 3, (255,255,0), 5) #draw a point at the center of the target

        cv2.imshow("Contours", ConImage) #displays the contour image 
        cv2.waitKey(5)
    


if __name__ == '__main__': #START HERE:
    Setup()
    Loop()
    print("Exiting...")
    quit()
