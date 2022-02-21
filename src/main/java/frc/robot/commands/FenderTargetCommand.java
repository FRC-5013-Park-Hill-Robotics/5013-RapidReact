// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ShooterConstants.FendorShotConstants;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.Turret;

public class FenderTargetCommand extends CommandBase {
	private Turret m_turret;
	private CargoShooter m_shooter;
	/** Creates a new FenderTargetCommand. */
	public FenderTargetCommand(Turret turret, CargoShooter shooter) {
		super();
		addRequirements(turret, shooter);
		m_turret = turret;
		m_shooter = shooter;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_shooter.setTargetVelocity(FendorShotConstants.BOTTOM_WHEEL_SPEED);
		m_turret.setHeight(FendorShotConstants.HOOD_HEIGHT);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
