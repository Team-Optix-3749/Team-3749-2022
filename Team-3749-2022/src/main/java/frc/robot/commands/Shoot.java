package frc.robot.commands;

// import java.util.Timer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;


/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shoot extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter m_shooter;
    private double time;
    public Shoot(Shooter shooter){
        m_shooter = shooter;
        addRequirements(shooter);
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.setShooter(Constants.Shooter.maxRPM, 5000);
        if (m_shooter.getVelocity() >= 300){
            m_shooter.beltDown();
        }
        // m_shooter.beltDown();
    }

    @Override
    public void end(boolean interrupted){
        m_shooter.resetShooter();
        m_shooter.beltUp();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}