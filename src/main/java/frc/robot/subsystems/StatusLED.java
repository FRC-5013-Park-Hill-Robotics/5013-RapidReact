// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BlinkenConstants;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class StatusLED extends SubsystemBase {
  private PWM m_pwm = new PWM(Constants.STATUS_LED_PWM_PORT);
  private RobotContainer m_RobotContainer;

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
    if (m_RobotContainer.isRedAlliance()) {
      setColorPattern(BlinkenConstants.RED_BRIGHT);
    } else {
      setColorPattern(BlinkenConstants.BLUE);
    }
  }

  public void setColorPattern(double color) {
    m_pwm.setSpeed(color);
  }
}
