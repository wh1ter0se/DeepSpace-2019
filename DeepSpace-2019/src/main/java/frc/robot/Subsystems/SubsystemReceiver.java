/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.stream.IntStream;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Commands.IterativeCommandUpdateRumble;
import frc.robot.Util.Util;

/**
 * The receiver code that runs on the Rio to listen for UDP data
 */
public class SubsystemReceiver extends Subsystem {

  private static String latestSegment;

  private static Boolean inRange;

  private static DatagramSocket serverSocket;
  private static byte[]         receiveData;

  private static long latestTime;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new IterativeCommandUpdateRumble());
  }

  public SubsystemReceiver() {
    latestSegment = "-1,-1,-1,-1";
    latestTime    = System.currentTimeMillis();

    SmartDashboard.putString("RPi Data", latestSegment);

    inRange = false;

    try {
      serverSocket = new DatagramSocket(3695);
      receiveData  = new byte[1024];
    } catch (SocketException e) { //thrown when a socket cannot be created
      DriverStation.reportError("SOCKET EXCEPTION", true);
    }

    // EXPECTED FORMAT OF INPUT STRING:
    // :X,Y,H,D,A;
      // X = X-coordinate
      // Y = Y-coordinate
      // D = Distance from target
      // A = Angle from center (positive = CW)

    Thread listener = new Thread(() -> {
      while(!Thread.interrupted()) {
        try {
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //create a new packet for the receiving data 
          serverSocket.receive(receivePacket); //receive the packet from the Socket
          // DriverStation.reportError("I GOT PI DATA", false);
          String segment = new String(receivePacket.getData()).replaceAll("\\s+",""); //remove whitespace and place data in 'segment'
          latestSegment = segment.substring(segment.indexOf(":") + 1, segment.indexOf(";")); // store segment without borders
          latestTime = System.currentTimeMillis(); // add timestamp for stored segment
          SmartDashboard.putString("RPi Data", segment.substring(segment.indexOf(":") + 1, segment.indexOf(";"))); // put string on dashboard without borders
        } catch (IOException e) { //thrown when the socket cannot receive the packet
          DriverStation.reportError("IO EXCEPTION", true);
        }
      }
    });
    
    listener.start();

  }

  /**
   * Retrieves the last known pixel coordinates of the target
   * @return [0] = X-coordinate (in pixels from left)
   *         [1] = Y-coordinate (in pixels from bottom)
   *         [2] = Distance (in inches)
   *         [3] = Angle from center (in degrees; positive = CW)
   *         {-1,-1,-1,-1} for no known location
   */
  public double[] getLastKnownData() {
      double[] data = new double[]{-1,-1,-1,-1};
      int[] indices = new int[]{2,5,8};
      try {
        indices = IntStream.range(0, latestSegment.length() - 1)
                  .filter(i -> latestSegment.charAt(i) == ',')
                  .toArray();
        data[0] = Integer.parseInt(latestSegment.substring(0, latestSegment.indexOf(",", indices[0])));
        data[1] = Integer.parseInt(latestSegment.substring(latestSegment.indexOf(",", indices[0]) + 1, latestSegment.indexOf(",", indices[1])));
        data[2] = Integer.parseInt(latestSegment.substring(latestSegment.indexOf(",", indices[1]) + 1, latestSegment.indexOf(",", indices[2])));
        data[3] = Integer.parseInt(latestSegment.substring(latestSegment.indexOf(",", indices[2]) + 1));
      } catch (NumberFormatException e) {
        DriverStation.reportError("NUMBER FORMAT EXCEPTION", true); 
        DriverStation.reportError("latestSegment = " + latestSegment, false);
        // DriverStation.reportError("data[0] = " + data[0], false); 
        // DriverStation.reportError("data[1] = " + data[1], false); 
        // DriverStation.reportError("data[2] = " + data[2], false); 
        // DriverStation.reportError("data[3] = " + data[3], false);
      } catch (StringIndexOutOfBoundsException e) {
        DriverStation.reportError("STRING INDEX OUT OF BOUNDS EXCEPTION", true);
        DriverStation.reportError("latestSegment = " + latestSegment, false);
        // DriverStation.reportError("indices[0] = " + indices[0], false); 
        // DriverStation.reportError("indices[1] = " + indices[1], false); 
        // DriverStation.reportError("indices[2] = " + indices[2], false); 
      } catch (ArrayIndexOutOfBoundsException e) {
        DriverStation.reportError("ARRAY INDEX OUT OF BOUNDS EXCEPTION", true);
        DriverStation.reportError("latestSegment = " + latestSegment, false);
        // DriverStation.reportError("indices[0] = " + indices[0], false); 
        // DriverStation.reportError("indices[1] = " + indices[1], false); 
        // DriverStation.reportError("indices[2] = " + indices[2], false); 
      }
      updateTargetLock(data);
      return data;
  }

  /**
   * Returns the miliseconds since the pi sent the LastKnownLocation
   * @return ms since last received UDP packet
   */
  public double getSecondsSinceUpdate() {
    return Util.roundTo((double) ((System.currentTimeMillis() - latestTime) / 1000), 5);
  }

  /**
   * If data is being received, records whether or not its in "target lock" range
   * If dats is not being received, the last known state is kept
   */
  public void updateTargetLock( double[] data) {
    if (data[2] != -1) {
      inRange = data[2] < Constants.DOCKING_TARGET_LOCK_RANGE;
    }
  }

  /**
   * Gets the state of target lock
   * @return true if within range, false if out of range
   */
  public Boolean getWithinRange() {
    return inRange;
  }
}