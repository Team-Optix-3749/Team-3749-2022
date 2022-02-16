package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Xbox;
import frc.robot.subsystems.Shooter;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shoot extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private boolean visionToggle = true;

    private final Shooter m_shooter;

    public Shoot(Shooter shooter){
        m_shooter = shooter;
        addRequirements(shooter);
    }
    
    @Override
    public void initialize() {
        m_shooter.resetEncoder();
    }

    @Override
    public void execute() {
        // CALCULATE SPEEDS BASED ON DIST FROM HUB
        if(Xbox.rightTriggerValue.getAsDouble() > 0){
            m_shooter.setShintake(Constants.Intake.kIntakeSpeed); 
            m_shooter.setShooter();
        }
        if(Xbox.XBOX_X.getAsBoolean()) visionToggle = !visionToggle;
        m_shooter.visionAlign(visionToggle);
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