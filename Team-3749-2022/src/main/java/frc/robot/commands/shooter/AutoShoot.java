package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final Intake m_intake;
    private final Timer t = new Timer();

    public AutoShoot(Shooter shooter, Intake intake) {
        m_shooter = shooter;
        m_intake = intake;
        addRequirements(shooter, intake);
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
    }

    @Override
    public void execute() {
        m_shooter.visionAlign();
        m_shooter.setRPM(Constants.Shooter.upperRPM);
        
        if (m_shooter.getRPM() > Constants.Shooter.upperRPM) m_intake.setShintake();

    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopTurret();
        m_shooter.stopMotor();
        m_intake.stopShintake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
