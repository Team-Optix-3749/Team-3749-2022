
// Intake real
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * Intake Subsystem
 * 
 * @author Nikhil Lalwani
 * @author Junnie Niu
 * @author Aniket Chakradeo
 * 
 */

package frc.robot.subsystems;
 
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Intake extends SubsystemBase {
    public CANSparkMax m_intakeMotor;

    private final Compressor m_comp = new Compressor(0, PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid m_rightPiston;
    private final DoubleSolenoid m_leftPiston;

    public Intake(){
        m_rightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Intake.kSolenoidForwardChannel[0], Constants.Intake.kSolenoidReverseChannel[0]);
        m_leftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Intake.kSolenoidForwardChannel[1], Constants.Intake.kSolenoidReverseChannel[1]);

        m_intakeMotor = new CANSparkMax(Constants.Intake.intakeMotor, MotorType.kBrushless);
    }

    public void stopMotors() {
        m_intakeMotor.set(0);
    }

    public void setIntake(int dir){
        m_intakeMotor.set(dir*Constants.Intake.kIntakeSpeed);
    }

    public void startCompressor(){
        m_comp.enableDigital();
    }

    public void stopCompressor() {
        m_comp.disable();
    }

    public void intakePneumatics(DoubleSolenoid.Value val){
        m_rightPiston.set(val);
        m_leftPiston.set(val);
    }
}
