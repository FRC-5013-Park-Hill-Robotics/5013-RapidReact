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
	public static String RIGHT_3 = "Right Side 3";
	public static String RIGHT_5 = "Right Side 5";
	public static String LEFT_2 = "Left 2";
	public static final String[] AUTOS = { RIGHT_3, RIGHT_5, LEFT_2 };

	public static Command createStartupCommand(RobotContainer container, PathPlannerTrajectory trajectory) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();
		return new ParallelCommandGroup(
				new InstantCommand(() -> drivetrain.setInitialPosition(trajectory.getInitialPose(),
					trajectory.getInitialState().holonomicRotation)),
				new InstantCommand(container.getintake()::dropIntake),
				new InstantCommand(container.getshooter()::spinUp),
				new AutonomousTurnToTargetCommand(drivetrain, container.getshooterVision(), container.getshooter(),
						container.getturret(), container.getconveyor()::isAllianceBallNext)).withTimeout(2);
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
						container.getconveyor()::isAllianceBallNext),
				new AutonomousFire(container.getshooter(), container.getconveyor())).withTimeout(2);
	}

	public static Command createAutonomous(RobotContainer container) {
		return createRightSide3(container);
	}

	public static Command createAutonomous(RobotContainer container, String name) {
		if (RIGHT_3.equals(name)) {
			return createRightSide3(container);
		} else if (RIGHT_5.equals(name)) {
			return createRightSide5(container);
		} else if (LEFT_2.equals(name)) {
			return createLeftSide2(container);
		}
		return createRightSide3(container);
	}

	public static Command createRightSide3(RobotContainer container) {
		DrivetrainSubsystem drivetrain = container.getDrivetrainSubsystem();

		PathPlannerTrajectory leg1Trajectory = PathPlanner.loadPath("Right Leg1",
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
				DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);

		Command leg1 = createSwerveControllerCommand(leg1Trajectory, drivetrain);

		PathPlannerTrajectory leg1bTrajectory = PathPlanner.loadPath("Right Leg1b",
		DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
		DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		Command leg1b = createSwerveControllerCommand(leg1bTrajectory, drivetrain);

		PathPlannerTrajectory leg2Trajectory = PathPlanner.loadPath("Right Leg2a",
		DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND,
		DrivetrainGeometry.MAX_VELOCITY_METERS_PER_SECOND / .33);
		Command leg2 = createSwerveControllerCommand(leg2Trajectory, drivetrain);

		Command startup = createStartupCommand(container, leg1Trajectory);

		return new SequentialCommandGroup(
				startup,
				new AutonomousFire(container.getshooter(), container.getconveyor()),
				new ParallelCommandGroup(new ConveyorDefaultCommand(container.getconveyor(),container.getintake()),
					new SequentialCommandGroup(
						leg1,
						new WaitCommand(0.5),
						leg1b,
						new WaitCommand(0.5))),
				//new InstantCommand(container.getintake()::raiseIntake),
				createTurnAndShoot(container),
				leg2);
			
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
