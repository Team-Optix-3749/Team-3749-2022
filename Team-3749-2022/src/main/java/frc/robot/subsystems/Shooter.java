package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Constants.Auto;

public class Shooter extends SubsystemBase {
    private WPI_TalonFX m_leftShooterMotor = new WPI_TalonFX(Constants.Shooter.leftShooterMotor);
    private WPI_TalonFX m_rightShooterMotor = new WPI_TalonFX(Constants.Shooter.rightShooterMotor);

    private MotorControllerGroup m_shooterMotors = new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor);

    private PIDController m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI,
            Constants.Shooter.kD);
            
 
    public Shooter() {
        m_leftShooterMotor.setInverted(true);
        m_leftShooterMotor.setNeutralMode(NeutralMode.Coast);
        m_rightShooterMotor.setNeutralMode(NeutralMode.Coast);

        m_leftShooterMotor.setSensorPhase(false);
    }

    public void setRPM(double target) {
        m_shooterMotors.setVoltage(m_pidController.calculate(m_leftShooterMotor.getSelectedSensorVelocity(), target * 60) * .0019);
    }

    public double getRPM() {
        return m_leftShooterMotor.getSelectedSensorVelocity() * 0.034;
    }

    public void rawShoot(double speed) {
        m_shooterMotors.set(speed);
    }

    public void stopShooter() {
        m_shooterMotors.set(0);
    }

    public double getVelocity() {
        return m_leftShooterMotor.getSelectedSensorVelocity();
    }
    
    public double targetVelocity() {
        double hubY = Constants.Shooter.shooterHeight - Constants.Shooter.hubHeight;
        double hubX = getDistance()+0.61;
        double A = Math.toRadians(Constants.Shooter.shooterAngle);
        double velocity = Math.sqrt(((4.9 * hubX * hubX) / (Math.cos(A) * Math.cos(A))) * (1 / (hubY + (Math.tan(A) * hubX))));
        return velocity;
    }
     
    public double getDistance() {    
        double y = Auto.ty.getDouble(0.0);    
        return (Constants.Shooter.hubHeight - Constants.Shooter.shooterHeight)/Math.tan(Math.toRadians(Constants.Shooter.limelightAngle + y));
    }

    public void distanceCheck() {
        boolean upperCheck = (getDistance() > Constants.Shooter.upperHubLesserDistance && getDistance() < Constants.Shooter.upperHubGreaterDistance);
        boolean lowerCheck = (getDistance() < Constants.Shooter.lowerHubDistance);

        SmartDashboard.putBoolean("UPPER HUB DISTANCE CHECK", upperCheck);
        SmartDashboard.putBoolean("LOWER HUB DISTANCE CHECK", lowerCheck);
    }

    public void setTargetVelocity() {
        setRPM(targetVelocity());
    }

    public void setVelocity(double velocity) {
        setRPM(velocity / .476);
    }
}