package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shooter extends SubsystemBase{
    private CANSparkMax m_shooterMotor;
    private CANPIDController m_controller;
    private CANEncoder m_encorder;
    

    private PIDController m_pidController;
    
    private WPI_TalonSRX m_belt_front = new WPI_TalonSRX(Constants.Shooter.belt_front);
    private VictorSPX m_belt_back = new VictorSPX(Constants.Shooter.belt_back);

    public Shooter(){
        //Get shooter motor
        m_shooterMotor = new CANSparkMax(Constants.Shooter.motor, MotorType.kBrushless);
        //Don't brake it if you don't need to
        m_shooterMotor.setIdleMode(IdleMode.kCoast);
        //Shoots out of the front, not the intake
        m_shooterMotor.setInverted(true);
        //Get shooter encoder
        m_encorder = m_shooterMotor.getEncoder();
        //Link encoder to controller
        m_controller = m_shooterMotor.getPIDController();
        m_controller.setFeedbackDevice(m_encorder);
        m_controller.setP(Constants.Shooter.kP);
        m_controller.setI(Constants.Shooter.kI);
        m_controller.setD(Constants.Shooter.kD);
        //reset the motor
        if (resetShooter()){
            System.out.println("Motor cannot reset. May prevent PID from functioning properlly.");
        }
        //set the PID values
        m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD);
        //reverse the belt so that it intakes
        m_belt_back.setInverted(true);
    }

    public boolean resetShooter(){
        m_controller.setReference(0, ControlType.kDutyCycle);
        m_shooterMotor.stopMotor();
        return m_encorder.getVelocity() > 0.1;
    }
    public double getVelocity(){
        return m_encorder.getVelocity();
    }
    public boolean setShooter(double current, double target){
        m_shooterMotor.set(m_pidController.calculate(current, target) * 0.004);
        System.out.println(m_pidController.calculate(current, target) * 0.004);
        if (m_pidController.calculate(current, target) * 0.004 == 0){
            return true;
        }
        return false;
    }
    public void beltUp() {
        m_belt_front.set(ControlMode.PercentOutput, -Constants.Shooter.beltSpeed);
        m_belt_back.set(ControlMode.PercentOutput, -Constants.Shooter.beltSpeed);
    }
    public void beltDown() {
        m_belt_front.set(ControlMode.PercentOutput, Constants.Shooter.beltSpeed);
        m_belt_back.set(ControlMode.PercentOutput, Constants.Shooter.beltSpeed);
    }
    public void beltStop() {
        m_belt_front.set(ControlMode.PercentOutput,0.0);
        m_belt_back.set(ControlMode.PercentOutput,0.0);
    }
    public void shoot(){
        m_controller.setReference(Constants.Shooter.maxVoltage, ControlType.kDutyCycle);
    }
}
