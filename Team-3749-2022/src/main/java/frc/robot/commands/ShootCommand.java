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
    
    private int dir = 1;

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
        if (Controls.Shooter.shootTrigger.getAsBoolean()){
            m_shooter.setShooter();
        }

        if (Controls.Shooter.dirBtn.getAsBoolean()) dir = -dir;   
        if (Controls.Shooter.turnTurretTrigger.getAsDouble() > 0) m_shooter.setTurretMotor(dir*Controls.Shooter.turnTurretTrigger.getAsDouble());
        else m_shooter.visionAlign();
        
        // displays dist from hub on smart dashboard
        SmartDashboard.putNumber("Hub Distance: ", m_shooter.getDistance());
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
