// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeVision;
import static frc.robot.Constants.DrivetrainConstants.ThetaGains.*;

import java.util.function.DoubleSupplier;

public class Fetch extends CommandBase {
  private DrivetrainSubsystem m_drivetrain;
  private IntakeVision m_vision;
  private DoubleSupplier m_throttle;
  private DoubleSupplier m_yTranslation;
  private DoubleSupplier m_xTranslation;
  private PIDController m_thetaController;


  /** Creates a new Fetch. */
  public Fetch(DrivetrainSubsystem drivetrain, IntakeVision vision, DoubleSupplier xTranslation, DoubleSupplier yTranslation, DoubleSupplier throttle) {
    super();
	addRequirements(drivetrain);
    m_drivetrain = drivetrain;
    m_vision = vision;
    m_throttle = throttle;
	m_xTranslation = xTranslation;
	m_yTranslation = yTranslation;
    m_thetaController =  new PIDController(kP, kI, kD);
    m_thetaController.setTolerance(kTurnToleranceRad,kTurnRateToleranceRadPerS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Call pid controller calculate passing in the x offset from vision and 0 for the setpoint
   double PIDOutput = m_thetaController.calculate(m_drivetrain.getHeading(), m_vision.getTargetXAngle());
   ChassisSpeeds chassisSpeeds = new ChassisSpeeds(DrivetrainSubsystem.percentOutputToMetersPerSecond(getXTranslation()), 
   DrivetrainSubsystem.percentOutputToMetersPerSecond(getYTranslation()), PIDOutput);
   // use the output from calculate to make a new ChassisSpeed object to pass to the drivetrain
   // with a yVelocity of 0, an xVelocity based on the throttle, and an angular velocity of the 
   // pid calculate
   m_drivetrain.drive(chassisSpeeds);
   
  }

  private double getXTranslation(){
	  return -Math.copySign(MathUtil.applyDeadband(ControllerConstants.DEADBAND, m_throttle.getAsDouble()), m_xTranslation.getAsDouble());
  }

  private double getYTranslation(){
	return -MathUtil.applyDeadband(ControllerConstants.DEADBAND, m_yTranslation.getAsDouble());
}
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
