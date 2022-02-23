// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trobot5013lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

/** Add your docs here. */
public class IntensityPattern  implements TrobotAddressableLEDPattern {
	private Color m_color;
	private double m_intensity;

	/**
	 * 
	 * @param aColor Brightest color
	 * @param intensity 0..1 with 1 being the color and 0 being black 
	 */
	public IntensityPattern(Color aColor, double intensity){
		super();
		this.m_color = aColor;
		this.m_intensity = intensity;
	}

	@Override
	public void setLEDs(AddressableLEDBuffer buffer) {
		
		for (int index = 0; index < buffer.getLength(); index++){
			buffer.setLED(index, new Color(m_intensity * m_color.red,m_intensity * m_color.green, m_intensity * m_color.blue));
		}
		
	}
}
