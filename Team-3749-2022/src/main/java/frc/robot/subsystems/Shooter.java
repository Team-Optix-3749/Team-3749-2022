package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Constants.Auto;

public class Shooter extends SubsystemBase {
    private WPI_TalonFX m_leftShooterMotor = new WPI_TalonFX(Constants.Shooter.leftShooterMotor);
    private WPI_TalonFX m_rightShooterMotor = new WPI_TalonFX(Constants.Shooter.rightShooterMotor);

    private CANSparkMax m_turretMotor = new CANSparkMax(Constants.Shooter.turretMotor, MotorType.kBrushless);
    private RelativeEncoder m_turretEncoder = m_turretMotor.getEncoder();

    private MotorControllerGroup m_shooterMotors = new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor);

    private PIDController m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI,
            Constants.Shooter.kD);
            
    private PIDController m_pidTurretController = new PIDController(1.0, 0.4, 0.0);
 
    public Shooter() {
        m_leftShooterMotor.setInverted(true);
        m_turretMotor.setInverted(true);
        m_turretEncoder.setPositionConversionFactor(Constants.Shooter.gearRatio);
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

    public void stopMotor() {
        m_shooterMotors.set(0);
    }

    public double getVelocity() {
        return m_leftShooterMotor.getSelectedSensorVelocity();
    }

    public void setTurretPosition(double position) {
        setTurretMotor(m_pidTurretController.calculate(getTurretPosition(), position));
    }

    public void resetTurret() {
        setTurretPosition(0);
    }

    public void setTurretRaw(double speed) {
        m_turretMotor.set(speed * 0.05);
    }

    public void stopTurret() {
        m_turretMotor.stopMotor();
    }
    
    public void skewedVisionAlign() {
        double hubX = Constants.Auto.tx.getDouble(3749) + 1;
        SmartDashboard.putNumber("Hub Alignment (-3 < x < 3)", hubX - 1);
        if (hubX != 3750) setTurretMotor(hubX * 0.015); 
        else stopTurret();
    }

    public void visionAlign() {
        double hubX = Constants.Auto.tx.getDouble(3749);
        SmartDashboard.putNumber("Hub Alignment (-3 < x < 3)", hubX);
        if (hubX != 3749) setTurretMotor(hubX * 0.015); 
        else stopTurret();
    }


    public double getTurretPosition(){
        return m_turretEncoder.getPosition();
    }

    public void setTurretMotor(double speed) {
        if (Math.abs(m_turretEncoder.getPosition()) <= .23) {
                m_turretMotor.set(speed);
        }
        else if (m_turretEncoder.getPosition() * speed < 0){ //Checks if speed and encoder position have opposite signs
            m_turretMotor.set(speed);
            if (m_turretEncoder.getPosition() < 0) {m_turretMotor.set(Math.abs(speed));}
            else if (m_turretEncoder.getPosition() > 0) m_turretMotor.set(-Math.abs(speed));
        }
         else m_turretMotor.set(0);
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

    public void resetEncoder(){
        m_turretEncoder.setPosition(0);
    }
}