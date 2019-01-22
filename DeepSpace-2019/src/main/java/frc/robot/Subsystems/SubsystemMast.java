/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Enumeration.MastPosition;

/**
 * Add your docs here.
 */
public class SubsystemMast extends Subsystem {

  private static MastPosition storedPosition;

  private static TalonSRX firstStage;
  private static TalonSRX secondStage;

  @Override
  public void initDefaultCommand() {}

  public SubsystemMast() {
    storedPosition = MastPosition.LOW;
    setAmpLimit(60);
  }

  public void setStoredPosition(MastPosition position) {
    storedPosition = position;
  }

  public MastPosition getStoredPosition() {
    return storedPosition;
  }

  public void moveFirstStage(double percentOutput) {
    firstStage.set(ControlMode.PercentOutput, percentOutput);
  }

  public void moveSecondStage(double percentOutput) {
    secondStage.set(ControlMode.PercentOutput, percentOutput);
  }

  public void setAmpLimit(int amps) {
    firstStage.configPeakCurrentLimit(amps);
    secondStage.configPeakCurrentLimit(amps);
  }

  public Boolean[] getLimitSwitches() {
    Boolean[] array = new Boolean[4];
    // array[0] = firstStageLow;
    // array[1] = firstStageHigh;
    // array[2] = SecondStageLow;
    // array[3] = SecondStageHigh;
    return array;
  }

  public void setInverts() {
    firstStage.setInverted(Constants.FIRST_STAGE_INVERT); // TODO positive should move it up
    secondStage.setInverted(Constants.SECOND_STAGE_INVERT); // TODO positive should move it up
  }

}
