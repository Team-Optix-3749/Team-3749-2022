package frc.robot.utilities;

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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ResetDrivetrain;
import frc.robot.commands.intake.ContinousIntake;
import frc.robot.commands.shooter.VisionAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.shooter.*;

public class AutoGroups {

    Drivetrain m_drivetrain;
    Intake m_intake;
    Shooter m_shooter;


    public AutoGroups (Drivetrain drive, Intake intake, Shooter shoot) {
        m_drivetrain = drive;
        m_intake = intake;
        m_shooter = shoot;
    }

    public Command getRamseteName (String name) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, 1, 1);

        Trajectory exampleTrajectory = new Trajectory();

        exampleTrajectory = path;

        m_drivetrain.setBrake();

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
                m_drivetrain::tankDriveVolts,
                m_drivetrain);
        
        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, exampleTrajectory.getInitialPose()),
            ramseteCommand
        );
    }
    public Command getRamsetepath (String path) {
        Trajectory exampleTrajectory = new Trajectory();

        m_drivetrain.setBrake();

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);
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
                m_drivetrain::tankDriveVolts,
                m_drivetrain);
        
        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, exampleTrajectory.getInitialPose()),
            ramseteCommand
        );
    }

    public Command getOneBlue () {

        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                getRamseteName("1BlueIntake"), 
                new ContinousIntake(m_intake)
            ),
            getRamseteName("1BlueReverse")
        );
    }

    public Command getTwoBlue () {
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                getRamseteName("2BlueIntake"), 
                new ContinousIntake(m_intake)
            ),
            getRamseteName("2BlueReverse"),
            new VisionAlign(m_shooter),
            new AutoShoot(m_shooter)
        );
    }

    public Command getTwoSevenBlue () {
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                getRamseteName("1BlueIntake"), 
                new ContinousIntake(m_intake)
            ),
            getRamseteName("1BlueReverse"),
            new VisionAlign(m_shooter),
            new AutoShoot(m_shooter)
        );
    }
}
