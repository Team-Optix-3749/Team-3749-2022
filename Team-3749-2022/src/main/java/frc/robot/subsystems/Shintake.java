package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Constants.Shintake.BallColor;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shintake extends SubsystemBase{
    public CANSparkMax m_shintakeFront;
    public CANSparkMax m_shintakeBack; 

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    
    public Shintake(){
        m_shintakeFront = new CANSparkMax(Constants.Shintake.shintakeFront, MotorType.kBrushless);
        m_shintakeBack = new CANSparkMax(Constants.Shintake.shintakeBack, MotorType.kBrushless);
        
        m_shintakeBack.setInverted(true);
    }

    public void setShintake (double dir) {
        m_shintakeFront.set(dir*Constants.Shintake.kShintakeSpeed);
        m_shintakeBack.set(dir*-Constants.Shintake.kShintakeSpeed);
    }

    public void stopMotors () {
        setShintake(0);
    }

    public BallColor getColor(){
        Color detectedColor = m_colorSensor.getColor();      
        if (detectedColor.red >= .52) return BallColor.RED;
        else if (detectedColor.blue >= .3) return BallColor.BLUE;
        else return BallColor.NULL;
    }
}
