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
public enum MastPosition {
    LOW(1, "Low"),
    MID(2, "Mid"),
	HIGH(3, "High"),
	SOMEWHERE(4, "Somewhere");

	private final int value;
	private final String name;
    
	MastPosition(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int toInt() {
		return value;
	}

	public String toString() {
		return name;
	}
}
