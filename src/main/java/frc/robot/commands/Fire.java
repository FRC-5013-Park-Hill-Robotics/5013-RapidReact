// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.Conveyor;

public class Fire extends CommandBase {
	private CargoShooter m_Shooter;
  /** Creates a new FenderShot. */
  public Fire(CargoShooter shooter, Conveyor conveyor) {
	  super();
    // Use addRequirements() here to declare subsystem dependencies.	
	addRequirements(shooter, conveyor);
	m_Shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
	m_Shooter.fire();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  m_Shooter.stopFiring();
  }

}
