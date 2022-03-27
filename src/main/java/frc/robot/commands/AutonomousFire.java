// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.Conveyor;

public class AutonomousFire extends CommandBase {
	private CargoShooter m_Shooter;
	private Conveyor m_Conveyor;
	private Timer m_timer;

	/** Creates a new FenderShot. */
	public AutonomousFire(CargoShooter shooter, Conveyor conveyor) {
		super();
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(shooter, conveyor);
		m_Shooter = shooter;
		m_Conveyor = conveyor;
		m_timer = new Timer();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		System.out.println("Shooting");
		if (!m_Conveyor.isBallReadyToShoot()) {
			m_timer.start();
		} else {
			m_timer.reset();
		}
		m_Shooter.fire();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_Shooter.stopFiring();
	}

	@Override
	public boolean isFinished() {
		return m_timer.hasElapsed(0.75);
	}
}
