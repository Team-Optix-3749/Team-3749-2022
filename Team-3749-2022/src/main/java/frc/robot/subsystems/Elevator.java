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

/* COLOR SENSOR IMPS
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
*/

public class Elevator extends SubsystemBase {
    
    public CANSparkMax m_rightTilt;
    public CANSparkMax m_leftTilt;

    private CANEncoder m_rightTiltEncoder = m_rightTilt.getEncoder();
    private CANEncoder m_leftTiltEncoder = m_leftTilt.getEncoder();

    private final PIDController m_tiltPIDController = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    public CANSparkMax m_chain;

    private final PIDController m_chainPIDContoller = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    /* 
        COLOR SENSOR VARS
    
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

     */

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

    public void tiltForward(double target) {
        setTiltMotors(10);
    }

    public void tiltForwardIncrement() {
        setTiltMotors(getTiltEncoders() + 0.1);
    }

    public void tiltBackIncrement() {
        setTiltMotors(getTiltEncoders() - 0.1);
    }

    public void tiltBack() {
        setTiltMotors(0);
    }

    public void extendUp () {
        m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 10));
    }

    public void raise() {
        m_chain.set(m_chainPIDContoller.calculate(getTiltEncoders(), 0));
    }

    public void stopMotors(){
        setTiltMotors(0.0);
        m_chain.set(0.0);
    }

    /*
    COLOR SENSOR THING

    public void getColor(){
        Color detectedColor = m_colorSensor.getColor();      
        double IR = m_colorSensor.getIR();

        System.out.println("RED: " + detectedColor.red);
        System.out.println("BLUE: " + detectedColor.blue);
        System.out.println("GREEN: " + detectedColor.green );

        if (detectedColor.red >= .52) System.out.println("RED BALL");
        else if (detectedColor.blue >= .3) System.out.println("BLUE BALL");
        else System.out.println("NO BALL");
    }
    */

}
