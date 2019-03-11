/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Enumeration.DriveScheme;
import frc.robot.Enumeration.DriveSpeed;
import frc.robot.Util.Util;

public class ButtonCommandSetDriveSpeed extends InstantCommand {

  private DriveSpeed speed;

  public ButtonCommandSetDriveSpeed(DriveSpeed speed) {
    this.speed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (Robot.controlScheme == DriveScheme.RL_HILO) {
      switch (speed) {
        case LOW:
          SmartDashboard.putBoolean("Low Speed", true);
          SmartDashboard.putBoolean("High Speed", false);
          Robot.SUB_DRIVE.setDriveSpeed(DriveSpeed.LOW);
          break;
        case HIGH:
          SmartDashboard.putBoolean("Low Speed", false);
          SmartDashboard.putBoolean("High Speed", true);
          Robot.SUB_DRIVE.setDriveSpeed(DriveSpeed.HIGH);
          break;
      }
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }
}
