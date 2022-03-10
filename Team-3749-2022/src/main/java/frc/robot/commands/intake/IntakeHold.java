package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import java.util.function.BooleanSupplier;

public class IntakeHold extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final BooleanSupplier m_trigger;

    public IntakeHold(Intake intake, BooleanSupplier trigger) {
        m_intake = intake;
        m_trigger = trigger;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        m_intake.stopCompressor();
    }

    @Override
    public void execute() {
        if (m_trigger.getAsBoolean()) {
            m_intake.intakeFwd();
            m_intake.setIntake(1);
            m_intake.holdShintake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.intakeRev();
        m_intake.stopIntake();
        m_intake.stopShintake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
