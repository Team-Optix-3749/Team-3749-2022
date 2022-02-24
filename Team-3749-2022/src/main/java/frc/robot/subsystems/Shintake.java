package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shintake extends SubsystemBase{
    public CANSparkMax m_shintakeFront;
    public CANSparkMax m_shintakeBack; 

    public Shintake(){
        m_shintakeFront = new CANSparkMax(Constants.Shintake.shintakeFront, MotorType.kBrushless);
        m_shintakeBack = new CANSparkMax(Constants.Shintake.shintakeBack, MotorType.kBrushless);
        
        m_shintakeBack.setInverted(true);
    }

    public void setShintake (double dir) {
        m_shintakeFront.set(dir*Constants.Shooter.kShintakeSpeed);
        m_shintakeBack.set(dir*-Constants.Shooter.kShintakeSpeed);
    }

    public void stopMotors () {
        setShintake(0);
    }
}
