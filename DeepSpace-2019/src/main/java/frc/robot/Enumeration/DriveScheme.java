/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Enumeration;

/**
 * Positions that the two-stage mast can be in
 */
public enum DriveScheme {
	RL_GENUINE("RL Genuine"),
	RL_HILO("RL Hi-Lo");

	private final String name;
    
	DriveScheme(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
