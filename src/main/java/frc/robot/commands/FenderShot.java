// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ShooterConstants;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.Turret;
import frc.robot.trobot5013lib.TrobotUtil;

public class FenderShot extends CommandBase {
	private CargoShooter m_Shooter;
	private Turret m_Turret;
  /** Creates a new FenderShot. */
  public FenderShot(CargoShooter shooter, Turret turret) {
    // Use addRequirements() here to declare subsystem dependencies.
	m_Turret = turret;
	m_Shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
	m_Turret.setHeight(ShooterConstants.FendorShotConstants.HOOD_HEIGHT);
	m_Shooter.setTargetVelocity(ShooterConstants.FendorShotConstants.BOTTOM_WHEEL_SPEED);
	if (TrobotUtil.withinTolerance(m_Turret.getHeight(), ShooterConstants.FendorShotConstants.HOOD_HEIGHT, 2 ) &
		m_Shooter.atSpeed())
	{
		m_Shooter.fire();
	} else {
		m_Shooter.stopFiring();
	}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  m_Shooter.stopFiring();
  }

}
