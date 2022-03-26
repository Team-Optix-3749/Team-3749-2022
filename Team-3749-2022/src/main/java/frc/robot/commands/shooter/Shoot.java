package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** An example command that uses an example subsystem. */
public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Shooter m_shooter;
    private Shintake m_shintake;
    
    private BooleanSupplier m_upperShintakeShootTrigger;
    private BooleanSupplier m_lowerShintakeShootTrigger;
    private JoystickButton m_shintakeButton;

    private ShooterState state;

    public Shoot(Shooter shooter, Shintake shintake, BooleanSupplier rightTrig, BooleanSupplier leftTrig, JoystickButton shintakeButton) {
        m_shooter = shooter;
        m_shintake = shintake;
        m_upperShintakeShootTrigger = rightTrig;
        m_lowerShintakeShootTrigger = leftTrig;
        m_shintakeButton = shintakeButton;
        addRequirements(shooter);
    }

    public void dashboard() {
        SmartDashboard.putNumber("SHOOTER RPM", m_shooter.getRPM());
        SmartDashboard.putBoolean("LOWER SHOOT CHECK", m_shooter.getRPM() > Constants.Shooter.lowerRPM);
        SmartDashboard.putBoolean("UPPER SHOOT CHECK", m_shooter.getRPM() > Constants.Shooter.upperRPM);
        SmartDashboard.putNumber("HUB DISTANCE (METERS)", m_shooter.getDistance());
    }

    @Override
    public void initialize() {
        m_shooter.stopShooter();
        SmartDashboard.putNumber("TEST", 100);
    }

    public void manageState () {
        if (m_shintakeButton.get()) {
            state = ShooterState.SHINTAKE;
        } else if (m_upperShintakeShootTrigger.getAsBoolean()) {
            if (state != ShooterState.SHINTAKE) state = ShooterState.SPOOL_UPPER;
        } else if (m_lowerShintakeShootTrigger.getAsBoolean()) {
            if (state != ShooterState.SHINTAKE) state = ShooterState.SPOOL_LOWER;
        } else {
            state = ShooterState.STOP;
        }
        
    }

    @Override
    public void execute() {

        m_shooter.distanceCheck();
        dashboard();
        manageState();

        switch (state) {
            case SHINTAKE:
                m_shintake.setShintake();
                break;
            case SPOOL_UPPER:
                if (m_shooter.getRPM() > Constants.Shooter.upperRPM - 20) state = ShooterState.SHINTAKE;
                m_shooter.setRPM(Constants.Shooter.upperRPM);
            case SPOOL_LOWER:
                if (m_shooter.getRPM() > Constants.Shooter.lowerRPM - 20) state = ShooterState.SHINTAKE;
                m_shooter.setRPM(Constants.Shooter.lowerRPM);
            case STOP:
                m_shooter.stopShooter();
                m_shintake.stopShintake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopShooter();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    public enum ShooterState {
        STOP, SHINTAKE, SPOOL_LOWER, SPOOL_UPPER
    }
}