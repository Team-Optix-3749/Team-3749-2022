package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shintake.HoldShintake;
import frc.robot.commands.shintake.ShintakeShoot;
import frc.robot.commands.shintake.StopShintake;
import frc.robot.commands.shooter.*;
import frc.robot.commands.elevator.*;
import frc.robot.subsystems.*;
import frc.robot.utilities.AutoGroups;
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

    private final Shintake m_shintake = new Shintake();

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
        Pilot.leftBumper().whenPressed(new HoldShintake(m_shintake)).whenReleased(new StopShintake(m_shintake));

        Operator.a().whenPressed(new ShintakeShoot(m_shintake)).whenReleased(new StopShintake(m_shintake));
        Operator.rightBumper().whenPressed(new VisionAlign(m_shooter)).whenReleased(new StopTurret(m_shooter));
        OpPOV.up().whenPressed(new UpperShoot(m_shooter)).whenReleased(new StopShooter(m_shooter));
        OpPOV.up().whenPressed(new LowerShoot(m_shooter)).whenReleased(new StopShooter(m_shooter));


        m_drivetrain.setDefaultCommand(
                new ArcadeDrive(m_drivetrain, Pilot::getLeftY, Pilot::getRightX));

        m_shooter.setDefaultCommand(
                new Shoot(m_shooter, m_shintake, Operator::getRightTrigger, Operator::getLeftTrigger, Operator::getRightX, Operator.leftBumper()));

        m_intake.setDefaultCommand(
            new Input(m_intake, m_shintake, Pilot::getLeftTrigger));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        AutoGroups autoGroup = new AutoGroups(m_drivetrain, m_shooter, m_shintake, m_intake);

        return autoGroup.getFour();
    }
}
