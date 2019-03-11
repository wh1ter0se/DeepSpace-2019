/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Util.Util;

/**
 * Add your docs here.
 */
public class Vision extends TimedRobot {

    private UsbCamera ACam;
    private UsbCamera BCam;
    private VideoSink server;

    // public Vision(){
    //     ACam = CameraServer.getInstance().startAutomaticCapture(Constants.ACAM_ID);
    //         ACam.setResolution(80,50);
    //     BCam = CameraServer.getInstance().startAutomaticCapture(Constants.BCAM_ID);
    //         BCam.setResolution(80,50);

    //     updateAllSettings((int) (Util.getAndSetDouble("Cam Exposure", Constants.BACKUP_EXPOSURE)),
    //         (int) (Util.getAndSetDouble("Cam FPS", 30)),
    //         (int) (Util.getAndSetDouble("Cam White Balance", 1000)));

    //     server = CameraServer.getInstance().getServer();

    //     ACam.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);
    //     BCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kAutoManage);

    //     server.setSource(ACam);
    // }

    public Vision(){
        new Thread(() -> {
            ACam = CameraServer.getInstance().startAutomaticCapture();
                ACam.setResolution(80, 50);
                ACam.setFPS(30);
                ACam.setExposureManual(45);
            BCam = CameraServer.getInstance().startAutomaticCapture();
                BCam.setResolution(80, 50);
                BCam.setFPS(30);
                BCam.setExposureManual(45);

            ACam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
            BCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

            server = CameraServer.getInstance().getServer();
            // server.setSource(ACam);

            // CvSink cvSink = CameraServer.getInstance().getVideo();
            // CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 80, 50);
            
            // Mat source = new Mat();
            // Mat output = new Mat();
            
            // while(!Thread.interrupted()) {
            //     cvSink.grabFrame(source);
            //     Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
            //     outputStream.putFrame(output);
            // }
        }).start();

        // updateAllSettings((int) (Util.getAndSetDouble("Cam Exposure", Constants.BACKUP_EXPOSURE)),
            // (int) (Util.getAndSetDouble("Cam FPS", 30)),
            // (int) (Util.getAndSetDouble("Cam White Balance", 1000)));
    }

    /**
     * Switches the camera source to the camera with the given ID
     */
    public void setCamID(int ID) {
        if (ID == 0) {
            // server.setSource(ACam);
        } else if (ID == 1) {
            // server.setSource(BCam);
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

    public void updateAllSettings(int exposure, int FPS, int whiteBalance) {
        updateExposure(exposure);
        updateFPS(FPS);
        updateWhiteBalance(whiteBalance);
    }

    public void updateExposure(int exposure) {
        ACam.setExposureManual(exposure);
        BCam.setExposureManual(exposure);
    }

    public void updateFPS(int FPS) {
        ACam.setFPS(FPS);
        BCam.setFPS(FPS);
    }

    public void updateWhiteBalance(int WB) {
        ACam.setWhiteBalanceManual(WB);
        BCam.setWhiteBalanceManual(WB);
    }
}
