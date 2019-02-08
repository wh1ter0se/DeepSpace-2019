/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
  /**
 * Code that kills compressor until interruption
 */
public class ToggleCommandKillCompressor extends Command {

  public ToggleCommandKillCompressor() {
      requires(Robot.SUB_COMPRESSOR);
  }

  protected void initialize() {
    Robot.SUB_COMPRESSOR.setState(false);
  }

  protected void execute() {}

  protected boolean isFinished() {
      return false;
  }

  protected void end() {
    Robot.SUB_COMPRESSOR.setState(true);
  }

  protected void interrupted() {
    end();
  }
}
