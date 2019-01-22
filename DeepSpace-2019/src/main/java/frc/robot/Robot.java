/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.SubsystemBallManipulator;
import frc.robot.Subsystems.SubsystemClamp;
import frc.robot.Subsystems.SubsystemClimb;
import frc.robot.Subsystems.SubsystemCompressor;
import frc.robot.Subsystems.SubsystemDrive;
import frc.robot.Subsystems.SubsystemFlipper;
import frc.robot.Subsystems.SubsystemMast;
import frc.robot.Subsystems.SubsystemShifter;

//        _____   _____   ____     ______
//       |__  /  / ___/  / __ \   / ____/
//        /_ <  / __ \  / /_/ /  /___ \
//      ___/ / / /_/ /  \__, /  ____/ /
//     /____/  \____/  /____/  /_____/
//
//       2019 Deep Space - [Unnamed Bot]

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /**
   * Create Subsystems
   */
  public static SubsystemBallManipulator SUB_BALL_MANIPULATOR;
  public static SubsystemClamp           SUB_CLAMP;
  public static SubsystemClimb           SUB_CLIMB;
  public static SubsystemCompressor      SUB_COMPRESSOR;
  public static SubsystemDrive           SUB_DRIVE;
  public static SubsystemFlipper         SUB_FLIPPER;
  public static SubsystemMast            SUB_MAST;
  public static SubsystemShifter         SUB_SHIFTER;
  public static OI                       OI;
  public static Vision                   VISION;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    DriverStation.reportWarning("ROBOT STARTED", false);
    DriverStation.reportWarning("GOOD LUCK, HAVE FUN", false);
    DriverStation.reportWarning("AIM FOR THE FRESHMAN", false);

    /**
     * Instantiate Subsystems
     */
    SUB_BALL_MANIPULATOR = new SubsystemBallManipulator();
    SUB_CLAMP            = new SubsystemClamp();
    SUB_CLIMB            = new SubsystemClimb();
    SUB_COMPRESSOR       = new SubsystemCompressor();
    SUB_DRIVE            = new SubsystemDrive();
    SUB_FLIPPER          = new SubsystemFlipper();
    SUB_MAST             = new SubsystemMast();
    SUB_SHIFTER          = new SubsystemShifter();
    OI                   = new OI();
    VISION               = new Vision();



    DriverStation.reportWarning("ROBOT INIT COMPLETE", false);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("SD-LM Speed", Robot.SUB_DRIVE.getMotorValues()[0]); // Left Master speed
    SmartDashboard.putNumber("SD-RM Speed", Robot.SUB_DRIVE.getMotorValues()[2]); // Right Master speed

    SmartDashboard.putNumber("Left Motor", Robot.SUB_DRIVE.getAppliedOutputs()[0]);
    SmartDashboard.putNumber("Right Motor", Robot.SUB_DRIVE.getAppliedOutputs()[1]);

    SmartDashboard.putNumber("Current Left RPM", Robot.SUB_DRIVE.getVelocities()[0]);
    SmartDashboard.putNumber("Current Right RPM", Robot.SUB_DRIVE.getVelocities()[1]);

    SmartDashboard.putNumber("Highest Left RPM", Robot.SUB_DRIVE.getHighestVelocities()[0]);
    SmartDashboard.putNumber("Highest Right RPM", Robot.SUB_DRIVE.getHighestVelocities()[1]);

    SmartDashboard.putString("Mast Position", Robot.SUB_MAST.getStoredPosition().toString());

    SmartDashboard.putData("Sub_Ball_Manipulator", SUB_BALL_MANIPULATOR);
    SmartDashboard.putData("Sub_Clamp", SUB_CLAMP);
    SmartDashboard.putData("Sub_Climb", SUB_CLIMB);
    SmartDashboard.putData("Sub_Compressor", SUB_COMPRESSOR);
    SmartDashboard.putData("Sub_Drive", SUB_DRIVE);
    SmartDashboard.putData("Sub_Flipper", SUB_FLIPPER);
    SmartDashboard.putData("Sub_Shifter", SUB_SHIFTER);
    // SmartDashboard.putNumber("Bandwidth", Robot.VISION.getTotalBandwidth());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
