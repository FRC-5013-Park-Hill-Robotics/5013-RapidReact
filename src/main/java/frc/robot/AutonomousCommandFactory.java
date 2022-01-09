package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.constraint.SwerveDriveKinematicsConstraint;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.DrivetrainConstants.DrivetrainGeometry;
import frc.robot.Constants.DrivetrainConstants.ThetaGains;
import frc.robot.Constants.DrivetrainConstants.TranslationGains;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutonomousCommandFactory {

    public static SwerveControllerCommand createSwerveControllerCommand(Trajectory trajectory,
            DrivetrainSubsystem drivetrain) {
        Constraints constraints = new TrapezoidProfile.Constraints(DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
        2*DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);
        ProfiledPIDController thetaController = new ProfiledPIDController(ThetaGains.kP, ThetaGains.kI, ThetaGains.kD,
                constraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(trajectory, drivetrain::getPose,
                drivetrain.getKinematics(),
                new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD),
                new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD), thetaController,
                drivetrain::setDesiredStates, drivetrain);
        return swerveControllerCommand;
    }

    public static SwerveControllerCommand createAutonomous( DrivetrainSubsystem drivetrain){
        // Create a voltage constraint to ensure we don't accelerate too fast
        SwerveDriveKinematicsConstraint autoVoltageConstraint = new SwerveDriveKinematicsConstraint( drivetrain.getKinematics(),DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND );
        // Create config for trajectory
        TrajectoryConfig config =
            new TrajectoryConfig(
                DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND ,
                DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(drivetrain.getKinematics())
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

        Trajectory t = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(.66, .5), new Translation2d(1.33, -.5)),
            // End 3 meters straight ahead of where we started, facing Backward
            new Pose2d(2, 0, new Rotation2d(Math.PI)),
            // Pass config
            config);
            
        return createSwerveControllerCommand(t, drivetrain);
    }
}
