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

    public final Command getOneBlue() {
        return new SequentialCommandGroup(
                intake("1BlueIntake"),
                getRamsete("Auto2"),
                shoot());
    }

    public final Command getTwoBlue() {
        return new SequentialCommandGroup(
                intake("2BlueIntake"),
                getRamsete("2BlueReverse"),
                shoot());
    }

    public final Command getThreeBlue() {
        return new SequentialCommandGroup(
                intake("3BlueIntake"),
                getRamsete("3BlueReverse"),
                shoot());
    }

    public final Command getOneTwoBlue() {
        return new SequentialCommandGroup(
                intake("1BlueIntake"),
                getRamsete("1BlueReverse"),
                intake("12BlueIntake"),
                getRamsete("12BlueReverse"),
                shoot());
    }

    public final Command getTwoSevenBlue() {
        return new SequentialCommandGroup(
                intake("2BlueIntake"),
                getRamsete("2BlueReverse"),
                intake("27BlueIntake"),
                getRamsete("27BlueReverse"));
    }

    public final Command getOneTwoSevenBlue() {
        return new SequentialCommandGroup(
                intake("1BlueIntake"),
                getRamsete("1BlueReverse"),
                intake("127BlueIntake"),
                getRamsete("127BlueReverse"),
                shoot());
    }
}
