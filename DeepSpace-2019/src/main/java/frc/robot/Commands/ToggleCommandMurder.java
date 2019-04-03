/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Enumeration.DriveSpeed;
import frc.robot.Util.Util;

public class ToggleCommandMurder extends Command {

  private Boolean initFirstGear;
  private DriveSpeed initSpeed;

  public ToggleCommandMurder() {
    requires(Robot.SUB_DRIVE);
    requires(Robot.SUB_SHIFTER);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initFirstGear = Robot.SUB_SHIFTER.isFirstGear();
    initSpeed = Robot.SUB_DRIVE.getDriveSpeed();
    Robot.SUB_SHIFTER.downShift();
    Robot.SUB_DRIVE.setDriveSpeed(DriveSpeed.MURDER);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_DRIVE.driveRlHiLo(OI.DRIVER, Util.getAndSetDouble("RL Ramp", Constants.BACKUP_RAMP),
                                               Util.getAndSetDouble("Disengage Inhibitor", .2),
                                               Util.getAndSetDouble("Lower Drive Inhibitor", .3),
                                               Util.getAndSetDouble("Upper Drive Inhibitor", 8),
                                               Util.getAndSetDouble("Murder Inhibitor", 1));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_DRIVE.setDriveSpeed(initSpeed);
    if (initFirstGear) {
      Robot.SUB_SHIFTER.downShift();
    } else {
      Robot.SUB_SHIFTER.upShift();
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_DRIVE.setDriveSpeed(initSpeed);
    if (initFirstGear) {
      Robot.SUB_SHIFTER.downShift();
    } else {
      Robot.SUB_SHIFTER.upShift();
    }
  }
}
