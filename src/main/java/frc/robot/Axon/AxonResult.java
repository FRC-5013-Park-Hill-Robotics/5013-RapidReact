// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Axon;

import java.util.Arrays;
import java.util.List;

/** Add your docs here. */
public class AxonResult {
	List<Detection> detections;
	boolean coral;
	int resolutionWidth;
	int resolutioHeight;
	
	public AxonResult() {
	}

	public List<Detection> getDetections() {
		return detections;
	}
	public void setDetections(Detection[] detections) {
		this.detections = Arrays.asList(detections);
	}
	public void setDetections( List<Detection> detections) {
		this.detections = detections;
	}
	public boolean isCoral() {
		return coral;
	}
	public void setCoral(boolean coral) {
		this.coral = coral;
	}
	public int getResolutionWidth() {
		return resolutionWidth;
	}
	public void setResolutionWidth(int resolutionWidth) {
		this.resolutionWidth = resolutionWidth;
	}
	public int getResolutioHeight() {
		return resolutioHeight;
	}
	public void setResolutioHeight(int resolutioHeight) {
		this.resolutioHeight = resolutioHeight;
	}

	public boolean hasDetection(){
		return getDetections() != null && getDetections().size() > 0;
	}

	public Detection[] getDetectionsByLabel(String label){
		Detection[] result = null;
		if (hasDetection()){
			getDetections().stream().filter(detection -> detection.getLabel().equals(label));
		}
		return result;
	}
}
