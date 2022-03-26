package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

public class Turret extends SubsystemBase {
    private CANSparkMax m_turretMotor = new CANSparkMax(Constants.Shooter.turretMotor, MotorType.kBrushless);
    private RelativeEncoder m_turretEncoder = m_turretMotor.getEncoder();
    private PIDController m_pidTurretController = new PIDController(0.01, 0.4, 0.0);

    public Turret () {
        m_turretMotor.setInverted(true);
        m_turretEncoder.setPositionConversionFactor(Constants.Shooter.gearRatio);
    }

    public void setTurretMotor(double speed) {
        if (Math.abs(m_turretEncoder.getPosition()) <= .23) {
                m_turretMotor.set(speed);
        }
        else if (m_turretEncoder.getPosition() * speed < 0){ //Checks if speed and encoder position have opposite signs
            m_turretMotor.set(speed);
            if (m_turretEncoder.getPosition() < 0) {m_turretMotor.set(Math.abs(speed));}
            else if (m_turretEncoder.getPosition() > 0) m_turretMotor.set(-Math.abs(speed));
        }
         else m_turretMotor.set(0);
    } 

    public void setTurretPosition(double position) {
        // if(m_turretEncoder.getPosition() + 0.01 > position || m_turretEncoder.getPosition() - 0.01 < position) 
        setTurretMotor(m_pidTurretController.calculate(m_turretEncoder.getPosition()*(1/27), position));
        // else m_turretMotor.set(0);
    }

    public double getTurretPosition(){
        return m_turretEncoder.getPosition();
    }

    public void resetTurret() {
        setTurretPosition(0);
    }

    public void setTurretRaw(double speed) {
        m_turretMotor.set(speed * 0.05);
    }

    public void stopTurret() {
        m_turretMotor.stopMotor();
    }
    
    public void skewedVisionAlign() {
        double hubX = Constants.Auto.tx.getDouble(3749) + 1;
        SmartDashboard.putNumber("Hub Alignment (-3 < x < 3)", hubX - 1);
        if (hubX != 3750) setTurretMotor(hubX * 0.015); 
        else stopTurret();
    }

    public void visionAlign() {
        double hubX = Constants.Auto.tx.getDouble(3749);
        SmartDashboard.putNumber("Hub Alignment (-3 < x < 3)", hubX);
        if (hubX != 3749) setTurretMotor(hubX * 0.015); 
        else stopTurret();
    }

    
    public void resetEncoder(){
        m_turretEncoder.setPosition(0);
    }
}
