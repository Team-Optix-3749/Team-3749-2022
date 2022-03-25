package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.function.BooleanSupplier;

public class Input extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final BooleanSupplier m_trigger;
    private final JoystickButton m_shintake;
    private final JoystickButton m_shintakeBumper;
    private final JoystickButton comp;
    private Timer t = new Timer();

    public Input(Intake intake, BooleanSupplier trigger, JoystickButton shint, JoystickButton shintakeBumper, JoystickButton compress) {
        m_intake = intake;
        m_trigger = trigger;
        m_shintake = shint;
        m_shintakeBumper = shintakeBumper;
        comp = compress;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        t.start();
        m_intake.stopCompressor();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("FRONT SHINTAKE OUTPUT CURRENT", m_intake.m_shintakeFront.getAppliedOutput());
        SmartDashboard.putNumber("BACK SHINTAKE OUTPUT CURRENT", m_intake.m_shintakeBack.getAppliedOutput());


        if (comp.get()) m_intake.startCompressor();
        else if (t.get() >= 25 && t.get() <= 45) m_intake.startCompressor();
        else if (t.get() >= 45) t.reset();
        else m_intake.stopCompressor();
        
        m_intake.startCompressor();

        if (m_trigger.getAsBoolean()) {
            m_intake.intakeFwd();
            m_intake.setIntake();
            m_intake.holdShintake();
        } 
        else if (m_shintake.get()) { 
            m_intake.setShintake();
            // m_intake.setShintakeVoltage(1);
        }
        else if (m_shintakeBumper.get())
            m_intake.holdShintake();
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
        t.reset();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
