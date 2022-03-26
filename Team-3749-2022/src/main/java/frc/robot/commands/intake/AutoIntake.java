package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoIntake extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final Shintake m_shintake;

    public AutoIntake(Intake intake, Shintake shintake) {
        m_intake = intake;
        m_shintake = shintake;
        addRequirements(intake, shintake);
    }

    @Override
    public void initialize() {
        m_intake.intakeFwd();
    }

    @Override
    public void execute() {
        m_intake.setIntake();
        m_shintake.holdShintake();
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopMotors();
        m_shintake.stopShintake();
        m_intake.intakeRev();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
