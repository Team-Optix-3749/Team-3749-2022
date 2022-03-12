package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.commands.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Xbox;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

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

    private final Base m_base = new Base();

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
        
        Operator.x().whenPressed(new InstantCommand(m_intake::startCompressor))
            .whenReleased(new InstantCommand(m_intake::stopCompressor));

        Operator.y().whenPressed(new InstantCommand(m_elevator::rawClimbUp))
            .whenReleased(new InstantCommand(m_elevator::stopClimb));

        Operator.b().whenPressed(new InstantCommand(m_elevator::rawClimbDown))
            .whenReleased(new InstantCommand(m_elevator::stopClimb));

        // Pilot.povUp().whenPressed(new Extend(m_elevator))
        //     .whenReleased(new InstantCommand(m_elevator::stopClimb));

        // Pilot.povDown().whenPressed(new Lift(m_elevator))
        //     .whenReleased(new InstantCommand(m_elevator::stopClimb));

        // Pilot.povLeft().whenPressed(new Untilt(m_elevator))
        //     .whenReleased(new InstantCommand(m_elevator::stopTilt));

        // Pilot.povRight().whenPressed(new Tilt(m_elevator))
        //     .whenReleased(new InstantCommand(m_elevator::stopTilt));

        m_drivetrain.setDefaultCommand(
            new ArcadeDrive(m_drivetrain, Pilot::getLeftY, Pilot::getRightX));

        m_shooter.setDefaultCommand(
            new Shoot(m_shooter, Operator::getRightTrigger));
        
        m_intake.setDefaultCommand(
            new IntakeHold(m_intake, Pilot::getLeftTrigger, Operator.a()));

        m_base.setDefaultCommand(
            new Controls(m_base));
        
        // m_elevator.setDefaultCommand(new Tilt(m_elevator));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // return null;
        Trajectory exampleTrajectory = new Trajectory();
        System.out.println("asdfljasd;flkjsad;lgkj;lksjdg;laskjdggl;jksadg;ljksa;lkgj;lasdjgk;lsjkdg;lajsdg;ljas;dlgjkas;dlkjg;lsakjfg;lakjfsg;ljagkf");

        PathPlannerTrajectory examplePath = PathPlanner.loadPath("bottomBlueCargo", 1, 1);


        m_drivetrain.setBrakeMode();

        try {
          Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("paths/output/Unnamed.wpilib.json");
          exampleTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
       } catch (IOException ex) {
          DriverStation.reportError("Unable to open trajectory", ex.getStackTrace());
       }
       
        RamseteCommand ramseteCommand =
            new RamseteCommand(
                exampleTrajectory,
                m_drivetrain::getPose,
                new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
                new SimpleMotorFeedforward(
                    Constants.Auto.ksVolts,
                    Constants.Auto.kvVoltSecondsPerMeter,
                    Constants.Auto.kaVoltSecondsSquaredPerMeter),
                Constants.Auto.kDriveKinematics,
                m_drivetrain::getWheelSpeeds,
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                // RamseteCommand passes volts to the callback
                m_drivetrain::tankDriveVolts,
                m_drivetrain);
    
        // Reset odometry to the starting pose of the trajectory.
        m_drivetrain.resetOdometry(exampleTrajectory.getInitialPose());
    
        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> m_drivetrain.tankDriveVolts(0, 0));
        // return new Print(m_drivetrain);
    }
}
