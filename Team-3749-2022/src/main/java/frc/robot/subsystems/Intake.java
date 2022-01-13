// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Intake Subsystem
 * 
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Intake extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  public CANSparkMax m_intakeMotor;
  public CANSparkMax m_liftMotor;
  
  private final Timer m_timer = new Timer();
  
  public Intake() {
    m_intakeMotor = new CANSparkMax(Constants.Intake.intakeMotor, MotorType.kBrushless);
    m_intakeMotor.setIdleMode(IdleMode.kCoast);
    m_intakeMotor.setInverted(true);
    m_liftMotor = new CANSparkMax(Constants.Intake.liftMotor, MotorType.kBrushless);
    
  }
  public void intakeIn() {
    m_intakeMotor.set(Constants.Intake.kIntakeSpeed);
  }

  /**
   * Expel power cells out
   */
  public void intakeOut() {
    m_intakeMotor.set(-Constants.Intake.kIntakeSpeed);
  }

  /**
   * Stop intake
   */
  public void intakeStop() {
    m_intakeMotor.set(0);
  }

  /**
   * Lift intake up
   */
  public void liftUp() {
    m_liftMotor.set(Constants.Intake.kIntakeLiftUpSpeed);
  }

  /**
   * Drop intake down
   */
  public void liftDown() {
    m_liftMotor.set(Constants.Intake.kIntakeLiftDownSpeed);
  }

  /**
   * Drop intake down with timer
   */
  public void liftDownTimer() {
    m_timer.reset();
    m_timer.start();

    while (m_timer.get() < 0.3)
    m_liftMotor.set(Constants.Intake.kIntakeLiftDownSpeed);
  }

  /**
   * Stop intake lift
   */
  public void liftStop() {
    m_liftMotor.set(0);
  }
  /** */

  
}
