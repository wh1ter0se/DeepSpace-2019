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
import frc.robot.Enumeration.MastPosition;
import frc.robot.Util.Util;

public class IterativeCommandMoveMast extends Command {

  private static Boolean      stable;

  private static MastPosition position;

  public IterativeCommandMoveMast() {
    requires(Robot.SUB_MAST);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    position = Robot.SUB_MAST.getStoredPosition();
    switch (position) {
      case LOW:
        stable = Robot.SUB_MAST.getLimitSwitches()[0] && Robot.SUB_MAST.getLimitSwitches()[2];
        
        if (!Robot.SUB_MAST.getLimitSwitches()[0]) { // lower first stage until limit
          Robot.SUB_MAST.moveFirstStage(-1 * Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED))); }
        if (!Robot.SUB_MAST.getLimitSwitches()[2]) { // lower second stage until limit
          Robot.SUB_MAST.moveSecondStage(-1 * Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED))); }
        break;

      case MID:
        stable = Robot.SUB_MAST.getLimitSwitches()[1] && Robot.SUB_MAST.getLimitSwitches()[2];
          
        if (!Robot.SUB_MAST.getLimitSwitches()[1]) { // raise first stage until limit
          Robot.SUB_MAST.moveFirstStage(Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED))); }
        if (!Robot.SUB_MAST.getLimitSwitches()[2]) { // lower second stage until limit
          Robot.SUB_MAST.moveSecondStage(-1 * Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED))); }
        break;

      case HIGH:
        stable = Robot.SUB_MAST.getLimitSwitches()[1] && Robot.SUB_MAST.getLimitSwitches()[3];
          
        if (!Robot.SUB_MAST.getLimitSwitches()[1]) { // raise first stage until limit
          Robot.SUB_MAST.moveFirstStage(Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED))); }
        if (!Robot.SUB_MAST.getLimitSwitches()[3]) { // raise second stage until limit
          Robot.SUB_MAST.moveSecondStage(Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED))); }
        break;

      default:
        DriverStation.reportError("DESIRED MAST POSITION NOT FOUND", false);
        break;
    }

    SmartDashboard.putBoolean("Stable Mast", stable);
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
