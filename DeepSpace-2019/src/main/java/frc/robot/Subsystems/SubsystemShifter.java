/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * Pneumatics for the drivetrain's two-speed gearboxes
 */
public class SubsystemShifter extends Subsystem {

  private Solenoid downShift;
  private Solenoid upShift;

  private boolean firstGear;
  private boolean autoShifting;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemShifter() {
    downShift = new Solenoid(Constants.DOWNSHIFT_ID);
    upShift   = new Solenoid(Constants.UPSHIFT_ID);
  }

  /**
   * Puts both gearboxes in first gear
   */
  public void downShift() {
    firstGear = true;
    downShift.set(true);
    upShift.set(false);
  }

  /**
   * Puts both gearboxes in second gear
   */
  public void upShift() {
    firstGear = false;
    downShift.set(false);
    upShift.set(true);
  }

  /**
   * Checks the current gear and toggles it
   */
  public void toggleShift() {
    if (firstGear) {
      firstGear = false;
      downShift.set(false);
      upShift.set(true);
    } else {
      firstGear = true;
      downShift.set(true);
      upShift.set(false);
    }
  }

  /**
   * Checks what gear the gearboxes are in
   * @return whether gearboxes are in first gear
   */
  public Boolean isFirstGear() {
    return firstGear;
  }

  /**
   * Retrieves the state of the automatic shifting
   * @return true if auto, false if manual
   */
  public Boolean isAutoShifting() {
    return autoShifting;
  }

  /**
   * Enables RPM/throttle-based shifting instead of manual shifting
   */
  public void setAutoShifting(boolean isAutoShifting) {
    autoShifting = isAutoShifting;
  }
}
