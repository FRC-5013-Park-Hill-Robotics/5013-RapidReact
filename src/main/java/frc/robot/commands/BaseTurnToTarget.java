// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;

import static frc.robot.Constants.DrivetrainConstants.ThetaGains.*;

public class BaseTurnToTarget extends CommandBase {
	private ShooterVision m_Vision;
	private DrivetrainSubsystem m_Drivetrain;
	private PIDController controller = new PIDController(kP, kI, kD);

	/** Creates a new AutonomousTurnToTargetCommand. */
	public BaseTurnToTarget(DrivetrainSubsystem driveTrain, ShooterVision vision) {
		super();
		addRequirements(driveTrain);
		m_Vision = vision;
		m_Drivetrain = driveTrain;
		controller.setTolerance(kTurnToleranceRad, kTurnRateToleranceRadPerS);
		controller.enableContinuousInput(0, 360);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		controller.reset();

	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		double setpoint = m_Vision.getHorazontalAngleOfError() + m_Drivetrain.getHeading();
		double output = controller.calculate(m_Drivetrain.getHeading(), setpoint);
		m_Drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(0, 0, output, m_Drivetrain.getGyroscopeRotation()));
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
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
