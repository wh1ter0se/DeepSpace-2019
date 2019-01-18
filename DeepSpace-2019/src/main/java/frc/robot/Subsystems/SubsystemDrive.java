/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Commands.ManualCommandDrive;
import frc.robot.Util.Xbox;

/**
 * Subsystem controlling the motors in the drivetrain
 */
public class SubsystemDrive extends Subsystem {

  private static CANSparkMax leftMaster;
  private static CANSparkMax leftSlave;
  private static CANSparkMax rightMaster;
  private static CANSparkMax rightSlave;

  private static CANPIDController leftPID;
  private static CANPIDController rightPID;

  private static double[] highestRPM;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandDrive());
  }

  public SubsystemDrive() {
    DriverStation.reportWarning("SUB_DRIVE CREATED", false);
    leftMaster  = new CANSparkMax(Constants.LEFT_MASTER_ID, MotorType.kBrushless);
    leftSlave   = new CANSparkMax(Constants.LEFT_SLAVE_ID, MotorType.kBrushless);

    rightMaster = new CANSparkMax(Constants.RIGHT_MASTER_ID, MotorType.kBrushless);
    rightSlave  = new CANSparkMax(Constants.RIGHT_SLAVE_ID, MotorType.kBrushless);

    highestRPM = new double[]{0,0};
  }

  /**
   * Rocket League/Tank hybrid control system
   * Left and right triggers accelerate linearly and left stick rotates
   * @param joy the joystick to be used
   */
  public void driveRLTank(Joystick joy, double ramp) {
    setInverts();
    setBraking(true);
    setRamps(ramp);

    double adder = Xbox.RT(joy) - Xbox.LT(joy);
    double left = adder + (Xbox.LEFT_X(joy) / 1.333333);
    double right = adder - (Xbox.LEFT_X(joy) / 1.333333);
    left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
    right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
    
    leftMaster.set(left);
      leftSlave.set(left);
    rightMaster.set(right);
      rightSlave.set(right);
  }

  public void driveByPosition(double inches, double[] PID) {
    double rotations = Constants.ROTATIONS_PER_INCH * inches;

    leftMaster.getPIDController().setP(PID[0], 0);
      leftMaster.getPIDController().setI(PID[1], 0);
      leftMaster.getPIDController().setD(PID[2], 0);
      leftMaster.burnFlash();
    rightMaster.getPIDController().setP(PID[0], 1);
      rightMaster.getPIDController().setI(PID[1], 1);
      rightMaster.getPIDController().setD(PID[2], 1);
      rightMaster.burnFlash();

    leftMaster.setMotorType(MotorType.kBrushless);
    rightMaster.setMotorType(MotorType.kBrushless);

    leftMaster.getPIDController().setOutputRange(-100, 100);
    rightMaster.getPIDController().setOutputRange(-100, 100);

    leftMaster.getPIDController().setReference(rotations * Constants.ENCODER_TICKS_PER_ROTATION, ControlType.kPosition, 0);
    rightMaster.getPIDController().setReference(rotations * Constants.ENCODER_TICKS_PER_ROTATION, ControlType.kPosition, 1);
  }

  public double[] getError(double[] initEncoderPositions, double inches) {
    double ticks = inches * Constants.ENCODER_TICKS_PER_ROTATION * Constants.ROTATIONS_PER_INCH;
    double[] output = new double[2];
    output[0] = initEncoderPositions[0] 
                + (ticks *= leftMaster.getInverted() ? -1 : 1) 
                - leftMaster.getEncoder().getPosition();
    output[1] = initEncoderPositions[1] 
                + (ticks *= rightMaster.getInverted() ? -1 : 1) 
                - rightMaster.getEncoder().getPosition();
    return output;
  }

  public double[] getEncoderPositions() {
    double[] output = new double[2];
    output[0] = leftMaster.getEncoder().getPosition();
    output[1] = rightMaster.getEncoder().getPosition();
    return output;
  }

  /**
   * Gets the applied outputs/percent outputs of each motor
   * @return [0] = leftMaster % output
   *         [1] = rightMaster % output
   */
  public double[] getAppliedOutputs() {
    return new double[]{ leftMaster.getAppliedOutput(), rightMaster.getAppliedOutput() };
  }

  /**
   * Sets all motor controller values to zero
   */
  public void stopMotors() {
    leftMaster.stopMotor();
      leftSlave.stopMotor();
    rightMaster.stopMotor();
      rightSlave.stopMotor();
  }

  /**
   * Sets the inverts of each motor controller
   */
  private void setInverts() {
    leftMaster.setInverted(Constants.LEFT_DRIVE_INVERT);
      leftSlave.setInverted(Constants.LEFT_DRIVE_INVERT);
    rightMaster.setInverted(Constants.RIGHT_DRIVE_INVERT);
      rightSlave.setInverted(Constants.RIGHT_DRIVE_INVERT);
  }

  /**
   * Sets each motor to braking or coasting mode
   * @param braking true if braking mode, false if coasting mode
   */
  private void setBraking(Boolean braking) {
    leftMaster.setIdleMode(braking ? IdleMode.kBrake : IdleMode.kCoast);
      leftSlave.setIdleMode(braking ? IdleMode.kBrake : IdleMode.kCoast);
    rightMaster.setIdleMode(braking ? IdleMode.kBrake : IdleMode.kCoast);
      rightSlave.setIdleMode(braking ? IdleMode.kBrake : IdleMode.kCoast);
  }

  /**
   * Sets the ramp rate of each motor
   * @param ramp ramp rate in seconds
   */
  private void setRamps(double ramp) {
    leftMaster.setRampRate(ramp);
      leftSlave.setRampRate(ramp);
    rightMaster.setRampRate(ramp);
      rightSlave.setRampRate(ramp);
  }

  /**
   * Retrieves the percentOutput/speed values of each motor controller
   * @return array of percentOutputs/speeds stored as doubles
   *         [0] = Left Master speed
   *         [1] = Left Slave speed
   *         [2] = Right Master speed
   *         [3] = Right Slave speed
   */
  public double[] getMotorValues() {
    double[] output = new double[4];
    output[0] = leftMaster.get();
    output[1] = leftSlave.get();
    output[2] = rightMaster.get();
    output[3] = rightSlave.get();
    return output;
  }

  public double[] getHighestVelocities() {
    if (leftMaster.getEncoder().getVelocity() > highestRPM[0]) {
      highestRPM[0] = leftMaster.getEncoder().getVelocity(); }
    if (rightMaster.getEncoder().getVelocity() > highestRPM[1]) {
      highestRPM[0] = rightMaster.getEncoder().getVelocity(); }
    return highestRPM;
  }

  
}
