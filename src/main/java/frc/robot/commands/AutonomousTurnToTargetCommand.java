// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.Turret;

/** Add your docs here. */
public class AutonomousTurnToTargetCommand extends BaseTurnToTarget {

	public AutonomousTurnToTargetCommand(DrivetrainSubsystem driveTrain, ShooterVision vision, CargoShooter shooter, Turret turret){
		super(driveTrain, vision, shooter,turret);
	}

	@Override
	public boolean isFinished() {
		return getController().atSetpoint();
	}
}
