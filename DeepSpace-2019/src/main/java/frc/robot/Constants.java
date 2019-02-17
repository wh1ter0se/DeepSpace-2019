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
     * Advanced Shifting Values
     */
    public static final double
        QUARTER_UPSHIFT_RPM              = 6000,
            QUARTER_DOWNSHIFT_RPM        = 4000,
        HALF_UPSHIFT_RPM                 = 5000,
            HALF_DOWNSHIFT_RPM           = 3000,
        THREE_QUARTERS_UPSHIFT_RPM       = 4000,
            THREE_QUARTERS_DOWNSHIFT_RPM = 2000,
        FULL_UPSHIFT_RPM                 = 3000,
            FULL_DOWNSHIFT_RPM           = 1000;

    /** 
     * Docking Values
     */
    public static final double
        DOCKING_TARGET_LOCK_RANGE = 20; // in inches

    /**
     * Drive values
     */
    public static final double
        BACKUP_RAMP          = 0,
        BACKUP_DOCKING_SPEED = .25,
        MAX_ALLOWABLE_AO     = .2, // percent output
        MAX_ALLOWABLE_ERROR  = .25, // inches
        DOWNSHIFT_RPM        = 900,
        UPSHIFT_RPM          = 1000;

    /**
     * Inverts
     */
    public static final Boolean
        LEFT_DRIVE_INVERT   = false,
        RIGHT_DRIVE_INVERT  = true,
        INNER_STAGE_INVERT  = true,
        OUTER_STAGE_INVERT  = true,
        LAUNCHER_INVERT     = true,
        INTAKE_INVERT       = false,
        FLIPPER_INVERT      = false;

    /**
     * Mast Values
     */
    public static final double
        MAST_ALLOWABLE_ERROR = 100, // error in ticks
        CARGO_1_HEIGHT = 0.0,
        HATCH_2_HEIGHT = 0.0,
        CARGO_2_HEIGHT = 0.0,
        HATCH_3_HEIGHT = 0.0; // TODO these need to be calibrated

    /**
     * Mast Speed Backup Values
     */
    public static final double
        INNER_STAGE_SPEED  = .5,
        OUTER_STAGE_SPEED = .5;

    /**
     * Per values
     */
    public static final double
        CTRE_TICKS_PER_ROTATION = 4096,
        MAST_ROTATIONS_PER_INCH = 0, //TODO find this out
        REV_ENCODER_TICKS_PER_ROTATION = 42,
        ROTATIONS_PER_INCH = 1 / (4.67 * 6 *  Math.PI); // calculated with a cimple box and 42 TPR

    /**
     * PID backup values (safety values)
     */
    public static final double
        BACKUP_POSITION_kP = 0,
        BACKUP_POSITION_kI = 0,
        BACKUP_POSITION_kD = 0,
        BACKUP_DOCKING_kP  = 0,
        BACKUP_DOCKING_kI  = 0,
        BACKUP_DOCKING_kD  = 0;

    /**
     * Safety Values
     */
    public static final int
        DANGER_AMPERAGE  = 55,
        PUSHING_AMPERAGE = 55;
        
    /**
     * Solenoid IDS
     */
    public static final int
        CLOSE_CLAMP_ID = 1,
        OPEN_CLAMP_ID  = 0,
        DOWNSHIFT_ID   = 7,
        UPSHIFT_ID     = 6,
        EXTEND_ID      = 2,
        RETRACT_ID     = 3;
        
    /**
     * Spark IDs
     */
    public static final int
        LEFT_MASTER_ID  = 0,
        LEFT_SLAVE_ID   = 3,
        RIGHT_MASTER_ID = 1,
        RIGHT_SLAVE_ID  = 2;

    /**
     * Talon IDS
     */
    public static final int
        CLIMBER_ID      = 5,
        INNER_STAGE_ID  = 8,
        OUTER_STAGE_ID  = 9,
        LAUNCHER_ID     = 6,
        INTAKE_ID       = 10,
        FLIPPER_ID      = 7;

    /**
     * Vision Values
     */
    public static final int
        CAM_HEIGHT      = 1080,
        CAM_WIDTH       = 1920,
        ACAM_ID         = 0,
        BCAM_ID         = 1,
        CCAM_ID         = 2,
        BACKUP_EXPOSURE = 35;

}
