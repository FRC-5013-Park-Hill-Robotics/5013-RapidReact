package frc.robot;


import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

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
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutonomousCommandFactory {

    public static PPSwerveControllerCommand createSwerveControllerCommand(PathPlannerTrajectory trajectory,
            DrivetrainSubsystem drivetrain) {
        Constraints constraints = new TrapezoidProfile.Constraints(DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
        2*DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);
        ProfiledPIDController thetaController = new ProfiledPIDController(ThetaGains.kP, ThetaGains.kI, ThetaGains.kD,
                constraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        PPSwerveControllerCommand swerveControllerCommand = new PPSwerveControllerCommand(trajectory, drivetrain::getPose,
                drivetrain.getKinematics(),
                new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD),
                new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD), thetaController,
                drivetrain::setDesiredStates, drivetrain);
        return swerveControllerCommand;
    }

	public static Command createTurnAndShoot(RobotContainer container){
		return new SequentialCommandGroup(
			new AutonomousTurnToTargetCommand(container.getDrivetrainSubsystem(),
				 container.getshooterVision(),
				 container.getshooter(),
				  container.getturret(),
				  container.getconveyor())	,
			new AutonomousFire(container.getshooter(), container.getconveyor())
		);
	}
    public static Command createAutonomous( RobotContainer container){
		return createRightSide(container);
    }

	public static Command createRightSide(RobotContainer container){
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		ParallelCommandGroup startup = new ParallelCommandGroup(
					new InstantCommand(container.getintake()::dropIntake),
					new InstantCommand(container.getshooter()::spinUp),
					new AutonomousTurnToTargetCommand(drivetrain, container.getshooterVision(),container.getshooter(), container.getturret(), container.getconveyor())
		);

        PathPlannerTrajectory leg1Trajectory =  PathPlanner.loadPath("Right Leg1", DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND, DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		drivetrain.resetPosition(leg1Trajectory.getInitialPose(), leg1Trajectory.getInitialState().holonomicRotation); 
        Command leg1 = createSwerveControllerCommand(leg1Trajectory, drivetrain);

		PathPlannerTrajectory leg2Trajectory =  PathPlanner.loadPath("Right Leg2", DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND, DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		Command leg2 = createSwerveControllerCommand(leg2Trajectory, drivetrain);

		return  new SequentialCommandGroup(
					startup,
					new AutonomousFire(container.getshooter(), container.getconveyor()),
					leg1,
					createTurnAndShoot(container),
					leg2,
					createTurnAndShoot(container)
		);
	}
}
