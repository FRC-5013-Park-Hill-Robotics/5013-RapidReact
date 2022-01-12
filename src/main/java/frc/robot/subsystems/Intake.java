// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants.Conveyor;
import frc.robot.Constants.DrivetrainConstants.IntakeConstants;

/** Add your docs here. */





public class Intake extends SubsystemBase {
    
        private TalonSRX intakeMotor = new TalonSRX(IntakeConstants.INTAKE_MOTOR);
        
        //Dropping the intake is set up as forward and raising it as reverse, may have to change based on mechanics and wiring.
        private Solenoid dropIntakeSolenoid;// = new Solenoid(Constants.PCM_ID,PneumaticsModuleType.REVPH, IntakeConstants.DROP_INTAKE_SOLENOID_CHANNEL);
        private Solenoid raiseIntakeSolenoid;// = new Solenoid(Constants.PCM_ID,PneumaticsModuleType.REVPH,IntakeConstants.RAISE_INTAKE_SOLENOID_CHANNEL);
      
       
        /**
         * Creates a new Intake.
         */
        public Intake(Conveyor conveyor) {
          super();
          intakeMotor.configFactoryDefault();
          intakeMotor.setInverted(true);
          intakeMotor.setNeutralMode(NeutralMode.Brake);
        }
      
        @Override
        public void periodic() {
          Faults faults = new Faults();
          StickyFaults stickyFaults = new StickyFaults();
          intakeMotor.getFaults(faults);
          intakeMotor.getStickyFaults(stickyFaults);
          if (faults.hasAnyFault()){
            SmartDashboard.putString("Intake Faults",faults.toString());
          } else {
            SmartDashboard.putString("Intake Faults", "none");
          }
          if (stickyFaults.hasAnyFault()){
            SmartDashboard.putString("Intake Sticky Faults",stickyFaults.toString());
          } else {
            SmartDashboard.putString("IntakeSticky Faults", "none");
          }
      
         
        }
      
        public void dropIntake(){
          //Using command scheduler because shooter may be controlling conveyor and it gets presidence
          //CommandScheduler.getInstance().schedule(new InstantCommand(() -> m_conveyor.start(), m_conveyor));
          dropIntakeSolenoid.set(true); 
          raiseIntakeSolenoid.set(false); 
          intakeMotor.set(ControlMode.PercentOutput, .45 );
        }
      
        public void raiseIntake(){
          //Using command scheduler because shooter may be controlling conveyor and it gets presidence
         // CommandScheduler.getInstance().schedule(new InstantCommand(() -> m_conveyor.stop(), m_conveyor));
          intakeMotor.set(ControlMode.PercentOutput, 0 );
          dropIntakeSolenoid.set(false); 
          raiseIntakeSolenoid.set(true); 
          
        }
      
        public boolean isDown(){
          return dropIntakeSolenoid.get();
        }
        public void reverseIntake(){
          intakeMotor.set(ControlMode.PercentOutput, -.75 );
        }
        
      }
 

