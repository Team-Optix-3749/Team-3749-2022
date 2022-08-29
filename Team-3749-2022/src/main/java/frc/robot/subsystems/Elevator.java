// package frc.robot.subsystems;

// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// // import edu.wpi.first.math.controller.PIDController;
// // import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.utilities.Constants;

// public class Elevator extends SubsystemBase {

//     // private CANSparkMax m_chain = new CANSparkMax(Constants.Elevator.chain, MotorType.kBrushless);

//     // private RelativeEncoder m_chainEncoder = m_chain.getEncoder();

//     public Elevator() {
//         // m_chain.setInverted(false);
//         // m_chain.setIdleMode(IdleMode.kBrake);
//         m_chainEncoder.setPositionConversionFactor(1);
//     }

//     @Override
//     public void periodic() {
//         SmartDashboard.putNumber("ELEVATOR ENCODER", m_chainEncoder.getPosition());
//     }

//     public double getChain() {
//         return m_chainEncoder.getPosition();
//     }

//     public void rawClimb(double speed) {
//         // m_chain.set(speed);
//     }

//     public void rawClimbUp() {
//         // m_chain.set(1);
//     }

//     public void rawClimbDown() {
//         // m_chain.set(-1);
//     }

//     public void stopClimb() {
//         // m_chain.set(0.0);
//     }

//     public void extend() {
//         if (getChain() < 18200) rawClimbUp();
//         else {stopClimb();}
//     }

//     public void lift() {
//         if (getChain() > 0) rawClimbDown();
//         else {stopClimb();}
//     }
// }