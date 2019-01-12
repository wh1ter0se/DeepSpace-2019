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
        Stream = cv2.VideoCapture(0) # create and initialize our video streamer


    def Loop(): #constantly loops and processes data from the camera
        #vars to be used
        img = None #the image we are processing (returned as a binary image from the streamer)


        while !endProgram: # keep looping as long as the program is not ending...
            img = Stream.read()
            Util.DisplayImg("Output", img)
            
        
    

    

if __name__ == '__main__': #START HERE
    processor = Postprocessor()
    processor.Loop()
    quit() #when loop breaks program ends

    
