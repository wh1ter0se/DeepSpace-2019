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
 * Just starts up the extremely loud pneumatic compressor
 */
public class SubsystemCompressor extends Subsystem {
  
  Compressor comp = new Compressor();

  @Override
  public void initDefaultCommand() {
  }

  /**
   * Retrieves the on/off state of the compressor
   * @return true if compressor is active, false if it is idle
   */
  public boolean isEnabled() {
    return comp.enabled();
  }

  /**
   * Sets the on/off state of the compressor
   * @param state true for on, false for off
   */
  public void setState(Boolean state) {
    if (state) {  comp.start(); }
    else { comp.stop(); }
  }

  /**
   * Checks the on/off state of the compressor and sets
   * it to the opposite
   */
  public void toggle() {
    setState(!isEnabled());
  }

}
