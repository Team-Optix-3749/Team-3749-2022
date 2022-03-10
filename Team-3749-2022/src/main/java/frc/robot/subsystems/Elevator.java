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

    private final PIDController m_tiltPIDController = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI,
            Constants.Elevator.kD);

    private CANSparkMax m_chain = new CANSparkMax(Constants.Elevator.chain, MotorType.kBrushless);

    private final PIDController m_chainPIDContoller = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI,
            Constants.Elevator.kD);

    public Elevator() {
        m_rightTilt.setIdleMode(IdleMode.kCoast);

        m_leftTilt.setIdleMode(IdleMode.kBrake);

        m_chain.setInverted(true);
        m_chain.setIdleMode(IdleMode.kBrake);
    }

    public void rawTilt(double speed) {
        tilt.set(speed);
    }

    public void stopMotors() {
        rawTilt(0.0);
        m_chain.set(0.0);
    }
}