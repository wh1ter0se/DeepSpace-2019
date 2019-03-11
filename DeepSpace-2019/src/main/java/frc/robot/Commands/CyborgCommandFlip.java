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

public class CyborgCommandFlip extends Command {

  private static Boolean stalling;

  private static long stallInitTime;
  private static double stallLength;

  public CyborgCommandFlip() {
    requires(Robot.SUB_FLIPPER);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    stalling = false;
    stallLength = Util.getAndSetDouble("Flip Stall Time", .5);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_FLIPPER.moveByPercentOutput(Util.getAndSetDouble("Flip Speed", .2));
    if (stalling && !Robot.SUB_FLIPPER.isStalling()) {
      stalling = false;
    } else if (!stalling && Robot.SUB_FLIPPER.isStalling()) {
      stalling = true;
      stallInitTime = System.currentTimeMillis();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (stalling && stallInitTime + stallLength < System.currentTimeMillis());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_FLIPPER.setAtFront(!Robot.SUB_FLIPPER.isAtFront());
    Robot.SUB_FLIPPER.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.SUB_FLIPPER.stopMotor();
  }
}
