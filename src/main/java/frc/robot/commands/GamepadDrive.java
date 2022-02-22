// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.trobot5013lib.TrobotUtil;
import frc.robot.LogitechController;
import static frc.robot.Constants.ControllerConstants.DEADBAND;

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
		SmartDashboard.putNumber("Left Y", m_gamepad.getLeftY());
		SmartDashboard.putNumber("Left X", m_gamepad.getLeftX());
		SmartDashboard.putNumber("Right X", m_gamepad.getRightX());

		;
		double throttle = TrobotUtil.modifyAxis(m_gamepad.getRightTriggerAxis(),DEADBAND);

		double translationX = TrobotUtil.modifyAxis(-m_gamepad.getLeftY(),DEADBAND);
		double translationY = TrobotUtil.modifyAxis(-m_gamepad.getLeftX(),DEADBAND);
		if (!(translationX == 0.0 && translationY == 0.0)) {
			double angle = calculateTranslationDirection(translationX, translationY);
			translationX = Math.cos(angle) * throttle;
			translationY = Math.sin(angle) * throttle;
		}
		SmartDashboard.putNumber("rotation ", getRotationRadiansPerSecond());
		m_drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(
				getTranslationMetersPerSecond(translationX, xLimiter),
				getTranslationMetersPerSecond(translationY,yLimiter), getRotationRadiansPerSecond(),
				m_drivetrain.getGyroscopeRotation()));

	 }

	@Override
	public void end(boolean interrupted) {
		m_drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
	}

	private double getTranslationMetersPerSecond(double translationPercentOutput, SlewRateLimiter limiter){
		return -limiter.calculate(DrivetrainSubsystem.percentOutputToMetersPerSecond(translationPercentOutput));
	}
	private double getRotationRadiansPerSecond() {
		return -DrivetrainSubsystem
				.percentOutputToRadiansPerSecond(rotationLimiter.calculate(TrobotUtil.modifyAxis(m_gamepad.getRightX(),2,DEADBAND))) / 3;

	}


	private double calculateTranslationDirection(double x, double y) {
		// Calculate the angle.
		// Swapping x/y
		return Math.atan2(x, y) + Math.PI / 2;
	}
}
