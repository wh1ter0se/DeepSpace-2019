/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * Mechanism to suck balls
 */
public class SubsystemPreloader extends Subsystem {
  
  TalonSRX intake;

  private Solenoid extend;
  private Solenoid retract;

  private DoubleSolenoid intakeSolenoid;

  private Boolean extended;

  @Override
  public void initDefaultCommand() {}

  public SubsystemPreloader() {
    intake = new TalonSRX(Constants.INTAKE_ID);
      setInvert();
      intake.configOpenloopRamp(0); //TODO remove when not needed
    extend = new Solenoid(Constants.EXTEND_ID);
      // extend.setPulseDuration(Constants.SOLENOID_PULSE_SECONDS);
    retract = new Solenoid(Constants.RETRACT_ID);
      // retract.setPulseDuration(Constants.SOLENOID_PULSE_SECONDS);
  }

  public void eat(double speed) {
    intake.set(ControlMode.PercentOutput, speed);
  }

  public void stopMotor() {
    intake.set(ControlMode.PercentOutput, 0);
  }

  private void setInvert() {
    intake.setInverted(Constants.INTAKE_INVERT);
  }

  public void extend() {
    extended = true;
    extend.set(true);
    retract.set(false);
    // pulseSolenoids();
  }

  public void retract() {
    extended = false;
    extend.set(false);
    retract.set(true);
    // pulseSolenoids();
  }

  public void toggleExtender() {
    if (extended) {
      retract();
    } else {
      extend();
    }
  }

  public void pulseSolenoids() {
    extend.startPulse();
    retract.startPulse();
  }

  public double getAmps() {
    return intake.getOutputCurrent();
  }

  public double getPercentOutput() {
    return intake.getMotorOutputPercent();
  }
}
