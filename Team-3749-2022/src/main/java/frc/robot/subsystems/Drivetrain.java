// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.*;

/** Represents a differential drive style drivetrain. */
public class Drivetrain extends SubsystemBase {
    public static final double kMaxSpeed = Constants.Auto.kMaxSpeedMetersPerSecond; // meters per second
    public static final double kMaxAngularSpeed = 2 * Math.PI; // one rotation per second
    private final double kDistancePerPulse = (2 * Math.PI * kWheelRadius / kEncoderResolution);

    private static final double kTrackWidth = Constants.Auto.kTrackwidthMeters; // meters
    private static final double kWheelRadius = Constants.Auto.kWheelRadius; // meters
    private static final int kEncoderResolution = Constants.Auto.kEncoderResolution;

    private final WPI_TalonFX m_leftLeader = new WPI_TalonFX(Constants.Drivetrain.leftFront);
    private final WPI_TalonFX m_leftFollower = new WPI_TalonFX(Constants.Drivetrain.leftBack);
    private final WPI_TalonFX m_rightLeader = new WPI_TalonFX(Constants.Drivetrain.rightFront);
    private final WPI_TalonFX m_rightFollower = new WPI_TalonFX(Constants.Drivetrain.rightBack);

    private final MotorControllerGroup m_leftGroup = new MotorControllerGroup(m_leftLeader, m_leftFollower);
    private final MotorControllerGroup m_rightGroup = new MotorControllerGroup(m_rightLeader, m_rightFollower);

    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

    private final PIDController m_leftPIDController = new PIDController(1, 0, 0);
    private final PIDController m_rightPIDController = new PIDController(1, 0, 0);

    private final DifferentialDriveKinematics m_kinematics = new DifferentialDriveKinematics(kTrackWidth);

    private final DifferentialDriveOdometry m_odometry;

    // must be determined for your own robot!
    private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 1);

    /**
     * Constructs a differential drive object & resets the gyro
     */
    public Drivetrain() {
        m_gyro.reset();

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        m_rightGroup.setInverted(true);

        m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
    }

    /**
     * Find the distance by dividing the sensor pos by the dist traveled per tick/pulse
     * 
     * @return left leader distance traveled
     */
    public double getLeftDistance() {
        return (m_leftLeader.getSelectedSensorPosition() / kDistancePerPulse);
    }

    /**
     * Find the distance by dividing the sensor pos by the dist traveled per tick/pulse
     * 
     * @return right leader distance traveled
     */
    public double getRightDistance() {
        return (m_rightLeader.getSelectedSensorPosition() / kDistancePerPulse);
    }

    /**
     * Sets the desired wheel speeds.
     *
     * @param speeds The desired wheel speeds.
     */
    public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
        final double leftFeedforward = m_feedforward.calculate(speeds.leftMetersPerSecond);
        final double rightFeedforward = m_feedforward.calculate(speeds.rightMetersPerSecond);

        final double leftOutput = m_leftPIDController.calculate(m_leftLeader.getSelectedSensorVelocity(), speeds.leftMetersPerSecond);
        final double rightOutput = m_rightPIDController.calculate(m_rightLeader.getSelectedSensorVelocity(),
                speeds.rightMetersPerSecond);
        m_leftGroup.setVoltage(leftOutput + leftFeedforward);
        m_rightGroup.setVoltage(rightOutput + rightFeedforward);
    }

    /**
     * Drives the robot with the given linear velocity and angular velocity.
     *
     * @param xSpeed Linear velocity in m/s.
     * @param rot    Angular velocity in rad/s.
     */
    @SuppressWarnings("ParameterName")
    public void arcadeDrive(double xSpeed, double rot) {
        var wheelSpeeds = m_kinematics.toWheelSpeeds(new ChassisSpeeds(xSpeed, 0.0, rot));
        setSpeeds(wheelSpeeds);
    }

    /** Updates the field-relative position. */
    public void updateOdometry() {
        m_odometry.update(
                m_gyro.getRotation2d(), getLeftDistance(), getRightDistance());
    }
}