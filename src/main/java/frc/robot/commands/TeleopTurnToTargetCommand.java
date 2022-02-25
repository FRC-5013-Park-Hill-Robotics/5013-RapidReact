// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;

/** Add your docs here. */
public class TeleopTurnToTargetCommand extends BaseTurnToTarget {
	
	
	public TeleopTurnToTargetCommand(DrivetrainSubsystem driveTrain, ShooterVision vision) {
		super(driveTrain, vision);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//always return false because command will end when controller button is released

		return false ;
	}
}
