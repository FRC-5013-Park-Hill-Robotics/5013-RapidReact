// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import static frc.robot.IntakeVisionConstants.*;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Axon.AxonResult;
import frc.robot.Axon.AxonUtil;
import frc.robot.Axon.Detection;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class IntakeVision extends SubsystemBase {
	// change to match camera name//
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("ML");
    private String label;
	private RobotContainer m_robotContainer;

	/** Creates a new IntakeVision. */
	public IntakeVision(RobotContainer container) {
		super();
		m_robotContainer = container;
		setLabel(container.isRedAlliance()?RED_CARGO:RED_CARGO);
	}

	public AxonResult getResult() {
		AxonResult result = AxonUtil.getAxonResult(table);
		return result;
	}

    public void setLabel(String l){
        label=l;
    }
	
	public boolean hasTarget() {
		boolean hasTarget = this.getResult().hasDetection();
		return hasTarget;
	}

	public Detection getTarget() {
		Detection target = this.getResult().getClosest(label);
		return target;
	}

	public double getDistanceFromTarget() {
		return 0;
	}

	public double getAngleOfError() {
		if (hasTarget()){
			return this.getResult().getXAngle(getTarget().getBox(), CAMERA_FIELD_OF_VIEW_HORIZONTAL_DEGREES);
		}
		return 0;
	}

	public double getTargetAngle(){
	}

	@Override
	public void periodic() {
		SmartDashboard.putString("Test", "TEst");
		SmartDashboard.putNumber("Angle of Error", getAngleOfError());
		SmartDashboard.putBoolean("Has Target", hasTarget());
	}
}
