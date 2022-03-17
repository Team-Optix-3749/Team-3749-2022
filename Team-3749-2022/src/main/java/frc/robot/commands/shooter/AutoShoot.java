package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class AutoShoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private final Intake m_intake;

    public AutoShoot(Shooter shooter, Intake intake) {
        m_shooter = shooter;
        m_intake = intake;
        addRequirements(shooter, intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.visionAlign();
        // m_shooter.setRPM(Constants.Shooter.shooterRPM);
        m_shooter.setTargetVelocity();
        // System.out.println(m_shooter.getRPM());
        if (m_shooter.getRPM() > m_shooter.targetVelocity())
            m_intake.setShintake();
        else
            m_intake.stopShintake();
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
