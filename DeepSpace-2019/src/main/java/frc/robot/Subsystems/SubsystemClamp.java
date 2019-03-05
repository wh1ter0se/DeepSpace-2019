/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Hatch Cover clamp on the swinging manipulator
 */
public class SubsystemClamp extends Subsystem {

  private boolean isOpen;
  
  private Solenoid close;
  private Solenoid open;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemClamp() {
    isOpen = false;
    close = new Solenoid(Constants.CLOSE_CLAMP_ID);
    open = new Solenoid(Constants.OPEN_CLAMP_ID);
  }

  /**
   * Closes the clamp
   */
  public void closeClamp() {
    DriverStation.reportWarning("closing", false);
    SmartDashboard.putBoolean("Open Clamp", false);
    isOpen = false;
    close.set(true);
    open.set(false);
  }
  
  /**
   * Opens the clamp
   */
  public void openClamp() {
    DriverStation.reportWarning("opening", false);
    SmartDashboard.putBoolean("Open Clamp", true);
    isOpen = true;
    close.set(false);
    open.set(true);
  }

  /**
   * Checks the state of the clmap and sets it to the opposite
   */
  public void toggleClamp() {
    if (isOpen) {
      closeClamp();
    } else {
      openClamp();
    }
  }
}
