/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class SubsystemCompressor extends Subsystem {
  
  Compressor comp = new Compressor();

  @Override
  public void initDefaultCommand() {
  }

  public boolean isEnabled() {
    return comp.enabled();
  }

  public void setState(Boolean state) {
    if (state) {  comp.start(); }
    else { comp.stop(); }
  }

  public void toggle() {
    setState(!isEnabled());
  }

}
