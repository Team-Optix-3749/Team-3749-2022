package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;

public class Input extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final Shintake m_shintake;

    private final BooleanSupplier m_trigger;
    private Timer t = new Timer();

    public Input(Intake intake, Shintake shintake, BooleanSupplier trigger) {
        m_intake = intake;
        m_trigger = trigger;
        m_shintake = shintake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        t.start();
    }

    @Override
    public void execute() {
        if (t.get() >= 25 && t.get() <= 45) m_intake.startCompressor();
        else if (t.get() >= 45) t.reset();
        
        m_intake.startCompressor();

        if (m_trigger.getAsBoolean()) {
            m_intake.intakeFwd();
            m_intake.setIntake();
            m_shintake.holdShintake();
        } else {
            m_intake.intakeRev();
            m_intake.stopIntake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.intakeRev();
        m_intake.stopIntake();
        m_shintake.stopShintake();
        t.reset();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
