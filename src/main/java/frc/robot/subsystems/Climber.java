// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
	private WPI_TalonFX leftMotor = new WPI_TalonFX(ClimberConstants.LEFT_MOTOR);
	private WPI_TalonFX rightMotor = new WPI_TalonFX(ClimberConstants.RIGHT_MOTOR);
	private SlewRateLimiter leftLimiter = new SlewRateLimiter(2);
	private SlewRateLimiter rightLimiter = new SlewRateLimiter(2);
	/** Creates a new Climber. */
	public Climber() {
	  leftMotor.configFactoryDefault();
	  leftMotor.setNeutralMode(NeutralMode.Brake);
	  rightMotor.configFactoryDefault();
	  rightMotor.setNeutralMode(NeutralMode.Brake);
	}
	public void extendLeft(double speed){
	  leftMotor.set(ControlMode.PercentOutput,leftLimiter.calculate(speed));
	}
	public void extendRight(double speed){
	  rightMotor.set(ControlMode.PercentOutput,rightLimiter.calculate(speed));
	}
	@Override
	public void periodic() {
	  // This method will be called once per scheduler run
	}
  }
