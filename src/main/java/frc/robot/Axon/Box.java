// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Axon;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Add your docs here. */
public class Box {
	@JsonProperty
	private int ymin;
	@JsonProperty
	private int xmin;
	@JsonProperty
	private int ymax;
	@JsonProperty
	private int xmax;
	public Box() {
	}
	public int getYmin() {
		return ymin;
	}
	public void setYmin(int ymin) {
		this.ymin = ymin;
	}
	public int getXmin() {
		return xmin;
	}
	public void setXmin(int xmin) {
		this.xmin = xmin;
	}
	public double getYmax() {
		return ymax;
	}
	public void setYmax(int ymax) {
		this.ymax = ymax;
	}
	public int getXmax() {
		return xmax;
	}
	public void setXmax(int xmax) {
		this.xmax = xmax;
	}
	public int getHorizontalCenter(){
		return xmin + (xmax - xmin)/2;
	}
	public int getVerticalCenter(){
		return ymin + (ymax - ymin)/2;
	}
}