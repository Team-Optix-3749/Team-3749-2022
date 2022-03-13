package frc.robot.commands.shooter;

import frc.robot.subsystems.*;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;

    public AutoShoot(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // m_shooter.setVelocity(m_shooter.targetVelocity());
        m_shooter.setRPM(4000);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
