package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.function.BooleanSupplier;

public class Input extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final BooleanSupplier m_trigger;
    private final JoystickButton button;

    public Input(Intake intake, BooleanSupplier trigger, JoystickButton btn) {
        m_intake = intake;
        m_trigger = trigger;
        button = btn;
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
            m_intake.setIntake();
            m_intake.holdShintake();
            // System.out.println(m_intake.getShintake());
        } 
        else if (button.get()) { 
            m_intake.setShintake();
        }
        else {
            m_intake.intakeRev();
            m_intake.stopIntake();
            m_intake.stopShintake();
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
