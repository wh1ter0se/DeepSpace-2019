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
     * Drive values
     */
    public static final double
        BACKUP_RAMP         = 0,
        MAX_ALLOWABLE_AO    = .2;

    /**
     * Encoder values
     */
    public static final int
        ENCODER_TICKS_PER_ROTATION = 42,
        MAX_ALLOWABLE_ERROR        = 42;

    /**
     * PID backup values
     */
    public static final double
        BACKUP_POSITION_kP = 0,
        BACKUP_POSITION_kI = 0,
        BACKUP_POSITION_kD = 0;

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
        RIGHT_MASTER_ID = 2,
        RIGHT_SLAVE_ID  = 3;

    /**
     * Vision Values
     */
    public static final int
        CAM_HEIGHT      = 1080,
        CAM_WIDTH       = 1920,
        ACAM_ID         = 0,
        BCAM_ID         = 1,
        BACKUP_EXPOSURE = 35;
}
