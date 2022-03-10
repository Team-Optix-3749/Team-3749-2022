package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

public class Elevator extends SubsystemBase {

    private CANSparkMax m_rightTilt = new CANSparkMax(Constants.Elevator.rightTilt, MotorType.kBrushless);
    private CANSparkMax m_leftTilt = new CANSparkMax(Constants.Elevator.leftTilt, MotorType.kBrushless);
    private MotorControllerGroup tilt = new MotorControllerGroup(m_leftTilt, m_rightTilt);

    private RelativeEncoder m_rightTiltEncoder = m_rightTilt.getEncoder();
    private RelativeEncoder m_leftTiltEncoder = m_leftTilt.getEncoder();

    private final PIDController m_tiltPID = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI,
            Constants.Elevator.kD);

    private CANSparkMax m_chain = new CANSparkMax(Constants.Elevator.chain, MotorType.kBrushless);

    private RelativeEncoder m_chainEncoder = m_chain.getEncoder();

    private final PIDController m_chainPIDContoller = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI,
            Constants.Elevator.kD);

    public Elevator() {
        m_rightTilt.setIdleMode(IdleMode.kBrake);
        m_rightTiltEncoder.setPositionConversionFactor(9);

        m_leftTilt.setIdleMode(IdleMode.kBrake);
        m_leftTiltEncoder.setPositionConversionFactor(9);

        m_chain.setInverted(true);
        m_chain.setIdleMode(IdleMode.kBrake);
    }

    public double getTilt() {
        return (m_rightTiltEncoder.getPosition() + m_leftTiltEncoder.getPosition())/2;
    }

    public double getChain() {
        return m_chainEncoder.getPosition();
    }

    public void rawTilt(double speed) {
        tilt.set(speed);
    }

    public void rawClimb(double speed) {
        m_chain.set(speed);
    }

    public void stopTilt() {
        rawTilt(0.0);
        m_chain.set(0.0);
    }

    public void stopClimb() {
        rawClimb(0);
    }

    public void extend() {
        // rawClimb(m_tiltPID.calculate(getChain(), 3000));
        // Dhruv said 10 in 💀
        // need to test encoder pos (https://frc-pdr.readthedocs.io/en/latest/control/pid_control.html#cascade-elevator)
    }

    public void lift() {
        rawClimb(m_tiltPID.calculate(getChain(), 0));
    }

    public void tilt() {
        // rawClimb(m_tiltPID.calculate(getChain(), 3000));
        // need to test encoder pos (https://frc-pdr.readthedocs.io/en/latest/control/pid_control.html#cascade-elevator)
    }

    public void untilt() {
        rawTilt(m_tiltPID.calculate(getChain(), 0));
    }
}