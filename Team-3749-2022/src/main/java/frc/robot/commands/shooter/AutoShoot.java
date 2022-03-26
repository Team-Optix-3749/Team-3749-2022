package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class AutoShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final Turret m_turret;
    private final Shintake m_shintake;
    private final Timer t = new Timer();

    public AutoShoot(Shooter shooter, Shintake shintake, Turret turret) {
        m_shooter = shooter;
        m_shintake = shintake;
        m_turret = turret;
        addRequirements(shooter, shintake);
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
    }

    @Override
    public void execute() {
        m_turret.visionAlign();
        m_shooter.setRPM(Constants.Shooter.upperRPM);
        
        if (m_shooter.getRPM() > Constants.Shooter.upperRPM - 20) m_shintake.setShintake();
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.stopTurret();
        m_shooter.stopShooter();
        m_shintake.stopShintake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
