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

    private MotorControllerGroup m_shooterMotor = new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor);

    private PIDController m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD);

    public Shooter(){
        resetEncoder();

        m_rightShooterMotor.setInverted(true);
        m_turretEncoder.setPositionConversionFactor(Constants.Shooter.gearRatio);
        m_leftShooterMotor.setNeutralMode(NeutralMode.Coast);
        m_rightShooterMotor.setNeutralMode(NeutralMode.Coast);

        
    }

    public void stopMotors(){
        m_shooterMotor.set(0);
        m_turretMotor.set(0);
    }

    public void setShooter(double voltage){
        m_shooterMotor.setVoltage(voltage);
    }

    public void setShooterValue(){
        // System.out.println(velocity*12);
        // m_shooterMotor.set(-5);
        // m_shooterMotor.setVoltage(-14);
        m_leftShooterMotor.set(-1);
        //m_rightShooterMotor.set(-1);
    }

    public double targetVelocity(){
        double hubY = Constants.Shooter.shooterHeight - Constants.Shooter.hubHeight;
        double hubX = getDistance()+0.61;
        double A = Math.toRadians(Constants.Shooter.shooterAngle);
        double velocity = Math.sqrt(
        ((4.9*hubX*hubX)/(Math.cos(A)*Math.cos(A)))
        *(1/(hubY+(Math.tan(A)*hubX))));
        
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
            System.out.println("don\'t care + didn\'t ask + cry about it + stay mad + get real + L + mald seethe cope harder + hoes mad + basic + skill issue + ratio + you fell off + the audacity + triggered + any askers + get a life + ok and? + cringe + touch grass + not based + your\'re probably white + not funny didn\'t laugh + grammar issue + go outside + get good + reported + ad hominem + GG! + ur mom + don\'t care + didn\'t ask + cry about it + stay mad + get real + L + mald seethe cope harder + hoes mad + basic + skill issue + ratio + you fell off + the audacity + triggered + any askers + redpilled + get a life + ok and? + cringe + touch grass + donowalled + not based + your\'re a full time discordian + not funny didn\'t laugh + you\'re* + grammar issue + go outside + get good + your gay + reported + ad hominem + GG! + ur mom + no + you thought you ate don\'t care + didn\'t ask + cry about it + stay mad + get real + L + mald seethe cope harder + hoes mad + basic + skill issue + ratio + you fell off + the audacity + triggered + L bozo");  
        }
        if (getEncoder() < 36 || getEncoder() > 224) stopMotors(); 
        else m_turretMotor.set(speed);
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
        m_shooterMotor.setVoltage(m_pidController.calculate(current, target)*.0019);
    }

    public void setVelocity(double current, double target){
        setRPM(m_leftShooterMotor.getSelectedSensorVelocity(), targetVelocity()*60/.476);
    }

    public double getDistance(){    
        double y = Auto.ty.getDouble(0.0);    
        return (Constants.Shooter.hubHeight - Constants.Shooter.shooterHeight)/Math.tan(Math.toRadians(Constants.Shooter.limelightAngle + y));
    }
    
    public double getEncoder () {
        return m_turretEncoder.getPosition();
    }

    public void resetEncoder(){
        m_turretEncoder.setPosition(0);
    }
}
