package frc.robot.commands.auton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class Blue extends AutoGroups {
    Drivetrain m_drivetrain;
    Intake m_intake;
    Shooter m_shooter;

    public Blue(Drivetrain drive, Intake intake, Shooter shoot) {
        super(drive, intake, shoot);
    }

    public final Command getTwoBlue() {
        return new SequentialCommandGroup(
                intake("Blue2Intake"),
                getRamsete("Blue2Shoot"),
                shoot());
    }

    public final Command getThreeBlue() {
        return new SequentialCommandGroup(
                intake("3BlueIntake"),
                getRamsete("3BlueTurn"),
                shoot());
    }

    public final Command getThreeBlueReverse() {
        return new SequentialCommandGroup(
                intake("3BlueIntake"),
                getRamsete("3BlueReverse", false, true),
                shoot());
    }
}
