// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.IntakeConstants;

/** Add your docs here. */
public class Intake extends SubsystemBase {


	private WPI_TalonFX  intakeMotor = new WPI_TalonFX(IntakeConstants.INTAKE_MOTOR);

	// Dropping the intake is set up as forward and raising it as reverse, may have
	// to change based on mechanics and wiring.
	private DoubleSolenoid intakeSolenoid;// = new DoubleSolenoid(PneumaticsModuleType.REVPH ,IntakeConstants.DROP_INTAKE_SOLENOID_CHANNEL, IntakeConstants.RAISE_INTAKE_SOLENOID_CHANNEL);

	/**
	 * Creates a new Intake.
	 */
	public Intake(Conveyor conveyor, RobotContainer container) {
		super();
		intakeMotor.configFactoryDefault();
		intakeMotor.setInverted(false);
		intakeMotor.setNeutralMode(NeutralMode.Brake);
		intakeSolenoid = container.getPneumaticsHub().makeDoubleSolenoid(IntakeConstants.DROP_INTAKE_SOLENOID_CHANNEL, IntakeConstants.RAISE_INTAKE_SOLENOID_CHANNEL);
	}

	@Override
	public void periodic() {

	}

	public void dropIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kForward);
		//dropIntakeSolenoid.set(true);
		//raiseIntakeSolenoid.set(false);
		intakeMotor.set(ControlMode.PercentOutput, .45);
	}

	public void raiseIntake() {
		intakeMotor.set(ControlMode.PercentOutput, 0);
		intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
		//dropIntakeSolenoid.set(false);
		//raiseIntakeSolenoid.set(true);

	}

	public boolean isDown() {
		return intakeSolenoid.get() == DoubleSolenoid.Value.kForward;
	}
	public void start(){
		intakeMotor.set(ControlMode.PercentOutput, .40);
	}
	public void stop(){
		intakeMotor.set(ControlMode.PercentOutput, 0);
	}
	public void reverseIntake() {
		intakeMotor.set(ControlMode.PercentOutput, -.40);
	}
}

