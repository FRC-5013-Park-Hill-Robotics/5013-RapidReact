// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.LogitechController;
import frc.robot.Constants.ControllerConstants;

public class GamepadDrive extends CommandBase {
	private DrivetrainSubsystem m_drivetrain;
	private LogitechController m_gamepad;
	private SlewRateLimiter xLimiter = new SlewRateLimiter(3);
	private SlewRateLimiter yLimiter = new SlewRateLimiter(3);
	private SlewRateLimiter rotationLimiter = new SlewRateLimiter(3);

	/**
	 * Constructor method for the GamepadDrive class
	 * - Creates a new GamepadDrive object.
	 */
	public GamepadDrive(DrivetrainSubsystem drivetrain, LogitechController gamepad) {
		super();
		addRequirements(drivetrain);
		m_gamepad = gamepad;
		m_drivetrain = drivetrain;
	}

	@Override
	public void execute() {
		double throttle = modifyAxis(m_gamepad.getRightTriggerAxis());

		double translationX = modifyAxis(-m_gamepad.getLeftY());
		double translationY = modifyAxis(-m_gamepad.getLeftX());
		if (!(translationX == 0.0 && translationY == 0.0)) {
			
			double angle = calculateTranslationDirection(translationX, translationY);
			translationX = Math.cos(angle) * throttle;
			translationY = Math.sin(angle) * throttle;
		}

		m_drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(
				-DrivetrainSubsystem.percentOutputToMetersPerSecond(xLimiter.calculate(translationX)),
				DrivetrainSubsystem.percentOutputToMetersPerSecond(yLimiter.calculate(translationY)), getRotationRadiansPerSecond(),
				m_drivetrain.getYawR2d()));

		/*
		 * m_drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(
		 * getXTranslationMetersPerSecond(),
		 * getYTranslationMetersPerSecond(), getRotationRadiansPerSecond(),
		 * m_drivetrain.getGyroscopeRotation()));
		 */ }

	@Override
	public void end(boolean interrupted) {
		m_drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
	}

	private double getXTranslationMetersPerSecond() {
		// on the controller y is up, on the field x is away from the driver
		return -DrivetrainSubsystem
				.percentOutputToMetersPerSecond(xLimiter.calculate(modifyAxis(m_gamepad.getLeftY())));
	}

	private double getYTranslationMetersPerSecond() {
		// on the controller y is up, on the field x is away from the driver
		return -DrivetrainSubsystem
				.percentOutputToMetersPerSecond(yLimiter.calculate(modifyAxis(m_gamepad.getLeftX())));
	}

	private double getRotationRadiansPerSecond() {
		return -DrivetrainSubsystem
				.percentOutputToRadiansPerSecond(rotationLimiter.calculate(modifyAxis(m_gamepad.getRightX(),2))) / 3;

	}

	private static double modifyAxis(double value) {
	
		return modifyAxis(value, 1);
	}
	private static double modifyAxis(double value, int exponent) {
		// Deadband
		value = MathUtil.applyDeadband(value, ControllerConstants.DEADBAND);

		 value = Math.copySign(Math.pow(value, exponent), value);

		return value;
	}
	private double calculateTranslationDirection(double x, double y) {
		// Calculate the angle.
		// Swapping x/y
		return Math.atan2(x, y) + Math.PI / 2;
	}
}