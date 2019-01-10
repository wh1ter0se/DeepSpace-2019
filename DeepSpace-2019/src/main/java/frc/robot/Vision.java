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

    private UsbCamera cameraFrame;

    private Mat failImage;

    public Vision(){
        Size camSize = new Size(Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
        failImage = Mat.zeros(camSize, 0);
    }

    public void startFrameCameraThread(){
    	new Thread(this::frameCameraStream).start();
    }
    
    private void frameCameraStream(){
        cameraFrame = CameraServer.getInstance().startAutomaticCapture("Frame", Constants.CAM_ID);
        // cameraFrame = new UsbCamera("frame", 0);
        // cameraFrame.setFPS(30);
        // cameraFrame.setResolution(480,270);
    	CvSink cvsinkFrame = new CvSink("frameSink");
    	cvsinkFrame.setSource(cameraFrame);
    	cvsinkFrame.setEnabled(true);
    	
    	Mat streamImages = new Mat();

    	CvSource outputFrame = CameraServer.getInstance().putVideo("Frame", Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
    	 while (!Thread.interrupted()){
             try {
                 cvsinkFrame.grabFrame(streamImages);
                 outputFrame.putFrame(streamImages);
             } catch (CvException cameraFail){
                 DriverStation.reportWarning("Frame Camera: " + cameraFail.toString(), false);
                 outputFrame.putFrame(failImage);
             }
    	 }
    }
}
