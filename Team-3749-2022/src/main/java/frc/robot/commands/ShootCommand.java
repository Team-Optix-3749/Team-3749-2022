package frc.robot.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shintake;
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
    private final Shintake m_shintake;

    public ShootCommand(Shooter shooter, Shintake shintake){
        m_shooter = shooter;
        m_shintake = shintake;
        addRequirements(shooter, shintake);
    }
    
    @Override
    public void initialize() {
        m_shooter.resetEncoder();
    }

    @Override
    public void execute() {
        // m_shooter.setShooter();
        double shootCtrl = Constants.round(Controls.Shooter.shootTrigger.getAsDouble());
        // double otherShootCtrl = Constants.round(Controls.Shooter.otherShootTrigger.getAsDouble());
        m_shooter.setShooter();
        
        if (shootCtrl > 0) {
            System.out.println("hdasjhgdf");
            // try { TimeUnit.SECONDS.sleep(2); } catch (Exception e) {}
            // m_shintake.runShintake();
            // m_shintake.runShintake();
        } 
        // else if(Controls.testBtn.getAsBoolean()) m_shooter.setShooter();
        // else if(otherShootCtrl > 0) m_shooter.setVelocity();

        else {
            m_shooter.stopShooterMotors();
            m_shintake.stopMotors(); 
        }

        // double turretControl = Constants.round(Controls.Shooter.turretJoystick.getAsDouble());
        // if (turretControl == 0.0) m_shooter.setTurretMotor(.1*Controls.Shooter.turretJoystick.getAsDouble());
        // else m_shooter.stopTurretMotors(0);// m_shooter.visionAlign();
        
        // displays dist from hub on smart dashboard
        // SmartDashboard.putNumber("Hub Distance: ", m_shooter.getDistance());
    }

    @Override
    public void end(boolean interrupted){
        m_shooter.stopShooterMotors();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
