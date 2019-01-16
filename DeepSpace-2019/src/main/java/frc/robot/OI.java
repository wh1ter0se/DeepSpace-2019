/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Commands.ButtonCommandCalibrateCamera;
import frc.robot.Commands.ButtonCommandFlipCamera;
import frc.robot.Commands.CyborgCommandDriveByPosition;
import frc.robot.Util.Util;
import frc.robot.Util.Xbox;

/**
 * Set up file for the operator interface
 */
public class OI {
    public static final Joystick DRIVER = new Joystick(0);
    public static final Joystick OPERATOR = new Joystick(1);
    
    /** 
     * Assigns what every SmartDash and controller button does 
     */
	public OI() {
        Button flipCamera = new JoystickButton(DRIVER, Xbox.A);
            flipCamera.toggleWhenPressed(new ButtonCommandFlipCamera());

        Button updateCameraConfig = new JoystickButton(DRIVER, Xbox.B);
            updateCameraConfig.whenPressed(new ButtonCommandCalibrateCamera());

        SmartDashboard.putData("Drive by Distance", new CyborgCommandDriveByPosition());
    }
}
