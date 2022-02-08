// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
    topMotor.setInverted(true);
    bottomMotor.setInverted(!topMotor.getInverted());
    m_conveyor = conveyor;
    setPID(bottomMotor,FLY_WHEEL_GAINS.kP, 0, 0, FLY_WHEEL_GAINS.kF);
    setPID(topMotor,FLY_WHEEL_GAINS.kP, 0, 0, FLY_WHEEL_GAINS.kF);
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
    setTargetVelocity(heightVelocity);
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
    //SmartDashboard.putString("bottomShooterTargetVelocity", ""+getTargetVelocity());
    //SmartDashboard.putString("topShooterVelocity",""+ topMotor.getSelectedSensorVelocity());
    SmartDashboard.putString("shooterVelocity", ""+topMotor.getSelectedSensorVelocity());
    if (firing){
      if (atSpeed()){
        //SmartDashboard.putString("at speed", ""+true);
        m_conveyor.start();
      } else {
        //SmartDashboard.putString("at speed", ""+false);
        m_conveyor.stop();
        if (m_conveyor.isBallReadyToShoot()){
          m_conveyor.reverse();
        }
      }
      topMotor.set(ControlMode.Velocity,getTargetVelocity()); 
      bottomMotor.set(ControlMode.Velocity,getTargetVelocity());
    } else {
      topMotor.set(ControlMode.Velocity,SHOOTER_VELOCITY);
      bottomMotor.set(ControlMode.Velocity,SHOOTER_VELOCITY);
    }

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

    return topMotor.getSelectedSensorVelocity() >= getTargetVelocity() *.95&&
    bottomMotor.getSelectedSensorVelocity() >= getTargetVelocity() *.95;
  }

  public void changeVelocity(double amount){
    heightVelocity += amount;
  }
  public void resetVelocity(){
    heightVelocity = SHOOTER_VELOCITY;
  }
}