package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

public class Shintake extends SubsystemBase {
    
    public CANSparkMax m_shintakeFront = new CANSparkMax(Constants.Shintake.shintakeFront, MotorType.kBrushless);
    public CANSparkMax m_shintakeBack = new CANSparkMax(Constants.Shintake.shintakeBack, MotorType.kBrushless);
    
    public Shintake() {
        m_shintakeFront.setIdleMode(IdleMode.kBrake);
        m_shintakeBack.setIdleMode(IdleMode.kBrake);

        m_shintakeFront.setInverted(true);
    }

    public void setShintakeVoltage(double volts) {
        m_shintakeFront.setVoltage(volts);
        m_shintakeBack.setVoltage(volts);
    }

    public void holdShintake() {
        m_shintakeFront.set(Constants.Shintake.kShintakeSpeed);
        m_shintakeBack.set(-Constants.Shintake.kShintakeSpeed);
    }

    public void setShintake() {
        m_shintakeFront.set(Constants.Shintake.kShintakeSpeed);
        m_shintakeBack.set(Constants.Shintake.kShintakeSpeed);
    }

    public void stopShintake() {
        m_shintakeFront.stopMotor();
        m_shintakeBack.stopMotor();
    }

}
