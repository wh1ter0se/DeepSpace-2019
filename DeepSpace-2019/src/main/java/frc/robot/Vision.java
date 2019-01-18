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
import edu.wpi.cscore.VideoException;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Util.Util;

/**
 * Add your docs here.
 */
public class Vision extends TimedRobot {

    private UsbCamera ACam;
    private UsbCamera BCam;
    private UsbCamera CCam;

    private VideoSink server;

    private int camID;

    private Mat failImage;

    private long initTime;

    public Vision(){
        Size camSize = new Size(Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
        failImage = Mat.zeros(camSize, 0);
        ACam = CameraServer.getInstance().startAutomaticCapture(Constants.ACAM_ID);
        BCam = CameraServer.getInstance().startAutomaticCapture(Constants.BCAM_ID);
        CCam = CameraServer.getInstance().startAutomaticCapture(Constants.CCAM_ID);
        server = CameraServer.getInstance().getServer();
        ACam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        BCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        CCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        
        server.setSource(ACam);

        updateAllSettings((int) (Util.getAndSetDouble("Cam Exposure", Constants.BACKUP_EXPOSURE)));
    }

    /**
     * Switches the camera source to the camera with the given ID
     */
    public void setCamID(int ID) {
        if (ID == 0) {
            server.setSource(ACam);
        } else if (ID == 1) {
            server.setSource(BCam);
        } else if (ID == 2) {
            server.setSource(CCam);
        }
    }

    // public double getTotalBandwidth() {
    //     double total = 0;
    //     try { total += ACam.getActualDataRate(); } 
    //         catch (VideoException e) {  DriverStation.reportWarning("Video Exception on ACam", false); }
    //     try { total += BCam.getActualDataRate(); } 
    //         catch (VideoException e) {  DriverStation.reportWarning("Video Exception on BCam", false); }
    //     return (total / 1048576);
    // }

    public void updateAllSettings(int exposure) {
        updateExposure(exposure);
        // add settings here as needed
    }

    public void updateExposure(int exposure) {
        ACam.setExposureManual(exposure);
        BCam.setExposureManual(exposure);
        CCam.setExposureManual(exposure);
    }

    // public void startFrameCameraThread(){
    // 	new Thread(()-> {

    //     // ACam = new UsbCamera("frame", 0);
    //     // ACam.setFPS(30);
    //     // ACam.setResolution(480,270);

    // 	Mat streamImages = new Mat();

    //     CvSink cvsinkA;
    //     CvSink cvsinkB;

    //     CvSource outputFrame;
    //     if (camID == 0) {
    //         ACam = CameraServer.getInstance().startAutomaticCapture("A-Cam", Constants.ACAM_ID);
    //         cvsinkA = new CvSink("A-Sink");
    // 	    cvsinkA.setSource(ACam);
    //         cvsinkA.setEnabled(true);
    //         outputFrame = CameraServer.getInstance().putVideo("A-Cam", Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
    //     } else {
    //         BCam = CameraServer.getInstance().startAutomaticCapture("B-Cam", Constants.BCAM_ID);
    //         cvsinkB = new CvSink("B-Sink");
    // 	    cvsinkB.setSource(BCam);
    // 	    cvsinkB.setEnabled(true);
    //         outputFrame = CameraServer.getInstance().putVideo("B-Cam", Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
    //     }
    // 	 while (!Thread.interrupted()){
    //          try {
    //              if (!streamImages.empty()) {
    //                 if (camID == 0) {
    //                     cvsinkA.grabFrame(streamImages);
    //                 } else {
    //                     cvsinkB.grabFrame(streamImages);
    //                 }
    //                 outputFrame.putFrame(streamImages);
    //             }
    //          } catch (CvException cameraFail){
    //              DriverStation.reportWarning("Camera: " + cameraFail.toString(), false);
    //              outputFrame.putFrame(failImage);
    //          }
    //      }
    //     }).start();
    // }
}
