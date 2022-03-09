package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Controls;

/**
 * @author Rohin Sood
 */
public class ShintakeCommand extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shintake m_shintake;

    public ShintakeCommand(Shintake shintake){
        m_shintake = shintake;
        addRequirements(shintake);
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // double outTakeCtrl = Constants.round(Controls.Shooter.shootTrigger.getAsDouble());
        // if (Controls.Shintake.intakeBtn.getAsBoolean()) m_shintake.holdShintake();
        // else if(outTakeCtrl > 0) { try { TimeUnit.SECONDS.sleep(3);} catch (Exception e) {} m_shintake.runShintake(); }
        // else if (Controls.testBtn.getAsBoolean()) m_shintake.setShintake(1);
        // else m_shintake.stopMotors();
    }

    @Override
    public void end(boolean interrupted){
        m_shintake.stopMotors();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
