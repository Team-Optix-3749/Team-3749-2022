package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.elevator.*;
import frc.robot.subsystems.*;
import frc.robot.utilities.AutoGroups;
import frc.robot.utilities.Xbox;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    private final Drivetrain m_drivetrain = new Drivetrain();

    private final Shooter m_shooter = new Shooter();

    private final Intake m_intake = new Intake();

    private final Elevator m_elevator = new Elevator();

    Xbox Pilot;
    Xbox Operator;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        Pilot = new Xbox(0);
        Operator = new Xbox(1);

        Pilot.x().whenPressed(new InstantCommand(m_intake::startCompressor))
                .whenReleased(new InstantCommand(m_intake::stopCompressor));

        Operator.y().whenPressed(new Tilt(m_elevator))
                .whenReleased(new StopTilt(m_elevator));

        Operator.b().whenPressed(new Untilt(m_elevator))
                .whenReleased(new InstantCommand(m_elevator::stopTilt));

        Pilot.a().whenPressed(new VisionAlign(m_shooter))
                .whenReleased(new InstantCommand(m_shooter::stopTurret));

        // Pilot.povUp().whenPressed(new Extend(m_elevator))
        // .whenReleased(new InstantCommand(m_elevator::stopClimb));

        // Pilot.povDown().whenPressed(new Lift(m_elevator))
        // .whenReleased(new InstantCommand(m_elevator::stopClimb));

        // Pilot.povLeft().whenPressed(new Untilt(m_elevator))
        // .whenReleased(new InstantCommand(m_elevator::stopTilt));

        // Pilot.povRight().whenPressed(new Tilt(m_elevator))
        // .whenReleased(new InstantCommand(m_elevator::stopTilt));

        m_drivetrain.setDefaultCommand(
                new ArcadeDrive(m_drivetrain, Pilot::getLeftY, Pilot::getRightX));

        m_shooter.setDefaultCommand(
                new Shoot(m_shooter, Operator::getRightTrigger));

        m_intake.setDefaultCommand(
            new Input(m_intake, Pilot::getLeftTrigger, Operator.a()));


        // m_base.setDefaultCommand(
        // new Controls(m_base, Pilot, Operator));

        // m_elevator.setDefaultCommand(new Tilt(m_elevator));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        AutoGroups autoGrp = new AutoGroups(m_drivetrain, m_intake, m_shooter);

        return autoGrp.getOneBlue();
    }
}
