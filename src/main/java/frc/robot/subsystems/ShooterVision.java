// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ShooterVisionConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ShooterVision extends SubsystemBase {
  /** Creates a new ShooterVision. */
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");
  private NetworkTableEntry tv = table.getEntry("tv");
  private NetworkTableEntry ledMode = table.getEntry("ledMode");
  private boolean m_targeting = false;

  public ShooterVision() {
    /**
     * tx - Horizontal Offset
     * ty - Vertical Offset 
     * ta - Area of target 
     * tv - Target Visible
     */

    
    this.tx = table.getEntry("tx");
    this.ty = table.getEntry("ty");
    this.ta = table.getEntry("ta");
    this.tv = table.getEntry("tv");
    this.ledMode = table.getEntry("ledMode");
    setPipeline(ShooterVisionConstants.DRIVE_PIPELINE);
    setLedOn(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

     //read values periodically
    double x = this.tx.getDouble(0.0);
    double y = this.ty.getDouble(0.0);
    double area = this.ta.getDouble(0.0);


    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);

  }

    /**Returns distance to target in inches */
  public double distanceToTargetinMeter(){
    double cameraAngle = ShooterVisionConstants.CAMERA_ANGLE; 
    double angleToTarget = this.tx.getDouble(0.0);
    double camHeight = ShooterVisionConstants.CAMERA_HEIGHT;
    double targetHeight = ShooterVisionConstants.TARGET_HEIGHT;
    double distance =  ((targetHeight-camHeight) / Math.tan(cameraAngle+angleToTarget));
    
    return distance;
    
  }

  /** Returns if limelight can see defined retroreflective target */
  public boolean hasTarget(){
   // this.table.getEntry("ledMode").setNumber(3);
    SmartDashboard.putNumber("tv; ", tv.getDouble(0));
    if(tv.getDouble(0) != 0)
      return true;
    

    return false;
  }
 public NetworkTableEntry getTx() {
    return tx;
  }

  public NetworkTableEntry getTy() {
    return ty;
  }

  public NetworkTableEntry getTa() {
    return ta;
  }

  public void setPipeline(int pipeline){
    this.table.getEntry("pipeline").setNumber(pipeline);
  }

  public void setLedOn(boolean isOn) {
    if (isOn){
      ledMode.setNumber(ShooterVisionConstants.LED_ON);
    } else {
      ledMode.setNumber(ShooterVisionConstants.LED_OFF);
    }
  }
  
  /**Returns the angle to targed in degrees negative values to the left and positive to the right
   * used for turn to target
   */
  public double getAngleOfError(){
    //+1 is a fudge factor cor camera mounting
    return getTx().getDouble(0.0) + 1.5;
  }

  public void switchPipeline(boolean targeting){
    if(targeting == true){
      setPipeline(ShooterVisionConstants.TARGET_PIPELINE);
    } else {
      setPipeline(ShooterVisionConstants.DRIVE_PIPELINE);
    }
  }
  
  public boolean isTargeting(){
    return m_targeting;
  }

  public boolean isOutOfRange(){
    return (getTy().getDouble(0) > ShooterVisionConstants.RANGE_TOO_CLOSE || getTy().getDouble(0) < ShooterVisionConstants.RANGE_TOO_FAR);
  }

  public boolean isPrimeRange(){
    return (getTy().getDouble(0) > ShooterVisionConstants.RANGE_PRIME_END && getTy().getDouble(0) < ShooterVisionConstants.RANGE_PRIME_START);
  }
}
