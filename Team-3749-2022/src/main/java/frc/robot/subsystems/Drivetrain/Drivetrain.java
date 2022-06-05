// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Drivetrain;

import frc.robot.utilities.Constants;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    private final DrivetrainIO io;
    private final DrivetrainIO.DrivetrainIOInputs inputs = new DrivetrainIO.DrivetrainIOInputs();

    private final double wheelRadiusMeters = Constants.Auto.kWheelRadius;
    private final double maxVelocityMetersPerSec = Constants.Auto.maxVelocity;
    private final double maxAccelerationMetersPerSecSq = Constants.Auto.kMaxAccelerationMetersPerSecondSquared;
    private final double trackWidthMeters = Constants.Auto.kTrackwidthMeters;

    public static final double kS = Constants.Auto.ksVolts;
    public static final double kV = Constants.Auto.kvVoltSecondsPerMeter;
    public static final double kA = Constants.Auto.kaVoltSecondsSquaredPerMeter;


    private final Matrix<N5, N1> stateStdDev =
        new MatBuilder<>(Nat.N5(), Nat.N1()).fill(0.02, 0.02, 0.01, 0.02, 0.02);
    private final Matrix<N3, N1> localMeasurementStdDevs =
        new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01);
    private final Matrix<N3, N1> globalMeasurementStdDevs =
        new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01);

    private DifferentialDrivePoseEstimator odometry;
    private Field2d field2d = new Field2d();
    private double baseDistanceLeft = 0.0;
    private double baseDistanceRight = 0.0;

    private final SimpleMotorFeedforward leftFFModel;
    private final SimpleMotorFeedforward rightFFModel;

    public Drivetrain(DrivetrainIO io) {
        this.io = io;

        io.setCoast();

        SmartDashboard.putData("Odometry", field2d);

        leftFFModel = new SimpleMotorFeedforward(kS, kV, kA);
        rightFFModel = new SimpleMotorFeedforward(kS, kV, kA);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    
        Logger.getInstance().processInputs("DriveTrain", inputs);

        Logger.getInstance().recordOutput("DriveTrain/LeftVelocityMetersPerSec",
            getLeftVelocityMetersPerSec());
        Logger.getInstance().recordOutput("DriveTrain/RightVelocityMetersPerSec",
            getRightVelocityMetersPerSec());

        if (odometry == null) {
            odometry = new DifferentialDrivePoseEstimator(
                new Rotation2d(inputs.gyroPositionRad * -1), new Pose2d(),
                stateStdDev, localMeasurementStdDevs, globalMeasurementStdDevs);
            baseDistanceLeft = getLeftPositionMeters();
            baseDistanceRight = getRightPositionMeters();
          } else {
            Pose2d pose = odometry.updateWithTime(Timer.getFPGATimestamp(),
                new Rotation2d(inputs.gyroPositionRad * -1),
                new DifferentialDriveWheelSpeeds(getLeftVelocityMetersPerSec(),
                    getRightVelocityMetersPerSec()),
                getLeftPositionMeters() - baseDistanceLeft,
                getRightPositionMeters() - baseDistanceRight);
            Logger.getInstance().recordOutput("Odometry/Robot", new double[] {
                pose.getX(), pose.getY(), pose.getRotation().getRadians()});
            field2d.setRobotPose(pose);
        }

    }

    public void ArcadeDrive(double fwd, double rot) {}

    public void TankDrive (double left, double right) {}

    /**
     * Returns the position of the left drive in meters.
     */
    public double getLeftPositionMeters() {
        return inputs.leftPositionRad * wheelRadiusMeters;
    }

    /**
     * Returns the position of the right drive in meters.
     */
    public double getRightPositionMeters() {
        return inputs.rightPositionRad * wheelRadiusMeters;
    }

    
    /**
     * Returns the velocity of the left drive in meters per second.
     */
    public double getLeftVelocityMetersPerSec() {
        return inputs.leftVelocityRadPerSec * wheelRadiusMeters;
    }

    /**
     * Returns the velocity of the right drive in meters per second.
     */
    public double getRightVelocityMetersPerSec() {
        return inputs.rightVelocityRadPerSec * wheelRadiusMeters;
    }

    /** Returns the maximum velocity (meters/second) */
    public double getMaxVelocityMetersPerSec() {
        return maxVelocityMetersPerSec;
    }

    /** Returns the maximum acceleration (meters/second^2) */
    public double getMaxAccelerationMetersPerSecSq() {
        return maxAccelerationMetersPerSecSq;
    }

    /** Returns the empirical track width (meters) */
    public double getTrackWidthMeters() {
        return trackWidthMeters;
    }
}

