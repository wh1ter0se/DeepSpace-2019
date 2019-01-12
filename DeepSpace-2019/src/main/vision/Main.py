#! /usr/bin/python


#########################################
#                                       #
#      3695 Vision postprocessing       #
#       written by: Brach Knutson       #
#                                       #
#########################################

#import other files
import Settings
import Util


#import 3rd parties
import cv2
import numpy


# Now for the good stuff

class Postprocessor: # main postprocessor class that does all the work

    #utilities
    Stream = None # cv2.VideoCapture that gives us our video data

    # data
    unpaired = [] #array of unpaired Util.ContourData for potential further processing
    pairs = [] #array of paired Util.PairData for further processing

    #other
    endProgram = False # when true the program breaks

    def __init__(self):
        self.Stream = cv2.VideoCapture(0) # create and initialize our video streamer
        self.Stream.set(cv2.CV_CAP_PROP_FRAME_WIDTH, 176);
        self.Stream.set(cv2.CV_CAP_PROP_FRAME_HEIGHT,144);


    def Loop(self): #constantly loops and processes data from the camera
        #vars to be used
        img = None #the image we are processing (returned as a binary image from the streamer)


        while not self.endProgram: # keep looping as long as the program is not ending...
            ret, img = self.Stream.read()
            if ret:   
                img = cv2.flip(img, 1)
                Util.DisplayImg("Output", img)

            else:
                print("CAMERA GRAB FAILED!")
                break;
            
        
    

    

if __name__ == '__main__': #START HERE
    processor = Postprocessor()
    processor.Loop()
    quit() #when loop breaks program ends

    
