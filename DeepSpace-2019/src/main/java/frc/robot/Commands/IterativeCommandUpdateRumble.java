/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Util.Util;

public class IterativeCommandUpdateRumble extends Command {
  
  Boolean probing;

  double  lockTime;

  long    probingInit;

  public IterativeCommandUpdateRumble() {
    requires(Robot.SUB_RECEIVER);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    probing = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    lockTime = Util.getAndSetDouble("Lock Time", 500);
    if (!probing && Robot.SUB_RECEIVER.getLastKnownData()[2] != -1) {
      OI.DRIVER.setRumble(RumbleType.kLeftRumble, 1);
      OI.DRIVER.setRumble(RumbleType.kRightRumble, 0);
      probing = true;
      probingInit = System.currentTimeMillis();
    } else if (Robot.SUB_RECEIVER.getLastKnownData()[2] == -1) {
      OI.DRIVER.setRumble(RumbleType.kLeftRumble, 0);
      OI.DRIVER.setRumble(RumbleType.kRightRumble, 0);
      probing = false;
    } else if (probing && System.currentTimeMillis() > probingInit + lockTime) {
      OI.DRIVER.setRumble(RumbleType.kLeftRumble, 0);
      OI.DRIVER.setRumble(RumbleType.kRightRumble, 1);
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
    OI.DRIVER.setRumble(RumbleType.kLeftRumble, 0);
    OI.DRIVER.setRumble(RumbleType.kRightRumble, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    OI.DRIVER.setRumble(RumbleType.kLeftRumble, 0);
    OI.DRIVER.setRumble(RumbleType.kRightRumble, 0);
  }
}
