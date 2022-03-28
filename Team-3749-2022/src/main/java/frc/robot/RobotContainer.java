package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.auton.AutoGroups;
import frc.robot.commands.shooter.*;
import frc.robot.commands.elevator.*;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.*;
import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;

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

        Pilot.y().whenPressed(new Extend(m_elevator)).whenReleased(new StopClimb(m_elevator));
        Pilot.b().whenPressed(new Lift(m_elevator)).whenReleased(new StopClimb(m_elevator));

        m_drivetrain.setDefaultCommand(
                new ArcadeDrive(m_drivetrain, Pilot::getLeftY, Pilot::getRightX));
        m_shooter.setDefaultCommand(
                new Shoot(m_shooter, m_intake, Operator.a(), Operator::getRightTrigger, Operator::getLeftTrigger, Operator.rightBumper(), Operator.leftBumper(), Operator::getRightX, OpPOV.up(), OpPOV.down(), Pilot.leftBumper(), Pilot.rightBumper()));
        m_intake.setDefaultCommand(
            new Input(m_intake, Pilot::getLeftTrigger, Pilot::getRightTrigger));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        AutoGroups autoGroup = new AutoGroups(m_drivetrain, m_intake, m_shooter);

        return autoGroup.getFour();
    }
}
