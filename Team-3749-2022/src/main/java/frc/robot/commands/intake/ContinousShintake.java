package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ContinousShintake extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;

    public ContinousShintake(Intake intake) {
        m_intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        m_intake.stopShintake();
    }

    @Override
    public void execute() {
        m_intake.setShintake();
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopMotors();
        m_intake.stopShintake();
        m_intake.intakeRev();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
