// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.Turret;

import static frc.robot.Constants.DrivetrainConstants.ThetaGains.*;
import static frc.robot.ShooterConstants.TargetConstants.*;

import java.util.function.BooleanSupplier;

public class BaseTurnToTarget extends CommandBase {
	private ShooterVision m_Vision;
	private CargoShooter m_Shooter;
	private Turret m_Turret;
	private BooleanSupplier m_isAllianceCargo;
	private DrivetrainSubsystem m_Drivetrain;
	private PIDController controller = new PIDController(kP, kI, kD);

	/** Creates a new AutonomousTurnToTargetCommand. */
	public BaseTurnToTarget(DrivetrainSubsystem driveTrain, ShooterVision vision, CargoShooter shooter, Turret turret, BooleanSupplier isAllianceCargo) {
		super();
		addRequirements(driveTrain);
		m_Vision = vision;
		m_Drivetrain = driveTrain;
		controller.setTolerance(kTurnToleranceRad, kTurnRateToleranceRadPerS);
		controller.enableContinuousInput(-Math.PI, Math.PI);
		m_Shooter = shooter;
		m_Turret = turret;
		m_isAllianceCargo = isAllianceCargo;
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
		if (m_Vision.hasTarget() && !m_Vision.isOnTarget()){
			double vertical_angle = m_Vision.getVerticalAngleOfErrorDegrees();
			double horizontal_amgle = -m_Vision.getHorazontalAngleOfErrorDegrees() ;
			double setpoint = Math.toRadians(horizontal_amgle)+ m_Drivetrain.getYawR2d().getRadians();
			if (!m_isAllianceCargo.getAsBoolean()){
				setpoint += Math.toRadians(15);
			}
			double output = controller.calculate(m_Drivetrain.getYawR2d().getRadians(), setpoint);
			m_Drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(getXTranslationMetersPerSecond(),
				 getYTranslationMetersPerSecond(), output, m_Drivetrain.getYawR2d()));
			m_Shooter.setTargetVelocity(SHOOTER_SPEED_INTERPOLATOR.getInterpolatedValue(vertical_angle));
			m_Turret.setHeight(HOOD_INTERPOLATOR.getInterpolatedValue(vertical_angle));
		} else {
			m_Drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(0,	0, 0, m_Drivetrain.getYawR2d()));
		}
	}

	protected double getXTranslationMetersPerSecond(){
		return 0;
	}
	protected double getYTranslationMetersPerSecond(){
		return 0;
	}
	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_Drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(0,	0, 0, m_Drivetrain.getYawR2d()));
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
