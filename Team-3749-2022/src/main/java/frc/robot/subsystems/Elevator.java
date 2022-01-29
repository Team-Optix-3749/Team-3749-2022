// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    public CANSparkMax m_rightTilt;
    public CANSparkMax m_leftTilt;
    private CANEncoder m_rightTiltEncoder = m_rightTilt.getEncoder();
    private CANEncoder m_leftTiltEncoder = m_leftTilt.getEncoder();
    private final PIDController m_tiltPIDController = new PIDController(Constants.Auto.kP, Constants.Auto.kI, Constants.Auto.kD);

    public CANSparkMax m_chain;
    private final PIDController m_chainPIDContoller = new PIDController(Constants.Auto.kP, Constants.Auto.kI, Constants.Auto.kD);


    public Elevator() {
        m_rightTilt = new CANSparkMax(Constants.Elevator.rightTilt, MotorType.kBrushless);
        m_rightTilt.setIdleMode(IdleMode.kCoast);

        m_leftTilt = new CANSparkMax(Constants.Intake.liftMotor, MotorType.kBrushless);
        m_leftTilt.setIdleMode(IdleMode.kBrake);

        m_chain = new CANSparkMax(Constants.Intake.intakeMotor, MotorType.kBrushless);
        m_chain.setIdleMode(IdleMode.kBrake);
    }

    private void setTiltMotors(double speed) {
        m_rightTilt.set(speed);
        m_leftTilt.set(speed);
    }

    public double getTiltEncoders() {
        return (m_rightTiltEncoder.getPosition() + m_leftTiltEncoder.getPosition())/2;
    }
    
    public void resetEncoders(){
        m_rightTiltEncoder.setPosition(0.0);
        m_leftTiltEncoder.setPosition(0.0);
    }

    public void tiltForward() {
        //setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), 10));
        setTiltMotors(0.5);
    }

    public void tiltForwardIncrement() {
        setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), m_tiltPIDController.calculate(getTiltEncoders() + 0.1)));
    }

    public void tiltBackIncrement() {
        setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), m_tiltPIDController.calculate(getTiltEncoders() - 0.1)));
    }

    public void tiltBack() {
        setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), 0));
    }

    public void extendUp () {
        m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 10));
    }

    public void raise() {
        m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 0));
    }

}
