/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class CyborgCommandGroupDock extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CyborgCommandGroupDock() {
    Boolean secondGear = !Robot.SUB_SHIFTER.isFirstGear();
    if (secondGear) { addSequential(new ButtonCommandSetGear(1, false)); }
    addSequential(new CyborgCommandDock());
    addSequential(new ButtonCommandToggleClamp());
    if (secondGear) { addSequential(new ButtonCommandSetGear(2, false)); }
  }
}
