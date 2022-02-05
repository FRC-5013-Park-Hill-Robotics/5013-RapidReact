// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.TurretConstants.*;

public class Turret extends SubsystemBase {
    private WPI_TalonSRX motor = new WPI_TalonSRX(TURRET_MOTOR);
	/** Creates a new Turret. */
	public Turret() {

	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
