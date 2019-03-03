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

/**
 * Ball hood that ejects balls
 */
public class SubsystemLauncher extends Subsystem {
  
  TalonSRX launcher;

  @Override
  public void initDefaultCommand() {}

  public SubsystemLauncher() {
    launcher = new TalonSRX(Constants.LAUNCHER_ID);
    setInverts();
  }

  public void spit(double speed) {
    launcher.set(ControlMode.PercentOutput, speed);
  }

  public void stopMotor() {
    launcher.set(ControlMode.PercentOutput, 0);
  }

  private void setInverts() {
    launcher.setInverted(Constants.LAUNCHER_INVERT);
  }

  public double getAmps() {
    return launcher.getOutputCurrent();
  }

  public double getPercentOutput() {
    return launcher.getMotorOutputPercent();
  }

}
