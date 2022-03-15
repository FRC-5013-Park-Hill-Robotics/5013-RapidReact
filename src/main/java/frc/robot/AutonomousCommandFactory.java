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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.DrivetrainConstants.DrivetrainGeometry;
import frc.robot.Constants.DrivetrainConstants.ThetaGains;
import frc.robot.Constants.DrivetrainConstants.TranslationGains;
import frc.robot.commands.AutonomousFire;
import frc.robot.commands.AutonomousTurnToTargetCommand;
import frc.robot.commands.ConveyorDefaultCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutonomousCommandFactory {
	public static String FAR_RIGHT = "Far Right";
	public static String NEAR_RIGHT = "Near Right";
	public static String LEFT_2 = "Left 2";
	public static final String[] AUTOS = { FAR_RIGHT, NEAR_RIGHT, LEFT_2 };

	public static Command createStartupCommand(RobotContainer container, PathPlannerTrajectory trajectory) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();
		return new ParallelCommandGroup(
				new InstantCommand(() -> drivetrain.setInitialPosition(trajectory.getInitialPose(),
					trajectory.getInitialState().holonomicRotation)),
				new InstantCommand(container.getintake()::dropIntake),
				new InstantCommand(container.getshooter()::spinUp)
			);
	}
	
	public static PPSwerveControllerCommand createSwerveControllerCommand(PathPlannerTrajectory trajectory,
			DrivetrainSubsystem drivetrain) {
		Constraints constraints = new TrapezoidProfile.Constraints(
				DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
				2 * DrivetrainGeometry.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);
		ProfiledPIDController thetaController = new ProfiledPIDController(ThetaGains.kP, ThetaGains.kI, ThetaGains.kD,
				constraints);
		thetaController.enableContinuousInput(-Math.PI, Math.PI);
		PPSwerveControllerCommand swerveControllerCommand = new PPSwerveControllerCommand(trajectory,
				drivetrain::getPose,
				drivetrain.getKinematics(),
				new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD),
				new PIDController(TranslationGains.kP, TranslationGains.kI, TranslationGains.kD), thetaController,
				drivetrain::setDesiredStates, drivetrain);
		return swerveControllerCommand;
	}

	public static Command createTurnAndShoot(RobotContainer container) {
		return new SequentialCommandGroup(
				new AutonomousTurnToTargetCommand(container.getDrivetrainSubsystem(),
						container.getshooterVision(),
						container.getshooter(),
						container.getturret(),
						container.getconveyor()::isAllianceBallNext).withTimeout(2),
				new AutonomousFire(container.getshooter(), container.getconveyor()));
	}

	public static Command createAutonomous(RobotContainer container) {
		return createFarRight(container);
	}

	public static Command createAutonomous(RobotContainer container, String name) {
		if (FAR_RIGHT.equals(name)) {
			return createFarRight(container);
		} else if (NEAR_RIGHT.equals(name)) {
			return createNearRight(container);
		} else if (LEFT_2.equals(name)) {
			return createLeftSide2(container);
		}
		return createFarRight(container);
	}

	public static Command createFarRight(RobotContainer container) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		PathPlannerTrajectory leg1Trajectory = PathPlanner.loadPath("Far Right",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);

		Command leg1 = createSwerveControllerCommand(leg1Trajectory, drivetrain);
		Command startup = createStartupCommand(container, leg1Trajectory);

		return new SequentialCommandGroup(
				startup,
				leg1,
				createTurnAndShoot(container));
	}
	public static Command createNearRight(RobotContainer container) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		PathPlannerTrajectory leg1Trajectory = PathPlanner.loadPath("Near Right",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);

		Command leg1 = createSwerveControllerCommand(leg1Trajectory, drivetrain);
		Command startup = createStartupCommand(container, leg1Trajectory);

		return new SequentialCommandGroup(
				startup,
				leg1,
				createTurnAndShoot(container));
	}

	public static Command createLeftSide2(RobotContainer container) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		PathPlannerTrajectory leg1Trajectory = PathPlanner.loadPath("Left",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);

		Command leg1 = createSwerveControllerCommand(leg1Trajectory, drivetrain);
		Command startup = createStartupCommand(container, leg1Trajectory);

		return new SequentialCommandGroup(
				startup,
				leg1,
				createTurnAndShoot(container));
	}

	public static Command createRightSide5(RobotContainer container) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		PathPlannerTrajectory leg1Trajectory = PathPlanner.loadPath("Right Leg1",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		Command leg1 = createSwerveControllerCommand(leg1Trajectory, drivetrain);

		Command startup = createStartupCommand(container, leg1Trajectory);

		PathPlannerTrajectory leg2Trajectory = PathPlanner.loadPath("Right Leg2",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		Command leg2 = createSwerveControllerCommand(leg2Trajectory, drivetrain);

		PathPlannerTrajectory leg3Trajectory = PathPlanner.loadPath("Right Leg3",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		Command leg3 = createSwerveControllerCommand(leg3Trajectory, drivetrain);
		return new SequentialCommandGroup(
				startup,
				new AutonomousFire(container.getshooter(), container.getconveyor()),
				leg1,
				new InstantCommand(container.getintake()::raiseIntake),
				createTurnAndShoot(container),
				new InstantCommand(container.getintake()::dropIntake),
				leg2,
				new WaitCommand(1),
				new InstantCommand(container.getintake()::raiseIntake),
				leg3,
				createTurnAndShoot(container));
	}
}
