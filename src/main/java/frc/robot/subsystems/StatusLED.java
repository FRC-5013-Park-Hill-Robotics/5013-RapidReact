// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.trobot5013lib.led.ChasePattern;
import frc.robot.trobot5013lib.led.RainbowPattern;
import frc.robot.trobot5013lib.led.SolidColorPattern;
import frc.robot.trobot5013lib.led.TrobotAddressableLED;
import frc.robot.trobot5013lib.led.TrobotAddressableLEDPattern;

public class StatusLED extends SubsystemBase {
	private TrobotAddressableLED m_led = new TrobotAddressableLED(Constants.STATUS_LED_PWM_PORT,60);
	private RobotContainer m_RobotContainer;
	private TrobotAddressableLEDPattern m_bluePattern = new SolidColorPattern(Color.kBlue);
	private TrobotAddressableLEDPattern m_redPattern = new SolidColorPattern(Color.kRed);
	//private TrobotAddressableLEDPattern m_disabledPattern = new ChasePattern(new Color[]{Color.kRed,Color.kWhite},3);
	private TrobotAddressableLEDPattern m_disabledPattern = new RainbowPattern();

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

		// Until we have interesting statuses to code set color to alliance
		if (m_RobotContainer.isDisabled()){
			m_led.setPattern(m_disabledPattern);
		} else if (m_RobotContainer.isRedAlliance()) {
			m_led.setPattern(m_redPattern);
		} else {
			m_led.setPattern(m_bluePattern);
		}
	}


}
