package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.function.BooleanSupplier;

public class ContinousIntake extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;

    public ContinousIntake(Intake intake) {
        m_intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        m_intake.intakeFwd();
    }

    @Override
    public void execute() {
        m_intake.setIntake();
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopMotors();
        m_intake.intakeRev();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
