/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Util.MiniPID;
import frc.robot.Util.Util;
import frc.robot.Util.Xbox;

public class CyborgCommandAlign extends Command {

  private static MiniPID turning;

  private static Boolean canSee;
  private static Boolean inRange;
  private static Boolean isFinished;

  private static double idleSpeed;
  private static double lastAngle;
  private static double loopOutput;

  public CyborgCommandAlign() {
    requires(Robot.SUB_DRIVE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    lastAngle = 180;
    turning = new MiniPID(Util.getAndSetDouble("Docking kP", Constants.BACKUP_DOCKING_kP),
                          Util.getAndSetDouble("Docking kI", Constants.BACKUP_DOCKING_kI),
                          Util.getAndSetDouble("Docking kD", Constants.BACKUP_DOCKING_kD));
    idleSpeed = Util.getAndSetDouble("Docking Speed", Constants.BACKUP_DOCKING_SPEED);
    turning.setOutputLimits(-1 * idleSpeed, idleSpeed);
    turning.setSetpoint(0);

    // isFinished = Robot.SUB_RECEIVER.getLastKnownData()[3] == -1;
    isFinished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.SUB_RECEIVER.getLastKnownData()[3] != 180) {
      lastAngle = Robot.SUB_RECEIVER.getLastKnownData()[3];
    }
    loopOutput = turning.getOutput(Robot.SUB_RECEIVER.getLastKnownData()[3]);
      if (Math.abs(loopOutput) > 10 && Robot.SUB_RECEIVER.getLastKnownData()[4] < Constants.DOCKING_TARGET_LOCK_RANGE) { loopOutput = 0; }
    inRange = Robot.SUB_RECEIVER.getWithinRange();
    canSee = Robot.SUB_RECEIVER.getLastKnownData()[2] != -1;
    SmartDashboard.putBoolean("canSee", canSee);
    SmartDashboard.putBoolean("inRange", inRange);
    SmartDashboard.putBoolean("Aligning", true);

    double feedForward = Xbox.RT(OI.DRIVER) - Xbox.LT(OI.DRIVER);
    feedForward *= Util.getAndSetDouble("Align Inhibitor", .5);
    if (canSee) {
      Robot.SUB_DRIVE.driveByPercentOutputs(-1 * loopOutput + feedForward, loopOutput + feedForward);
    } else {
      Robot.SUB_DRIVE.driveByPercentOutputs(feedForward, feedForward);
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
    SmartDashboard.putBoolean("Aligning", false);
    Robot.SUB_DRIVE.stopMotors();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    SmartDashboard.putBoolean("Aligning", false);
    Robot.SUB_DRIVE.stopMotors();
  }
}
