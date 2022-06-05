package frc.robot.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static double round(double input) {
        BigDecimal bd = new BigDecimal(Double.toString(input));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static final class Drivetrain {
        public enum Mode {
            BRAKE,
            COAST
        }

        public static final int leftFront = 11;
        public static final int leftBack = 12;
        public static final int rightFront = 13;
        public static final int rightBack = 14;
        public static final double rotationalSpeed = 0.7;
    }

    public static final class Auto {
        public static final double maxVelocity = 2.0;
        public static final double maxAcceleration = 1.67;
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

        public static NetworkTable m_limelight = NetworkTableInstance.getDefault().getTable("limelight");
        public static NetworkTableEntry tx = m_limelight.getEntry("tx");
        public static NetworkTableEntry ty = m_limelight.getEntry("ty");

        public static NetworkTable m_pi = NetworkTableInstance.getDefault().getTable("!ML");
        public static final NetworkTableEntry coords = m_pi.getEntry("coordinates");

        public static NetworkTable m_vision = NetworkTableInstance.getDefault().getTable("photovision");
        public static NetworkTableEntry visionTarget = m_vision.getEntry("hasTarget");
        public static NetworkTableEntry visionPitch = m_vision.getEntry("targetPitch");
        public static NetworkTableEntry visionYaw = m_vision.getEntry("targetYaw");
        public static double wheelMult = 2048 * 9.29 / (2*Math.PI*kWheelRadius);
    }

}
