// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.trobot5013lib.led.BlinkingPattern;
import frc.robot.trobot5013lib.led.ChasePattern;
import frc.robot.trobot5013lib.led.RainbowPattern;
import frc.robot.trobot5013lib.led.SolidColorPattern;
import frc.robot.trobot5013lib.led.TrobotAddressableLED;
import frc.robot.trobot5013lib.led.TrobotAddressableLEDPattern;
import frc.robot.subsystems.ShooterVision;
import frc.robot.trobot5013lib.led.IntensityPattern;

public class StatusLED extends SubsystemBase {
	private TrobotAddressableLED m_led = new TrobotAddressableLED(Constants.STATUS_LED_PWM_PORT, 60);
	private RobotContainer m_RobotContainer;
	private TrobotAddressableLEDPattern m_bluePattern = new SolidColorPattern(Color.kBlue);
	private TrobotAddressableLEDPattern m_redPattern = new SolidColorPattern(Color.kRed);
	// private TrobotAddressableLEDPattern m_disabledPattern = new ChasePattern(new
	// Color[]{Color.kRed,Color.kWhite},3);
	private TrobotAddressableLEDPattern m_disabledPattern = new RainbowPattern();
	private TrobotAddressableLEDPattern m_greenPattern = new SolidColorPattern(Color.kGreen);
	private TrobotAddressableLEDPattern m_yellowPattern = new SolidColorPattern(Color.kLightYellow);
	private TrobotAddressableLEDPattern m_blinkingRed = new BlinkingPattern(Color.kRed, 0.25);
	private TrobotAddressableLEDPattern m_blinkingGreen = new BlinkingPattern(Color.kGreen, 0.25);
	private TrobotAddressableLEDPattern m_purplePattern = new SolidColorPattern(Color.kPurple);
	private IntensityPattern m_blueIntensityPattern = new IntensityPattern(Color.kBlue, 0);
	private IntensityPattern m_redIntensityPattern = new IntensityPattern(Color.kRed, 0);
	private int intensityDegrees = 10;

	/** Creates a new StatusLED. */
	public StatusLED(RobotContainer robotContainer) {
		super();
		m_RobotContainer = robotContainer;
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
		// Set color based off of robot status. Use m_RobotContainer to access robot
		// subsystems
		double pitch = m_RobotContainer.getDrivetrainSubsystem().getGyroscopeRotationPitch().getDegrees();

		if (m_RobotContainer.getshooterVision().isTargeting()) {
			if (m_RobotContainer.getshooterVision().hasTarget()) {	
				if (m_RobotContainer.getshooterVision().isPrimeRange()) {
					if (m_RobotContainer.getshooter().atSpeed() &&
						m_RobotContainer.getshooterVision().isOnTarget()){
						m_led.setPattern(m_blinkingGreen);
					} else {
						m_led.setPattern(m_greenPattern);
					}
				} else {
					m_led.setPattern(m_yellowPattern);
				}
			} else {
				m_led.setPattern(m_blinkingRed);
			}
		} else if (m_RobotContainer.getDrivetrainSubsystem().getGyroscopeRotationRoll().getDegrees() >= 100
				|| m_RobotContainer.getDrivetrainSubsystem().getGyroscopeRotationRoll().getDegrees() <= 80) {
			m_led.setPattern(m_purplePattern);
		} else if (pitch >= intensityDegrees) {
			m_redIntensityPattern.setIntensity(pitch / 90);
			m_led.setPattern(m_redIntensityPattern);
		} else if (pitch <= -intensityDegrees) {
			m_blueIntensityPattern.setIntensity(pitch / -90);
			m_led.setPattern(m_blueIntensityPattern);
		} else if (m_RobotContainer.isDisabled()) {
			m_led.setPattern(m_disabledPattern);
		} else if (m_RobotContainer.isRedAlliance()) {
			m_led.setPattern(m_redPattern);
		} else {
			m_led.setPattern(m_bluePattern);
		}

	}}
