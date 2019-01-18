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
 * Add your docs here.
 */
public class SubsystemShifter extends Subsystem {

  private Solenoid downShiftLeft;
  private Solenoid downShiftRight;
  private Solenoid upShiftLeft;
  private Solenoid upShiftRight;

  private boolean firstGear;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemShifter() {
    firstGear = true;
    // downShiftLeft  = new Solenoid(Constants.LEFT_DOWNSHIFT_ID);
    // downShiftRight = new Solenoid(Constants.RIGHT_DOWNSHIFT_ID);
    // upShiftLeft    = new Solenoid(Constants.LEFT_UPSHIFT_ID);
    // upShiftRight   = new Solenoid(Constants.RIGHT_UPSHIFT_ID);
  }

  /**
   * Puts both gearboxes in first gear
   */
  public void downShift() {
    firstGear = true;
    // downShiftLeft.set(true);
    // downShiftRight.set(true);
    // upShiftLeft.set(false);
    // upShiftRight.set(false);
  }

  /**
   * Puts both gearboxes in second gear
   */
  public void upShift() {
    firstGear = false;
    // downShiftLeft.set(false);
    // downShiftRight.set(false);
    // upShiftLeft.set(true);
    // upShiftRight.set(true);
  }

  /**
   * Checks the current gear and toggles it
   */
  public void toggleShift() {
    if (firstGear) {
      firstGear = false;
      // downShiftLeft.set(false);
      // downShiftRight.set(false);
      // upShiftLeft.set(true);
      // upShiftRight.set(true);
    } else {
      firstGear = true;
      // downShiftLeft.set(true);
      // downShiftRight.set(true);
      // upShiftLeft.set(false);
      // upShiftRight.set(false);
    }
  }

  /**
   * Checks what gear the gearboxes are in
   * @return whether gearboxes are in first gear
   */
  public Boolean isFirstGear() {
    return firstGear;
  }

}
