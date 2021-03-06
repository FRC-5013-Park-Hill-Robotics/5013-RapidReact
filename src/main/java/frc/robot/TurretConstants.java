// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class TurretConstants {
    public static final int TURRET_MOTOR = 16;
    // minimum angle the turret can rotate to
    public static final int TURRET_MIN_ANGLE = 0;
    // maximum angle the turret can rotate to
    public static final int TURRET_MAX_ANGLE = 270;
    public static final int SERVO_LEFT_ID = 0;
	public static final double HOOD_MAX_HEIGHT = 140;
	public static final double SHOOTING_ANGLE = 339;
	public static final double STARTING_ANGLE = 129;
    public static final class FLY_WHEEL_GAINS{
		public static final double kP = .5;
        public static final double kF = 1;
    }
}
