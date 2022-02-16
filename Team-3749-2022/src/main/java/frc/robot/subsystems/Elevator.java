// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import com.revrobotics.ColorSensorV3;


public class Elevator extends SubsystemBase {
    
    public CANSparkMax m_rightTilt;
    // public CANSparkMax m_leftTilt = new CANSparkMax(Constants.Elevator.leftTilt, MotorType.kBrushless);
    private RelativeEncoder m_rightTiltEncoder = m_rightTilt.getEncoder();
    // private CANEncoder m_leftTiltEncoder = m_leftTilt.getEncoder();
    private final PIDController m_tiltPIDController = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    // public CANSparkMax m_chain;
    // private final PIDController m_chainPIDContoller = new PIDController(Constants.Auto.kP, Constants.Auto.kI, Constants.Auto.kD);

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    // private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);


    public Elevator() {
        m_rightTilt = new CANSparkMax(10, MotorType.kBrushless);
        m_rightTilt.setIdleMode(IdleMode.kBrake);

        m_rightTiltEncoder = m_rightTilt.getEncoder();

        // m_leftTilt.setIdleMode(IdleMode.kBrake);

        // m_chain = new CANSparkMax(Constants.Elevator.chain, MotorType.kBrushless);
        // m_chain.setIdleMode(IdleMode.kBrake);        
    }

    private void setTiltMotors(double speed) {
        m_rightTilt.set(speed);
        // m_leftTilt.set(speed);
    }

    public double getTiltEncoders() {
        // return (m_rightTiltEncoder.getPosition() + m_leftTiltEncoder.getPosition())/2;
        return m_rightTiltEncoder.getPosition();
    }
    
    public void resetEncoders(){
        m_rightTiltEncoder.setPosition(0.0);
        // m_leftTiltEncoder.setPosition(0.0);
    }

    public void tiltForward(double target) {
        //setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), 10));
        if (Math.abs(target-getTiltEncoders())<.15) setTiltMotors(.02);
        else setTiltMotors(.03);
    }

    public void tiltForwardIncrement() {
        // setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), m_tiltPIDController.calculate(getTiltEncoders() + 0.1)));
    }

    public void tiltBackIncrement() {
        // setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), m_tiltPIDController.calculate(getTiltEncoders() - 0.1)));
    }

    public void tiltBack() {
        // setTiltMotors(m_tiltPIDController.calculate(getTiltEncoders(), 0));
    }

    public void extendUp () {
        // m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 10));
    }

    public void raise() {
        // m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 0));
    }

    public void stopMotors(){
        setTiltMotors(0.0);
    }

//     public void getColor(){
//         Color detectedColor = m_colorSensor.getColor();      
//         double IR = m_colorSensor.getIR();

//         // System.out.println("RED: " + detectedColor.red);
//         // System.out.println("BLUE: " + detectedColor.blue);

//         // System.out.println("GREEN: " + detectedColor.green );

// r
//         if (detectedColor.red >= .52) System.out.println("RED BALL");
//         else if (detectedColor.blue >= .3) System.out.println("BLUE BALL");
//         else System.out.println("NO BALL");

//     }

}
