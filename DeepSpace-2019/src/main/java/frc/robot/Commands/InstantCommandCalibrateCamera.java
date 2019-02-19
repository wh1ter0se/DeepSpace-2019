/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Util.Util;

public class InstantCommandCalibrateCamera extends InstantCommand {

  public InstantCommandCalibrateCamera() {
    super();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.VISION.updateExposure((int) (Util.getAndSetDouble("Cam Exposure", Constants.BACKUP_EXPOSURE)));
  }

}
