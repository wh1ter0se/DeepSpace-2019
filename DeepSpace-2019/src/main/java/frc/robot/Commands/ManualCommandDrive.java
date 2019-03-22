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

public class ManualCommandDrive extends Command {
  public ManualCommandDrive() {
    requires(Robot.SUB_DRIVE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch(Robot.controlScheme) {
      case RL_GENUINE:
        Robot.SUB_DRIVE.driveRLGenuine(OI.DRIVER, Util.getAndSetDouble("RL Ramp", Constants.BACKUP_RAMP), 
                                                  Util.getAndSetDouble("Caleb Inhibitor", 1));;
        break;
      case RL_HILO:
        Robot.SUB_DRIVE.driveRlHiLo(OI.DRIVER, Util.getAndSetDouble("RL Ramp", Constants.BACKUP_RAMP),
                                               Util.getAndSetDouble("Lower Drive Inhibitor", .4),
                                               Util.getAndSetDouble("Upper Drive Inhibitor", 1));
        break;
    }
    Robot.SUB_DRIVE.updateSpeedData();
    
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
