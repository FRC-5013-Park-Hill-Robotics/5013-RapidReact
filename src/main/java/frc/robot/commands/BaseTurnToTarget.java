// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ShooterConstants;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.Turret;

import static frc.robot.Constants.DrivetrainConstants.ThetaGains.*;
import static frc.robot.ShooterConstants.TargetConstants.*;

public class BaseTurnToTarget extends CommandBase {
	private ShooterVision m_Vision;
	private CargoShooter m_Shooter;
	private Turret m_Turret;
	private DrivetrainSubsystem m_Drivetrain;
	private PIDController controller = new PIDController(kP, kI, kD);

	/** Creates a new AutonomousTurnToTargetCommand. */
	public BaseTurnToTarget(DrivetrainSubsystem driveTrain, ShooterVision vision, CargoShooter shooter, Turret turret) {
		super();
		addRequirements(driveTrain);
		m_Vision = vision;
		m_Drivetrain = driveTrain;
		controller.setTolerance(kTurnToleranceRad, kTurnRateToleranceRadPerS);
		controller.enableContinuousInput(0, 360);
		m_Shooter = shooter;
		m_Turret = turret;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		controller.reset();

	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_Vision.setTargeting(true);
		if (m_Vision.hasTarget()){
			double vision_angle = m_Vision.getHorazontalAngleOfError() ;
		//	double setpoint = vision_angle + m_Drivetrain.getGyroscopeRotation().getDegrees();
		//	double output = controller.calculate(m_Drivetrain.getHeading(), setpoint);
		//	m_Drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(0, 0, output, m_Drivetrain.getGyroscopeRotation()));
			m_Shooter.setTargetVelocity(SHOOTER_SPEED_INTERPOLATOR.getInterpolatedValue(vision_angle));
			m_Turret.setHeight(HOOD_INTERPOLATOR.getInterpolatedValue(vision_angle));
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_Vision.setTargeting(false);
	}

	public ShooterVision getVision() {
		return m_Vision;
	}

	public void setVision(ShooterVision m_Vision) {
		this.m_Vision = m_Vision;
	}

	public DrivetrainSubsystem getDrivetrain() {
		return m_Drivetrain;
	}

	public void setDrivetrain(DrivetrainSubsystem m_Drivetrain) {
		this.m_Drivetrain = m_Drivetrain;
	}

	public PIDController getController() {
		return controller;
	}

	public void setController(PIDController controller) {
		this.controller = controller;
	}


}
