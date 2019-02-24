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
import frc.robot.Util.Util;

public class ManualCommandTestMastPID extends Command {

  private static Boolean withinAllowableError;

  private static long    inRangeInit;

  private static double  errorMs;


  public ManualCommandTestMastPID() {
    requires(Robot.SUB_FLIPPER);
    requires(Robot.SUB_MAST);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    withinAllowableError = false;

    errorMs = 100;

    Robot.SUB_MAST.setInnerStagePIDF(new double[]{ Util.getAndSetDouble("Test Mast kP", 0),
                                                   Util.getAndSetDouble("Test Mast kI", 0),
                                                   Util.getAndSetDouble("Test Mast kD", 0),
                                                   Util.getAndSetDouble("Test Mast kF", 0)});
    Robot.SUB_MAST.moveInnerStageByPosition(Util.getAndSetDouble("Inner Mast PID Inches", 10));

    Robot.SUB_MAST.setOuterStagePIDF(new double[]{ Util.getAndSetDouble("Test Mast kP", 0),
                                                   Util.getAndSetDouble("Test Mast kI", 0),
                                                   Util.getAndSetDouble("Test Mast kD", 0),
                                                   Util.getAndSetDouble("Test Mast kF", 0)});
    Robot.SUB_MAST.moveOuterStageByPosition(Util.getAndSetDouble("Outer Mast PID Inches", 10));
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putBoolean("Within Error", Robot.SUB_MAST.innerStageWithinRange(Util.getAndSetDouble("Inner Mast PID Inches", 10)));
    if (!withinAllowableError && Robot.SUB_MAST.innerStageWithinRange(Util.getAndSetDouble("Inner Mast PID Inches", 10))) {
      withinAllowableError = true;
      inRangeInit = System.currentTimeMillis();
    } else if (withinAllowableError && System.currentTimeMillis() > inRangeInit + errorMs) {
      Robot.SUB_MAST.moveInnerStageByPercent(0);
    } else if (withinAllowableError && !Robot.SUB_MAST.innerStageWithinRange(Util.getAndSetDouble("Inner Mast PID Inches", 10))) {
      Robot.SUB_MAST.moveInnerStageByPosition(Util.getAndSetDouble("Inner Mast PID Inches", 10));
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
