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

    public Command getRamsete (String name) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, 1, 1);

        Trajectory exampleTrajectory = new Trajectory();

        exampleTrajectory = path;

        m_drivetrain.setBrakeMode();

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
        
        m_drivetrain.resetOdometry(exampleTrajectory.getInitialPose());
        
        return ramseteCommand;
    }

    public Command getOneBlue () {

        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                getRamsete("1BlueIntake"), 
                new ContinousIntake(m_intake)
            ),
            getRamsete("1BlueReverse")
            // new VisionAlign(m_shooter),
            // new AutoShoot(m_shooter)
        );
    }

    public Command getTwoBlue () {
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                getRamsete("2BlueIntake"), 
                new ContinousIntake(m_intake)
            ),
            getRamsete("2BlueReverse"),
            new VisionAlign(m_shooter),
            new AutoShoot(m_shooter)
        );
    }

    public Command getTwoSevenBlue () {
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                getRamsete("1BlueIntake"), 
                new ContinousIntake(m_intake)
            ),
            getRamsete("1BlueReverse"),
            new VisionAlign(m_shooter),
            new AutoShoot(m_shooter)
        );
    }
}
