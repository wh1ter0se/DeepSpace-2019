/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Commands.ManualCommandGrow;

/**
 * Add your docs here.
 */
public class SubsystemMast extends Subsystem {

  private int storedPosition;

  private static TalonSRX firstStage;
  private static TalonSRX secondStage;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandGrow());
  }

  public void updatePosition(int position) {
    switch(position) {
      case 1:

        storedPosition = position;
        break;
      case 2:

        storedPosition = position;
        break;
      case 3:
        
        storedPosition = position;
        break;
    }
  }

}
