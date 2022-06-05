package frc.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.utilities.Constants;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DrivetrainIOTalonFX implements DrivetrainIO {

    private static final double radiansPerTick = (2.0 * Math.PI) / 2048.0;

    private WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.Drivetrain.leftFront);
    private WPI_TalonFX m_leftBack = new WPI_TalonFX(Constants.Drivetrain.leftBack);
    private MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftBack);

    private WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.Drivetrain.rightFront);
    private WPI_TalonFX m_rightBack = new WPI_TalonFX(Constants.Drivetrain.rightBack);
    private MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightBack);

    private DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);
    
    public DrivetrainIOTalonFX () {
        m_leftFront.configFactoryDefault();
        m_leftBack.configFactoryDefault();
        m_rightFront.configFactoryDefault();
        m_rightBack.configFactoryDefault();
    
        m_leftFront.configVoltageCompSaturation(12);
        m_rightBack.configVoltageCompSaturation(12);
    
        m_leftFront.setInverted(false);
        m_leftBack.setInverted(false);
        m_rightFront.setInverted(true);
        m_rightBack.setInverted(true);
    
        m_leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
        m_rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
        m_leftFront.setSelectedSensorPosition(0);
        m_rightFront.setSelectedSensorPosition(0);
        m_leftFront.setSensorPhase(false);
        m_rightFront.setSensorPhase(true);
    
        m_leftBack.follow(m_leftFront);
        m_rightBack.follow(m_rightFront);
    
        m_gyro.zeroYaw();
    }

    @Override
    public void updateInputs (DrivetrainIOInputs inputs) {
        inputs.leftPositionRad = m_leftFront.getSelectedSensorPosition() * radiansPerTick;
        inputs.rightPositionRad = m_rightFront.getSelectedSensorPosition() * radiansPerTick;
        inputs.leftVelocityRadPerSec = m_leftFront.getSelectedSensorVelocity() * radiansPerTick * 10;
        inputs.rightVelocityRadPerSec = m_rightFront.getSelectedSensorVelocity() * radiansPerTick * 10;
        inputs.leftCurrentAmps = new double[] { m_leftFront.getStatorCurrent() };
        inputs.rightCurrentAmps = new double[] { m_rightFront.getStatorCurrent() };
        inputs.gyroPositionRad = Math.toRadians(m_gyro.getAngle());
        inputs.gyroVelocityRadPerSec = Math.toRadians(m_gyro.getRawGyroZ());
    }

    

}
