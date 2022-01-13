// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.LogitechController;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.DrivetrainConstants.DrivetrainGeometry;

public class GamepadDrive extends CommandBase {
    
    private DrivetrainSubsystem m_drivetrain;
    private LogitechController m_gamepad;
    private SlewRateLimiter xLimiter = new SlewRateLimiter(3);
    private SlewRateLimiter yLimiter = new SlewRateLimiter(3);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(3);


    /** 
     *  Constructor method for the GamepadDrive class
     *  - Creates a new GamepadDrive object. 
     */
    public GamepadDrive(DrivetrainSubsystem drivetrain, LogitechController gamepad) {
        super();
        addRequirements(drivetrain);
        m_gamepad = gamepad;
        m_drivetrain = drivetrain;
    }

    @Override
    public void execute() {
        m_drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(getXTranslationMetersPerSecond(),
                getYTranslationMetersPerSecond(), getRotationRadiansPerSecond(), m_drivetrain.getGyroscopeRotation()));
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }

    private double getXTranslationMetersPerSecond() {
        // on the controller y is up, on the field x is away from the driver
        return -percentOutputToMetersPerSecond(xLimiter.calculate(modifyAxis(m_gamepad.getLeftY())));
        //return -percentOutputToMetersPerSecond(modifyAxis(m_gamepad.getLeftY()));
    }

    private double getYTranslationMetersPerSecond() {
        // on the controller y is up, on the field x is away from the driver
        return -percentOutputToMetersPerSecond(yLimiter.calculate(modifyAxis(m_gamepad.getLeftX())));
        //return -percentOutputToMetersPerSecond(modifyAxis(m_gamepad.getLeftX()));
    }

    private double getRotationRadiansPerSecond() {
        return -percentOutputToRadiansPerSecond(rotationLimiter.calculate(modifyAxis(m_gamepad.getRightX())));

    }

    private double percentOutputToMetersPerSecond(double percentOutput){
        return DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND * percentOutput;
    }

    private double percentOutputToRadiansPerSecond(double percentOutput){
        return DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * percentOutput;
    }

    private static double modifyAxis(double value) {
        // Deadband
        value = MathUtil.applyDeadband(value, ControllerConstants.DEADBAND);

        // Square the axis
        value = Math.copySign(value * value, value);

        return value;
    }
}
