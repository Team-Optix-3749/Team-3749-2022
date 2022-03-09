package frc.robot.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Controls;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class IntakeCommand extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Intake m_intake;
    private final Shintake m_shintake;

    private boolean comp = false;

    public IntakeCommand(Intake intake, Shintake shintake){
        m_intake = intake;
        m_shintake = shintake;
        addRequirements(intake, shintake);
    }
    
    @Override
    public void initialize() {
        System.out.println("init intake");
        m_intake.stopCompressor();
        if (Constants.round(Controls.Intake.intakeBtn.getAsDouble()) > 0) {
            // m_intake.intakePneumatics(kForward);
            m_intake.setIntake(1);
            m_shintake.holdShintake();
            System.out.println("asdf");
        } 
        else if (Controls.aTestBtn.getAsBoolean()) m_intake.setIntake(1); 
        else {
            m_intake.stopMotors();
            m_shintake.stopMotors();
            System.out.println("asdf1");
            // m_intake.intakePneumatics(kReverse);
        }

        if (Controls.Intake.compBtn.getAsBoolean()) { m_intake.startCompressor();             System.out.println("asdf2");}
        else if (comp == false) { m_intake.stopCompressor(); }
         

        if(Constants.round(Controls.Intake.intakeBtn.getAsDouble()) > 0) m_intake.intakePneumatics(kForward);
        else m_intake.intakePneumatics(kReverse);
    }

    @Override
    public void execute() {
        System.out.println("intake");
        
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
