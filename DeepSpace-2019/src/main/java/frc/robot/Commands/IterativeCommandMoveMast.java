/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 inner. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the inner BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Enumeration.MastPosition;
import frc.robot.Util.Util;

public class IterativeCommandMoveMast extends Command {

  private static Boolean      stable;
  private static Boolean      innerWithinAllowableError;
  private static Boolean      outerWithinAllowableError;

  private static double       innerStageHeight;
  private static double       outerStageHeight;
  private static double       errorMs;
  private static double       allowableError;

  private static long         innerInRangeInit;
  private static long         outerInRangeInit;

  private static MastPosition position;

  public IterativeCommandMoveMast() {
    requires(Robot.SUB_MAST);
  }

  // Called just before this Command runs the inner time
  @Override
  protected void initialize() {
    innerWithinAllowableError = false;
    outerWithinAllowableError = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_MAST.setInnerStagePIDF(new double[]{ Util.getAndSetDouble("Inner Mast kP", 0),
                                                   Util.getAndSetDouble("Inner Mast kI", 0),
                                                   Util.getAndSetDouble("Inner Mast kD", 0),
                                                   Util.getAndSetDouble("Inner Mast kF", 0)});
    Robot.SUB_MAST.setOuterStagePIDF(new double[]{ Util.getAndSetDouble("Outer Mast kP", 0),
                                                   Util.getAndSetDouble("Outer Mast kI", 0),
                                                   Util.getAndSetDouble("Outer Mast kD", 0),
                                                   Util.getAndSetDouble("Outer Mast kF", 0)});                                                  

    position = Robot.SUB_MAST.getStoredPosition();

    allowableError = Util.getAndSetDouble("Mast Allowable Error", Constants.MAST_ALLOWABLE_ERROR);
    
    switch(position) {
      case SOMEWHERE:
      case HATCH_1:
        innerStageHeight = Constants.NOT_QUITE_ZERO;
        outerStageHeight = Constants.NOT_QUITE_ZERO;
        break;

      case CARGO_1:
        innerStageHeight = Constants.CARGO_1_HEIGHT;
        outerStageHeight = Constants.NOT_QUITE_ZERO;
        break;

      case HATCH_2:
        innerStageHeight = Constants.HATCH_2_HEIGHT;
        outerStageHeight = Constants.NOT_QUITE_ZERO;
        break;

      case CARGO_2:
        innerStageHeight = Constants.TOP_TIER_INNER_HEIGHT;
        outerStageHeight = Constants.CARGO_2_HEIGHT;
        break;

      case HATCH_3:
        innerStageHeight = Constants.TOP_TIER_INNER_HEIGHT;
        outerStageHeight = Constants.HATCH_3_HEIGHT;
        break;

      case CARGO_3:
        innerStageHeight = Constants.TOP_TIER_INNER_HEIGHT;
        outerStageHeight = Constants.CARGO_3_HEIGHT;
        break;
    }

    stable = Robot.SUB_MAST.innerStageWithinRange(innerStageHeight, allowableError) 
          && Robot.SUB_MAST.outerStageWithinRange(outerStageHeight, allowableError);
    SmartDashboard.putBoolean("Stable Mast", stable);

    if (Robot.SUB_MAST.innerStageWithinRange(innerStageHeight, allowableError) && !innerWithinAllowableError) { // if it just entered the range
      innerInRangeInit = System.currentTimeMillis();
      innerWithinAllowableError = true;
    } else if (!Robot.SUB_MAST.innerStageWithinRange(innerStageHeight, allowableError)) { // outside of the range
      innerWithinAllowableError = false;
      Robot.SUB_MAST.moveInnerStageByPosition(innerStageHeight);
    } else if (innerWithinAllowableError && innerInRangeInit + errorMs < System.currentTimeMillis()) { // if it's just now timing out on the range
      Robot.SUB_MAST.moveInnerStageByPercent(0);
    } else if (innerWithinAllowableError && innerInRangeInit + errorMs > System.currentTimeMillis()) { // if it's in range but not timed out
      Robot.SUB_MAST.moveInnerStageByPosition(innerStageHeight);
    }

    if (Robot.SUB_MAST.outerStageWithinRange(outerStageHeight, allowableError) && !outerWithinAllowableError) { // if it just entered the range
      outerInRangeInit = System.currentTimeMillis();
      outerWithinAllowableError = true;
    } else if (!Robot.SUB_MAST.outerStageWithinRange(outerStageHeight, allowableError)) { // outside of the range
      outerWithinAllowableError = false;
      Robot.SUB_MAST.moveOuterStageByPosition(outerStageHeight);
    } else if (outerWithinAllowableError && outerInRangeInit + errorMs < System.currentTimeMillis()) { // if it's just now timing out on the range
      Robot.SUB_MAST.moveOuterStageByPercent(0);
    } else if (outerWithinAllowableError && outerInRangeInit + errorMs > System.currentTimeMillis()) { // if it's in range but not timed out
      Robot.SUB_MAST.moveOuterStageByPosition(outerStageHeight);
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
    Robot.SUB_MAST.stopMotors();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_MAST.stopMotors();
  }
}
