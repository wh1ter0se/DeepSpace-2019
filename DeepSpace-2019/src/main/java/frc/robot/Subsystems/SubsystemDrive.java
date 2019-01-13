/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
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
  }

  /**
   * Rocket League/Tank hybrid control system
   * Left and right triggers accelerate linearly and left stick rotates
   * @param joy the joystick to be used
   */
  public void driveRLTank(Joystick joy) {
    double adder = Xbox.RT(joy) - Xbox.LT(joy);
    double left = adder + (Xbox.LEFT_X(joy) / 1.333333);
    double right = adder - (Xbox.LEFT_X(joy) / 1.333333);
    left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
    right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));

    setInverts();
    
    leftMaster.set(left);
      leftSlave.set(left);
    rightMaster.set(right);
      rightSlave.set(right);
  }

  /**
   * Sets the inverts of each motor controller
   */
  private void setInverts() {
    leftMaster.setInverted(Constants.LEFT_DRIVE_INVERT);
    leftSlave.setInverted(Constants.LEFT_DRIVE_INVERT);
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

  
}
