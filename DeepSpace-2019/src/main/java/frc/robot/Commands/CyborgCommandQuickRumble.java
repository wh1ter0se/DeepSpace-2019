/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class CyborgCommandQuickRumble extends Command {

  private double motor;
  private double intensity;
  private Joystick joy;

  private long initTime;
  private long rumbleLength;
  
  public CyborgCommandQuickRumble(Joystick joy, int motor, double intensity, int rumbleMs) {
    requires(Robot.SUB_CALEB);
    rumbleLength = rumbleMs;
    this.joy = joy;
    this.motor = motor;
    this.intensity = intensity;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initTime = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    joy.setRumble(motor == 0 ? RumbleType.kLeftRumble : RumbleType.kRightRumble, intensity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() > initTime + rumbleLength;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    joy.setRumble(motor == 0 ? RumbleType.kLeftRumble : RumbleType.kRightRumble, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    joy.setRumble(motor == 0 ? RumbleType.kLeftRumble : RumbleType.kRightRumble, 0);
  }
}
