// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Intake;

public class ConveyorDefaultCommand extends CommandBase {
	private Conveyor m_Conveyor;
	private Intake m_Intake;

  public ConveyorDefaultCommand( Conveyor conveyor, Intake intake ) {
    // Use addRequirements() here to declare subsystem dependencies.
    super();
    addRequirements( conveyor );
	m_Intake = intake;
	m_Conveyor = conveyor;;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {

	if(!m_Conveyor.isBallReadyToShoot() && m_Intake.isDown()){
		m_Conveyor.start();
	} else {
		m_Conveyor.stop();
	}

  }
}
