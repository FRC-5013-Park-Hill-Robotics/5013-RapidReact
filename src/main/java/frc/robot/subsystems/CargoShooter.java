// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.trobot5013lib.TrobotUtil;

import static frc.robot.ShooterConstants.*;

public class CargoShooter extends SubsystemBase {
  private WPI_TalonFX topMotor = new WPI_TalonFX(SHOOTER_TOP_MOTOR);
  private WPI_TalonFX bottomMotor = new WPI_TalonFX(SHOOTER_BOTTOM_MOTOR);
  private boolean firing = false;
  private double heightVelocity = SHOOTER_VELOCITY;
  private double m_targetVelocity = 0;
  private Conveyor m_conveyor;
  
  /**
   * Creates a new Shooter.
   */
  public CargoShooter(Conveyor conveyor) {
    m_conveyor = conveyor;

	topMotor.setInverted(false);
	topMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 50);
	topMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 50);
	topMotor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_6_Misc, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_10_Targets, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus, 250);
	topMotor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 250);
	setPID(topMotor,FLY_WHEEL_GAINS.kP, 0, 0, FLY_WHEEL_GAINS.kF);

	bottomMotor.setInverted(!topMotor.getInverted());
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 50);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 50);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_6_Misc, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_10_Targets, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus, 250);
	bottomMotor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 250);
	setPID(bottomMotor,FLY_WHEEL_GAINS.kP, 0, 0, FLY_WHEEL_GAINS.kF);
	
  }

  //method for testing.
  public void changeSpeed(double velocity){
    setTargetVelocity(getTargetVelocity() + velocity);
    //adjustMotorsToTarget();
    firing = true;
    //String("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    //SmartDashboard.putString("bottomShooterVelocity", ""+bottomMotor.getSelectedSensorVelocity());
  }
  

  public void setPID(WPI_TalonFX motor,double kP, double kI, double kD, double kF) {
    motor.config_kP(0, kP, 30);
    motor.config_kI(0, kI, 30);
		motor.config_kD(0, kD, 30);
		motor.config_kF(0, kF, 30);
  }

  public void spinUp(){
    setTargetVelocity(heightVelocity);
  }

  public void fire(){
   // setTargetVelocity(heightVelocity);
   m_conveyor.start();
    firing = true;
    //String("Shooter is Firing: ", ""+firing);
  }

  public void stopFiring(){
    firing = false;
   // setTargetVelocity(SHOOTER_NO_VELOCITY);
    m_conveyor.stop();
  }

  @Override
  public void periodic() {
    //SmartDashboard.putString("topShooterTargetVelocity",""+ getTopTargetVelocity());
    SmartDashboard.putString("target Velocity", ""+getTargetVelocity());
    SmartDashboard.putString("Actual Velocity", ""+bottomMotor.getSelectedSensorVelocity());
   
	if (firing){
      if (atSpeed()){
        m_conveyor.start();
      } else {
        m_conveyor.stop();
      }
      topMotor.set(ControlMode.Velocity,getTopTargetVelocity()); 
      bottomMotor.set(ControlMode.Velocity,getTargetVelocity());
    } else {
      topMotor.set(ControlMode.Velocity,getTopTargetVelocity());
      bottomMotor.set(ControlMode.Velocity,getTargetVelocity() );
    }

  }

  public void stop(){
	  topMotor.set(ControlMode.PercentOutput, 0);
	  bottomMotor.set(ControlMode.PercentOutput, 0);
  }
  
  public void setTargetVelocity(double bottomMotorTarget){
    m_targetVelocity = bottomMotorTarget;
  }
  public double getTopTargetVelocity(){
    return m_targetVelocity * TOP_PERCENT_OF_BOTTOM;
  }
  public double getTargetVelocity(){
    return m_targetVelocity;
  }
  public boolean atSpeed(){
    //Boolean("Is BottomMotor at speed?: ", bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() );

    return TrobotUtil.withinTolerance(topMotor.getSelectedSensorVelocity(), getTopTargetVelocity(), .05* getTopTargetVelocity()) && 
		TrobotUtil.withinTolerance(bottomMotor.getSelectedSensorVelocity(), getTargetVelocity(), .05* getTargetVelocity()) && 
    bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() *.95;
  }

  public void changeVelocity(double amount){
    heightVelocity += amount;
  }
  public void resetVelocity(){
    heightVelocity = SHOOTER_VELOCITY;
  }
}