package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.Drivetrain.leftFront);
    private WPI_TalonFX m_leftBack = new WPI_TalonFX(Constants.Drivetrain.leftBack);
    private MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftBack);

    private WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.Drivetrain.rightFront);
    private WPI_TalonFX m_rightBack = new WPI_TalonFX(Constants.Drivetrain.rightBack);
    public MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightBack);

    private DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    private final Encoder m_leftEncoder = new Encoder(0, 1);
    private final Encoder m_rightEncoder = new Encoder(2, 3);

    private final Gyro m_gyro = new AHRS();

    public Drivetrain() {
        m_right.setInverted(true);
    }

    public void arcadeDrive(double speed, double rotation) {
        m_drive.arcadeDrive(speed, rotation);
    }
}
