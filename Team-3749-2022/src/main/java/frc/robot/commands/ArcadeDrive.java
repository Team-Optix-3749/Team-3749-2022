package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    public final Drivetrain m_drive;
    DoubleSupplier fwd;
    DoubleSupplier rot;

    public ArcadeDrive(Drivetrain m_drive, DoubleSupplier fwd, DoubleSupplier rot) {
        this.m_drive = m_drive;
        this.fwd = fwd;
        this.rot = rot;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        m_drive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble());
    }

    // returns true when the command should end
    @Override
    public boolean isFinished() {
        return false;
    }

}
