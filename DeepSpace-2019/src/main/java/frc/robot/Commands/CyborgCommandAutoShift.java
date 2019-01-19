/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Util.Util;

public class CyborgCommandAutoShift extends Command {
  public CyborgCommandAutoShift() {
    requires(Robot.SUB_SHIFTER);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double upShiftRPM = Util.getAndSetDouble("Upshift RPM", Constants.UPSHIFT_RPM);
    double downShiftRPM = Util.getAndSetDouble("Downshift RPM", Constants.DOWNSHIFT_RPM);
    if (Robot.SUB_SHIFTER.isFirstGear() && Robot.SUB_DRIVE.getVelocities()[0] >= upShiftRPM && Robot.SUB_DRIVE.getVelocities()[1] >= upShiftRPM) {
      Robot.SUB_SHIFTER.upShift();
    } else if (!Robot.SUB_SHIFTER.isFirstGear() && Robot.SUB_DRIVE.getVelocities()[0] <= downShiftRPM && Robot.SUB_DRIVE.getVelocities()[1] <= downShiftRPM) {
      Robot.SUB_SHIFTER.downShift();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
