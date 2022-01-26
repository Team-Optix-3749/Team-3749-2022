package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;


/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shoot extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter m_shooter;
    public Shoot(Shooter shooter){
        m_shooter = shooter;
        addRequirements(shooter);
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.setShooter(m_shooter.getLeftVelocity(), Constants.Shooter.targetRPM);

    }

    @Override
    public void end(boolean interrupted){
        m_shooter.stopMotor();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}