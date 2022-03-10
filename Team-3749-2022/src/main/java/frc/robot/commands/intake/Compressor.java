package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Compressor extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;

    public Compressor(Intake intake) {
        m_intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        m_intake.stopCompressor();
    }

    @Override
    public void execute() {
        m_intake.startCompressor();
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopCompressor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
