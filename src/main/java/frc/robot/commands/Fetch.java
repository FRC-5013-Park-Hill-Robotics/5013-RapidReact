// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.LogitechController;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeVision;
import static frc.robot.Constants.DrivetrainConstants.ThetaGains.*;

public class Fetch extends CommandBase {
  private DrivetrainSubsystem m_drivetrain;
  private IntakeVision m_vision;
  private Intake m_intake;
  private LogitechController m_controller;
  private PIDController m_thetaController;

  /** Creates a new Fetch. */
  public Fetch(DrivetrainSubsystem drivetrain, IntakeVision vision, Intake intake, LogitechController controller) {
    super();
    addRequirements(drivetrain, intake);
    m_drivetrain = drivetrain;
    m_intake = intake;
    m_vision = vision;
    m_controller = controller;
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
