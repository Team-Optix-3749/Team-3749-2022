
// Intake real
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;
 
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Intake extends SubsystemBase {
    public CANSparkMax m_frontMotor;
    public CANSparkMax m_leftMotor;
    public CANSparkMax m_rightMotor; 
  
    int backupPortNumber = 100;
    int my_front_motor_port = Constants.CAN.intake_front;

//Intake myIntake = new Intake(my_front_motor_port);

    public Intake(){
      m_frontMotor = new CANSparkMax(Constants.CAN.intake_front, MotorType.kBrushless);
      m_leftMotor = new CANSparkMax(Constants.CAN.intake_left, MotorType.kBrushless);
      m_rightMotor = new CANSparkMax(Constants.CAN.intake_right, MotorType.kBrushless);

      m_rightMotor.setInverted(true);
      final SpeedControllerGroup m_shintake = new SpeedControllerGroup(m_leftMotor, m_rightMotor);
      m_shintake.set(Constants.CAN.shintake_speed);
    }


public void IntakeIn(){
  m_frontMotor.set(Constants.CAN.intakein_speed);
 }

 public void IntakeOut(){
  m_frontMotor.set(-Constants.CAN.intakein_speed);
 }

 public void AlternateIntakePort(){
    if ( CheckPorts(my_front_motor_port) != true){
      if ( CheckPorts(backupPortNumber) == true){

        m_frontMotor = new CANSparkMax(backupPortNumber,MotorType.kBrushless);

      }
    }
 }
 public boolean CheckPorts(int port){
    return true;
 }

 public void Shintake(){
    // m_rightMotor.setInverted(true);
    // final SpeedControllerGroup m_shintake = new SpeedControllerGroup(m_leftMotor, m_rightMotor);
    // m_shintake.set(Constants.CAN.shintake_speed);
 }
 
 //@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
 
  //@Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
 


