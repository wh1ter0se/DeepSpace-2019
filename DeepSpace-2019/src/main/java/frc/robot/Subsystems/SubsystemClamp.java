/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class SubsystemClamp extends Subsystem {

  private boolean isOpen;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemClamp() {
    isOpen = false;
    // instantiate pneumatic stuff
  }

  public void closeClamp() {
    isOpen = false;
    // close pneumatics
  }
  
  public void openClamp() {
    isOpen = true;
    // open pneumatics
  }

  public void toggleClamp() {
    if (isOpen) {
      isOpen = false;
      // close pneumatics
    } else {
      isOpen = true;
      // open pneumatics
    }
  }
}
