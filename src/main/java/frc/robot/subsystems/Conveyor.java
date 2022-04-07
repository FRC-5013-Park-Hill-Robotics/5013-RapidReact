// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConveyorConstants;
import frc.robot.RobotContainer;

public class Conveyor extends SubsystemBase {

	// Componenets ---------------------------------------------------
	private WPI_TalonFX conveyorMotor = new WPI_TalonFX(ConveyorConstants.CONVEYOR_ID);
	// REPLACE WITH ACTUAL COLOR SENCOR
	private DigitalInput eye = new DigitalInput(ConveyorConstants.EYE_ID);

    private ColorSensorV3 colorSensor = new ColorSensorV3( Port.kMXP );

    private RobotContainer m_robotContainer;

	// Values --------------------------------------------------------
	private double percentOutput;
	private long startTime;
	private boolean override = false;


  

	// Statics ------------------------------------------------------
	/** Precent Output **/
	public static final double kSpeed = 0.6;
	/** Precent Output **/
	public static final double kSpeedToShoot = 0.6;

	// Constructor --------------------------------------------------
	/** Creates a new Conveyor. */
	public Conveyor(RobotContainer container) {
		this.conveyorMotor.configFactoryDefault();
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 100);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_6_Misc, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_10_Targets, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus, 250);
		conveyorMotor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 250);

		m_robotContainer = container;
	}

    @Override
    public void periodic() 
    {	
      conveyorMotor.set(ControlMode.PercentOutput, percentOutput);
	  SmartDashboard.putString("Color","R:" + colorSensor.getRed() + " B:" + colorSensor.getBlue() + " G:" + colorSensor.getGreen());
    }

	// Set Output ---------------------------------------------------
	private void setPercentOutput(double targetPercent, Long timeout) {
		this.percentOutput = targetPercent;
		if (timeout == 0) {
			this.startTime = 0;
		} else if (startTime == 0) {
			// Don't set if you are delaying already.
			this.startTime = System.currentTimeMillis() + timeout;
		}
	}

	// Starts ----------------------------------------------------------
	public void start(long timeout) {
		this.override = false;
		if (!this.isMoving()) {
			setPercentOutput(kSpeed, timeout);
		}
	}

	public void start() {
		this.override = false;
		setPercentOutput(kSpeed, 0L);
	}

	public void sendToShooter() {
		this.override = false;
		setPercentOutput(kSpeedToShoot, 0L);
	}

	public void reverse() {
		this.override = true;
		setPercentOutput(-kSpeed, 0L);
	}

	// Start Override ----------------------------------------
	public void startOverride() {
		override = true;
		if (isBallReadyToShoot()) {
			setPercentOutput(kSpeed, 0L);
		}
	}

	public void stop() {
		setPercentOutput(0, 0L);
	}

	// Checks --------------------------------------------------
	public boolean isMoving() {
		return Math.abs(percentOutput) > 0.0;
	}

	public boolean isBallReadyToShoot() {
		// return true;
		return !this.eye.get();
	}

   /** 
   *    Returns true if the next ball in the conveyor is one of our
   *    alliance's balls. A ball is an alliance ball if it is the same
   *    color as our alliance team color, either red or blue.
   */
  public boolean isAllianceBallNext()
  {
	  return true;
	  /*
    Color currentColor = this.colorSensor.getColor();
    boolean redTeam = this.m_robotContainer.isRedAlliance();
    boolean colorRed = currentColor.red > currentColor.blue && currentColor.red > currentColor.green;

    return  (redTeam && colorRed) || (!redTeam && !colorRed);*/
  }

}
