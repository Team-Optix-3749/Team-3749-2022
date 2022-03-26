package frc.robot.commands.turret;

import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class VisionAlign extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Turret m_turret;

    public VisionAlign(Turret turret) {
        m_turret = turret;
        addRequirements(turret);
    }

    @Override
    public void initialize() {
        m_turret.resetTurret();
    }

    @Override
    public void execute() {
        m_turret.visionAlign();
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.stopTurret();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
