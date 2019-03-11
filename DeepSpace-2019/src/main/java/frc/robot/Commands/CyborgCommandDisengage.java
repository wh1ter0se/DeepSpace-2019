/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Util.Util;

public class CyborgCommandDisengage extends Command {

  private double disengagementTime;

  private long   initTime;

  public CyborgCommandDisengage() {
    requires(Robot.SUB_DRIVE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    disengagementTime = Util.getAndSetDouble("Disengagement Time", 150);
    initTime = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_DRIVE.setBraking(false);
    Robot.SUB_DRIVE.stopMotors();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() > initTime + disengagementTime;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_DRIVE.setBraking(true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
