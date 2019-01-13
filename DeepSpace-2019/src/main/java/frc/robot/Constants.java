/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * A place to store magic numbers
 */
public class Constants {
    
    /**
     * Inverts
     */
    public static final Boolean
        LEFT_DRIVE_INVERT  = false,
        RIGHT_DRIVE_INVERT = true;
    /**
     * Spark IDs
     */
    public static final int
        LEFT_MASTER_ID  = 0,
        LEFT_SLAVE_ID   = 1,
        RIGHT_MASTER_ID = 3,
        RIGHT_SLAVE_ID  = 2;

    /**
     * Vision Values
     */
    public static final int
        CAM_HEIGHT = 1080,
        CAM_WIDTH  = 1920,
        ACAM_ID    = 0,
        BCAM_ID    = 1;
}
