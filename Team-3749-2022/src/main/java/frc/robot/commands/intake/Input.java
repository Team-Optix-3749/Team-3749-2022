package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;

public class Input extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final BooleanSupplier m_triggerLeft;
    private final BooleanSupplier m_triggerRight;
    private Timer t = new Timer();

    public Input(Intake intake, BooleanSupplier triggerLeft, BooleanSupplier triggerRight) {
        m_intake = intake;
        m_triggerLeft = triggerLeft;
        m_triggerRight = triggerRight;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        t.start();
    }

    @Override
    public void execute() {
        if (m_triggerLeft.getAsBoolean()) {
            m_intake.setIntake();
            m_intake.intakeFwd();
            m_intake.holdShintake();
        } else if (m_triggerRight.getAsBoolean()) {
            m_intake.setIntakeReverse();
            m_intake.intakeFwd();
            m_intake.setShintakeReverse();
        } else {
            m_intake.intakeRev();
            m_intake.stopIntake();
            // m_intake.stopShintake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.intakeRev();
        m_intake.stopIntake();
        m_intake.stopShintake();
        t.reset();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
