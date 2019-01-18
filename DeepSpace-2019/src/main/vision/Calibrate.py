# Calibration program for FRC vision thing

#How this program works:
"""Aim the camera at a singular target so that no other contours other than the two angles are seen by the camera.
Then press the "Process" button and the program will output onto the UI window the angle, aspect ratio, pixel-to-inch
scale, and distance of the two contours of the seen target, as well as some recommended ranges of error. Those values
can be plugged into Settings.py for the main program to use.  NOTE: this program assumes that a JeVois camera is being 
used. Any regular webcam will not work because the image being read is not preprocessed. """


import cv2
import numpy
import Tkinter as tk

import Settings
import Util

#GUI:
#master window that everything is put on
MasterWindow = tk.Tk()

# stringvars and labels
OutputLabelString = tk.StringVar(MasterWindow) # tk.StringVar
OutputLabel = None # tk.Label
KillButton = None # tk.Button
ProcessButton = None # tk.Button
ContinueButton = None # tk.Button

#CV
Stream = cv2.VideoCapture(0)
Stream.set(3, Settings.IMAGE_RESOLUTION_X)
Stream.set(4, Settings.IMAGE_RESOLUTION_Y)

#util
ProgramContinue = False
ProgramEnd = False

def Kill():
    global ProgramEnd
    ProgramEnd = True
    
def Continue():
    global ProgramContinue
    ProgramContinue = True # lets program continue after processing an image

#Takes an image, processes it, and displays all data on the output label.
def PostProcess():
    global ProgramContinue

    print ("Taking and processing image.")
    _, img = Stream.read() # grabs our image and stores in in img
    ImageToOutput = img.copy() # gives us a BGR image we can draw on and output

    img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY) #converts to grayscale so we can find contours
    Contours, _ = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE) #edge detection

    print(str(len(Contours)) + " contours found.") #output how many contours we found

    ValidContours = [] # Util.ContourData. When the length of this array reaches 2, we will return
    for contour in Contours:
        box = cv2.minAreaRect(contour) # draw tight rectangle around contour, may be rotated
        rect = Util.ContourData(box) #create a wrapper for the data

        angle = abs(rect.angle)
        if(angle < 4): #contour is invalid, do not continue
            print("Invalid contour detected!")
        else: #contour is valid, continue on
            ValidContours.append(rect) #add the ContourData to the array of valid contours.

        if len(ValidContours) >= 2:
            break

    print( str(len(ValidContours)) + " Contours found.")
    #now go through the valid contours and display their data (angle, aspect ratio, distance, scale)
    #first find the leftmost contour
    
    if len(Contours) >= 2:
        angle_1 = 0 #the leftmost contour's angle
        angle_2 = 0 #the rightmost contour's angle
        aspect_ratio = 0.0 #the average aspect ratio of both contours
        distance = 0 #the distance between both contours
        scale = 0 #the average pixel-to-inch scalar of both contours

        if ValidContours[0].x < ValidContours[1].x: # 0 is the leftmost
            angle_1 = ValidContours[0].angle
            angle_2 = ValidContours[1].angle
        
        else:
            angle_1 = ValidContours[1].angle
            angle_2 = ValidContours[0].angle

        contour_1_AR = ValidContours[0].w / ValidContours[0].h #aspect ratio of contours
        contour_2_AR = ValidContours[1].w / ValidContours[1].h
        #average the aspect ratio
        aspect_ratio = contour_1_AR + contour_2_AR
        aspect_ratio = aspect_ratio / 2
        #distance
        distance = ValidContours[0].x - ValidContours[1].x # gets the distance between both contours
        distance = abs(distance) # just in case the distance turns up negative...

        #get the average scale of both contours
        scale = ValidContours[0].GetScale() + ValidContours[1].GetScale()
        scale = scale / 2

        distance = distance * scale #convert scale to inches

        #package into a string for the UI
        TargetInfo = "TARGET CHARACTERISTICS:"
        TargetInfo += "\nLEFT ANGLE(angle 1) : " + str(angle_1)
        TargetInfo += "\nRIGHT ANGLE(angle 2): " + str(angle_2)
        TargetInfo += "\nASPECT RATIO        : " + str(aspect_ratio)
        TargetInfo += "\nDISTANCE            : " + str(distance)
        TargetInfo += "\nSCALE               : " + str(scale)
        TargetInfo += "\n\nPress \"Continue\" to continue."

        # modify the output image so user can see what "target" was seen
        target = Util.PairData(ValidContours[0], ValidContours[1])
        x,y = target.returnCenter() #get center of target
        cv2.circle(ImageToOutput, (x, y), 3, (255,255,0), 5) #draw a point at the center of the target

        #draw image and update GUI
        cv2.imshow("Image", ImageToOutput)
        cv2.waitKey(5)

        OutputLabelString.set(TargetInfo) #set text
        #wait for the user to continue
        ProgramContinue = False
        while not ProgramContinue:
            MasterWindow.update()

    return


# sets up the program, completes the UI
def Setup():
    #OutputLabelString = tk.StringVar(MasterWindow) #string variable used on the output label
    OutputLabelString.set("Calibration")
    OutputLabel = tk.Label(MasterWindow, textvariable=OutputLabelString, anchor=tk.W) # Label where all output is displayed. To modify the text, set OutputLabelString
    ProcessButton = tk.Button(MasterWindow, text="Postprocess Image", command=PostProcess) # run the postprocessor when pressed
    ContinueButton = tk.Button(MasterWindow, text="Continue", command=Continue)
    KillButton = tk.Button(MasterWindow, text="Kill Program", command=Kill) #kill the program when pressed

    #now grids all UI elems
    OutputLabel.grid(row=0,column=0)
    ProcessButton.grid(row=1,column=0)
    ContinueButton.grid(row=2,column=0)
    KillButton.grid(row=3,column=0)


def Loop():
    while not ProgramEnd:
        _, img = Stream.read() #grabs image
        cv2.imshow("Image", img) #updates the cv2 image readout window
        cv2.waitKey(5) #required for imshow to work
        MasterWindow.update() # updates the tkinter window


if __name__ == '__main__':
    Setup()
    Loop()
    Stream.release()
    quit()

