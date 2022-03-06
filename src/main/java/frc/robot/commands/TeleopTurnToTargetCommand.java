// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.Turret;
import frc.robot.trobot5013lib.TrobotUtil;

/** Add your docs here. */
public class TeleopTurnToTargetCommand extends BaseTurnToTarget {
	private DoubleSupplier m_xTranslation;
	private DoubleSupplier m_yTranslation;
	private DoubleSupplier m_throttle;

	public TeleopTurnToTargetCommand(DrivetrainSubsystem driveTrain, ShooterVision vision, CargoShooter shooter,
			Turret turret, BooleanSupplier isAllianceCargo) {
		super(driveTrain, vision, shooter, turret, isAllianceCargo);
	}

	public TeleopTurnToTargetCommand(DrivetrainSubsystem driveTrain, ShooterVision vision, CargoShooter shooter,
			Turret turret, BooleanSupplier isAllianceCargo, DoubleSupplier xTranslation,
			DoubleSupplier yTranslation, DoubleSupplier throttle) {
		super(driveTrain, vision, shooter, turret, isAllianceCargo);
		m_xTranslation = xTranslation;
		m_yTranslation = yTranslation;
		m_throttle = throttle;
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		// always return false because command will end when controller button is
		// released

		return false;
	}

	@Override
	protected double getXTranslationMetersPerSecond() {
		return 0;
	/*	double throttle = TrobotUtil.modifyAxis(m_throttle.getAsDouble(),ControllerConstants.DEADBAND);

		double translationX =TrobotUtil. modifyAxis(-m_xTranslation.getAsDouble(),ControllerConstants.DEADBAND);
		double translationY = TrobotUtil.modifyAxis(-m_yTranslation.getAsDouble(),ControllerConstants.DEADBAND);
		if (!(translationX == 0.0 && translationY == 0.0)) {	
			double angle = calculateTranslationDirection(translationX, translationY);
			translationX = Math.cos(angle) * throttle;
		}
		return DrivetrainSubsystem.percentOutputToMetersPerSecond(translationX);*/
	}

	@Override
	protected double getYTranslationMetersPerSecond() {
		return 0;
		/*double throttle = TrobotUtil.modifyAxis(m_throttle.getAsDouble(),ControllerConstants.DEADBAND);

		double translationX =TrobotUtil. modifyAxis(-m_xTranslation.getAsDouble(),ControllerConstants.DEADBAND);
		double translationY = TrobotUtil.modifyAxis(-m_yTranslation.getAsDouble(),ControllerConstants.DEADBAND);
		if (!(translationX == 0.0 && translationY == 0.0)) {	
			double angle = calculateTranslationDirection(translationX, translationY);
			translationX = Math.cos(angle) * throttle;
		}
		return DrivetrainSubsystem.percentOutputToMetersPerSecond(translationY);*/
	}
	
	private double calculateTranslationDirection(double x, double y) {
		// Calculate the angle.
		// Swapping x/y
		return Math.atan2(x, y) + Math.PI / 2;
	}
}
