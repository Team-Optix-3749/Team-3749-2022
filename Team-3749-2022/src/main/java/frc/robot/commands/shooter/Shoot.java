package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** An example command that uses an example subsystem. */
public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Shooter m_shooter;
    private Intake m_intake;
    private BooleanSupplier m_rightTrigger;
    private BooleanSupplier m_leftTrigger;
    // private Sendable send = new Sendable();

    private JoystickButton align;
    private JoystickButton alignSkewed;
    private DoubleSupplier m_joystick;

    public Shoot(Shooter shooter, Intake intake, BooleanSupplier rightTrig, BooleanSupplier leftTrig, JoystickButton alignBtn, JoystickButton alignBtnSkewed, DoubleSupplier joystick) {
        m_shooter = shooter;
        m_intake = intake;
        m_rightTrigger = rightTrig;
        m_leftTrigger = leftTrig;
        align = alignBtn;
        m_joystick = joystick;
        alignSkewed = alignBtnSkewed;
        addRequirements(shooter);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Turret Position", m_shooter.getTurretPosition());
        SmartDashboard.putNumber("Shooter RPM", m_shooter.getRPM());
        SmartDashboard.putBoolean("Lower Shoot RPM", m_shooter.getRPM() > Constants.Shooter.lowerRPM);
        SmartDashboard.putBoolean("Upper Shoot RPM", m_shooter.getRPM() > Constants.Shooter.shooterRPM);
        SmartDashboard.putNumber("Distance", m_shooter.getDistance());
        SmartDashboard.putNumber("Shooter Velocity", m_shooter.getVelocity());
    }

    @Override
    public void initialize() {
        m_shooter.stopMotor();
        m_shooter.stopTurret();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("SHOOTER RPM", 0);

        // SmartDashboard.getNumber("SHOOTER RPM");

        dashboard();

        // System.out.println(SmartDashboard.getString("RPM CHOOSER", "0"));

        double turretControl = Constants.round(m_joystick.getAsDouble());
        if (Math.abs(turretControl) >= .1) { 
            m_shooter.setTurretMotor(turretControl*Constants.Shooter.turretSpeed);
        } else if (align.get()) {
             m_shooter.visionAlign();
        } else if (alignSkewed.get()){
            m_shooter.skewedVisionAlign();
        } else {m_shooter.stopTurret();}  

        if (m_rightTrigger.getAsBoolean()) {
            // m_shooter.visionAlign();
            
            m_shooter.setRPM(Constants.Shooter.shooterRPM);
        } else if (m_leftTrigger.getAsBoolean())
            m_shooter.setRPM(Constants.Shooter.lowerRPM);
            // m_shooter.setVelocity(100);
        else m_shooter.stopMotor();
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
