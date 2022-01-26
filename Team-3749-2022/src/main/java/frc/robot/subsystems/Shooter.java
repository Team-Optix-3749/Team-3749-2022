package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
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
    private MotorControllerGroup m_shooterMotor = new MotorControllerGroup(m_motorLeft, m_motorRight);

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
}
