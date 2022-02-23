// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.LogitechController;
import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
  private LogitechController m_controller;
  private Climber m_climber;
  /** Creates a new ClimberCommand. */
  public ClimberCommand(Climber climber, LogitechController controller) {
    super();
    addRequirements(climber);
    m_controller= controller;
    m_climber = climber;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climber.extendLeft(modifyAxis(m_controller.getRightY()));
    m_climber.extendRight(modifyAxis(m_controller.getLeftY())
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  private double modifyAxis(double axis){
    double result = MathUtil.applyDeadband(axis, ControllerConstants.DEADBAND);
    result = Math.copySign(Math.pow(result, 2), result );
    return result;
  }
}
