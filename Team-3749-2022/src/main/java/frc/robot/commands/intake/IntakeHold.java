package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import java.util.function.DoubleSupplier;

public class IntakeHold extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final DoubleSupplier m_trigger;

    public IntakeHold(Intake intake, DoubleSupplier trigger) {
        m_intake = intake;
        m_trigger = trigger;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        m_intake.stopCompressor();
        m_trigger.toString();
    }

    @Override
    public void execute() {
        if (Constants.round(m_trigger.getAsDouble()) > 0) {
            m_intake.intakePneumatics(kForward);
            m_intake.setIntake(1);
            m_intake.holdShintake();
        }
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
