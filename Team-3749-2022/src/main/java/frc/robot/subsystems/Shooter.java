package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class Shooter extends SubsystemBase{
    public CANSparkMax m_leftShintakeMotor;
    public CANSparkMax m_rightShintakeMotor; 

    private WPI_TalonFX m_leftShooterMotor = new WPI_TalonFX(Constants.Shooter.leftShooterMotor);
    private WPI_TalonFX m_rightShooterMotor = new WPI_TalonFX(Constants.Shooter.rightShooterMotor);

    private CANSparkMax m_turretMotor = new CANSparkMax(Constants.Shooter.turretMotor, MotorType.kBrushless);
    private CANEncoder m_turretEncoder = m_turretMotor.getEncoder();

    private MotorControllerGroup m_shooterMotor = new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor);

    NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = m_table.getEntry("tx");
    private PIDController m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD);

    public Shooter(){
        resetEncoder();

        m_rightShooterMotor.setInverted(true);
        m_turretEncoder.setPositionConversionFactor(Constants.Shooter.gearRatio);

        m_leftShintakeMotor = new CANSparkMax(Constants.Shooter.shintakeLeft, MotorType.kBrushless);
        m_rightShintakeMotor = new CANSparkMax(Constants.Shooter.shintakeRight, MotorType.kBrushless);
        
        m_rightShintakeMotor.setInverted(true);
    }

    public void setShintake (double dir) {
        m_leftShintakeMotor.set(dir*Constants.Shooter.kShintakeSpeed);
        m_rightShintakeMotor.set(dir*Constants.Shooter.kShintakeSpeed);
    }

    public void stopMotors(){
        m_shooterMotor.set(0);
        m_turretMotor.set(0);
    }

    public void setShooter(){
        m_shooterMotor.set(Constants.Shooter.kShootSpeed);
    }

    public void visionAlign(){
        // https://docs.limelightvision.io/en/latest/cs_aiming.html
    }

    public void resetEncoder(){
        m_turretEncoder.setPosition(0);
    }

}
