// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public final class TurretConstants {
    kTurretConstants.kName = "Turret";

	kTurretConstants.kMasterConstants.id = 10;
	kTurretConstants.kMasterConstants.invert_motor = false;
	kTurretConstants.kMasterConstants.invert_sensor_phase = false;

	// Unit == Degrees
	kTurretConstants.kHomePosition = 0.0; // CCW degrees from forward
	kTurretConstants.kTicksPerUnitDistance = (2048.0 * 27.84) / 360.0;
	kTurretConstants.kKp = 0.07; // 0.5
	kTurretConstants.kKi = 0;
	kTurretConstants.kKd = 12.0;
	kTurretConstants.kKf = 0.10;
	kTurretConstants.kKa = 0.0;
	kTurretConstants.kMaxIntegralAccumulator = 0;
	kTurretConstants.kIZone = 0; // Ticks
	kTurretConstants.kDeadband = 0; // Ticks

	kTurretConstants.kPositionKp = 0.1;
	kTurretConstants.kPositionKi = 0.0;
	kTurretConstants.kPositionKd = 10.0;
	kTurretConstants.kPositionKf = 0.0;
	kTurretConstants.kPositionMaxIntegralAccumulator = 0;
	kTurretConstants.kPositionIZone = 0; // Ticks
	kTurretConstants.kPositionDeadband = 0; // Ticks
	kTurretConstants.kMinUnitsLimit = -130.0;
	kTurretConstants.kMaxUnitsLimit = 100.0;

	kTurretConstants.kCruiseVelocity = 5000; // Ticks / 100ms
	kTurretConstants.kAcceleration = 7000; // Ticks / 100ms / s
	kTurretConstants.kRampRate = 0.0; // s
	kTurretConstants.kContinuousCurrentLimit = 20; // amps
	kTurretConstants.kPeakCurrentLimit = 40; // amps
	kTurretConstants.kPeakCurrentDuration = 10; // milliseconds
	kTurretConstants.kMaxVoltage = 12.0;

	// kTurretConstants.kStatusFrame8UpdateRate = 50;
	// kTurretConstants.kRecoverPositionOnReset = true;
}


