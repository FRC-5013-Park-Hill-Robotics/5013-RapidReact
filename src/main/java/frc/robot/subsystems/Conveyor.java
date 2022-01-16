// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ConveyorConstants.*;

public class Conveyor extends SubsystemBase {
    private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(LEFT_CONVEYOR_MOTOR);
    private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(RIGHT_CONVEYOR_MOTOR);
    public static final double kSpeed = 0.4;//percent output
    public static final double kSpeedForShooter = 0.6;//percent output
    
  
    /**
     * Creates a new Conveyor.
     */
    public Conveyor() {
      leftMotor1.configFactoryDefault();
      rightMotor1.configFactoryDefault();
      leftMotor1.setInverted(true);
      rightMotor1.setInverted(false);
      leftMotor1.setNeutralMode(NeutralMode.Brake);
      rightMotor1.setNeutralMode(NeutralMode.Brake);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void stop(){

  }

  public void start(){

  }

  public void startForShooter(){

  }

  public boolean isBallReadyToShoot(){
	  return false;
  }

  public void reverse(){

  }
}
