#UI file for GUI output of vision data.

from __future__ import division #forces float divisions each time
import Tkinter as tk #the UI module
import Settings

def KillProgram(): Quit()

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

#Some Random Labels
ModeLabel = tk.Label(MasterWindow, text="Mode: Debug", anchor=tk.W)
HeaderLabel = tk.Label(MasterWindow, text="Settings", anchor=tk.W)
SettingsLabel = tk.Label(MasterWindow, textvariable=SettingsLabelText, anchor=tk.W)

#Buttons
QuitButton = tk.Button(MasterWindow, text="Kill Program", command=KillProgram)


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


#set init values of the UI elements
Slider_Aspect_X.set(6)
Slider_Aspect_Y.set(2)
Slider_Aspect_Error.set(Settings.ASPECT_RATIO_ERROR)

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
    
    
    
