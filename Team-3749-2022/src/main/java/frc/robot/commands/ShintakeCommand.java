package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
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
        if (Controls.Shintake.intakeBtn.getAsBoolean() || Controls.Shintake.outakeBtn.getAsBoolean()) {
            m_shintake.setShintake(1);
        }
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
