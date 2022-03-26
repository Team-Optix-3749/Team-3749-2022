package frc.robot.commands.turret;

import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SkewedVisionAlign extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private final Turret m_turret;

    public SkewedVisionAlign(Turret shooter) {
        m_turret = shooter;
        addRequirements(shooter);
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