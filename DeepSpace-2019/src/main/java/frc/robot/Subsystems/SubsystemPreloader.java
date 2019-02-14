/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * Mechanism to suck balls
 */
public class SubsystemPreloader extends Subsystem {
  
  TalonSRX intake;

  private Solenoid extend;
  private Solenoid retract;

  private Boolean extended;

  @Override
  public void initDefaultCommand() {}

  public SubsystemPreloader() {
    intake = new TalonSRX(Constants.INTAKE_ID);
      intake.configOpenloopRamp(0); //TODO remove when not needed
    extend = new Solenoid(Constants.EXTEND_ID);
    retract = new Solenoid(Constants.RETRACT_ID);
  }

  public void eat(double speed) {
    intake.set(ControlMode.PercentOutput, speed);
  }

  public void stopMotor() {
    intake.set(ControlMode.PercentOutput, 0);
  }

  private void setInverts() {
    intake.setInverted(Constants.INTAKE_INVERT);
  }

  public void extend() {
    extended = true;
    extend.set(true);
    retract.set(false);
  }

  public void retract() {
    extended = false;
    extend.set(false);
    retract.set(true);
  }

  public void toggleExtender() {
    if (extended) {
      retract();
    } else {
      extend();
    }
  }
}
