// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.TurretConstants.*;
import frc.robot.trobot5013lib.LinearServo;

public class Turret extends SubsystemBase {
    private WPI_TalonSRX motor = new WPI_TalonSRX(TURRET_MOTOR);
    private LinearServo servo = new LinearServo(SERVO_LEFT_ID,140,32);


    double desiredAngle = 0;
    
	/** Creates a new Turret. */
	public Turret() {
		servo.setHeight(140);
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
        servo.setHeight(height);
    }

	public double getHeight() {
        return servo.getHeight();
    }

	public void up(double increment){
		setHeight(servo.getHeight() + increment);
	}

	public void down(double increment){
		setHeight(servo.getHeight() - increment);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Height", servo.getHeight());
		// This method will be called once per scheduler run
        //pid controls
        //motor.set
        //coding stuff by baylee, a super expert programmer
	}
}
