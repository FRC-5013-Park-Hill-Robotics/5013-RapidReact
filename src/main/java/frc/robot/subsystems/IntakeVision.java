// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import frc.robot.IntakeVisionConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeVision extends SubsystemBase {
  //change to match camera name//
  private PhotonCamera camera = new PhotonCamera("photonvision");

  /** Creates a new IntakeVision. */
  public IntakeVision() {}

  public PhotonPipelineResult getResult(){
    PhotonPipelineResult result = camera.getLatestResult();
    return result;
  }
  public void setPipeline(int pipeline){
    camera.setPipelineIndex(pipeline);
  }

  public boolean hasTarget(){
    boolean hasTarget = this.getResult().hasTargets();
    return hasTarget;
  }

  public PhotonTrackedTarget getTarget(){
    PhotonTrackedTarget target = this.getResult().getBestTarget();
    return target;
  }
  public void getDistanceFromTarget(){
    PhotonUtils.calculateDistanceToTargetMeters(IntakeVisionConstants.CAMERA_HEIGHT, IntakeVisionConstants.TARGET_HEIGHT, Math.toRadians(this.getTarget().getYaw()), Math.toRadians(this.getTarget().getPitch()));
  }
  public double getAngleOfError(){
    return this.getResult().getBestTarget().getYaw();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}


