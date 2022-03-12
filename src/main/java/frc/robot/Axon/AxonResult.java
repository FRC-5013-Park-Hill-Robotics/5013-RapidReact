// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Axon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class AxonResult {
	private List<Detection> detections;
	private boolean coral;
	private int resolutionWidth;
	private int resolutioHeight;
	private double timestamp;

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
	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	public boolean hasDetection(){
		return getDetections() != null && getDetections().size() > 0;
	}

	public List<Detection> getDetectionsByLabel(String label){
		List<Detection> result = null;
		List<Detection> detections = getDetections();
		if (label == null){
			result = detections;
		} else if (getDetections() != null){
			result = new ArrayList<>();
			for (Detection detection : detections) {
				if (detection.getLabel().equals(label)){
					result.add(detection);
				}
			}
		
		}
		return result;
	}

	public Detection getClosest(String label){
		Detection result = null;
		List<Detection> detections = getDetectionsByLabel(label);
		SmartDashboard.putNumber("AxonDetections", detections.size());
		if (detections != null && !detections.isEmpty()){
			detections.sort(null);
			result = detections.get(0);
		}
		return result;
	}

	public double getXAngleDegrees(Box target, double cameraFieldOfViewXAngle){
		double pixelsPerDegree = getResolutionWidth()/cameraFieldOfViewXAngle;
		SmartDashboard.putNumber("pixelsPerDegree", pixelsPerDegree);
		int horizontalOffsetPixels = target.getHorizontalCenter() - (getResolutionWidth()/2);
		SmartDashboard.putNumber("horizontalOffsetPixels", horizontalOffsetPixels);
		return horizontalOffsetPixels/pixelsPerDegree;
	}

	public double getYAngleDegrees(Box target, double cameraFieldOfViewYAngle){
		double pixelsPerDegree = getResolutioHeight()/cameraFieldOfViewYAngle;
		int verticalOffsetPixels = target.getVerticalCenter() - (getResolutioHeight()/2);
		return verticalOffsetPixels/pixelsPerDegree;
	}
}
