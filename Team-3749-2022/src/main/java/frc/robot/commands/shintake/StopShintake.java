package frc.robot.commands.shintake;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopShintake extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Shintake m_shintake;

    public StopShintake(Shintake shintake) {
        m_shintake = shintake;
        addRequirements(shintake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shintake.stopShintake();
    }

    @Override
    public void end(boolean interrupted) {
        m_shintake.stopShintake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
