// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoClimb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ClimberConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TraverseBarHook extends CommandBase {
	private Climber m_Climber;
	private DrivetrainSubsystem m_Drivertrain;

	/** Creates a new HookTraverseBar. */
	public TraverseBarHook(Climber climber, DrivetrainSubsystem drivetrain) {
		super();
		addRequirements(climber);
		m_Climber = climber;
		m_Drivertrain = drivetrain;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Extend right arm all the way. 
		m_Climber.setRightPosition(ClimberConstants.RIGHT_FULL_ENCODER_CLICKS,false);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//When a big enough angle is read to confirm our hook is on the bar, go to step 5
		return false;
	}
}
