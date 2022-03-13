package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.function.BooleanSupplier;

public class ContinousShintake extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final Timer t = new Timer();

    public ContinousShintake(Intake intake) {
        m_intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        t.start();
        m_intake.stopShintake();
    }

    @Override
    public void execute() {
        m_intake.setShintake();
    }

    @Override
    public void end(boolean interrupted) {
        t.reset();
        m_intake.stopShintake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
