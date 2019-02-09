/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Util.Xbox;

/**
 * Mechanism on the carriage that rotates the manipulator along its pitch
 */
public class SubsystemFlipper extends Subsystem {
  
  TalonSRX flipper;

  @Override
  public void initDefaultCommand() {}

  public SubsystemFlipper() {
    flipper = new TalonSRX(Constants.FLIPPER_ID);
      initConfig(20, 0, true);
  }

  /**
   * Moves the flipper based on the left joystick of the given controller
   * @param joy the joystick to read from
   */
  public void moveByJoystick(Joystick joy){
    flipper.set(ControlMode.PercentOutput, Xbox.LEFT_Y(joy));
  }

  /**
   * Moves the flipper based on a given percent output
   * @param speed the percent output to directly assign
   */
  public void movebyPercentOutput(double speed) {
    flipper.set(ControlMode.PercentOutput, speed);
  }

  public Boolean forwardUntilSwitch() {
    return true; // should return true once at switch
  }

  public Boolean backwardUntilSwitch() {
    return true; // should return true once at switch
  }

  public void stopMotor() {
    flipper.set(ControlMode.PercentOutput, 0);
  }

  public void setAmpLimit(int amps) {
    flipper.configPeakCurrentLimit(amps);
  }

  public void setInvert() {
    flipper.setInverted(Constants.FLIPPER_INVERT);
  }

  public void setBraking(Boolean braking) {
    flipper.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);  
  }

  public double getAmps() {
    return flipper.getOutputCurrent();
  }

  public void initConfig(int ampLimit, double ramp, Boolean braking) {
    flipper.setInverted(Constants.FLIPPER_INVERT);
      flipper.configOpenloopRamp(ramp);
      flipper.configContinuousCurrentLimit(ampLimit);
      flipper.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);;
  }

}
