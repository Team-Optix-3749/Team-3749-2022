package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Shooter m_shooter;
    private Intake m_intake;
    
    private Xbox Pilot;
    private Xbox Operator;
    private POV OpPOV;
    private POV PiPOV;


    public Shoot(Shooter shooter, 
    Intake intake, 
    Xbox pilot, Xbox operator, POV opPOV, POV piPOV) {
        m_shooter = shooter;
        m_intake = intake;
        Pilot = pilot;
        Operator = operator;
        OpPOV = opPOV;
        PiPOV = piPOV;
        addRequirements(shooter);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Turret Position", m_shooter.getTurretPosition());
        SmartDashboard.putNumber("SHOOTER RPM", m_shooter.getRPM());
        SmartDashboard.putBoolean("LOWER SHOOT CHECK", m_shooter.getRPM() > Constants.Shooter.lowerRPM);
        SmartDashboard.putBoolean("UPPER SHOOT CHECK", m_shooter.getRPM() > Constants.Shooter.upperRPM);
        SmartDashboard.putNumber("HUB DISTANCE (METERS)", m_shooter.getDistance());
        m_shooter.distanceCheck();
    }

    @Override
    public void initialize() {
        m_shooter.stopMotor();
        m_shooter.stopTurret();
        SmartDashboard.putNumber("TEST", 100);
    }

    @Override
    public void execute() {

        dashboard();

        // why did we do this bruh 💀💀
        
        double turretControl = Constants.round(Operator.getRightX());
        if (Math.abs(turretControl) >= .1) { 
            m_shooter.setTurretMotor(turretControl*Constants.Shooter.turretSpeed);
        } else if (Operator.rightBumper().get()) {
            System.out.println("align align align");
             m_shooter.visionAlign();
        } else if (Operator.leftBumper().get()) {
            m_shooter.resetTurret();
        } else m_shooter.stopTurret();  

        if (Operator.a().get()) {
            // m_intake.setShintake();
            m_intake.setShintakePID(); // TODO test if shintake PID works
        } else if (Operator.leftBumper().get()) {
            m_shooter.resetTurret();
        } else if (Operator.getRightTrigger()) {
            if (m_shooter.getRPM() > Constants.Shooter.upperRPM) {
                // m_intake.setShintake(0.015);
                m_intake.setShintakePID(); // TODO test if shintake PID works
            } else m_intake.stopShintake();

            m_shooter.setRPM(Constants.Shooter.upperRPM);
        } else if (Operator.getLeftTrigger()) {
            if (m_shooter.getRPM() > Constants.Shooter.lowerRPM - 15) {
                m_intake.setShintake();
            } else m_intake.stopShintake();
            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        } else if (Pilot.leftBumper().get()) {
            if (m_shooter.getRPM() > Constants.Shooter.lowerRPM - 15) {
                m_intake.setShintake();
            } else m_intake.stopShintake();
            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        } else if (OpPOV.up().get()) {
            m_shooter.setRPM(Constants.Shooter.upperRPM);
        } else if (OpPOV.down().get()) {
            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        } else if (PiPOV.left().get()) {
            m_intake.holdShintake();
        } else if (PiPOV.right().get()) {
            m_intake.setShintakeReverse();
        } else if (Pilot.getLeftTrigger()) {
            m_intake.setIntake();
            m_intake.intakeFwd();
            m_intake.holdShintake();
        } else if (Pilot.getRightTrigger()) {
            m_intake.setIntakeReverse();
            m_intake.intakeFwd();
            m_intake.setShintakeReverse();
        } else if (Pilot.rightBumper().get()){
            m_intake.setIntakeHalfReverse();
            m_intake.intakeFwd();
            m_intake.setShintakeReverse();
        } else {
            m_intake.stopShintake();
            m_shooter.stopMotor();
            m_intake.intakeRev();
            m_intake.stopIntake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}