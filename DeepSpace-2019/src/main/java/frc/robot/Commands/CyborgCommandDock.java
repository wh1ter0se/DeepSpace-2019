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
import frc.robot.Robot;
import frc.robot.Util.MiniPID;
import frc.robot.Util.Util;

public class CyborgCommandDock extends Command {

  private static MiniPID turning;

  private static Boolean canSee;
  private static Boolean inRange;
  private static Boolean isFinished;

  private static double idleSpeed;
  private static double lastAngle;
  private static double loopOutput;

  public CyborgCommandDock() {
    requires(Robot.SUB_DRIVE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    lastAngle = 180;
    turning = new MiniPID(Util.getAndSetDouble("Docking kP", Constants.BACKUP_DOCKING_kP),
                          Util.getAndSetDouble("Docking kI", Constants.BACKUP_DOCKING_kI),
                          Util.getAndSetDouble("Docking kD", Constants.BACKUP_DOCKING_kD));
    turning.setOutputLimits(-.5, .5);
    idleSpeed = Util.getAndSetDouble("Docking Speed", Constants.BACKUP_DOCKING_SPEED);
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
    inRange = Robot.SUB_RECEIVER.getWithinRange();
    // idleSpeed = .25;
    canSee = Robot.SUB_RECEIVER.getLastKnownData()[2] != -1;
    SmartDashboard.putBoolean("canSee", canSee);

    if (canSee) {
      // Robot.SUB_DRIVE.driveByPercentOutputs(idleSpeed - loopOutput, idleSpeed + loopOutput);
      Robot.SUB_DRIVE.driveByPercentOutputs(-1 * loopOutput, loopOutput);
    // } else if (!canSee && inRange) {
    //   Robot.SUB_DRIVE.driveByPercentOutputs(idleSpeed, idleSpeed);
      DriverStation.reportError("I AM DRIVING: " + idleSpeed + "," + loopOutput, false);
    } else {
      if (lastAngle > 0) {
        Robot.SUB_DRIVE.driveByPercentOutputs(idleSpeed, -1 * idleSpeed);
      } else {
        Robot.SUB_DRIVE.driveByPercentOutputs(-1 * idleSpeed, idleSpeed);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished || Robot.SUB_DRIVE.isPushing();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_DRIVE.stopMotors();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_DRIVE.stopMotors();
  }
}
