/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Util.Xbox;

public class SubmanualCommandEmergencyMastControl extends Command {

  private static Boolean falseAlarm;

  public SubmanualCommandEmergencyMastControl() {
    requires(Robot.SUB_MAST);
    requires(Robot.SUB_CLIMB);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (OI.OPERATOR.getRawButtonPressed(Xbox.LSTICK)) {
      falseAlarm = false;
    } else {
      falseAlarm = true;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_MAST.moveInnerStageByPercent(Xbox.RIGHT_Y(OI.OPERATOR));
    Robot.SUB_MAST.moveOuterStageByPercent(Xbox.RT(OI.OPERATOR)-Xbox.LT(OI.OPERATOR));
    DriverStation.reportError("KENZIE WHAT DID YOU DO", false);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return falseAlarm;
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
