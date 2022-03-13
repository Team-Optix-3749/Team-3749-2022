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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ResetDrivetrain;
import frc.robot.commands.intake.ContinousIntake;
import frc.robot.commands.intake.ContinousShintake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.shooter.*;

public final class AutoGroups {

    static Drivetrain m_drivetrain;
    static Intake m_intake;
    static Shooter m_shooter;


    public AutoGroups(Drivetrain drive, Intake intake, Shooter shoot) {
        m_drivetrain = drive;
        m_intake = intake;
        m_shooter = shoot;
    }

    public final static Command getRamsetePath(String path) {
        Trajectory traj = new Trajectory();

        try {
            Path trajpath = Filesystem.getDeployDirectory().toPath().resolve(path);
            traj = TrajectoryUtil.fromPathweaverJson(trajpath);
        } catch (IOException e) {
            DriverStation.reportError("Unable to open traj", e.getStackTrace());
        }
        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand =
            new RamseteCommand(
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
        
        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, traj.getInitialPose()),
            ramseteCommand
        );
    }

    public final static Command getRamsete(String name) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, .5, .5);

        Trajectory traj = new Trajectory();

        traj = path;

        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand =
            new RamseteCommand(
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

                // m_drivetrain.resetEncoders();
                // m_drivetrain.resetOdometry(m_drivetrain.getPose());
                
                // return ramseteCommand;
        
        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, traj.getInitialPose()),
            ramseteCommand
        );
    }

    public final static Command intake(String name) {
        return new ParallelRaceGroup(
            getRamsete(name), 
            new ContinousIntake(m_intake)
        );
    }

    public final static Command shoot() {
        return new SequentialCommandGroup(
            // new VisionAlign(m_shooter),
            new ParallelRaceGroup(
                new ShootNew(m_shooter),
                new ContinousShintake(m_intake),
                new WaitCommand(2)
            )
        );
    }

    public final Command getOneBlue () {
        return new SequentialCommandGroup(
            intake("1BlueIntake"),
            getRamsete("1BlueReverse"),
            shoot()
        );
    }
    
    public final Command getTwoBlue () {
        return new SequentialCommandGroup(
            intake("2BlueIntake"),
            getRamsete("2BlueReverse"),
            shoot()
        );
    }

    public final Command getThreeBlue () {
        return new SequentialCommandGroup(
            intake("3BlueIntake"),
            getRamsete("3BlueReverse"),
            shoot()
        );
    }

    public final Command getOneTwoBlue () {
        return new SequentialCommandGroup(
            intake("1BlueIntake"),
            getRamsete("1BlueReverse"),
            intake("12BlueIntake"),
            getRamsete("12BlueReverse"),
            shoot()
        );
    }

    public final Command getTwoSevenBlue () {
        return new SequentialCommandGroup(
            intake("2BlueIntake"),
            getRamsete("2BlueReverse"),
            intake("27BlueIntake"),
            getRamsete("27BlueReverse")
        );
    }

    public final Command getOneTwoSevenBlue () {
        return new SequentialCommandGroup(
            intake("1BlueIntake"),
            getRamsete("1BlueReverse"),
            intake("127BlueIntake"),
            getRamsete("127BlueReverse"),
            shoot()
        );
    }

    public static final class Blue {

        public static final Command shootOne() {
            return new AutoShoot(m_shooter);
        }

        // public static final class TwoCargo {
        //     public static final Command getOneBlue () {
        //         return new SequentialCommandGroup(
        //             intake("1BlueIntake"),
        //             getRamsete("1BlueReverse"),
        //             shoot()
        //         );
        //     }

        //     public static final Command getTwoBlue () {
        //         return new SequentialCommandGroup(
        //             intake("2BlueIntake"),
        //             getRamsete("2BlueReverse"),
        //             shoot()
        //         );
        //     }

        //     public static final Command getThreeBlue () {
        //         return new SequentialCommandGroup(
        //             intake("3BlueIntake"),
        //             getRamsete("3BlueReverse"),
        //             shoot()
        //         );
        //     }
        // }

        // public final static class ThreeCargo {
        //     public static final Command getOneTwoBlue () {
        //         return new SequentialCommandGroup(
        //             intake("1BlueIntake"),
        //             getRamsete("1BlueReverse"),
        //             intake("12BlueIntake"),
        //             getRamsete("12BlueReverse"),
        //             shoot()
        //         );
        //     }

        //     public static final Command getTwoSevenBlue () {
        //         return new SequentialCommandGroup(
        //             intake("2BlueIntake"),
        //             getRamsete("2BlueReverse"),
        //             intake("27BlueIntake"),
        //             getRamsete("27BlueReverse")
        //         );
        //     }
        // }

        // public final static class FourCargo {
        //     public static final Command getOneTwoSevenBlue () {
        //         return new SequentialCommandGroup(
        //             intake("1BlueIntake"),
        //             getRamsete("1BlueReverse"),
        //             intake("127BlueIntake"),
        //             getRamsete("127BlueReverse"),
        //             shoot()
        //         );
        //     }
        // }
    }

    public  final static class Red {

        public final static Command shootOne() {
            return shoot();
        }

        public final static class TwoCargo {
            
        }

        public final static class ThreeCargo {
            
        }

        public final static class FourCargo {
            
        }
    }
}
