// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class TestInterpolation {
	
	public static void main(String... args) {
		for (int x = -15; x < 15; x++)
         	System.out.println("input: " + x + " output:"+ShooterConstants.TargetConstants.SHOOTER_SPEED_INTERPOLATOR.getInterpolatedValue(x));
    }
}
