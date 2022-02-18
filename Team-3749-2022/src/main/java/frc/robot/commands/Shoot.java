package frc.robot.commands;

import java.util.function.DoubleSupplier;

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
    private final DoubleSupplier m_triggerInput;

    public Shoot(Shooter shooter, DoubleSupplier triggerInput){
        m_shooter = shooter;
        m_triggerInput = triggerInput;
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

        if(m_triggerInput.getAsDouble() > 0){
            m_shooter.setTurretMotor(-m_triggerInput.getAsDouble()*0.2);
        }
        if(Xbox.XBOX_X.getAsBoolean() == true){
            m_shooter.setTurretMotor(0.2);
        }
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