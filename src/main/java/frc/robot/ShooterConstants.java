// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public final class ShooterConstants {

    public static final int SHOOTER_MOTOR_ID = 0;
    public static final double SHOOTER_VELOCITY = 0;
    
    public static final class FLY_WHEEL_GAINS{

        public static final double kF = 0.0;
        // (output% * 1023) / (tuner value for velocity at output%)

        public static final double kP = 0.0;
        // (throttle% x 1023) / (servo error # after just running w/ F gain)

        public static final double kI = 0.0;
        // 1. find closed loop error units by change in sensor by 100 
        // 2. multiply the units by 2.5 to cover the typical error

        public static final double kD = 0.0;
        // (10 x kP)
    }

    public static final class TURRET{

        public static final double MIN_TURN = 0.0;

    }

}
