// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoClimb;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ClimberConstants;
import frc.robot.subsystems.Climber;

public class TraverseBarRetract extends CommandBase {
	private Climber m_Climber;
	private DoubleSupplier m_pitchSupplier;

	/** Creates a new TraverseBarRetract. */
	public TraverseBarRetract(Climber climber, DoubleSupplier pitch) {
		super();
		addRequirements(climber);
		m_Climber = climber;
		m_pitchSupplier = pitch;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Retract the right arm to partial. Done!
		m_Climber.setRightPosition(ClimberConstants.RIGHT_PARTIAL_ENCODER_CLICKS,true);
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
