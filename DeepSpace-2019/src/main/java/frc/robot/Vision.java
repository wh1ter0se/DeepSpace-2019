/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Add your docs here.
 */
public class Vision extends IterativeRobot {

    private UsbCamera ACam;
    private UsbCamera BCam;

    private int camID;

    private Mat failImage;

    public Vision(){
        Size camSize = new Size(Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
        failImage = Mat.zeros(camSize, 0);
        camID = 0;
    }

    public void startFrameCameraThread(){
    	new Thread(this::cameraStream).start();
    }
    
    private void cameraStream(){
        ACam = CameraServer.getInstance().startAutomaticCapture("A-Cam", Constants.ACAM_ID);
        BCam = CameraServer.getInstance().startAutomaticCapture("B-Cam", Constants.BCAM_ID);
        // ACam = new UsbCamera("frame", 0);
        // ACam.setFPS(30);
        // ACam.setResolution(480,270);
    	CvSink cvsinkA = new CvSink("A-Sink");
    	cvsinkA.setSource(ACam);
        cvsinkA.setEnabled(true);

        CvSink cvsinkB = new CvSink("B-Sink");
    	cvsinkB.setSource(BCam);
    	cvsinkB.setEnabled(true);
    	
    	Mat streamImages = new Mat();

        CvSource outputFrame;
        if (camID == 0) {
            outputFrame = CameraServer.getInstance().putVideo("A-Cam", Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
        } else {
            outputFrame = CameraServer.getInstance().putVideo("B-Cam", Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
        }
    	 while (!Thread.interrupted()){
             try {
                 if (camID == 0) {
                    cvsinkA.grabFrame(streamImages);
                 } else {
                    cvsinkB.grabFrame(streamImages);
                 }
                 outputFrame.putFrame(streamImages);
             } catch (CvException cameraFail){
                 DriverStation.reportWarning("Camera: " + cameraFail.toString(), false);
                 outputFrame.putFrame(failImage);
             }
    	 }
    }

    public void setCamID(int ID) {
        camID = ID;
    }
}
