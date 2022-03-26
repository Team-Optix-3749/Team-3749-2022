package frc.robot.commands.turret;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** An example command that uses an example subsystem. */
public class TurretCmd extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Turret m_turret;
    
    private DoubleSupplier m_turretControl;

    public TurretCmd(Turret turret, DoubleSupplier joystick) {
        m_turret = turret;
        m_turretControl = joystick;
        addRequirements(turret);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Turret Position", m_turret.getTurretPosition());
    }

    @Override
    public void initialize() {
        m_turret.stopTurret();
        SmartDashboard.putNumber("TEST", 100);
    }

    @Override
    public void execute() {
        dashboard();

        double turretControl = Constants.round(m_turretControl.getAsDouble());
        if (Math.abs(turretControl) >= .1) { 
            m_turret.setTurretMotor(turretControl*Constants.Shooter.turretSpeed);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}