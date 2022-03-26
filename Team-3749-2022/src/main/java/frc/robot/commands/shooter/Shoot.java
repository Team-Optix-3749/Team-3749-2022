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
    private DoubleSupplier m_turretControl;
    private JoystickButton alignButton;

    public Shoot(Shooter shooter, Shintake shintake, BooleanSupplier rightTrig, BooleanSupplier leftTrig,  DoubleSupplier joystick, JoystickButton align) {
        m_shooter = shooter;
        m_shintake = shintake;
        m_upperShintakeShootTrigger = rightTrig;
        m_lowerShintakeShootTrigger = leftTrig;
        m_turretControl = joystick;
        alignButton = align;
        addRequirements(shooter);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Turret Position", m_shooter.getTurretPosition());
        SmartDashboard.putNumber("SHOOTER RPM", m_shooter.getRPM());
        SmartDashboard.putBoolean("LOWER SHOOT CHECK", m_shooter.getRPM() > Constants.Shooter.lowerRPM);
        SmartDashboard.putBoolean("UPPER SHOOT CHECK", m_shooter.getRPM() > Constants.Shooter.upperRPM);
        SmartDashboard.putNumber("HUB DISTANCE (METERS)", m_shooter.getDistance());
    }

    @Override
    public void initialize() {
        m_shooter.stopShooter();
        m_shooter.stopTurret();
        SmartDashboard.putNumber("TEST", 100);
    }

    @Override
    public void execute() {

        m_shooter.distanceCheck();

        dashboard();

        double turretControl = Constants.round(m_turretControl.getAsDouble());
        if (Math.abs(turretControl) >= .1) { 
            m_shooter.setTurretMotor(turretControl*Constants.Shooter.turretSpeed);
        } if (alignButton.get()) {
            m_shooter.visionAlign();
        }

        if (m_upperShintakeShootTrigger.getAsBoolean()) {
            if (m_shooter.getRPM() > Constants.Shooter.upperRPM - 20) {
                m_shintake.setShintake();
            } else m_shintake.stopShintake();

            m_shooter.setRPM(Constants.Shooter.upperRPM);
        } else if (m_lowerShintakeShootTrigger.getAsBoolean()) {
            if (m_shooter.getRPM() > Constants.Shooter.lowerRPM - 20) {
                m_shintake.setShintake();
            } else m_shintake.stopShintake();

            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        } else {
            m_shooter.stopShooter();
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
}