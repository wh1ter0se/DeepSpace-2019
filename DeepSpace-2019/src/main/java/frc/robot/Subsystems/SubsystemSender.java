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
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class SubsystemSender extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private byte[] buffer;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public SubsystemSender() {
    buffer = new byte[]{Constants.ASCII_ZERO};
    // new Thread(() -> {
    //   byte [] IP   = {10,36,95,16};
    //   int     port = 3695;
    //   while (!Thread.interrupted()) {
    //     try {
    //       InetAddress address = InetAddress.getByAddress(IP);
    //       DatagramPacket packet = new DatagramPacket(
    //         buffer, buffer.length, address, port
    //         );
    //       // DriverStation.reportWarning("Packet:" + buffer.toString(), false);
    //       DatagramSocket datagramSocket = new DatagramSocket();
    //       // datagramSocket.
    //       // DriverStation.reportWarning("Port:" + datagramSocket.getLocalPort(), false);
    //       datagramSocket.send(packet);
    //     } catch (UnknownHostException e) {
    //       DriverStation.reportError(e.toString(), true);
    //     } catch (SocketException e) {
    //       DriverStation.reportError(e.toString(), true);
    //     } catch (IOException e) {
    //       DriverStation.reportError(e.toString(), true);
    //     }
    //   }
    // }).start();
  }

  public void setData(byte[] buffer)  {
    this.buffer = buffer; 
  }

}
