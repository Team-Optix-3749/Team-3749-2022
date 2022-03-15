package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.elevator.*;
import frc.robot.subsystems.*;
import frc.robot.utilities.AutoGroups;
import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;
import edu.wpi.first.wpilibj.GenericHID;
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

    // private final Base m_balls = new Base();

    Xbox Pilot;
    Xbox Operator;
    POV PilotPOV;
    POV OpPOV;

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

        PilotPOV = new POV(new GenericHID(0));
        OpPOV = new POV(new GenericHID(1));

        Pilot.x().whenPressed(new InstantCommand(m_intake::startCompressor))
                .whenReleased(new InstantCommand(m_intake::stopCompressor));

        Pilot.y().whenPressed(new Extend(m_elevator))
                .whenReleased(new StopClimb(m_elevator));

        Pilot.b().whenPressed(new Lift(m_elevator))
                .whenReleased(new StopClimb(m_elevator));

        // Pilot.x().whenPressed(new Untilt(m_elevator))
        //     .whenReleased(new StopTilt(m_elevator));
        
        Pilot.a().whenPressed(new VisionAlign(m_shooter))
            .whenReleased(new InstantCommand(m_shooter::stopTurret));

        // Pilot.a().whenPressed(new VisionAlign(m_shooter))
        //         .whenReleased(new InstantCommand(m_shooter::stopTurret));

        PilotPOV.up().whenPressed(new Extend(m_elevator))
            .whenReleased(new StopClimb(m_elevator));

        PilotPOV.down().whenPressed(new Lift(m_elevator))
            .whenReleased(new StopClimb(m_elevator));

        PilotPOV.left().whenPressed(new Untilt(m_elevator))
            .whenReleased(new StopTilt(m_elevator));

        PilotPOV.right().whenPressed(new Tilt(m_elevator))
            .whenReleased(new StopTilt(m_elevator));

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

        return autoGrp.getAutoCommand();
    }
}
