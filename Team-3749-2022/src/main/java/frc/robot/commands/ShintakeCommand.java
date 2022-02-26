package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.utilities.Controls;
import frc.robot.utilities.Constants.Shintake.BallColor;;

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
        if (Controls.Shintake.intakeBtn.getAsBoolean()) {
            if (m_shintake.getColor() == BallColor.NULL) m_shintake.setShintake(1);
            else m_shintake.stopMotors();
        }

        if(Controls.Shintake.outakeBtn.getAsBoolean()) m_shintake.setShintake(1);
        

        if (Controls.Shintake.runBtn.getAsBoolean()) m_shintake.setShintake(1);
        else m_shintake.stopMotors();
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
