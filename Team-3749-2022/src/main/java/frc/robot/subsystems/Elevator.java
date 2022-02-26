// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

/* COLOR SENSOR IMPS
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
*/

public class Elevator extends SubsystemBase {
    
    public CANSparkMax m_rightTilt;
    public CANSparkMax m_leftTilt;

    private RelativeEncoder m_rightTiltEncoder = m_rightTilt.getEncoder();
    private RelativeEncoder m_leftTiltEncoder = m_leftTilt.getEncoder();

    private final PIDController m_tiltPIDController = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    public CANSparkMax m_chain;

    private final PIDController m_chainPIDContoller = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    public Elevator() {
        m_rightTilt = new CANSparkMax(Constants.Elevator.rightTilt, MotorType.kBrushless);
        m_rightTilt.setIdleMode(IdleMode.kCoast);

        m_leftTilt = new CANSparkMax(Constants.Elevator.leftTilt, MotorType.kBrushless);
        m_leftTilt.setIdleMode(IdleMode.kBrake);

        m_chain = new CANSparkMax(Constants.Elevator.chain, MotorType.kBrushless);
        m_chain.setIdleMode(IdleMode.kBrake);  
    }

    private void setTiltMotors(double dist) {
        m_rightTilt.set(m_tiltPIDController.calculate(getTiltEncoders(), dist));
        m_leftTilt.set(m_tiltPIDController.calculate(getTiltEncoders(), dist));
    }

    public double getTiltEncoders() {
        return (m_rightTiltEncoder.getPosition() + m_leftTiltEncoder.getPosition())/2;
    }
    
    public void resetEncoders(){
        m_rightTiltEncoder.setPosition(0.0);
        m_leftTiltEncoder.setPosition(0.0);
    }

    public void tilt(double target) {
        setTiltMotors(target);
    }

    public void tiltIncrement(double dist) {
        setTiltMotors(getTiltEncoders() + dist);
    }

    public void extendUp () {
        m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 10));
    }

    public void extendClimber(double target) {
        m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), target));
    }

    public void stopMotors(){
        setTiltMotors(0.0);
        m_chain.set(0.0);
    }
}
