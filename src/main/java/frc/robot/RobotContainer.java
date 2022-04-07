// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.ConveyorDefaultCommand;
import frc.robot.commands.FenderShot;
import frc.robot.commands.Fire;
import frc.robot.commands.GamepadDrive;
import frc.robot.commands.TeleopTurnToTargetCommand;
import frc.robot.commands.autoClimb.PreClimbCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Intake;

import frc.robot.subsystems.CargoShooter;
import frc.robot.subsystems.ShooterVision;
import frc.robot.subsystems.StatusLED;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
	private final LogitechController m_controller = new LogitechController(ControllerConstants.DRIVER_CONTROLLER_PORT);
	private final LogitechController m_operator_controller = new LogitechController(
			ControllerConstants.OPERATOR_CONTROLLER_PORT);
	//private final LogitechController m_programmer_controller = new LogitechController(
	//		ControllerConstants.PROGRAMMER_CONTROLLER_PORT);

	private PowerDistribution m_PowerDistribution = new PowerDistribution(PCM_ID, ModuleType.kRev);
	private PneumaticsControlModule m_pneumaticsHub = new PneumaticsControlModule(PNEUMATICS_HUB);
	private Turret m_turret = new Turret();
	//private StatusLED m_StatusLED = new StatusLED(this);
	private ShooterVision m_shooterVision = new ShooterVision();
	private Conveyor m_conveyor = new Conveyor(this);
	private CargoShooter m_shooter = new CargoShooter(m_conveyor);
	
	private Intake m_intake = new Intake(m_conveyor, this);
	private Climber m_Climber = new Climber();

	/**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Set up the default command for the drivetrain.
        // The controls are for field-oriented driving:
        // Left stick Y axis -> forward and backwards movement
        // Left stick X axis -> left and right movement
        // Right stick X axis -> rotation
        m_drivetrainSubsystem.setDefaultCommand(new GamepadDrive(m_drivetrainSubsystem, m_controller));
		m_conveyor.setDefaultCommand(new ConveyorDefaultCommand(m_conveyor, m_intake));
		m_Climber.setDefaultCommand(new ClimberCommand(m_Climber, m_operator_controller));
		// Configure the button bindings
        configureButtonBindings();
		m_pneumaticsHub.enableCompressorDigital();
	
		SmartDashboard.putStringArray("Auto List",AutonomousCommandFactory.AUTOS );

		LiveWindow.disableAllTelemetry();
    }

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a {@link GenericHID} or one of its subclasses
	 * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
	 * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		// Back button zeros the gyroscope
		new Button(m_controller::getBackButton)
				// No requirements because we don't need to interrupt anything
				.whenPressed(new InstantCommand(m_drivetrainSubsystem::zeroGyroscope));

		new Button(m_controller::getBButton).whileHeld(new Fire(m_shooter, m_conveyor))
				.whenReleased(new InstantCommand(m_shooter::stopFiring));
		new Button(m_controller::getYButton).whileHeld(new InstantCommand(m_conveyor::start));
		new Button(m_controller::getRightBumper).whileHeld(new InstantCommand(m_intake::dropIntake))
				.whenReleased(new InstantCommand(m_intake::raiseIntake));
		new Button(m_controller::getXButton).whileHeld(new FenderShot(m_shooter, m_turret));
		new Button(m_controller::getLeftTriggerButton)
				.whileHeld(new TeleopTurnToTargetCommand(m_drivetrainSubsystem, m_shooterVision, m_shooter,
						m_turret, m_conveyor::isAllianceBallNext, m_controller::getLeftY, m_controller::getLeftY,
						m_controller::getRightTriggerAxis));
		new Button(m_controller::getLeftBumper).whenPressed(new Fire(m_shooter, m_conveyor))
				.whenReleased(m_shooter::stopFiring);

		new Button(m_controller::getDPadUp).whenPressed(new InstantCommand(() -> m_turret.up(10)));
		new Button(m_controller::getDPadDown).whenPressed(new InstantCommand(() -> m_turret.down(10)));
		new Button(m_controller::getDPadRight).whenPressed(new InstantCommand(() -> m_shooter.changeSpeed(100)));
		new Button(m_controller::getDPadLeft).whenPressed(new InstantCommand(() -> m_shooter.changeSpeed(-100)));
		// Operator Control

		new Button(m_operator_controller::getLeftBumper)
				.whenPressed(new InstantCommand(() -> m_conveyor.reverse(), m_conveyor));
		new Button(m_operator_controller::getRightBumper)
				.whenPressed(new InstantCommand(() -> m_conveyor.start(), m_conveyor));
		
		//new Button(m_operator_controller::getXButton).whenHeld(new AutoClimber(m_Climber, m_drivetrainSubsystem.getPitchR2d()::getDegrees));
		//new Button(m_operator_controller::getBButton).whenPressed(new PreClimbCommand(this));
		// programmer controls
		/*	new Button(m_programmer_controller::getBButton).whileHeld(new InstantCommand(m_shooter::fire))
				.whenReleased(new InstantCommand(m_shooter::stopFiring));
		new Button(m_programmer_controller::getYButton).whileHeld(new InstantCommand(m_conveyor::start));
		new Button(m_programmer_controller::getXButton).whileHeld(new InstantCommand(m_intake::start));// .whenReleased(new
																									// InstantCommand(m_intake::stop));
		new Button(m_programmer_controller::getDPadRight)
				.whenPressed(new InstantCommand(() -> m_shooter.changeSpeed(100)));
		new Button(m_programmer_controller::getDPadLeft)
				.whenPressed(new InstantCommand(() -> m_shooter.changeSpeed(-100)));
		new Button(m_programmer_controller::getLeftBumper).whileHeld(new InstantCommand(m_intake::dropIntake))
				.whenReleased(new InstantCommand(m_intake::raiseIntake));*/
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		String autoName = SmartDashboard.getString("Auto Selector", AutonomousCommandFactory.FAR_RIGHT);
		return AutonomousCommandFactory.createAutonomous(this,autoName);
	}

	public boolean isRedAlliance() {
		return DriverStation.getAlliance() == Alliance.Red;
	}

	public boolean isDisabled() {
		return DriverStation.isDisabled();
	}

	public boolean isAutonomous() {
		return DriverStation.isAutonomous();
	}

	public boolean isTeleop() {
		return DriverStation.isTeleop();
	}

	public DrivetrainSubsystem getDrivetrainSubsystem() {
		return m_drivetrainSubsystem;
	}

	public LogitechController getcontroller() {
		return m_controller;
	}

	public PowerDistribution getPowerDistribution() {
		return m_PowerDistribution;
	}

	public void setPowerDistribution(PowerDistribution m_PowerDistribution) {
		this.m_PowerDistribution = m_PowerDistribution;
	}

	public Turret getturret() {
		return m_turret;
	}

	public void setturret(Turret m_turret) {
		this.m_turret = m_turret;
	}
/*
	public StatusLED getStatusLED() {
		return m_StatusLED;
	}

	public void setStatusLED(StatusLED m_StatusLED) {
		this.m_StatusLED = m_StatusLED;
	}
*/
	public ShooterVision getshooterVision() {
		return m_shooterVision;
	}

	public void setshooterVision(ShooterVision m_shooterVision) {
		this.m_shooterVision = m_shooterVision;
	}

	public CargoShooter getshooter() {
		return m_shooter;
	}

	public void setshooter(CargoShooter m_shooter) {
		this.m_shooter = m_shooter;
	}

	public Conveyor getconveyor() {
		return m_conveyor;
	}

	public void setconveyor(Conveyor m_conveyor) {
		this.m_conveyor = m_conveyor;
	}

	public Intake getintake() {
		return m_intake;
	}

	public void setintake(Intake m_intake) {
		this.m_intake = m_intake;
	}

	public Climber getClimber() {
		return m_Climber;
	}

	public void setClimber(Climber m_Climber) {
		this.m_Climber = m_Climber;
	}

	public PneumaticsControlModule getPneumaticsHub() {
		return this.m_pneumaticsHub;
	}
}
