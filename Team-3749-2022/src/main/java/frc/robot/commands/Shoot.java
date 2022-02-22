package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Xbox;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shoot extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private boolean visionToggle;
    private boolean dir;

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
        // runs shooter w/calcuated speed and shintake when right trig is held down
        if(Xbox.rightTriggerValue.getAsDouble() > 0 && !Xbox.XBOX_A.get()){
            m_shooter.setShintake(Constants.Intake.kIntakeSpeed); 
            m_shooter.setShooter();
        }

        // turns off vision align when pressed && allows for manual control of turret
        if(Xbox.XBOX_A.get()) visionToggle = !visionToggle;
        m_shooter.visionAlign(visionToggle);
        

        // toggles neg or pos speed of turret when left trigger is held
        if(Xbox.XBOX_Y.get()) dir = !dir;   
        if (visionToggle == false) {
            if((Xbox.leftTriggerValue.getAsDouble() > 0) && (dir == false)) m_shooter.setTurretMotor(Xbox.leftTriggerValue.getAsDouble());
            else if (Xbox.leftTriggerValue.getAsDouble() > 0 && (dir == true)) m_shooter.setTurretMotor(-Xbox.leftTriggerValue.getAsDouble());
        }

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