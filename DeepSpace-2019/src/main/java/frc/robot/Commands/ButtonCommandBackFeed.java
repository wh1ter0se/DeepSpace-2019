/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Util.Util;

public class ButtonCommandBackFeed extends Command {
  public ButtonCommandBackFeed() {
    requires(Robot.SUB_PRELOADER);
    requires(Robot.SUB_LAUNCHER);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_PRELOADER.eat(-1 * Util.getAndSetDouble("Eat Speed", 1));
    Robot.SUB_LAUNCHER.spit(-1 * Util.getAndSetDouble("Spit Speed", 1));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_PRELOADER.stopMotor();
    Robot.SUB_LAUNCHER.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_PRELOADER.stopMotor();
    Robot.SUB_LAUNCHER.stopMotor();
  }
}
