import cv2
import numpy

#Settings file for the vision program. Constants put here

DEBUG = True

#imaging
IMAGE_RESOLUTION_X = 320
IMAGE_RESOLUTION_Y = 200

IMAGE_CAPTURE_STREAM = True #when true uses the camera to get images. When false uses image in folder.

#angling 
TARGET_ANGLE_1 = -19 #the target angle of the right contour
TARGET_ANGLE_2 =  -74 #the target angle of the left contour
ANGLE_ERROR = 15

#targeting
TARGET_ASPECT_RATIO = 0.45 #aspect ratio of any given contour to move on to further testing
ASPECT_RATIO_ERROR = 0.1 #maximum error in the aspect ratio test

TARGET_CONTOUR_AREA = 30000
TARGET_AREA_ERROR = 15000

TARGET_CONTOUR_DISTANCE = 9 #distance between contours in inches
CONTOUR_DISTANCE_ERROR = 6 #amount of error 


#returns highDistance, lowDistance
def ReturnDistance():
    return TARGET_CONTOUR_DISTANCE + CONTOUR_DISTANCE_ERROR, TARGET_CONTOUR_DISTANCE - CONTOUR_DISTANCE_ERROR

#returns highRange, lowRange
def ReturnAspectRatio():
    return TARGET_ASPECT_RATIO + ASPECT_RATIO_ERROR, TARGET_ASPECT_RATIO - ASPECT_RATIO_ERROR

#returns higharea, lowarea
def ReturnArea():
    return TARGET_CONTOUR_AREA + TARGET_AREA_ERROR, TARGET_CONTOUR_AREA - TARGET_AREA_ERROR

#returns highangle, lowangle
def ReturnAngle_1():
    return TARGET_ANGLE_1 + ANGLE_ERROR, TARGET_ANGLE_1 - ANGLE_ERROR

def ReturnAngle_2():
    return TARGET_ANGLE_2 + ANGLE_ERROR, TARGET_ANGLE_2 - ANGLE_ERROR
