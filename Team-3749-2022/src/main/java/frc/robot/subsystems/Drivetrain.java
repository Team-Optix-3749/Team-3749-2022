package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.Drivetrain.leftFront);
    private WPI_TalonFX m_leftBack = new WPI_TalonFX(Constants.Drivetrain.leftBack);
    private MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftBack);

    private WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.Drivetrain.rightFront);
    private WPI_TalonFX m_rightBack = new WPI_TalonFX(Constants.Drivetrain.rightBack);
    public MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightBack);

    private DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    private final Gyro m_gyro = new AHRS();

    private final DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());


    public Drivetrain() {
        m_leftFront.setNeutralMode(NeutralMode.Coast);
        m_leftBack.setNeutralMode(NeutralMode.Coast);

        m_rightBack.setNeutralMode(NeutralMode.Coast);
        m_rightFront.setNeutralMode(NeutralMode.Coast);

        m_right.setInverted(true);
    }

    public void setBrakeMode() {
        m_leftFront.setNeutralMode(NeutralMode.Brake);
        m_leftBack.setNeutralMode(NeutralMode.Brake);

        m_rightBack.setNeutralMode(NeutralMode.Brake);
        m_rightFront.setNeutralMode(NeutralMode.Brake);
    }

    public void arcadeDrive(double speed, double rotation) {
        m_drive.arcadeDrive(speed, -rotation * Constants.Drivetrain.rotationalSpeed);
    }

    @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(), m_leftFront.getSelectedSensorPosition()/Constants.Auto.wheelMult, m_rightFront.getSelectedSensorPosition()/Constants.Auto.wheelMult);
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    System.out.println(m_odometry.getPoseMeters().getX() + " " + m_odometry.getPoseMeters().getY());
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftFront.getSelectedSensorVelocity()/Constants.Auto.wheelMult, m_rightFront.getSelectedSensorVelocity()/Constants.Auto.wheelMult);
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }
  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_left.setVoltage(-leftVolts*0.3);
    m_right.setVoltage(-rightVolts*0.3);
    m_drive.feed();
  }

//   /** Resets the drive encoders to currently read a position of 0. */
//   public void resetEncoders() {
//     m_leftEncoder.reset();
//     m_rightEncoder.reset();
//   }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    SmartDashboard.putNumber("bruh", ((m_leftFront.getSelectedSensorPosition() + m_rightFront.getSelectedSensorPosition()) / 2.0) / Constants.Auto.wheelMult);
    return ((m_leftFront.getSelectedSensorPosition() + m_rightFront.getSelectedSensorPosition()) / 2.0) / Constants.Auto.wheelMult;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }


  public double getDistanceLeft() {
    return m_leftFront.getSelectedSensorPosition()/Constants.Auto.wheelMult;
  }

  public double getDistanceRight() {
    return m_rightFront.getSelectedSensorPosition()/Constants.Auto.wheelMult;
  }

  public void stop() {
    m_drive.arcadeDrive(0, 0);
  }

  public void resetEncoders () {
    m_leftFront.setSelectedSensorPosition(0);
    m_rightFront.setSelectedSensorPosition(0);
  }
}
