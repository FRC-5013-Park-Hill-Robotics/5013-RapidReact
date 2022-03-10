// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
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
	
		SupplyCurrentLimitConfiguration currentConfig = new SupplyCurrentLimitConfiguration(true, 110, 110, .5  );
	
		leftMotor.configFactoryDefault();
		leftMotor.setNeutralMode(NeutralMode.Brake);
		leftMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		leftMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		leftMotor.configSupplyCurrentLimit(currentConfig);
		leftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 30);
		leftMotor.setSelectedSensorPosition(0);
		leftMotor.config_kP(0, ClimberConstants.kP, 30);
		leftMotor.config_kF(0, ClimberConstants.kF, 30);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 50);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 50);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_6_Misc, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_10_Targets, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus, 250);
		leftMotor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 250);
	
		rightMotor.configFactoryDefault();
		rightMotor.setNeutralMode(NeutralMode.Brake);
		rightMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		rightMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		rightMotor.configSupplyCurrentLimit(currentConfig);
		rightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 30);
		rightMotor.setSelectedSensorPosition(0);
		rightMotor.config_kP(0, ClimberConstants.kP, 30);
		rightMotor.config_kF(0, ClimberConstants.kF, 30);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 50);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 50);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_6_Misc, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_10_Targets, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus, 250);
		rightMotor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 250);
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

	public void setRightPosition(double position,boolean withLoad){
		if (withLoad){
			rightMotor.set(ControlMode.Position, position, DemandType.ArbitraryFeedForward, ClimberConstants.kF);
		} else {
			rightMotor.set(ControlMode.Position, position);
		}
	}

	public double getLeftPosition(){
		return rightMotor.getSelectedSensorPosition();
	}

	public void setLeftPosition(double position, boolean withLoad){
		if (withLoad){
			leftMotor.set(ControlMode.Position, position, DemandType.ArbitraryFeedForward, ClimberConstants.kF);
		} else {
			leftMotor.set(ControlMode.Position, position);
		}
	} 
	
	@Override
	public void periodic() {
	}
}
