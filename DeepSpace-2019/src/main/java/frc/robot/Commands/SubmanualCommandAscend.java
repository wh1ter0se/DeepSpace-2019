/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;

public class SubmanualCommandAscend extends Command {

  double amperage;

  public SubmanualCommandAscend() {
    requires(Robot.SUB_CLIMB);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.SUB_CLIMB.setSafetyMode(false);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    amperage = Robot.SUB_CLIMB.ascendByJoystick(OI.OPERATOR);
    SmartDashboard.putNumber("Climb Amperage", amperage);
    SmartDashboard.putBoolean("Climb Danger Zone", amperage >= Constants.DANGER_AMPERAGE);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_CLIMB.setSafetyMode(true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_CLIMB.setSafetyMode(true);
  }
}
