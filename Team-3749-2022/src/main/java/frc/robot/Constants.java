// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Drivetrain {
        public static final int leftFront = 11;
        public static final int leftBack = 12;
        public static final int rightFront = 13;
        public static final int rightBack = 14;
    }

    public static final class Auto {
        // public static final double ksVolts = 0.0015; //0.59817;
        // public static final double kvVoltSecondsPerMeter = 2; //3.0078
        // public static final double kaVoltSecondsSquaredPerMeter = 0.15896;
        // public static final double kPDriveVel = 2.9456;//2.9456;
        // public static final double kTrackwidthMeters = 0.584;
        public static final double ksVolts = 0.59817;
        public static final double kvVoltSecondsPerMeter = 3.0078;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15896;
        public static final double kPDriveVel = 2.9456;
        public static final double kTrackwidthMeters = 0.584;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
        public static final double kMaxSpeedMetersPerSecond = .1;
        public static final double kMaxAccelerationMetersPerSecondSquared = .1;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        public static final double kWheelRadius = 0.0508;
        public static final int kEncoderResolution = 2048;
    }
    public final class Shooter{
        public static final int shintakeFront = 0; // FIND DEVICE ID
        public static final int shintakeRight = 0;
        public static final int shintakeLeft = 0; // FIND DEVICE ID
        public static final int turretMotor = 0;
        public static final int rightShooterMotor = 0;
        public static final int leftShooterMotor = 0;

        public static final double kShootSpeed = 0; // https://docs.limelightvision.io/en/latest/cs_estimating_distance.html
        public static final double gearRatio = 1;
        public static final double kShintakeSpeed = 0.5;
        public static final double kP = 1.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
    } 

    public static final class Vision{
        public static final double kVisionP = 0.1;
        public static final double kVisionLimit = 0.5;
    }
  
    public static final class Intake {
        public static final int intakePiston = 0; // FIND DEVICE ID
        public static final double kIntakeSpeed = 1.0;
    }

    public static class Pneumatics {
        public static enum SolenoidDirection {
            FORWARD, REVERSE, OFF, COMPRESSOR, TRIGGER
        }
        public final static int[] kSolenoidForwardChannel = {2,4};
        public final static int[] kSolenoidReverseChannel = {3,5};
        public final static double kPneumaticsSpeed = 0.5;
        public final static double kPneumaticsSpeedInverted = -0.5;
    }

    public static final class Elevator {
        public static final int leftTilt = 0; // FIND CAN ID
        public static final int rightTilt = 10; // FIND CAN ID
        public static final int chain = 0; // FIND CAN ID
        public static final double chainMPR = 0.0; // FMD SPROCKET DIAMETER
        public static final double chainGR = 0.0; // FIND GEAR RATIO ON MOTOR
        public static final double kP = 1.0;
        public static final double kI = 0.5;
        public static final double kD = 0.0;
    }
}
