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

public class CyborgCommandSlowDownBuckaroo extends Command {

  private long initTime;
  private double lockoutTime;
  private double damperTime;

  private Boolean isFinished;

  private DriveSpeed initSpeed;

  public CyborgCommandSlowDownBuckaroo() {
    requires(Robot.SUB_DRIVE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initTime = System.currentTimeMillis();
    lockoutTime = Util.getAndSetDouble("Lockout", 200);
    damperTime  = Util.getAndSetDouble("Slowdown", 1000);
    initSpeed = Robot.SUB_DRIVE.getDriveSpeed();
    isFinished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (System.currentTimeMillis() < initTime + lockoutTime) {
      Robot.SUB_DRIVE.stopMotors();
    } else if (System.currentTimeMillis() < initTime + lockoutTime + damperTime) {
      Robot.SUB_DRIVE.setDriveSpeed(DriveSpeed.DISENGAGE);
      Robot.SUB_DRIVE.driveRlHiLo(OI.DRIVER, Util.getAndSetDouble("RL Ramp", Constants.BACKUP_RAMP),
                                               Util.getAndSetDouble("Disengage Inhibitor", .2),
                                               Util.getAndSetDouble("Lower Drive Inhibitor", .4),
                                               Util.getAndSetDouble("Upper Drive Inhibitor", .8),
                                               Util.getAndSetDouble("Murder Inhibtitor", 1));
    } else if (System.currentTimeMillis() > initTime + lockoutTime + damperTime) {
      isFinished = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_DRIVE.setDriveSpeed(initSpeed);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_DRIVE.setDriveSpeed(initSpeed);
  }
}
