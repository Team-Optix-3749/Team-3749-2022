package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class RawShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final DoubleSupplier m_trigger;

    public RawShoot(Shooter shooter, DoubleSupplier trigger) {
        m_shooter = shooter;
        m_trigger = trigger;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        m_shooter.stopMotor();
    }

    @Override
    public void execute() {
        if (Constants.round(m_trigger.getAsDouble()) > 0)
            m_shooter.rawShoot(Constants.Shooter.shooterSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
