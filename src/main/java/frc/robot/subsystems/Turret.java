// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.TurretConstants.*;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.subsystems.ShooterVision;

public class Turret extends SubsystemBase {
    private WPI_TalonSRX motor = new WPI_TalonSRX(TURRET_MOTOR);
    private Servo leftServo = new Servo(SERVO_LEFT_ID);
    private Servo rightServo = new Servo(SERVO_RIGHT_ID);

    double desiredAngle = 0;
    
	/** Creates a new Turret. */
	public Turret() {
	}
    //Gets the current angle of the Talon SRX.
    public double getCurrentAngle() {
        return motor.getSelectedSensorPosition();
        
    }
    //Sets the desired angle of the turret
    public void setDesiredAngle(double desiredAngle) {
        this.desiredAngle = desiredAngle;
        desiredAngle = getCurrentAngle();
        
    }
    public void setOpenLoop(double speed) {
        motor.set(speed);
    }
    
    //Sets the pulse width (height) of the left/right servos
    public void setHeight(double height) {
        leftServo.set(height);
        rightServo.set(height);
    }
	@Override
	public void periodic() {
		// This method will be called once per scheduler run
        //pid controls
        //motor.set
        //coding stuff by baylee, a super expert programmer
	}
}
