// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
public class Drivetrain extends SubsystemBase {
  // tracking
  NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = m_table.getEntry("tx");
  

  public WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.Drivetrain.leftFront);
  public WPI_TalonFX m_leftBack = new WPI_TalonFX(Constants.Drivetrain.leftBack);
  public MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftBack);

  public WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.Drivetrain.rightFront);
  public WPI_TalonFX m_rightBack = new WPI_TalonFX(Constants.Drivetrain.rightBack);
  public MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightBack);

  public DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);


  private final Gyro m_gyro = new AHRS();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  public double convertToMeters(double in) {
    return in * 2 * Math.PI * Constants.Drivetrain.kWheelRadius / Constants.Drivetrain.kEncoderResolution / 9.29;
  }

  public Drivetrain() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_left.setInverted(true);

    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());

  }

  private double leftVel () {
    return convertToMeters(m_leftFront.getSelectedSensorVelocity());
  }

  private double rightVel () {
    return convertToMeters(m_rightFront.getSelectedSensorVelocity());
  }

  private double leftDist () {
    return convertToMeters(m_leftFront.getSelectedSensorPosition());
  }

  private double rightDist () {
    return convertToMeters(m_rightFront.getSelectedSensorPosition());
  }

  public void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }
  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed);
  }
  public void limeAlign () {
    double x = tx.getDouble(0.0);
    double output = 0;
    output = x * Constants.Vision.kVisionP;
    output *= Constants.Vision.kVisionLimit;
    
    m_drive.tankDrive(-output, output);
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(), leftDist(), rightDist());
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    // System.out.println(m_odometry.getPoseMeters().getX() + " " + m_odometry.getPoseMeters().getY());
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftVel(), rightVel());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }
  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_left.setVoltage(leftVolts);
    m_right.setVoltage(rightVolts);
    m_drive.feed();
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_rightFront.setSelectedSensorPosition(0);
    m_leftFront.setSelectedSensorPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (leftDist() + rightDist()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public double getLeftEncoder() {
    return leftDist();
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public double getRightEncoder() {
    return rightDist();
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
    return leftDist();
  }

  public double getDistanceRight() {
    return rightDist();
  }
}
