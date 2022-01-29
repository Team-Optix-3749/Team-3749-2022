
// Intake real
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;
 
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Intake extends SubsystemBase {
  public CANSparkMax m_frontIntakeMotor;
  public CANSparkMax m_leftShintakeMotor;
  public CANSparkMax m_rightShintakeMotor; 

  int backupPortNumber = 100;
  int my_front_motor_port = Constants.Intake.intakeFront;

  public Intake(){
    m_frontIntakeMotor = new CANSparkMax(Constants.Intake.intakeFront, MotorType.kBrushless);
    m_leftShintakeMotor = new CANSparkMax(Constants.Intake.intakeLeft, MotorType.kBrushless);
    m_rightShintakeMotor = new CANSparkMax(Constants.Intake.intakeRight, MotorType.kBrushless);
    
    m_rightShintakeMotor.setInverted(true);
  }

  
public void setShintake (double speed) {
  m_leftShintakeMotor.set(speed);
  m_rightShintakeMotor.set(speed);
}
//beans
  public void IntakeIn(){
    m_frontIntakeMotor.set(Constants.Intake.intakeSpeed);
  }

  public void IntakeOut(){
    m_frontIntakeMotor.set(-Constants.Intake.intakeSpeed);
  }

 public void AlternateIntakePort(){
    if ( CheckPorts(my_front_motor_port) != true){
      if ( CheckPorts(backupPortNumber) == true){

        m_frontIntakeMotor = new CANSparkMax(backupPortNumber,MotorType.kBrushless);

      }
    }
 }
 public boolean CheckPorts(int port){
    return true;
 }

// no mas errors but how do I fix the speedcontroller groups they dont exist anymore
 //@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
 
  //@Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
 


