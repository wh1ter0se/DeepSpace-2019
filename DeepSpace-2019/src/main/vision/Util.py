

#Util file for Vision postprocessor
#Holds classes and methods that might be helpful and make the main file look kinda good

#imports
import cv2


#Values used by the UI renderer. just values sent by the processor to be displayed on screen
Message = ""



def DisplayImg(window, img):
    cv2.imshow(window, img)
    cv2.waitKey(5) #for some reason this is requied for imshow() to work???


class ContourData: #holds integer values pulled from contours to make processing better

    x = None
    y = None
    w = None
    h = None
    angle = None

    def __init__(self, x, y, w, h, angle):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.angle = angle


class PairData: #holds data for paired contours (also to make processing better

    x = None
    y = None

    def __init__(self, x, y):
        self.x = x
        self.y = y



