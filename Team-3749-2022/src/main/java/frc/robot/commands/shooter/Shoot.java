package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Shooter m_shooter;
    private Intake m_intake;
    private BooleanSupplier m_trigger;
    private DoubleSupplier m_joystick;


    public Shoot(Shooter shooter, Intake intake, BooleanSupplier trigger, DoubleSupplier joystick) {
        m_shooter = shooter;
        m_trigger = trigger;
        m_joystick = joystick;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        m_shooter.stopMotor();
    }

    @Override
    public void execute() {
        double turretControl = Constants.round(m_joystick.getAsDouble());
        if (turretControl != 0) m_shooter.setTurretMotor(turretControl*.2);
        else m_shooter.visionAlign();

        if (m_trigger.getAsBoolean()) {
            m_shooter.setTargetVelocity();

            if (m_shooter.getRPM() > m_shooter.targetVelocity())
                m_intake.setShintake();
            else m_intake.stopShintake();
        } else m_shooter.stopMotor();
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
