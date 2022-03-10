package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

public class IntakeHold extends CommandBase {
    private final Intake m_intake;

    public IntakeHold(Intake intake) {
        m_intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_intake.intakePneumatics(kForward);
        m_intake.setIntake(1);
        m_intake.holdShintake();
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.intakePneumatics(kReverse);
        m_intake.stopIntake();
        m_intake.stopShintake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
