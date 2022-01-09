// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.Mk4SwerveModuleHelper.GearRatio;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int FALCON_500_MAX_RPM = 6380;
    public static final int PDP_ID = 0;

    public static final class ControllerConstants {
        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final double DEADBAND = 0.05;
    }
    public static final class DrivetrainConstants {
        public static final int PIGEON_ID = 1; 
        public static final GearRatio SWERVE_GEAR_RATIO = GearRatio.L2;
        /**
         * The maximum voltage that will be delivered to the drive motors.
         * <p>
         * This can be reduced to cap the robot's maximum speed. Typically, this is
         * useful during initial testing of the robot.
         */
        public static final double MAX_VOLTAGE = 12.0;
        public static final class DrivetrainGeometry{
            /**
             * The left-to-right distance between the drivetrain wheels Should be measured
             * from center to center.
             */
            public static final double TRACKWIDTH_METERS = .590; 
            /**
             * The front-to-back distance between the drivetrain wheels. Should be measured
             * from center to center.
             */
            public static final double WHEELBASE_METERS = .590; 

            /**
             * The maximum velocity of the robot in meters per second.
             * <p>
             * This is a measure of how fast the robot should be able to drive in a straight
             * line.
             */
            public static final double MAX_VELOCITY_METERS_PER_SECOND = FALCON_500_MAX_RPM / 60.0
                    * SdsModuleConfigurations.MK4_L2.getDriveReduction() * SdsModuleConfigurations.MK4_L2.getWheelDiameter()
                    * Math.PI;
            /**
             * The maximum angular velocity of the robot in radians per second.
             * <p>
             * This is a measure of how fast the robot can rotate in place.
             */
            public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND
                    / Math.hypot(TRACKWIDTH_METERS / 2.0, WHEELBASE_METERS / 2.0);
        }


        public static final class FrontLeftSwerveConstants {
            public static final int STEER_MOTOR_ID = 2;
            public static final int DRIVE_MOTOR_ID = 3;
            public static final int ENCODER_ID = 4;
            public static final double ENCODER_OFFSET_RADIANS = -Math.toRadians(176); 
            public static final int STATES_INDEX = 0;        }

        public static final class FrontRightSwerveConstants {
            public static final int STEER_MOTOR_ID = 5;
            public static final int DRIVE_MOTOR_ID = 6;
            public static final int ENCODER_ID = 7;
            public static final double ENCODER_OFFSET_RADIANS = -Math.toRadians(177.2);
            public static final int STATES_INDEX = 1;
        }

        public static final class BackLeftSwerveConstants {
            public static final int STEER_MOTOR_ID = 8;
            public static final int DRIVE_MOTOR_ID = 9;
            public static final int ENCODER_ID = 10;
            public static final double ENCODER_OFFSET_RADIANS = -Math.toRadians(275.8); 
            public static final int STATES_INDEX = 2;
        }

        public static final class BackRightSwerveConstants {
            public static final int STEER_MOTOR_ID = 11;
            public static final int DRIVE_MOTOR_ID = 12;
            public static final int ENCODER_ID = 13;
            public static final double ENCODER_OFFSET_RADIANS =  -Math.toRadians(315.6);
            public static final int STATES_INDEX = 3;
        }

        //Turning the bot gains used by PIDControllers
        public static final class ThetaGains {
            public static final double kP = 4;
            public static final double kI = 0;
            public static final double kD = 0;
            public static final double kTurnToleranceRad = 0.025; 
            public static final double kTurnRateToleranceRadPerS = .17;
        }

        //Driving the bot gains used by PIDControllers
        public static final class TranslationGains {
            public static final double kP = 2.2956;
            public static final double kI = 0;
            public static final double kD = 0;
            public static final double kA = 0.12872;
            public static final double kV = 2.3014;
            public static final double kS = 0.55493;
        }

    }
}