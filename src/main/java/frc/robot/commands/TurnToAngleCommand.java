package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

import static frc.robot.Constants.DrivetrainConstants.ThetaGains.*;

public class TurnToAngleCommand extends PIDCommand {
    public TurnToAngleCommand(DrivetrainSubsystem driveTrain, double angleRadians) {
        super(
                // The controller that the command will use
                new PIDController(kP, kI, kD), driveTrain::getHeadingRadians, angleRadians,
                // Pipe the output to the turning controls
                output -> driveTrain
                        .drive(ChassisSpeeds.fromFieldRelativeSpeeds(0, 0, output, driveTrain.getYawR2d())));
        getController().setTolerance(kTurnToleranceRad,kTurnRateToleranceRadPerS);
		getController().enableContinuousInput(0, 2 * Math.PI);
    }
 @Override
 public boolean isFinished(){
     return getController().atSetpoint() ;
 }
   

}
