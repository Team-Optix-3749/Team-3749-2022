package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utils.Constants;

public class ArcadeDrive extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    public final Drivetrain m_drive;
    DoubleSupplier fwd;
    DoubleSupplier rot;

    public ArcadeDrive(Drivetrain m_drive, DoubleSupplier fwd, DoubleSupplier rot) {
        this.m_drive = m_drive;
        this.fwd = fwd;
        this.rot = rot;

        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_drive.resetRobotPose();
    }

    @Override
    public void execute() {
        double roundedFwd = Constants.round(fwd.getAsDouble());
        double roundedRot = Constants.round(rot.getAsDouble());

        m_drive.arcadeDrive(roundedFwd, -roundedRot);
    }

    // returns true when the command should end
    @Override
    public boolean isFinished() {
        return false;
    }

}
