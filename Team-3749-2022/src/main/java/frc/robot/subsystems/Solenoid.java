package frc.robot.subsystems;
 
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj.Compressor.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants.Pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

public class Solenoid extends SubsystemBase {
    private final Compressor m_comp = new Compressor(0, PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid[] m_doubleSolenoid = new DoubleSolenoid[2];

    public Solenoid () {
        for (int i = 0; i<2; i++) {
            m_doubleSolenoid[i] = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Pneumatics.kSolenoidForwardChannel[i], Pneumatics.kSolenoidReverseChannel[i]);
        }
    }
 
    public void forward(int pos) {
        m_doubleSolenoid[pos].set(kForward);
    }
    public void reverse(int pos) {
        m_doubleSolenoid[pos].set(kReverse);
    }

    public void off(int pos) {
        m_doubleSolenoid[pos].set(kOff);
    } 

    public void offAll() {
        for (int pos = 0; pos<m_doubleSolenoid.length; pos++) m_doubleSolenoid[pos].set(kOff);
    }
    
    public void forwardAll() {
        for (int pos = 0; pos<m_doubleSolenoid.length; pos++) m_doubleSolenoid[pos].set(kOff);
    }
    public void reverseAll() {
        for (int pos = 0; pos<m_doubleSolenoid.length; pos++) m_doubleSolenoid[pos].set(kOff);
    }



    public void loopControl(){
        m_comp.enableDigital();
    }

    public void disableLoopControl() {
        m_comp.disable();
    }
    
  public void IntakePneumatics(DoubleSolenoid.Value val){
    m_doubleSolenoid[0].set(val);
    m_doubleSolenoid[1].set(val);
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
