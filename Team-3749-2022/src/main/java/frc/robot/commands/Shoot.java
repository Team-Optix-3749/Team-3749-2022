package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        if(Xbox.rightTriggerValue.getAsDouble() > 0 && !Xbox.XBOX_A.get()){
            m_shooter.setShintake(Constants.Intake.kIntakeSpeed); 
            double shootSpeed = m_shooter.getDistance() * 5600;
            m_shooter.setShooter(shootSpeed);
        }
        if(Xbox.XBOX_X.getAsBoolean()) visionToggle = !visionToggle;
        m_shooter.visionAlign(visionToggle);

        // if(Xbox.leftTriggerValue.getAsDouble() > 0){
        //     m_shooter.setTurretMotor(-0.2);
        // }
        // if(Xbox.XBOX_A.getAsBoolean() == true){
        //     m_shooter.setTurretMotor(0.2);
        // }

        if(Xbox.XBOX_A.get()){   //Manual control for turret while pressing A, mainly for troubleshooting
            if(Xbox.rightTriggerValue.getAsDouble() > 0) m_shooter.setTurretMotor(Xbox.rightTriggerValue.getAsDouble() * 0.2);
            if(Xbox.leftTriggerValue.getAsDouble() > 0) m_shooter.setTurretMotor(-Xbox.leftTriggerValue.getAsDouble() * 0.2);
        }

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