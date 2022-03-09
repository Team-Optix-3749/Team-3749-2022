package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Controls;
import frc.robot.utilities.Xbox;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class ShootCommand extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Shooter m_shooter;

    public ShootCommand(Shooter shooter){
        m_shooter = shooter;
        addRequirements(shooter);
    }
    
    @Override
    public void initialize() {
        m_shooter.resetEncoder();
    }

    @Override
    public void execute() {
        double shootCtrl = Constants.round(Controls.Shooter.shootTrigger.getAsDouble());
        double otherShootCtrl = Constants.round(Controls.Shooter.otherShootTrigger.getAsDouble());

        if (shootCtrl > 0) m_shooter.setShooter();
        // else if(Controls.testBtn.getAsBoolean()) m_shooter.setShooter();
        else if(otherShootCtrl > 0.1) m_shooter.setVelocity();
        else m_shooter.stopMotors();

        double turretControl = Constants.round(Controls.Shooter.turretJoystick.getAsDouble());
        if (turretControl < -.1 || turretControl > .1) m_shooter.setTurretMotor(.1*Controls.Shooter.turretJoystick.getAsDouble());
        // else m_shooter.visionAlign();
        
        // displays dist from hub on smart dashboard
        // SmartDashboard.putNumber("Hub Distance: ", m_shooter.getDistance());
    }

    @Override
    public void end(boolean interrupted){
        m_shooter.stopMotors();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
