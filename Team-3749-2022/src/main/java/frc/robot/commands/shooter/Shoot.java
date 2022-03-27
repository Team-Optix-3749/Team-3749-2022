package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/** An example command that uses an example subsystem. */
public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Shooter m_shooter;
    private Intake m_intake;
    
    private BooleanSupplier m_upperShintakeShootTrigger;
    private BooleanSupplier m_lowerShintakeShootTrigger;
    private JoystickButton m_alignButton;
    private JoystickButton m_skewedAlignButton;
    private JoystickButton m_shintakeButton;
    private JoystickButton m_shintakeHoldButton;

    private DoubleSupplier m_turretControl;

    private POVButton m_upperShootButton;
    private POVButton m_lowerShootButton;

    public Shoot(Shooter shooter, Intake intake, JoystickButton shintakeBtn, JoystickButton shintakehold, BooleanSupplier rightTrig, BooleanSupplier leftTrig, JoystickButton alignBtn, JoystickButton alignBtnSkewed, DoubleSupplier joystick, POVButton up, POVButton down) {
        m_shooter = shooter;
        m_intake = intake;
        m_upperShintakeShootTrigger = rightTrig;
        m_lowerShintakeShootTrigger = leftTrig;
        m_alignButton = alignBtn;
        m_turretControl = joystick;
        m_skewedAlignButton = alignBtnSkewed;
        m_upperShootButton = up;
        m_lowerShootButton = down;
        m_shintakeButton = shintakeBtn;
        m_shintakeHoldButton = shintakehold;
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
        m_shooter.stopMotor();
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
        } else if (m_alignButton.get()) {
             m_shooter.visionAlign();
        } else if (m_skewedAlignButton.get()){
            m_shooter.skewedVisionAlign();
        } else m_shooter.stopTurret();  

        if (m_shintakeButton.get()) {
            m_intake.setShintake();
        }  else if (m_shintakeHoldButton.get()) {
            m_intake.holdShintake(); 
        } else if (m_upperShintakeShootTrigger.getAsBoolean()) {
            if (m_shooter.getRPM() > Constants.Shooter.upperRPM - 20) {
                m_intake.setShintake();
            } else m_intake.stopShintake();

            m_shooter.setRPM(Constants.Shooter.upperRPM);
        } else if (m_lowerShintakeShootTrigger.getAsBoolean()) {
            if (m_shooter.getRPM() > Constants.Shooter.lowerRPM - 20) {
                m_intake.setShintake();
            } else m_intake.stopShintake();

            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        } else if (m_upperShootButton.get()) {
            m_shooter.setRPM(Constants.Shooter.upperRPM);
        } else if (m_lowerShootButton.get()) {
            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        } else {
            m_intake.stopShintake();
            m_shooter.stopMotor();
        }

        
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