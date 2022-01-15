// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import com.team1678.frc2021.Constants;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.util.LatchedBoolean;

import edu.wpi.first.wpilibj.DigitalInput;

import com.team254.lib.drivers.MotorChecker;
import com.team254.lib.drivers.BaseTalonChecker;

//subsystem base?
public class Turret extends ServoMotorSubsystem {
    private static Turret mInstance;
    private LatchedBoolean mJustReset = new LatchedBoolean();
    private DigitalInput mLimitSwitch = new DigitalInput(1);

    private static final SupplyCurrentLimitConfiguration CURR_LIM = new SupplyCurrentLimitConfiguration(true, 40, 60, 0.01);

    public synchronized static Turret getInstance() {
        if (mInstance == null) {
            mInstance = new Turret(Constants.kTurretConstants);
        }
        return mInstance;
    }

    private Turret(final ServoMotorSubsystemConstants constants) {
        super(constants);

        mMaster.setSelectedSensorPosition(0);
        mMaster.overrideSoftLimitsEnable(false);
        mMaster.configSupplyCurrentLimit(CURR_LIM);
    }

    // Syntactic sugar.
    public synchronized double getAngle() {
        return getPosition();
    }

    public synchronized boolean safeToIntake() {
        Rotation2d angle = Rotation2d.fromDegrees(getAngle());
        if (angle.getDegrees() < 55 && angle.getDegrees() > -55) {
            return false;
        }
        return true;
    }

    @Override
    public synchronized void writePeriodicOutputs() {
        super.writePeriodicOutputs();
        // mMaster.set(ControlMode.PercentOutput, 0.3);
    }

    @Override
    public synchronized void readPeriodicInputs() {
        super.readPeriodicInputs();
    }

    @Override
    public boolean checkSystem() {
        return BaseTalonChecker.checkMotors(this, new ArrayList<MotorChecker.MotorConfig<BaseTalon>>() {
            private static final long serialVersionUID = 1636612675181038895L; // TODO find the right number

            {
                add(new MotorChecker.MotorConfig<>("master", mMaster));
            }
        }, new MotorChecker.CheckerConfig() {
            { // TODO change to legit config
                mRunOutputPercentage = 0.1;
                mRunTimeSec = 1.0;
                mCurrentFloor = 0.1;
                mRPMFloor = 90;
                mCurrentEpsilon = 2.0;
                mRPMEpsilon = 200;
                mRPMSupplier = mMaster::getSelectedSensorVelocity;
            }
        });
    }

    @Override
    public void outputTelemetry() {
        super.outputTelemetry();
    }

    public void setCoastMode() {
        mMaster.setNeutralMode(NeutralMode.Coast);
    }
    
}