/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Enumeration.MastPosition;
import frc.robot.Util.Util;

public class ButtonCommandChangeMastPosition extends Command {

  Boolean isFinished;

  int displacement;
  int intPosition;

  public ButtonCommandChangeMastPosition(int displacement) {
    this.displacement = displacement;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isFinished = false;
    intPosition = Robot.SUB_MAST.getStoredPosition().toInt() + displacement;
    intPosition = Util.truncateInt(intPosition, 1, 6);
    if (intPosition == Robot.SUB_MAST.getStoredPosition().toInt()) { isFinished = true; }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!isFinished) {
      MastPosition position = MastPosition.SOMEWHERE;
      switch (intPosition) {
        case 1:
          position = MastPosition.HATCH_1;
          break;
        case 2:
          position = MastPosition.CARGO_1;
          break;
        case 3:
          position = MastPosition.HATCH_2;
          break;
        case 4:
          position = MastPosition.CARGO_2;
          break;
        case 5:
          position = MastPosition.HATCH_3;
          break;
        case 6:
          position = MastPosition.CARGO_3;
          break;
      }
      Robot.SUB_MAST.setStoredPosition(position);
      isFinished = true;
    }
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
