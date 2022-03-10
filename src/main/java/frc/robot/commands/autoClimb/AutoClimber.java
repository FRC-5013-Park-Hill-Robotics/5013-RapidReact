// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoClimb;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;

public class AutoClimber extends SequentialCommandGroup {

	public AutoClimber(Climber climber, DoubleSupplier pitch) {
		super(new LowBarRetract(climber,pitch)
			,new MidBarHook(climber, pitch)
			//,new MidBarRetract(climber, drivetrain),
			//,new TraverseBarHook(climber, drivetrain),
			//n,w TraverseBarRetract(climber, drivetrain)
			);
		addRequirements(climber);
	}

}
