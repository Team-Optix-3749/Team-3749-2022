package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.function.BooleanSupplier;

public class Input extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final BooleanSupplier m_triggerLeft;
    private final BooleanSupplier m_triggerRight;
    private final JoystickButton m_bumperLeft;
    private final JoystickButton m_bumperRight;
    private final JoystickButton comp;
    private Timer t = new Timer();

    public Input(Intake intake, BooleanSupplier triggerLeft, BooleanSupplier triggerRight, JoystickButton bumperLeft, JoystickButton bumperRight, JoystickButton compress) {
        m_intake = intake;
        m_triggerLeft = triggerLeft;
        m_triggerRight = triggerRight;
        m_bumperLeft = bumperLeft;
        m_bumperRight = bumperRight;
        comp = compress;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        t.start();
    }

    @Override
    public void execute() {
        if (comp.get()) m_intake.startCompressor();
        // else if (t.get() >= 25 && t.get() <= 45) m_intake.startCompressor();
        // else if (t.get() >= 45) t.reset();
        else m_intake.stopCompressor();
        
        m_intake.startCompressor();

        if (m_triggerLeft.getAsBoolean()) {
            m_intake.setIntake();
            m_intake.intakeFwd();
            m_intake.holdShintake();
        } else if (m_triggerRight.getAsBoolean()) {
            m_intake.setIntakeReverse();
            m_intake.intakeFwd();
            m_intake.setShintakeReverse();
        } else if (m_bumperLeft.get()) {
            m_intake.holdShintake();
        } else if (m_bumperRight.get()) {
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
