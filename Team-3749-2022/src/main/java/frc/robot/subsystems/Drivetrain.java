// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import org.opencv.objdetect.CascadeClassifier;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.*;
import frc.robot.utils.Constants.Drivetrain.Mode;

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

    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftGroup, m_rightGroup);

    // must be determined for your own robot!
    private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 1);

    private final Pose2d zeroPose2d = new Pose2d(0.0, 0.0, new Rotation2d(0.0));
    private final Rotation2d zeroRotation2d = new Rotation2d(0.0);

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

    public void periodic() {
        // Update the odometry in the periodic block
        m_odometry.update(
                m_gyro.getRotation2d(), 
                getLeftDistance(),
                getRightDistance());

        odometryLogging();
    }

    public void setMode(Mode mode) {
        switch(mode) {
            case COAST:
                m_leftFollower.setNeutralMode(NeutralMode.Coast);
                m_rightFollower.setNeutralMode(NeutralMode.Coast);
                m_rightLeader.setNeutralMode(NeutralMode.Coast);
                m_leftLeader.setNeutralMode(NeutralMode.Coast);
                break;
            case BRAKE:
                m_leftFollower.setNeutralMode(NeutralMode.Brake);
                m_rightFollower.setNeutralMode(NeutralMode.Brake);
                m_rightLeader.setNeutralMode(NeutralMode.Brake);
                m_leftLeader.setNeutralMode(NeutralMode.Brake);
                break;
        }
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
     * Drives the robot with the rotation and speed.
     *
     * @param xSpeed Linear speed in %.
     * @param rot    Angular speed in %.
     */
    @SuppressWarnings("ParameterName")
    public void arcadeDrive(double xSpeed, double rot) {
        m_drive.arcadeDrive(xSpeed, rot);
    }

    /** Updates the field-relative position. */
    public void updateOdometry() {
        m_odometry.update(
                m_gyro.getRotation2d(), getLeftDistance(), getRightDistance());
    }

    public void odometryLogging() {
        Pose2d robotPose = m_odometry.getPoseMeters();

        double[] robotLocation = {robotPose.getX(), robotPose.getY(), robotPose.getRotation().getDegrees()};
        SmartDashboard.putNumber("X Pos", robotLocation[0]);
        SmartDashboard.putNumber("Y Pos", robotLocation[1]);
        SmartDashboard.putNumber("Rot", robotLocation[2]);

        SmartDashboard.putNumberArray("x vs y vs rot", robotLocation);
    }   

    public void talonLogging () {
        // m_leftLeader.
    }

    public void resetRobotPose() {
        m_odometry.resetPosition(zeroPose2d, zeroRotation2d);
    }
}