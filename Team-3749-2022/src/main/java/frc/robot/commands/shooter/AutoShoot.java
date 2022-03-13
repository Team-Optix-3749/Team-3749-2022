package frc.robot.commands.shooter;

import frc.robot.subsystems.*;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private Timer t = new Timer();

    public AutoShoot(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        t.start();
    }

    @Override
    public void execute() {
        // m_shooter.setVelocity(m_shooter.targetVelocity());
        if (t.get() < 4) m_shooter.setRPM(350);
    }

    @Override
    public void end(boolean interrupted) {
        t.reset();
        m_shooter.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
