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
  private static Boolean      loopRunning;

  private static double       firstStageSpeed;
  private static double       secondStageSpeed;

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
    loopRunning = Robot.SUB_MAST.getLoopRunning();

    firstStageSpeed = Math.abs(Util.getAndSetDouble("First Stage Speed", Constants.FIRST_STAGE_SPEED));
    secondStageSpeed = Math.abs(Util.getAndSetDouble("Second Stage Speed", Constants.FIRST_STAGE_SPEED));
    
    switch(position) {
      case HATCH_1:
        if (!Robot.SUB_MAST.getLimitSwitches()[0]) { Robot.SUB_MAST.moveFirstStageByPercent(-1 * firstStageSpeed); } // ram first stage down if not already
        if (!Robot.SUB_MAST.getLimitSwitches()[2]) { Robot.SUB_MAST.moveSecondStageByPercent(-1 * secondStageSpeed); } // ram second stage down if not already
        stable = Robot.SUB_MAST.getLimitSwitches()[0] && Robot.SUB_MAST.getLimitSwitches()[2];
        break;

      case CARGO_1:
        if (!Robot.SUB_MAST.getLimitSwitches()[2]) { Robot.SUB_MAST.moveSecondStageByPercent(-1 * secondStageSpeed); } // ram second stage down if not already
        if (!loopRunning) {
          loopRunning = true;
          Robot.SUB_MAST.moveFirstStageByPosition(Constants.CARGO_1_HEIGHT); // move first stage to x position
        }
        stable  = Robot.SUB_MAST.getLimitSwitches()[2] && Robot.SUB_MAST.firstStageWithinRange(Constants.CARGO_1_HEIGHT);
        break;

      case HATCH_2:
        if (!Robot.SUB_MAST.getLimitSwitches()[2]) { Robot.SUB_MAST.moveSecondStageByPercent(-1 * secondStageSpeed); } // ram second stage down if not already
        if (!loopRunning) {
          loopRunning = true;
          Robot.SUB_MAST.moveFirstStageByPosition(Constants.HATCH_2_HEIGHT); // move first stage to x position
        }
        stable = Robot.SUB_MAST.getLimitSwitches()[2] && Robot.SUB_MAST.firstStageWithinRange(Constants.HATCH_2_HEIGHT);
        break;

      case CARGO_2:
        if (!Robot.SUB_MAST.getLimitSwitches()[1]) { Robot.SUB_MAST.moveFirstStageByPercent(firstStageSpeed); } // ram first stage up if not already
        if (!loopRunning) {
          loopRunning = true;
          Robot.SUB_MAST.moveSecondStageByPosition(Constants.CARGO_2_HEIGHT); // move second stage to x position
        }
        stable = Robot.SUB_MAST.getLimitSwitches()[2] && Robot.SUB_MAST.secondStageWithinRange(Constants.CARGO_2_HEIGHT);
        break;

      case HATCH_3:
        if (!Robot.SUB_MAST.getLimitSwitches()[1]) { Robot.SUB_MAST.moveFirstStageByPercent(firstStageSpeed); } // ram first stage up if not already
        if (!loopRunning) {
          loopRunning = true;
          Robot.SUB_MAST.moveSecondStageByPosition(Constants.HATCH_3_HEIGHT); // move second stage to x position
        }
        stable = Robot.SUB_MAST.getLimitSwitches()[1] && Robot.SUB_MAST.secondStageWithinRange(Constants.HATCH_3_HEIGHT);
        break;

      case CARGO_3:
        if (!Robot.SUB_MAST.getLimitSwitches()[1]) { Robot.SUB_MAST.moveFirstStageByPercent(firstStageSpeed); } // ram first stage up if not already
        if (!Robot.SUB_MAST.getLimitSwitches()[3]) { Robot.SUB_MAST.moveSecondStageByPercent(secondStageSpeed); } // ram second stage up if not already
        stable = Robot.SUB_MAST.getLimitSwitches()[1] && Robot.SUB_MAST.getLimitSwitches()[3];
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

  private Boolean firstStageInSafeRange() {
    // return Robot.SUB_MAST.getFirstStageInches() < Constants.FIRST_STAGE_MAX_HEIGHT;
    // TODO finish
    return true;
  }

  private Boolean secondStageInSafeRange() {
    return true;
  }
}
