package frc.robot.utilities;

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

    public static final class Shooter{
        public static final double lowerRPM = 150; 
        public static final double upperRPM = 500;
        // 340 WORKED FOR 85
        public static final double turretSpeed = 0.2;

        public static final int turretMotor = 22;
        public static final int rightShooterMotor = 15;
        public static final int leftShooterMotor = 16;

        public static final double gearRatio = 12.0/775.0;
        public static final double kP = 0.295;
        public static final double kI = 1.0;
        public static final double kD = 0.0;
        public static final double shooterHeight = 0.8128;
        public static final double hubHeight = 2.64;
        public static final double limelightAngle = 50;
        public static final double shooterAngle = (47.5);

        public static final double upperHubLesserDistance = 1.1;
        public static final double upperHubGreaterDistance = 1.45; 
        public static final double lowerHubDistance = .4;
    } 

    public static final class Vision{
        public static final double kVisionP = 0.1;
        public static final double kVisionLimit = 0.5;
    }
  
    public static final class Intake {
        public static final int intakeMotor = 21;
        public static final int intakePiston = 1;
        public static final double kIntakeSpeed = 0.8;

        public static enum SolenoidDirection {
            FORWARD, REVERSE, OFF, COMPRESSOR, TRIGGER
        }
        public final static int[] kSolenoidForwardChannel = {2,5};
        public final static int[] kSolenoidReverseChannel = {3,4};
        public final static double kPneumaticsSpeed = 0.5;
        public final static double kPneumaticsSpeedInverted = -0.5;
    }

    public static class Shintake {
        public static final int shintakeFront = 23;
        public static final int shintakeBack = 24;
        public static final double kShintakeSpeed = 0.53;
        public static final double kP = .0002;
        public static final double kPlow = .0001;
        public static final double kI = 0;
        public static final double kD = 0;
        // public static final double targetRPM = 2500;
        public static final double targetRPM = 5500;


        public static enum BallColor {
            RED, BLUE, NULL
        }
    }

    public static final class Elevator {
        public static final int chain = 25;
        public static final double chainMPR = 0.0; // FMD SPROCKET DIAMETER
        public static final double chainGR = 0.0; // FIND GEAR RATIO ON MOTOR
        
        public static final double kP = 1.0;
        public static final double kI = 0.5;
        public static final double kD = 0.0;
    }

    public static final class LEDs {
        public static final int LEDport = 9;
        public static final int bufferLength = 60; // Length of LED Strips in Pixels 
    }
}
