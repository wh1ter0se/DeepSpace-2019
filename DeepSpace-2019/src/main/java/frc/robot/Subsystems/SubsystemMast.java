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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Commands.IterativeCommandMoveMast;
import frc.robot.Enumeration.MastPosition;
import frc.robot.Util.Xbox;

/**
 * Two-stage system that moves the maniuplator vertically
 */
public class SubsystemMast extends Subsystem {

  private static MastPosition storedPosition;

  private static TalonSRX innerStage;
  private static TalonSRX outerStage;

  private static Boolean loopRunning;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new IterativeCommandMoveMast());
  }

  public SubsystemMast() {
    storedPosition = MastPosition.HATCH_1;

    innerStage  = new TalonSRX(Constants.INNER_STAGE_ID);
    outerStage = new TalonSRX(Constants.OUTER_STAGE_ID);

    loopRunning = false;

    initConfig(50, 0, 1, 0, true);
  }

  public void setStoredPosition(MastPosition position) {
    storedPosition = position;
    loopRunning    = false;
  }

  public MastPosition getStoredPosition() {
    return storedPosition;
  }

  /**
   * Moves the inner stage by a percent output
   * @param speed percent output to move at
   */
  public void moveInnerStageByPercent(double speed) {
    innerStage.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Moves the inner stage to a set position
   * @param inches the target height in inches
   */
  public void moveInnerStageByPosition(double inches) {
    innerStage.set(ControlMode.Position, -1 * inches * Constants.INNER_MAST_TICKS_PER_INCH);
    innerStage.configAllowableClosedloopError(0,0);
  }

  /**
   * Shows if the inner mast is stable (within the allowable error)
   * @param inches the target height of the current loop
   * @return       whether the position is within the allowable error from the target or not
   */
  public Boolean innerStageWithinRange(double inches, double allowableError) {
    double position = Math.abs(innerStage.getSensorCollection().getQuadraturePosition());
    double target   = Math.abs(inches * Constants.INNER_MAST_TICKS_PER_INCH);
    return Math.abs(position - target) < allowableError * Constants.INNER_MAST_TICKS_PER_INCH;
  }

  /**
   * Moves the outer stage by a percent output
   * @param speed percent output to move at
   */
  public void moveOuterStageByPercent(double speed) {
    outerStage.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Moves the outer stage to a set position
   * @param inches the target height in inches
   */
  public void moveOuterStageByPosition(double inches) {
    outerStage.set(ControlMode.Position, -1 * inches * Constants.OUTER_MAST_TICKS_PER_INCH);
    outerStage.configAllowableClosedloopError(0,0);
  }

  /**
   * Shows if the outer mast is stable (within the allowable error)
   * @param inches the target height of the current loop
   * @return       whether the position is within the allowable error from the target or not
   */
  public Boolean outerStageWithinRange(double inches, double allowableError) {
    double position = Math.abs(outerStage.getSensorCollection().getQuadraturePosition());
    double target   = Math.abs(inches * Constants.OUTER_MAST_TICKS_PER_INCH);
    return Math.abs(position - target) < allowableError * Constants.OUTER_MAST_TICKS_PER_INCH;
  }

  /**
   * Controls both masts based on the position of the given controller's joysticks
   * @param joy the controller to read data from
   * @param innerStageInhibitor maximum percent output of the inner stage
   * @param outerStageInhibitor maximum percent output of the outer stage
   */
  public void moveWithJoystick(Joystick joy, double innerStageInhibitor, double outerStageInhibitor) {
    innerStage.set(ControlMode.PercentOutput, Xbox.LEFT_Y(joy) * Math.abs(innerStageInhibitor));
    outerStage.set(ControlMode.PercentOutput, Xbox.RIGHT_Y(joy) * Math.abs(outerStageInhibitor));
  }

  /**
   * Returns a boolean matrix of limit switch closed states
   * @return [0] = innerStageLow
   *         [1] = innerStageHigh
   *         [2] = outerStageLow
   *         [3] = outerStageHigh
   */
  public Boolean[] getLimitSwitches() {
    Boolean[] array = new Boolean[4];
    array[0] = innerStage.getSensorCollection().isFwdLimitSwitchClosed();
    array[1] = innerStage.getSensorCollection().isRevLimitSwitchClosed();
    array[2] = outerStage.getSensorCollection().isFwdLimitSwitchClosed();
    array[3] = outerStage.getSensorCollection().isRevLimitSwitchClosed();
    return array;
  }

  /**
   * Updates all limit switch state indicators on the dashboard
   */
  public void publishLimitSwitches() {
    SmartDashboard.putBoolean("Inner Stage Low [0]", getLimitSwitches()[0]);
    SmartDashboard.putBoolean("Inner Stage High [1]", getLimitSwitches()[1]);
    SmartDashboard.putBoolean("Outer Stage Low [2]", getLimitSwitches()[2]);
    SmartDashboard.putBoolean("Outer Stage High [3]", getLimitSwitches()[3]);
  }

  /**
   * Configures the mast motors
   * @param ampLimit      continuous current limit
   * @param nominalOutput the minimum output of the motors
   * @param ramp          motor ramprate
   * @param braking       true for braking, false for coasting
   */
  public void initConfig(int ampLimit, double nominalOutput, double maximumOutput, double ramp, Boolean braking) {
    innerStage.setInverted(Constants.INNER_STAGE_INVERT);
    innerStage.setSensorPhase(Constants.INNER_STAGE_ENCODER_INVERT);
      innerStage.configNominalOutputForward(nominalOutput);
      innerStage.configNominalOutputReverse(-1 * nominalOutput);
      innerStage.configPeakOutputForward(maximumOutput);
      innerStage.configPeakOutputReverse(-1 * maximumOutput);
      innerStage.configOpenloopRamp(ramp);
      innerStage.configContinuousCurrentLimit(ampLimit);
      innerStage.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);;
    outerStage.setInverted(Constants.OUTER_STAGE_INVERT);
    outerStage.setSensorPhase(Constants.OUTER_STAGE_ENCODER_INVERT);
      outerStage.configNominalOutputForward(nominalOutput);
      outerStage.configNominalOutputReverse(-1 * nominalOutput);
      outerStage.configPeakOutputForward(maximumOutput);
      outerStage.configPeakOutputReverse(-1 * maximumOutput);
      outerStage.configOpenloopRamp(ramp);
      outerStage.configContinuousCurrentLimit(ampLimit);
      outerStage.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);;
  }

  /**
   * 
   * @return
   */
  public double[] getAmperage() {
    return new double[]{innerStage.getOutputCurrent(), outerStage.getOutputCurrent()};
  }

  /**
   * Sets the encoder position of both masts to 0
   */
  public void zeroEncoders() {
    innerStage.getSensorCollection().setQuadraturePosition(0, 0);
    outerStage.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public int[] getEncoderValues() {
    return new int[]{ innerStage.getSensorCollection().getQuadraturePosition(), outerStage.getSensorCollection().getQuadraturePosition() };
  }

  public void setInnerStagePIDF(double[] PIDF) {
    innerStage.config_kP(0, PIDF[0]);
    innerStage.config_kI(0, PIDF[1]);
    innerStage.config_kD(0, PIDF[2]);
    innerStage.config_kF(0, PIDF[3]);
  }

  public void setOuterStagePIDF(double[] PIDF) {
    outerStage.config_kP(0, PIDF[0]);
    outerStage.config_kI(0, PIDF[1]);
    outerStage.config_kD(0, PIDF[2]);
    outerStage.config_kF(0, PIDF[3]);
  }

  public void stopMotors() {
    innerStage.set(ControlMode.PercentOutput, 0);
    outerStage.set(ControlMode.PercentOutput, 0);
  }

  public void publishInnerStagePIDData() {
    SmartDashboard.putNumber("Inner %", 100 * innerStage.getMotorOutputPercent());
    // SmartDashboard.putNumber("Inner Error", innerStage.getClosedLoopError());
  }

  public void publishOuterStagePIDData() {
    SmartDashboard.putNumber("Outer %", 100 * outerStage.getMotorOutputPercent());
    // SmartDashboard.putNumber("Outer Error", outerStage.getClosedLoopError());
  }
}
