package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.Controls;

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
        if (Controls.Shooter.shootTrigger.getAsDouble() > 0) m_shooter.setShooter();
        else m_shooter.setShooter();

        if (Controls.Shooter.turretJoystick.getAsDouble() != 0) m_shooter.setTurretMotor(Controls.Shooter.turretJoystick.getAsDouble());
        else m_shooter.visionAlign();
        System.out.println(Controls.Shooter.turretJoystick.getAsDouble());
        
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
