// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoClimb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.ClimberConstants;
import frc.robot.RobotContainer;

public class PreClimbCommand extends ParallelCommandGroup {
  /** Creates a new PreClimbCommand. */
  public PreClimbCommand(RobotContainer container) {
	super(
		 new InstantCommand(container.getshooter()::stop)
		, new InstantCommand(() -> container.getshooterVision().setLedOn(false))
		, new InstantCommand(container.getPneumaticsHub()::disableCompressor));
    addRequirements(container.getClimber(),container.getshooter());
  }


}
