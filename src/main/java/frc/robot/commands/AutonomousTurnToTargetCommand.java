// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.Turret;

/** Add your docs here. */
public class AutonomousTurnToTargetCommand extends BaseTurnToTarget {

	public AutonomousTurnToTargetCommand(DrivetrainSubsystem driveTrain, ShooterVision vision, CargoShooter shooter, Turret turret, BooleanSupplier isAllianceCargo){
		super(driveTrain, vision, shooter,turret, isAllianceCargo);
	}

	@Override
	public boolean isFinished() {
		return getVision().isOnTarget() && getShooter().atSpeed();
	}
}
