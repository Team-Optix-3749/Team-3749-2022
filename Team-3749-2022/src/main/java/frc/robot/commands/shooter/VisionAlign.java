package frc.robot.commands.shooter;

import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class VisionAlign extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Shooter m_shooter;
    private boolean finished = false;
    

    public VisionAlign(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.visionAlign();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
