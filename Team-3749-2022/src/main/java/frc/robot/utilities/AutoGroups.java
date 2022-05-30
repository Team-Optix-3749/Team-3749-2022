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
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ResetDrivetrain;
import frc.robot.commands.intake.AutoIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.shooter.*;

public class AutoGroups {

    static Drivetrain m_drivetrain;
    static Intake m_intake;
    static Shooter m_shooter;

    public AutoGroups(Drivetrain drive, Intake intake, Shooter shoot) {
        m_drivetrain = drive;
        m_intake = intake;
        m_shooter = shoot;
    }

    public final static Command getRamseteJSON(String name) {
        Trajectory traj = new Trajectory();

        try {
            Path trajpath = Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/" + name + ".wpilib.json");
            traj = TrajectoryUtil.fromPathweaverJson(trajpath);
        } catch (IOException e) {
            DriverStation.reportError("Unable to open traj", e.getStackTrace());
        }

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
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

        m_drivetrain.resetOdometry(traj.getInitialPose());

        return ramseteCommand;
    }

    public final static Command getRamsete(String name) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, 2, 1.67);

        Trajectory traj = new Trajectory();

        traj = path;

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
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

        m_drivetrain.resetOdometry(traj.getInitialPose());

        return ramseteCommand;
    }

    public final static Command getRamsete(String name, boolean reversed) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, 4, 4, reversed);

        Trajectory traj = new Trajectory();

        traj = path;

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
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

        m_drivetrain.resetOdometry(traj.getInitialPose());

        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, traj),
            ramseteCommand
        );
    }

    public final static Command getRamsete(String name, String translate) {
        Trajectory traj = PathPlanner.loadPath(name, 2.5, 2.5);
        Trajectory translation = PathPlanner.loadPath(name, 2.5, 2.5); // why does this work??????

        if(translate != "") { 
            traj = traj.relativeTo(translation.getInitialPose());
        }

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
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

        // m_drivetrain.resetOdometry(traj.getInitialPose());

        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, traj),
            ramseteCommand
        );
    }

    public final static Command intake(String name) {
        return new ParallelRaceGroup(
                getRamsete(name),
                new AutoIntake(m_intake));
    }

    public final static Command intake() {
        return new AutoIntake(m_intake); 
    }

    public final static Command intake(String name, String translation) {
        return new ParallelRaceGroup(
                getRamsete(name, translation),
                new AutoIntake(m_intake));
    }

    public final static Command intake(String name, boolean reversed) {
        return new ParallelRaceGroup(
                getRamsete(name, reversed),
                new AutoIntake(m_intake));
    }

    public final static Command shoot() {
        return new SequentialCommandGroup(
                new ParallelRaceGroup(
                        new AutoShoot(m_shooter, m_intake),
                        new WaitCommand(1.5)));
    }

    public final Command timerShoot() {
        return null;
    }

    public final Command getRaadwan() {
        return new SequentialCommandGroup(
                intake("1-Intake"),
                getRamsete("1-Shoot"),
                shoot());
    }

    public final Command getTwo() {
        return new SequentialCommandGroup(
                intake("1-Intake"),
                new ParallelRaceGroup(
                    intake(),
                    new WaitCommand(1.5)
                ),
                getRamsete("1-ShootRound", "1-intake"),
                shoot());
    }
    
    public final Command getThree() {
        return new SequentialCommandGroup(
            intake("3-Intake"),
                new ParallelRaceGroup(
                    intake(),
                    new WaitCommand(1)
                ),
                getRamsete("3-ShootRound", "3-Intake"),
                shoot());
    }

    public final Command getFour() {
        return new SequentialCommandGroup(
            intake("1-Intake", ""),
            getRamsete("1-ShootRound", "1-Intake"),
            shoot(),
            getRamsete("7-Intake", ""),
            new ParallelRaceGroup(  
                intake(),
                new WaitCommand(2)          
            ),
            getRamsete("7-ShootReversalNew", true),
            shoot()
        );
    }
    public final Command getFourJacked() {
        return new SequentialCommandGroup(
            intake("1-Intake", ""),
            getRamsete("1-ShootRound", "1-Intake"),
            timerShoot(),
            getRamsete("7-Intake", ""),
            new ParallelRaceGroup(  
                intake(),
                new WaitCommand(2)          
            ),
            getRamsete("7-ShootReversalNew", true),
            timerShoot()
        );
    }

    public final Command getTwoLA() {
        return new SequentialCommandGroup(
            intake("1-Intake", ""),
            getRamsete("1-ShootRound", "1-Intake"),
            timerShoot());
    }

    public final Command tarmacShoot() {
        return shoot();
    }
}
