package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Constants.Auto;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shooter extends SubsystemBase{
    private WPI_TalonFX m_leftShooterMotor = new WPI_TalonFX(Constants.Shooter.leftShooterMotor);
    private WPI_TalonFX m_rightShooterMotor = new WPI_TalonFX(Constants.Shooter.rightShooterMotor);

    private CANSparkMax m_turretMotor = new CANSparkMax(Constants.Shooter.turretMotor, MotorType.kBrushless);
    private RelativeEncoder m_turretEncoder = m_turretMotor.getEncoder();

    private MotorControllerGroup m_shooterMotors = new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor);

    private PIDController m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD);

    public Shooter(){
        resetEncoder();

        m_rightShooterMotor.setInverted(true);
        m_turretEncoder.setPositionConversionFactor(Constants.Shooter.gearRatio);
        m_leftShooterMotor.setNeutralMode(NeutralMode.Coast);
        m_rightShooterMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void stopShooterMotors(){
        m_shooterMotors.set(0);    
    }

    public void stopTurretMotor(){
        m_turretMotor.set(0);    
    }

    public void setShooterVolts(double voltage){
        m_shooterMotors.setVoltage(voltage);
    }

    public void setShooter(){
        m_shooterMotors.set(-1);
    }

    public double targetVelocity(){
        double hubY = Constants.Shooter.shooterHeight - Constants.Shooter.hubHeight;
        double hubX = getDistance()+0.61;
        double A = Math.toRadians(Constants.Shooter.shooterAngle);
        double velocity = Math.sqrt(
            ((4.9 * hubX * hubX) / (Math.cos(A) * Math.cos(A))) * (1 / (hubY + (Math.tan(A) * hubX)))
        );
        
        return velocity;
    }

    public void setTurretMotor(double speed){
        if (Math.abs(m_turretEncoder.getPosition()) <= .24){
            m_turretMotor.set(speed);
        }
        else if (m_turretEncoder.getPosition() * speed < 0) { //Checks if speed and encoder position have opposite signs
            m_turretMotor.set(speed);
        }
        else{
            m_turretMotor.set(0);
        }
    }

    public void visionAlign(){
        double x = Auto.tx.getDouble(0.0);
        double multiplier = 1;
        if (x<=5){
            multiplier = 5;
        }
        else if (x>=5){
            multiplier = 3;
        }
        else if (x>=10){
            multiplier = 2;
        }
        double input = x * Constants.Vision.kVisionP * multiplier;
        if (input>1){
            setTurretMotor(0.8*input);
        }
        // System.out.println(m_turretEncoder.getPosition());
    }

    public void setRPM(double current, double target){
        m_shooterMotors.setVoltage(m_pidController.calculate(current, target)*.0019);
    }

    public void setVelocity(){
        setRPM(m_leftShooterMotor.getSelectedSensorVelocity(), targetVelocity()*60/.476);
    }

    public double getDistance(){    
        double y = Auto.ty.getDouble(0.0);    
        return (Constants.Shooter.hubHeight - Constants.Shooter.shooterHeight)/Math.tan(Math.toRadians(Constants.Shooter.limelightAngle + y));
    }
    
    public double getTurretEncoder () {
        return m_turretEncoder.getPosition();
    }

    public void resetEncoder(){
        m_turretEncoder.setPosition(0);
    }
}
