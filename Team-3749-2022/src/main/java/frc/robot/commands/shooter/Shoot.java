package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final BooleanSupplier m_trigger;

    public Shoot(Shooter shooter, BooleanSupplier trigger) {
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
        if (m_trigger.getAsBoolean())
            m_shooter.setRPM(Constants.Shooter.shooterRPM);
        else
            m_shooter.stopMotor();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
