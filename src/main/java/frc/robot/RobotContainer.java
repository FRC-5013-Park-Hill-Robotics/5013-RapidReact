// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import frc.robot.commands.Fetch;
import frc.robot.commands.GamepadDrive;
import frc.robot.commands.TurnToAngleCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeVision;
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
    private PowerDistribution m_PowerDistribution = new PowerDistribution(PCM_ID, ModuleType.kRev);
	private Turret m_turret = new Turret();
	private StatusLED m_StatusLED;// = new StatusLed(this);
	private ShooterVision m_shooterVision = new ShooterVision();
	private Conveyor m_conveyor = new Conveyor();
	private CargoShooter m_shooter = new CargoShooter(m_conveyor);
	private IntakeVision m_IntakeVision;// = new IntakeVision(this);
	private Intake m_intake = new Intake(m_conveyor);
	private Climber m_Climber;// = new Climber();
	
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
        // Configure the button bindings
        configureButtonBindings();

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
		//new Button(m_controller::getLeftBumper)
		//	.whileHeld(new Fetch(m_drivetrainSubsystem, m_IntakeVision,m_controller::getLeftX,m_controller::getLeftY,
		//		 m_controller::getRightTriggerAxis));
	
		new Button(m_controller::getBButton).whileHeld(new InstantCommand(m_shooter::fire)).whenReleased(new InstantCommand(m_shooter::stopFiring));
		new Button(m_controller::getYButton).whileHeld(new InstantCommand(m_conveyor::start));
		new Button(m_controller::getXButton).whileHeld(new InstantCommand(m_intake::start));//.whenReleased(new InstantCommand(m_intake::stop));
		new Button(m_controller::getDPadUp).whenPressed(new InstantCommand(() -> m_turret.up(10)));
		new Button(m_controller::getDPadDown).whenPressed(new InstantCommand(() -> m_turret.down(10)));
		new Button(m_controller::getDPadRight).whenPressed(new InstantCommand(() -> m_shooter.changeSpeed(100)));
		new Button(m_controller::getDPadLeft).whenPressed(new InstantCommand(() -> m_shooter.changeSpeed(-100)));

		}

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return AutonomousCommandFactory.createAutonomous(m_drivetrainSubsystem);
    }

    public boolean isRedAlliance() {
        return DriverStation.getAlliance() == Alliance.Red;
    }
	public DrivetrainSubsystem getdrivetrainSubsystem() {
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

	public StatusLED getStatusLED() {
		return m_StatusLED;
	}

	public void setStatusLED(StatusLED m_StatusLED) {
		this.m_StatusLED = m_StatusLED;
	}

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

	public IntakeVision getIntakeVision() {
		return m_IntakeVision;
	}

	public void setIntakeVision(IntakeVision m_IntakeVision) {
		this.m_IntakeVision = m_IntakeVision;
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
}
