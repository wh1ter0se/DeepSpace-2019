#UI file for GUI output of vision data.

from __future__ import division #forces float divisions each time
import Tkinter as tk #the UI module

import Settings
import Util

def KillProgram(): Util.ProgramQuit = True

def PauseProgram(): Util.ProgramPause = not Util.ProgramPause #toggle program paused

MasterWindow = tk.Tk() #new main window

#The Aspect raito sliders
Slider_Aspect_X = tk.Scale(MasterWindow, from_=1, to=1000, orient=tk.HORIZONTAL, length=500, label="Aspect Ratio X: ")
Slider_Aspect_Y = tk.Scale(MasterWindow, from_=1, to=1000, orient=tk.HORIZONTAL, length=500, label="Aspect Ratio Y: ")
Slider_Aspect_Error = tk.Scale(MasterWindow, from_=0, to=100, orient=tk.HORIZONTAL, length=500, label="Aspect Ratio Error: ")

#The Angle sliders
Slider_Angle_1 = tk.Scale(MasterWindow, from_=-180, to=180, orient=tk.HORIZONTAL, length=500, label="Left Angle: ")
Slider_Angle_2 = tk.Scale(MasterWindow, from_=-180, to=180, orient=tk.HORIZONTAL, length=500, label="Right Angle: ")
Slider_Angle_Error = tk.Scale(MasterWindow, from_=0, to=25, orient=tk.HORIZONTAL, length=500, label="Angle Error: ")

#Contour Distance Sliders
Slider_Contour_Distance = tk.Scale(MasterWindow, from_=0, to=40, orient=tk.HORIZONTAL, length=500, label="Contour Distance: ")
Slider_Distance_Error = tk.Scale(MasterWindow, from_=0, to=40, orient=tk.HORIZONTAL, length=500, label="Distance Error: ")

#Label text vars
SettingsLabelText = tk.StringVar(MasterWindow)
ContourDataText = tk.StringVar(MasterWindow)

#Some Random Labels
ModeLabel = tk.Label(MasterWindow, text="Mode: Debug", anchor=tk.W)
HeaderLabel = tk.Label(MasterWindow, text="Settings", anchor=tk.W)
SettingsLabel = tk.Label(MasterWindow, textvariable=SettingsLabelText, anchor=tk.W)
ContourDataLabel = tk.Label(MasterWindow, textvariable=ContourDataText, anchor=tk.W)

#Buttons
QuitButton = tk.Button(MasterWindow, text="Kill Program", command=KillProgram)
PauseButton = tk.Button(MasterWindow, text="Pause Program", command=PauseProgram)


#Grid all UI elems onto the master
ModeLabel.grid(row=0, column=0)
HeaderLabel.grid(row=1, column=0)

Slider_Aspect_X.grid(row=2, column=0)
Slider_Aspect_Y.grid(row=3, column=0)
Slider_Aspect_Error.grid(row=4, column=0)

Slider_Angle_1.grid(row=5, column=0)
Slider_Angle_2.grid(row=6, column=0)
Slider_Angle_Error.grid(row=7, column=0)

Slider_Contour_Distance.grid(row=8, column=0)
Slider_Distance_Error.grid(row=9, column=0)

SettingsLabel.grid(row=1, column=1)
ContourDataLabel.grid(row=2, column=1)

PauseButton.grid(row=10, column=0)
QuitButton.grid(row=10, column=1)


#set init values of the UI elements
Slider_Aspect_X.set(2)
Slider_Aspect_Y.set(6)
Slider_Aspect_Error.set(Settings.ASPECT_RATIO_ERROR * 100)

Slider_Angle_1.set(Settings.TARGET_ANGLE_1)
Slider_Angle_2.set(Settings.TARGET_ANGLE_2)
Slider_Angle_Error.set(Settings.ANGLE_ERROR)

Slider_Contour_Distance.set(Settings.TARGET_CONTOUR_DISTANCE)
Slider_Distance_Error.set(Settings.CONTOUR_DISTANCE_ERROR)


def UpdateSettings():
    MasterWindow.update()
    Settings.TARGET_ANGLE_1 = Slider_Angle_1.get()
    Settings.TARGET_ANGLE_2 = Slider_Angle_2.get()
    Settings.ANGLE_ERROR = Slider_Angle_Error.get()

    #aspect ratio stuff
    w = Slider_Aspect_X.get()
    h = Slider_Aspect_Y.get()
    aspectRatio = w/h #width / height

    Settings.TARGET_ASPECT_RATIO = aspectRatio

    #aspect ratio error
    errorValue = Slider_Aspect_Error.get()
    aspectError = errorValue / 100 #get a decimal value
    Settings.ASPECT_RATIO_ERROR = aspectError

    #distance
    Settings.TARGET_CONTOUR_DISTANCE = Slider_Contour_Distance.get()
    Settings.CONTOUR_DISTANCE_ERROR = Slider_Distance_Error.get()

    #format all settings into a string to display onto the UI
    SettingsString = "SETTINGS:"
    SettingsString += "\nAngle 1         : " + str(Settings.TARGET_ANGLE_1)
    SettingsString += "\nAngle 2         : " + str(Settings.TARGET_ANGLE_2)
    SettingsString += "\nAngle Error     : " + str(Settings.ANGLE_ERROR)
    SettingsString += "\nAspect Ratio    : " + str(Settings.TARGET_ASPECT_RATIO)
    SettingsString += "\nAR error        : " + str(Settings.ASPECT_RATIO_ERROR)
    SettingsString += "\nContour Distance: " + str(Settings.TARGET_CONTOUR_DISTANCE)
    SettingsString += "\nDistance Error  : " + str(Settings.CONTOUR_DISTANCE_ERROR)

    SettingsLabelText.set(SettingsString)

    #update the contour data label
    TargetString = "No target data could be displayed becuase no target was found."
    if Util.TargetFound:
        TargetString = "Target Data:"
        TargetString += "\nContour 1:"
        TargetString += "\nAngle  : " + str(Util.ContourData_1.angle)

        contour_1_AR = Util.ContourData_1.w / Util.ContourData_1.h
        TargetString += "\nAR     : " + str(contour_1_AR)

        TargetString += "\n\nContour 2:"
        TargetString += "\nAngle    : " + str(Util.ContourData_2.angle)

        contour_2_AR = Util.ContourData_2.w / Util.ContourData_2.h
        TargetString += "\nAR     : " + str(contour_2_AR)

    ContourDataText.set(TargetString)
    
    
    
