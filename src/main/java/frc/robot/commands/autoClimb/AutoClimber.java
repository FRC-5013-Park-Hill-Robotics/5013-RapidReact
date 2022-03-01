// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoClimber extends CommandBase {
	/** Creates a new AutoClimber. */
	private Climber m_climber;
	private DrivetrainSubsystem m_DrivetrainSubsystem;
	private int m_step;

	public AutoClimber(Climber climber, DrivetrainSubsystem drivetrainSubsystem) {
		super();
		addRequirements(climber);
		m_climber = climber;
		m_DrivetrainSubsystem = drivetrainSubsystem;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		m_step = 1;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//retract first arm
		//while still retracting
			//after pitch apex abs(pitch) < abs(previous pitch) retract second arm
			

	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
