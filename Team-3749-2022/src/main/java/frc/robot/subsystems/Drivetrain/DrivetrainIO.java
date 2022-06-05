package frc.robot.subsystems.Drivetrain;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Drive subsystem hardware interface. */
public interface DrivetrainIO {
    /** The set of loggable inputs for the drive subsystem. */
    public static class DrivetrainIOInputs implements LoggableInputs {
      public double leftPositionRad = 0.0;
      public double leftVelocityRadPerSec = 0.0;
      public double leftAppliedVolts = 0.0;
      public double[] leftCurrentAmps = new double[] {};
      public double[] leftTempCelcius = new double[] {};

      public double rightPositionRad = 0.0;
      public double rightVelocityRadPerSec = 0.0;
      public double rightAppliedVolts = 0.0;
      public double[] rightCurrentAmps = new double[] {};
      public double[] rightTempCelcius = new double[] {};

      public double gyroPositionRad = 0.0;
      public double gyroVelocityRadPerSec = 0.0;

      public void toLog(LogTable table) {
        table.put("LeftPositionRad", leftPositionRad);
        table.put("LeftVelocityRadPerSec", leftVelocityRadPerSec);
        table.put("LeftAppliedVolts", leftAppliedVolts);
        table.put("LeftCurrentAmps", leftCurrentAmps);
        table.put("LeftTempCelcius", leftTempCelcius);

        table.put("RightPositionRad", rightPositionRad);
        table.put("RightVelocityRadPerSec", rightVelocityRadPerSec);
        table.put("RightAppliedVolts", rightAppliedVolts);
        table.put("RightCurrentAmps", rightCurrentAmps);
        table.put("RightTempCelcius", rightTempCelcius);

        table.put("GyroPositionRad", gyroPositionRad);
        table.put("GyroVelocityRadPerSec", gyroVelocityRadPerSec);
      }

      public void fromLog(LogTable table) {
        leftPositionRad = table.getDouble("LeftPositionRad", leftPositionRad);
        leftVelocityRadPerSec = table.getDouble("LeftVelocityRadPerSec", leftVelocityRadPerSec);
        leftAppliedVolts = table.getDouble("LeftAppliedVolts", leftAppliedVolts);
        leftCurrentAmps = table.getDoubleArray("LeftCurrentAmps", leftCurrentAmps);
        leftTempCelcius = table.getDoubleArray("LeftTempCelcius", leftTempCelcius);

        rightPositionRad = table.getDouble("RightPositionRad", rightPositionRad);
        rightVelocityRadPerSec = table.getDouble("RightVelocityRadPerSec", rightVelocityRadPerSec);
        rightAppliedVolts = table.getDouble("RightAppliedVolts", rightAppliedVolts);
        rightCurrentAmps = table.getDoubleArray("RightCurrentAmps", rightCurrentAmps);
        rightTempCelcius = table.getDoubleArray("RightTempCelcius", rightTempCelcius);

        gyroPositionRad = table.getDouble("GyroPositionRad", gyroPositionRad);
        gyroVelocityRadPerSec = table.getDouble("GyroVelocityRadPerSec", gyroVelocityRadPerSec);
      }
    }

    /** Updates the set of loggable inputs. */
    public default void updateInputs(DrivetrainIOInputs inputs) {
    }

    /** Run open loop at the specified voltage. */
    public default void setVoltage(double leftVolts, double rightVolts) {
    }

    /** Run closed loop at the specified velocity. */
    public default void setVelocity(double leftVelocityRadPerSec, double rightVelocityRadPerSec, double leftFFVolts,
        double rightFFVolts) {
    }

    /** Enable coast mode */
    public default void setCoast() {}

    /** Enable brake mode */
    public default void setBrake() {
    }

    /** Reset the encoder(s) to a known position. */
    public default void resetPosition(double leftPositionRad, double rightPositionRad) {
    }

}