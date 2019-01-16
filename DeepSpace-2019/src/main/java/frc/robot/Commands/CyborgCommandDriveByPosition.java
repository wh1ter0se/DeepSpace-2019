/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Util.Util;

public class CyborgCommandDriveByPosition extends Command {

  private boolean inRange;
  private boolean lowSpeed;

  private double[] initPositions;

  private double distance;

  public CyborgCommandDriveByPosition() {
    requires(Robot.SUB_DRIVE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    inRange = false;

    distance = Util.getAndSetDouble("Auto Test Distance", 12);

    initPositions = Robot.SUB_DRIVE.getEncoderPositions();

    Robot.SUB_DRIVE.driveByPosition(distance, new double[]{
      Util.getAndSetDouble("Position kP", Constants.BACKUP_POSITION_kP),
      Util.getAndSetDouble("Position kI", Constants.BACKUP_POSITION_kI),
      Util.getAndSetDouble("Position kD", Constants.BACKUP_POSITION_kD)});
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    DriverStation.reportWarning(Robot.SUB_DRIVE.getError(initPositions, distance)[0] + "," + Robot.SUB_DRIVE.getError(initPositions, distance)[1], false);
    inRange = Robot.SUB_DRIVE.getError(initPositions, distance)[0] < Constants.MAX_ALLOWABLE_ERROR
           && Robot.SUB_DRIVE.getError(initPositions, distance)[1] < Constants.MAX_ALLOWABLE_ERROR;
    lowSpeed = Robot.SUB_DRIVE.getAppliedOutputs()[0] < Constants.MAX_ALLOWABLE_AO 
            && Robot.SUB_DRIVE.getAppliedOutputs()[1] < Constants.MAX_ALLOWABLE_AO;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return inRange && lowSpeed;
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
  }
}
