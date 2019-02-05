#some utility classes that will make processing a lot easier for us.

import cv2
import Settings

#variables the UI uses to display data about any passing contours
ContourData_1 = None
ContourData_2 = None

TargetFound = False

#program pause and quit booleans
ProgramPause = False
ProgramQuit = False

#Message for UI to display
UIOutputMessage = ""

ProgramLoopTime = 0.0 #the loop time in milliseconds to be displayed in the UI

Targets = []

class ContourData:
    x = 0
    y = 0
    w = 0
    h = 0
    angle = 0


    #constructs a new ContourData.
    #@param box The rotated rect to pull the data from
    def __init__(self, box):
        self.x = box[0][0]
        self.y = box[0][1]
        
        w = box[1][0]
        h = box[1][1]
        if(w > h): #flip the sides so that we can get a good aspect ratio
            temp = w
            w = h
            h = temp

        self.w = w
        self.h = h
        
        self.angle = box[2]

    #tests the contour and passes is the contour passed all tests.
    #@param PixToInch the pixels-to-inches scalar 
    #@return boolean. True if it passed, False if not.
    def IsEligible(self):
        if self.w == 0 or self.h == 0:
            return False

        UpperRange, LowerRange = Settings.ReturnAspectRatio()
        #HEIGHT TEST
        aspectRatio = self.w / self.h
        # print(aspectRatio)
        test1 = (aspectRatio < UpperRange) and (aspectRatio > LowerRange)

        #ANGLE TEST
        AngleUpperRange, AngleLowerRange = Settings.ReturnAngle_1()
        angleTest1 = (self.angle < AngleUpperRange) and (self.angle > AngleLowerRange)
        AngleUpperRange, AngleLowerRange = Settings.ReturnAngle_2()
        angleTest2 = (self.angle < AngleUpperRange) and (self.angle > AngleLowerRange)
        test2 = angleTest1 or angleTest2 #if one of the angle tests are true, the test is passing

        #AREA TEST
        AreaHigh, AreaLow = Settings.ReturnArea() #get our allowed area range
        Area = self.x * self.y #get our area for testing
        test3 = (Area < AreaHigh) and (Area > AreaLow) #now test the area

        return test1 and test2 and test3 #returns if both are true. false if nah, true if yah

    
    def GetScale(self):
        return 5.5 / self.h
        
        
        


class PairData:
    x = 0
    y = 0
    area = 0

    #constructs new PairData using the two boxes provided
    #@param box1 the first box to use for coordinates
    #@param box2 the other box in the pair
    def __init__(self, data1, data2):
        y = data1.y #we only need 1 y because the targets will always be horizontally aligned

        x = data1.x # the first x
        x = x + data2.x #add the second box 
        x = x / 2 #average the two boxes for middle x
        x = int(x)
        y = int(y)
        
        self.y = y
        self.x = x

        area1 = data1.w * data1.y
        area2 = data2.w * data2.y
        self.area = area1 + area2
    
    def returnCenter(self):
        return self.x, self.y


#returns the opposite angle of angle as defined by Settings
def GetOppositeAngle(angle):
    angleHigh, angleLow = Settings.ReturnAngle_1()

    if(angle > angleLow) and (angle < angleHigh):
        return Settings.TARGET_ANGLE_2
    else:
        return Settings.TARGET_ANGLE_1
