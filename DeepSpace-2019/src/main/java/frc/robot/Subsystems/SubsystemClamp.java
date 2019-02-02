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
import frc.robot.Constants;

/**
 * Add your docs here.
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

  public void closeClamp() {
    DriverStation.reportWarning("closing", false);
    isOpen = false;
    close.set(true);
    open.set(false);
  }
  
  public void openClamp() {
    DriverStation.reportWarning("opening", false);
    isOpen = true;
    close.set(false);
    open.set(true);
  }

  public void toggleClamp() {
    if (isOpen) {
      closeClamp();
    } else {
      openClamp();
    }
  }
}
