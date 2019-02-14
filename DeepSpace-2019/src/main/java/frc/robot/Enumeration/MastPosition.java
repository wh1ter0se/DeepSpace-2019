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
    HATCH_1(1, "Hatch 1"),
    CARGO_1(2, "Cargo 1"),
	HATCH_2(3, "Hatch 2"),
	CARGO_2(4, "Cargo 2"),
	HATCH_3(5, "Hatch 3"),
	CARGO_3(6, "Cargo 3"),
	SOMEWHERE(42, "I'M LOST");

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
