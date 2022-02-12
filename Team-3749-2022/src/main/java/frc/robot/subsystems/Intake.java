
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
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants.Pneumatics;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import java.util.ArrayList;

public class Intake extends SubsystemBase {
    public CANSparkMax m_intakeMotor;

    private final Compressor m_comp = new Compressor(0, PneumaticsModuleType.CTREPCM);
    private final ArrayList<DoubleSolenoid> m_doubleSolenoid = new ArrayList<DoubleSolenoid>(2);

    public Intake(){
        for (int i = 0; i<2; i++) {
            m_doubleSolenoid.set(i,new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Pneumatics.kSolenoidForwardChannel[i], Pneumatics.kSolenoidReverseChannel[i]));
        }
        m_intakeMotor = new CANSparkMax(Constants.Shooter.shintakeFront, MotorType.kBrushless);
    }

    public void setIntake(int dir){
        m_intakeMotor.set(dir*Constants.Intake.kIntakeSpeed);
    }

    public void loopControl(){
        m_comp.enableDigital();
    }

    public void disableLoopControl() {
        m_comp.disable();
    }

    public void intakePneumatics(DoubleSolenoid.Value val){
        for (var i : m_doubleSolenoid) i.set(val);
    }
}