package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


/**
 * @author Jadon Lee
 * @author Rohin Sood
 */
public class MoveTurret extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter m_shooter;
    private final DoubleSupplier m_leftInput;
    private final DoubleSupplier m_rightInput;

    public MoveTurret(Shooter shooter, DoubleSupplier leftInput, DoubleSupplier rightInput){
        m_shooter = shooter;
        m_leftInput = leftInput;
        m_rightInput = rightInput;
        addRequirements(shooter);
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        m_shooter.moveTurret(m_leftInput.getAsDouble()-m_rightInput.getAsDouble());

    }

    @Override
    public void end(boolean interrupted){
        m_shooter.stopMotor();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}