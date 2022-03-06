// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoClimb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.ClimberConstants;
import frc.robot.commands.TurretStartingPosition;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Turret;

public class PreClimbCommand extends ParallelCommandGroup {
  /** Creates a new PreClimbCommand. */
  public PreClimbCommand(Climber climber, Turret turret) {
	super(//new TurretStartingPosition(turret),
		new InstantCommand(() -> climber.setRightPosition(ClimberConstants.RIGHT_ARM_LOW_BAR_HOOK))
		,new InstantCommand(() -> climber.setLeftPosition(ClimberConstants.LEFT_ARM_START)));
    addRequirements(climber,turret);
  }
}
