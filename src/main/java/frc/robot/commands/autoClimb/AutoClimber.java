// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoClimb;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoClimber extends SequentialCommandGroup {

	public AutoClimber(Climber climber, DrivetrainSubsystem drivetrain) {
		super(new LowBarRetract(climber,drivetrain), 
			new MidBarHook(climber, drivetrain),
			new MidBarRetract(climber, drivetrain),
			new TraverseBarHook(climber, drivetrain),
			new TraverseBarRetract(climber, drivetrain));
		addRequirements(climber);
	}

}
