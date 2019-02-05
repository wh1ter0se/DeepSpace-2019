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
import frc.robot.Util.Util;
import frc.robot.Util.Xbox;

public class CyborgCommandAutoShift extends Command {

  double downshiftRPM;
  double throttle;
  double upshiftRPM;

  double[][] shiftingPoints;

  public CyborgCommandAutoShift() {
    requires(Robot.SUB_SHIFTER);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.SUB_SHIFTER.setAutoShifting(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // throttles, upshift RPMs, downshift RPMs
    shiftingPoints = new double[][]{
      {.25, Util.getAndSetDouble(".25 Upshift RPM", Constants.QUARTER_UPSHIFT_RPM), Util.getAndSetDouble(".25 Downshift RPM", Constants.QUARTER_DOWNSHIFT_RPM)},
      { .5, Util.getAndSetDouble(".5 Upshift RPM", Constants.HALF_UPSHIFT_RPM), Util.getAndSetDouble(".5 Downshift RPM", Constants.HALF_DOWNSHIFT_RPM)},
      {.75, Util.getAndSetDouble(".75 Upshift RPM", Constants.THREE_QUARTERS_UPSHIFT_RPM), Util.getAndSetDouble(".75 Downshift RPM", Constants.THREE_QUARTERS_DOWNSHIFT_RPM)},
      {1.0, Util.getAndSetDouble("1.0 Upshift RPM", Constants.FULL_UPSHIFT_RPM), Util.getAndSetDouble("1.0 Downshift RPM", Constants.FULL_DOWNSHIFT_RPM)}};

    throttle = Math.abs(Xbox.RT(OI.DRIVER) - Xbox.LT(OI.DRIVER));

    if      (throttle <= shiftingPoints[0][0]) {
      upshiftRPM   = shiftingPoints[0][1];
      downshiftRPM = shiftingPoints[0][2]; } 
    else if (throttle <= shiftingPoints[1][0]) {
      upshiftRPM   = shiftingPoints[1][1];
      downshiftRPM = shiftingPoints[1][2]; } 
    else if (throttle <= shiftingPoints[2][0]) {
      upshiftRPM   = shiftingPoints[2][1];
      downshiftRPM = shiftingPoints[2][2]; } 
    else if (throttle <= shiftingPoints[3][0]) {
      upshiftRPM   = shiftingPoints[3][1];
      downshiftRPM = shiftingPoints[3][2]; } 

    // SAVE THIS -- this is the old, kinda working code
      // double upshiftRPM = Util.getAndSetDouble("Upshift RPM", Constants.UPSHIFT_RPM);
      // double downshiftRPM = Util.getAndSetDouble("Downshift RPM", Constants.DOWNSHIFT_RPM);

    if (Robot.SUB_SHIFTER.isFirstGear() && Robot.SUB_DRIVE.getVelocities()[0] >= upshiftRPM && Robot.SUB_DRIVE.getVelocities()[1] >= upshiftRPM && !Robot.SUB_DRIVE.isPushing()) {
      Robot.SUB_SHIFTER.upShift();
    } else if (!Robot.SUB_SHIFTER.isFirstGear() && Robot.SUB_DRIVE.getVelocities()[0] <= downshiftRPM && Robot.SUB_DRIVE.getVelocities()[1] <= downshiftRPM) {
      Robot.SUB_SHIFTER.downShift();
    } else if (!Robot.SUB_SHIFTER.isFirstGear() && Robot.SUB_DRIVE.isPushing()) {
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
    Robot.SUB_SHIFTER.setAutoShifting(false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
