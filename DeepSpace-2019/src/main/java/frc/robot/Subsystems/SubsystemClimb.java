/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Util.Xbox;

/**
 * Add your docs here.
 */
public class SubsystemClimb extends Subsystem {

  private static TalonSRX climber;

  private boolean safetyMode;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemClimb() {
    safetyMode = true;
    climber = new TalonSRX(Constants.CLIMBER_ID);
    setAmpLimit(60);
  }

  public double ascendByJoystick(Joystick joy) {
    double percentOutput = Xbox.RT(joy) - Xbox.LT(joy);
    climber.set(ControlMode.PercentOutput, percentOutput);
    DriverStation.reportWarning(percentOutput + "", false);
    return climber.getOutputCurrent();
  }

  public void setAmpLimit(int amps) {
    climber.configPeakCurrentLimit(amps);
  }

  public void setSafetyMode(boolean active) {
    safetyMode = active;
  }

  public Boolean getSafetyMode() {
    return safetyMode;
  }
}
