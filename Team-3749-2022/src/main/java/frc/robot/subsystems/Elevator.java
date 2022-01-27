// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.sensors.WPI_CANCoder;

import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    public CANSparkMax m_rightTilt;
    public CANSparkMax m_leftTilt;
    private final PIDController m_tiltPIDController = new PIDController(Constants.Auto.kP, Constants.Auto.kI, Constants.Auto.kD);

    private void setTiltMotors(double speed) {
        m_rightTilt.set(speed);
        m_leftTilt.set(speed);
    }
    
    public CANSparkMax m_chain;

    public Elevator() {
        m_rightTilt = new CANSparkMax(Constants.Elevator.rightTilt, MotorType.kBrushless);
        m_rightTilt.setIdleMode(IdleMode.kCoast);

        m_leftTilt = new CANSparkMax(Constants.Intake.liftMotor, MotorType.kBrushless);
        m_leftTilt.setIdleMode(IdleMode.kBrake);

        m_chain = new CANSparkMax(Constants.Intake.intakeMotor, MotorType.kBrushless);
        m_chain.setIdleMode(IdleMode.kBrake);
    }

    public void tiltForward() {
        m_rightTilt.set(0);

    }




}
