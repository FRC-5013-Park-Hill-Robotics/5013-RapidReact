// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;


public class TurretStartingPosition extends CommandBase {
    private Turret m_turret;
  /** Creates a new TurretStartingPosition. */
  public TurretStartingPosition(Turret turret) {
    addRequirements(turret);
    m_turret = turret;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      m_turret.setDesiredAngle(129);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}
//129 start
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
