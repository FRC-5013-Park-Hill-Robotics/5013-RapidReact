// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.Turret;

public class DefaultTurretCommand extends CommandBase {
  /** Creates a new DefaultTurretCommand. */
  private Turret mTurret;
  private ShooterVision mShooterVision; 
  public DefaultTurretCommand(Turret turret, ShooterVision shooterVision) {
    // Use addRequirements() here to declare subsystem dependencies.
    super();
    addRequirements(turret);
    mTurret = turret;
    mShooterVision = shooterVision;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //if shooter vision has target in vision, turn to target
      //else seek with the turret between min and max angle, go both ways
      //need to know what direction facing
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
