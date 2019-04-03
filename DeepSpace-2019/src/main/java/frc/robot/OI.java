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
import frc.robot.Commands.ButtonCommandBackFeed;
import frc.robot.Commands.ButtonCommandChangeMastPosition;
import frc.robot.Commands.ButtonCommandEat;
import frc.robot.Commands.ButtonCommandFlipCamera;
import frc.robot.Commands.ButtonCommandSetDriveSpeed;
import frc.robot.Commands.ButtonCommandSetGear;
import frc.robot.Commands.ButtonCommandSpit;
import frc.robot.Commands.ButtonCommandToggleClamp;
import frc.robot.Commands.ButtonCommandToggleIntake;
import frc.robot.Commands.ButtonCommandToggleShift;
import frc.robot.Commands.CyborgCommandAlign;
import frc.robot.Commands.CyborgCommandAutoShift;
import frc.robot.Commands.CyborgCommandDock;
import frc.robot.Commands.CyborgCommandFlip;
import frc.robot.Commands.CyborgCommandGroupDock;
import frc.robot.Commands.InstantCommandCalibrateCamera;
import frc.robot.Commands.InstantCommandZeroMastEncoders;
import frc.robot.Commands.IterativeCommandMoveMast;
import frc.robot.Commands.ManualCommandTestFlipper;
import frc.robot.Commands.ManualCommandTestMast;
import frc.robot.Commands.ManualCommandTestMastPID;
import frc.robot.Commands.SubmanualCommandAscend;
import frc.robot.Commands.SubmanualCommandEmergencyMastControl;
import frc.robot.Commands.ToggleCommandKillCompressor;
import frc.robot.Commands.ToggleCommandMurder;
import frc.robot.Enumeration.DriveSpeed;
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

        // To Compress, or Not To Compress. It is now an option.
            SmartDashboard.putData("Disable Compressor", new ToggleCommandKillCompressor());

        // tester functions
            SmartDashboard.putData("Test Mast", new ManualCommandTestMast());
            // SmartDashboard.putData("Test Mast PID", new ManualCommandTestMastPID());
            // SmartDashboard.putData("Test Flipper", new ManualCommandTestFlipper());
            SmartDashboard.putData("Zero Mast Encoders", new InstantCommandZeroMastEncoders());
            // SmartDashboard.putData("Maintain Position", new IterativeCommandMoveMast());
            SmartDashboard.putData("Dock", new CyborgCommandDock());
            
        /**
         * Driver
         */
            Button flipCamera = new JoystickButton(DRIVER, Xbox.Y);
                flipCamera.toggleWhenPressed(new ButtonCommandFlipCamera());

            Button updateCameraConfig = new JoystickButton(DRIVER, Xbox.RSTICK);
                updateCameraConfig.whenPressed(new InstantCommandCalibrateCamera());

            Button toggleShift = new JoystickButton(DRIVER, Xbox.X);
                toggleShift.whenPressed(new ButtonCommandToggleShift());

            Button downShift = new JoystickButton(DRIVER, Xbox.A);
                downShift.whenPressed(new ButtonCommandSetGear(1, true));

            Button upShift = new JoystickButton(DRIVER, Xbox.B);
                upShift.whenPressed(new ButtonCommandSetGear(2, true));

            Button lowerInhibitor = new JoystickButton(DRIVER, Xbox.A);
                lowerInhibitor.whenPressed(new ButtonCommandSetDriveSpeed(DriveSpeed.LOW));

            Button upperInhibitor = new JoystickButton (DRIVER, Xbox.B);
                upperInhibitor.whenPressed(new ButtonCommandSetDriveSpeed(DriveSpeed.HIGH));

            Button toggleAutoShifting = new JoystickButton(DRIVER, Xbox.START);
                toggleAutoShifting.toggleWhenPressed(new CyborgCommandAutoShift());
                
            // Button dock = new JoystickButton(DRIVER, Xbox.LB);
            //     dock.toggleWhenPressed(new CyborgCommandGroupDock());

            // Button overrideSpit = new JoystickButton(DRIVER, Xbox.RB);
            //     overrideSpit.whileHeld(new ButtonCommandSpit());

            Button overrideClamp = new JoystickButton(DRIVER, Xbox.Y);
                overrideClamp.toggleWhenPressed(new ButtonCommandToggleClamp());

            Button align = new JoystickButton(DRIVER, Xbox.LB);
                align.toggleWhenPressed(new CyborgCommandAlign());

            Button murder = new JoystickButton(DRIVER, Xbox.RB);
                murder.toggleWhenPressed(new ToggleCommandMurder());

        /**
         * Operator
         */
            Button toggleIntake = new JoystickButton(OPERATOR, Xbox.LB);
                toggleIntake.toggleWhenPressed(new ButtonCommandToggleIntake());

            Button ascend = new JoystickButton(OPERATOR, Xbox.RB);
                ascend.toggleWhenPressed(new SubmanualCommandAscend());

            Button eat = new JoystickButton(OPERATOR, Xbox.X);
                eat.whileHeld(new ButtonCommandEat());

            Button spit = new JoystickButton(OPERATOR, Xbox.A);
                spit.whileHeld(new ButtonCommandSpit());

            Button backFeed = new JoystickButton(OPERATOR, Xbox.Y);
                backFeed.whileHeld(new ButtonCommandBackFeed());

            Button toggleClamp = new JoystickButton(OPERATOR, Xbox.B);
                toggleClamp.toggleWhenPressed(new ButtonCommandToggleClamp());

            Button mastUp = new JoystickButton(OPERATOR, Xbox.START);
                mastUp.toggleWhenPressed(new ButtonCommandChangeMastPosition(1));

            Button mastDown = new JoystickButton(OPERATOR, Xbox.BACK);
                mastDown.toggleWhenPressed(new ButtonCommandChangeMastPosition(-1));

            Button panicSwitch = new JoystickButton(OPERATOR, Xbox.RSTICK);
                panicSwitch.whenPressed(new SubmanualCommandEmergencyMastControl());
    }
}
