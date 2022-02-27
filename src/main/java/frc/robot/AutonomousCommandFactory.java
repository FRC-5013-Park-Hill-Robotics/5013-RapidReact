package frc.robot;


import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DrivetrainConstants.DrivetrainGeometry;
import frc.robot.Constants.DrivetrainConstants.ThetaGains;
import frc.robot.Constants.DrivetrainConstants.TranslationGains;
import frc.robot.commands.AutonomousFire;
import frc.robot.commands.AutonomousTurnToTargetCommand;
import frc.robot.commands.PathPlannerSwerveControllerCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutonomousCommandFactory {

    public static PathPlannerSwerveControllerCommand createSwerveControllerCommand(PathPlannerTrajectory trajectory,
            DrivetrainSubsystem drivetrain) {
        Constraints constraints = new TrapezoidProfile.Constraints(DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
        2*DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);
        ProfiledPIDController thetaController = new ProfiledPIDController(ThetaGains.kP, ThetaGains.kI, ThetaGains.kD,
                constraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        PathPlannerSwerveControllerCommand swerveControllerCommand = new PathPlannerSwerveControllerCommand(trajectory, drivetrain::getPose,
                drivetrain.getKinematics(),
                new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD),
                new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD), thetaController,
                drivetrain::setDesiredStates, drivetrain);
        return swerveControllerCommand;
    }

    public static Command createAutonomous( RobotContainer container){
		
		return create2BallRightSide(container);
    }

	public static Command create2BallRightSide(RobotContainer container){
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		ParallelCommandGroup startup = new ParallelCommandGroup(
					new InstantCommand(container.getintake()::dropIntake),
					new InstantCommand(container.getshooter()::spinUp),
					new AutonomousTurnToTargetCommand(drivetrain, container.getshooterVision(),container.getshooter(), container.getturret())
		);

        // Create a voltage constraint to ensure we don't accelerate too fast
        PathPlannerTrajectory trajectory =  PathPlanner.loadPath("Right 2 ball", DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND, DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		drivetrain.resetPosition(trajectory.getInitialPose(), trajectory.getInitialState().holonomicRotation); 
        Command pathCommand = createSwerveControllerCommand(trajectory, drivetrain);

		return  new SequentialCommandGroup(
					startup,
					new AutonomousFire(container.getshooter(), container.getconveyor()),
					pathCommand,
					new AutonomousTurnToTargetCommand(drivetrain, container.getshooterVision(),container.getshooter(), container.getturret())	,
					new AutonomousFire(container.getshooter(), container.getconveyor())
		);
	}
}
