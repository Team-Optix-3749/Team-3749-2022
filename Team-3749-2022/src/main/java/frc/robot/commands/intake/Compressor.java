package frc.robot.commands.intake;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import java.util.function.DoubleSupplier;

public class Compressor extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final DoubleSupplier m_trigger;

    public Compressor(Intake intake, DoubleSupplier trigger) {
        m_intake = intake;
        m_trigger = trigger;
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
