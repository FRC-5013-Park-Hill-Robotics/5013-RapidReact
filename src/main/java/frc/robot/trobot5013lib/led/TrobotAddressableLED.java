// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trobot5013lib.led;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/** Add your docs here. */
public class TrobotAddressableLED {

	private AddressableLED m_LED;
	private AddressableLEDBuffer m_buffer;
	private TrobotAddressableLEDPattern m_pattern;

	public TrobotAddressableLED(int pwmPort, int length){
		super();
		m_LED = new AddressableLED(pwmPort);
		m_buffer = new AddressableLEDBuffer(length);
		m_LED.setLength(length);
		m_LED.setData(m_buffer);
		m_LED.start();
	}

	public AddressableLED getLED() {
		return m_LED;
	}

	public void setLED(AddressableLED led) {
		this.m_LED = led;
	}

	public AddressableLEDBuffer getBuffer() {
		return m_buffer;
	}

	public void setBuffer(AddressableLEDBuffer buffer) {
		this.m_buffer = buffer;
	}

	public void setPattern(TrobotAddressableLEDPattern pattern){
		m_pattern = pattern;
		update();
	}

	//Calls setLEd and setData agaim.  allows for
	// animated patterns if called repeatedly without setting pattern again
	public void update(){
		m_pattern.setLEDs(getBuffer());
		getLED().setData(getBuffer());
	}
}
