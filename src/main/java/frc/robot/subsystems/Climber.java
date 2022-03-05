// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ClimberConstants;

public class Climber extends SubsystemBase {
	private WPI_TalonFX leftMotor = new WPI_TalonFX(ClimberConstants.LEFT_MOTOR);
	private WPI_TalonFX rightMotor = new WPI_TalonFX(ClimberConstants.RIGHT_MOTOR);
	private SlewRateLimiter leftLimiter = new SlewRateLimiter(2);
	private SlewRateLimiter rightLimiter = new SlewRateLimiter(2);
	//TODO 
	// PID climber extension
	// make extend use possition pid
	// make retraction use % output and check encoder in periodic 
	/** Creates a new Climber. */
	public Climber() {
		leftMotor.configFactoryDefault();
		leftMotor.setNeutralMode(NeutralMode.Brake);
		//leftMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		SupplyCurrentLimitConfiguration currentConfig = new SupplyCurrentLimitConfiguration(true, 110, 110, .5  );
		leftMotor.configSupplyCurrentLimit(currentConfig);
		rightMotor.configFactoryDefault();
		rightMotor.setNeutralMode(NeutralMode.Brake);
		//rightMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		rightMotor.configSupplyCurrentLimit(currentConfig);
	
	}

	public void extendLeft(double speed) {
		leftMotor.set(ControlMode.PercentOutput, leftLimiter.calculate(speed));
	}

	public void extendRight(double speed) {
		rightMotor.set(ControlMode.PercentOutput, rightLimiter.calculate(speed));
	}

	public double getRightPosition(){
		return rightMotor.getSelectedSensorPosition();
	}

	public void setRightPosition(double position){
	}

	public double getLeftPosition(){
		return rightMotor.getSelectedSensorPosition();
	}

	public void setLeftPosition(double position){
	} 
	
	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
