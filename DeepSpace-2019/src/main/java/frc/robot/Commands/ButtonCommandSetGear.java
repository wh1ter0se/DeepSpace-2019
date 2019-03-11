/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Enumeration.DriveScheme;

public class ButtonCommandSetGear extends Command {

  private Boolean isFinished;
  private Boolean schemeDependent;
  private int gear;

  private CyborgCommandQuickRumble rumble;

  public ButtonCommandSetGear(int gear, Boolean schemeDependent) {
    requires(Robot.SUB_SHIFTER);
    this.gear = gear;
    this.schemeDependent = schemeDependent;
    rumble = new CyborgCommandQuickRumble(OI.DRIVER, 2, 1, 200);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // rumble.start();
    isFinished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if ((Robot.controlScheme == DriveScheme.RL_GENUINE && schemeDependent) || !schemeDependent) {
      if (gear == 1) {
        Robot.SUB_SHIFTER.downShift(); } 
      else if (gear == 2) {
        Robot.SUB_SHIFTER.upShift(); }
    }
    isFinished = true;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
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
