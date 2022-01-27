package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shooter extends SubsystemBase{
    private WPI_TalonFX m_motorLeft = new WPI_TalonFX(Constants.Shooter.motorLeft);
    private WPI_TalonFX m_motorRight = new WPI_TalonFX(Constants.Shooter.motorRight);
    
    private CANSparkMax m_turretMotor = new CANSparkMax(Constants.Shooter.turretMotor, MotorType.kBrushless);
    private CANEncoder m_turretEncoder = m_turretMotor.getEncoder();


    private MotorControllerGroup m_shooterMotor = new MotorControllerGroup(m_motorLeft, m_motorRight);

    NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = m_table.getEntry("tx");
    
    private PIDController m_pidController;
    public Shooter(){
        m_motorRight.setInverted(true);
    }

    public void stopMotor(){
        m_shooterMotor.set(0);
    }

    public double getLeftVelocity(){
        return m_motorLeft.getSelectedSensorVelocity();
    }

    public double getRightVelocity(){
        return m_motorRight.getSelectedSensorVelocity();
    }

    public void setShooter(double current, double target){
        m_shooterMotor.set(m_pidController.calculate(current, target) * 0.004);
    }

    public void setTurretMotor(double speed){
        m_turretMotor.set(speed);
    }
    public void visionAlign(){
        double x = tx.getDouble(0.0);
        double multiplier = 1;
        if (x<=5){
          multiplier = 5;
        }
        else if (x>=5){
          multiplier = 3;
        }
        else if (x>=10){
          multiplier = 2;
        }
        double input = x * Constants.Vision.kVisionP * multiplier;
        if (input>1){
            m_turretMotor.set(0.8*input);
        }
      }

    public void visionAlign2(){

    }
    
}
