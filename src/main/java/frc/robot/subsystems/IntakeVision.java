// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import frc.robot.IntakeVisionConstants;
import frc.robot.Axon.AxonResult;
import frc.robot.Axon.AxonUtil;
import frc.robot.Axon.Detection;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class IntakeVision extends SubsystemBase {
	// change to match camera name//
	private PhotonCamera camera = new PhotonCamera("IntakeCamera");
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("Ml");
    private String label;

	/** Creates a new IntakeVision. */
	public IntakeVision() {
		super();
	}

	public AxonResult getResult() {
		System.out.println("Get Results");
		if (camera != null){
			System.out.println("Camera exists");
		}
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
		
	}

	public double getAngleOfError() {
		if (hasTarget()){
			return this.getResult().getXAngle(getTarget(), cameraFieldOfViewXAngle);
		}
		return 0;
	}

	@Override
	public void periodic() {
		SmartDashboard.putString("Test", "TEst");
		SmartDashboard.putNumber("Angle of Error", getAngleOfError());
		SmartDashboard.putBoolean("Has Target", hasTarget());
	}
}
