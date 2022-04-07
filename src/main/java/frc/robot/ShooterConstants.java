// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.trobot5013lib.LinearInterpolator;

/** Add your docs here. */
public final class ShooterConstants {

    public static final int SHOOTER_TOP_MOTOR = 18;
    public static final int SHOOTER_BOTTOM_MOTOR = 17;
    public static final double SHOOTER_VELOCITY = 4650;
    public static final double TOP_PERCENT_OF_BOTTOM = 1.50;
    
    public static final class FLY_WHEEL_GAINS{
        // (output% * 1023) / (tuner value for velocity at output%)
		public static final double kP = .4;
        //public static final double kF = .049;
        public static final double kF = .055;

        // (throttle% x 1023) / (servo error # after just running w/ F gain)

        public static final double kI = 0.0;
        // 1. find closed loop error units by change in sensor by 100 
        // 2. multiply the units by 2.5 to cover the typical error

        public static final double kD = 0.2;
        // (10 x kP)
    }
	public static final class FendorShotConstants{
		public static final double BOTTOM_WHEEL_SPEED = 1630;
		public static final double  HOOD_HEIGHT = 140;
	}

	public static final double INITIAL_SPEED = 3120;

	public static final class TargetConstants{
		public static final double[][] HOOD_HEIGHT_ARRAY = {
				{-13,140},
				{-12,140},
				{-2,140},
				{0,140},
				{4.5, 140},
				{5,140},
				{7,140},
				{11,140}
			//	{-11,1,8},
			//{140,140,40}
			};
		public static final double[][] SHOOTER_SPEED_ARRAY = {
	/*
			{5.7, 3850},
			{5.2, 4100},
			{4.0, 4000},
			{-0.5,4200},
			{-2.3,4340},
			{-5,4550},
			{-10.5,4600},
			{-14,5000}*/
/*			{7.0, 3050},
			{3.9, 3200},
			{1.1, 3200},
			{-2.5,3350},
			{-7.5,3500}
		//	,
		//	{-11.9,}*/
			{9.6,3000},
			{4,3080},
			{-0.3,3120},
			{-3,3220},
			{-6.3,3300},
			{-8.6,3560},
			{-9,3440},
			{-13,3540}

		};
				

		public static final LinearInterpolator HOOD_INTERPOLATOR = new LinearInterpolator(HOOD_HEIGHT_ARRAY);
		public static final LinearInterpolator SHOOTER_SPEED_INTERPOLATOR = new LinearInterpolator(SHOOTER_SPEED_ARRAY);
		public static final double HOOD_TOLERANCE_MM = 2;
		public static final double SHOOTER_SPEED_PERCENT = .05;
	}
}
