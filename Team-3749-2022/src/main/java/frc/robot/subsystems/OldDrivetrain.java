package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("PMD.TooManyFields")
public class OldDrivetrain extends SubsystemBase {
  private WPI_VictorSPX m_leftFrontMotor = new WPI_VictorSPX(24);
  private WPI_TalonSRX m_leftBackMotor = new WPI_TalonSRX(13);
  public MotorControllerGroup m_leftMotors = new MotorControllerGroup(m_leftFrontMotor, m_leftBackMotor);

  private WPI_VictorSPX m_rightFrontMotor = new WPI_VictorSPX(23);
  private WPI_TalonSRX m_rightBackMotor = new WPI_TalonSRX(15);
  public MotorControllerGroup m_rightMotors = new MotorControllerGroup(m_rightFrontMotor, m_rightBackMotor);

  public DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);


  public OldDrivetrain() {

    m_leftMotors.setInverted(true);
    m_rightMotors.setInverted(true);

  }

  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(-fwd * 1, rot * 1, true);
  }
}