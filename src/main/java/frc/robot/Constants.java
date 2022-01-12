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
    public static final int BLINKEN_PWM_PORT = 0;

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
            public static final double ENCODER_OFFSET_RADIANS = 0;// -Math.toRadians(176); 
            public static final int STATES_INDEX = 0;        }

        public static final class FrontRightSwerveConstants {
            public static final int STEER_MOTOR_ID = 5;
            public static final int DRIVE_MOTOR_ID = 6;
            public static final int ENCODER_ID = 7;
            public static final double ENCODER_OFFSET_RADIANS =0;//  -Math.toRadians(177.2);
            public static final int STATES_INDEX = 1;
        }

        public static final class BackLeftSwerveConstants {
            public static final int STEER_MOTOR_ID = 8;
            public static final int DRIVE_MOTOR_ID = 9;
            public static final int ENCODER_ID = 10;
            public static final double ENCODER_OFFSET_RADIANS = 0;// -Math.toRadians(275.8); 
            public static final int STATES_INDEX = 2;
        }

        public static final class BackRightSwerveConstants {
            public static final int STEER_MOTOR_ID = 11;
            public static final int DRIVE_MOTOR_ID = 12;
            public static final int ENCODER_ID = 13;
            public static final double ENCODER_OFFSET_RADIANS = 0;//  -Math.toRadians(315.6);
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
    public final class ShooterVisionConstants {
        public static final double CAMERA_ANGLE = 0;
        public static final double CAMERA_HEIGHT = 0;
        public static final double TARGET_HEIGHT = 0;
        public static final int TARGET_PIPELINE = 0;
        public static final int DEFAULT_PIPELINE = 1;
        public static final int DRIVE_PIPELINE = 2;
        public static final int LED_ON = 3;
        public static final int LED_OFF = 1;
        public static final double TURN_TO_TARGET_TOLERANCE = 1.5;
        public static final double RANGE_TOO_CLOSE =4;
        public static final double RANGE_TOO_FAR=-9;
        public static final double RANGE_PRIME_START=0;
        public static final double RANGE_PRIME_END=-2;
    }
    public final class ShooterConstants {

        public static final int SHOOTER_MOTOR_ID = 0;
        public static final double SHOOTER_VELOCITY = 0;
    
        public static final class FLY_WHEEL_GAINS{

        public static final double KP = 0.0;
        public static final double KI = 0.0;
        public static final double KD = 0.0;

        }

        public static final class TURRET{

            public static final double MIN_TURN = 0.0;

        }

    }

    public class BlinkenConstants {
        public final static double RED_STROBE =  -0.11; //45 1445 -0.11 Fixed Palette Pattern Strobe, Red - - Brightness
        public final static double BLUE_STROBE = -0.09 ; //46 1455 -0.09 Fixed Palette Pattern Strobe, Blue - - Brightness
        public final static double GOLD_STROBE = -0.07 ; //47 1465 -0.07 Fixed Palette Pattern Strobe, Gold - - Brightness
        public final static double WHITE_STROBE = -0.05; //48 1475 -0.05 Fixed Palette Pattern Strobe, White - - Brightness
        public final static double BLACK_BLEND_COLOR1 = -0.03; //49 1485 -0.03 Color 1 Pattern End to End Blend to Black - - Brightness
        public final static double SCANNER_COLOR1 =  -0.01 ; //50 1495 -0.01 Color 1 Pattern Larson Scanner Pattern Width Speed Brightness
        public final static double LIGHT_CHASE_COLOR1 = 0.01; //51 1505 0.01 Color 1 Pattern Light Chase Dimming Speed Brightness
        public final static double HEARBEAT_SLOW_COLOR1= 0.03; //52 1515 0.03 Color 1 Pattern Heartbeat Slow - - Brightness
        public final static double HEARRBEAT_MEDIUM_COLOR1= 0.05; //53 1525 0.05 Color 1 Pattern Heartbeat Medium - - Brightness
        public final static double HEARTBEAT_FAST_COLOR1= 0.07; //54 1535 0.07 Color 1 Pattern Heartbeat Fast - - Brightness
        public final static double BREATH_SLOW_COLOR1 = 0.09; //55 1545 0.09 Color 1 Pattern Breath Slow - - Brightness22 1215 -0.57 Fixed Palette Pattern Fire, Large - - Brightness
        public final static double BREATH_FAST_COLOR1=  0.11 ; //56 1555 0.11 Color 1 Pattern Breath Fast - - Brightness
        public final static double SHOT_COLOR1 =  0.13; //57 1565 0.13 Color 1 Pattern Shot - - Brightness
        public final static double STROBE_COLOR1 = 0.15 ; //58 1575 0.15 Color 1 Pattern Strobe - - Brightness
        public final static double BLACK_BLEND_COLOR2= 0.17; //59 1585 0.17 Color 2 Pattern End to End Blend to Black - - Brightness
        public final static double SCANNER_COLOR2= 0.19; //60 1595 0.19 Color 2 Pattern Larson Scanner Pattern Width Speed Brightness
        public final static double LIGHT_CHASE_COLOR2= 0.21 ; //61 1605 0.21 Color 2 Pattern Light Chase Dimming Speed Brightness
        public final static double HEARTBEAT_SLOW_COLOR2= 0.23; //62 1615 0.23 Color 2 Pattern Heartbeat Slow - - Brightness
        public final static double HEARTBEAT_MEDIUM_COLOR2= 0.25 ; //63 1625 0.25 Color 2 Pattern Heartbeat Medium - - Brightness
        public final static double HEARTBEAT_FAST_COLOR2= 0.27; //64 1635 0.27 Color 2 Pattern Heartbeat Fast - - Brightness
        public final static double BREATH_SLOW_COLOR2=  0.29; //65 1645 0.29 Color 2 Pattern Breath Slow - - Brightness
        public final static double BREATH_FAST_COLOR2=  0.31; //66 1655 0.31 Color 2 Pattern Breath Fast - - Brightness
        public final static double SHOT_COLOR2= 0.33; //67 1665 0.33 Color 2 Pattern Shot - - Brightness
        public final static double STROBE_COLOR2= 0.35; //68 1675 0.35 Color 2 Pattern Strobe - - Brightness
        public final static double SPARKLE_COLOR1_ON_COLOR2 = 0.37; //69 1685 0.37 Color 1 and 2 Pattern Sparkle, Color 1 on Color 2 - - Brightness
        public final static double SPARKLE_COLOR2_ON_COLOR1 = 0.39; //70 1695 0.39 Color 1 and 2 Pattern Sparkle, Color 2 on Color 1 - - Brightness
        public final static double GRADIENT_COLOR1_TO_COLOR2= 0.41; //71 1705 0.41 Color 1 and 2 Pattern Color Gradient, Color 1 and 2 - - Brightness
        public final static double BEATS_COLOR1_COLOR2 = 0.43; //72 1715 0.43 Color 1 and 2 Pattern Beats per Minute, Color 1 and 2 Pattern Density Speed Brightness
        public final static double BLEND_COLOR1_TO_COLOR2 = 0.45; //73 1725 0.45 Color 1 and 2 Pattern End to End Blend, Color 1 to 2 - - Brightness
        public final static double BLEND_COLOR2_TO_COLOR1 = 0.47; //74 1735 0.47 Color 1 and 2 Pattern End to End Blend - - Brightness
        public final static double SETUP_PATTERN_COLOR1_COLOR2 = 0.49; //75 1745 0.49 Color 1 and 2 Pattern Color 1 and Color 2 no blending (Setup Pattern) - - Brightness
        public final static double TWINKLES_COLOR1_COLOR2 = 0.51; //76 1755 0.51 Color 1 and 2 Pattern Twinkles, Color 1 and 2 - - Brightness
        public final static double WAVES_COLOR1_COLOR2 = 0.53; //77 1765 0.53 Color 1 and 2 Pattern Color Waves, Color 1 and 2 - - Brightness
        public final static double SINGLETON_COLOR1_COLOR2 = 0.55; //78 1775 0.55 Color 1 and 2 Pattern Sinelon, Color 1 and 2 Pattern Density Speed Brightness
        public final static double PINK = 0.57; //79 1785 0.57 Solid Colors Hot Pink - - Brightness
        public final static double RED_DARK = 0.59; //80 1795 0.59 Solid Colors Dark red - - Brightness
        public final static double RED_BRIGHT = 0.61; //81 1805 0.61 Solid Colors Red - - Brightness
        public final static double RED_ORANGE = 0.63; //82 1815 0.63 Solid Colors Red Orange - - Brightness
        public final static double ORANGE = 0.65; //83 1825 0.65 Solid Colors Orange - - Brightness
        public final static double GOLD =  0.67; //84 1835 0.67 Solid Colors Gold - - Brightness
        public final static double YELLOW = 0.69; //85 1845 0.69 Solid Colors Yellow - - Brightness
        public final static double GREEN_LAWN = 0.71; //86 1855 0.71 Solid Colors Lawn Green - - Brightness
        public final static double GREEN_LIME = 0.73; //87 1865 0.73 Solid Colors Lime - - Brightness
        public final static double GREEN_DARK = 0.75; //88 1875 0.75 Solid Colors Dark Green - - Brightness
        public final static double GREEN = 0.77; //89 1885 0.77 Solid Colors Green - - Brightness
        public final static double BLUE_GREEN =  0.79; //90 1895 0.79 Solid Colors Blue Green - - Brightness
        public final static double BLUE_AQUA = 0.81 ; //91 1905 0.81 Solid Colors Aqua - - Brightness
        public final static double BLUE_SKY = 0.83; //92 1915 0.83 Solid Colors Sky Blue - - Brightness
        public final static double BLUE_DARK = 0.85; //93 1925 0.85 Solid Colors Dark Blue - - Brightness
        public final static double BLUE =  0.87; //94 1935 0.87 Solid Colors Blue - - Brightness
        public final static double BLUE_VIOLET = 0.89; //95 1945 0.89 Solid Colors Blue Violet - - Brightness
        public final static double VIOLET = 0.91; //96 1955 0.91 Solid Colors Violet - - Brightness
        public final static double  WHITE =  0.93; //97 1965 0.93 Solid Colors White - - Brightness
        public final static double GRAY = 0.95 ; //98 1975 0.95 Solid Colors Gray - - Brightness
        public final static double GRAY_DARK = 0.97; //99 1985 0.97 Solid Colors Dark Gray - - Brightness
        public final static double BLACK = 0.99; //100 1995 0.99 Solid Colors Black - - Brightness

    }
}