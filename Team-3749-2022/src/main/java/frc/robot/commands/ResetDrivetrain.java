package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ResetDrivetrain extends CommandBase {
    private final Drivetrain m_drive;
    private final Pose2d pose;
    private final Timer t = new Timer();

    public ResetDrivetrain (Drivetrain base, Pose2d p) {
        pose = p;
        m_drive = base;
        addRequirements(base);
    }

    @Override
    public void initialize () {
        t.start();
    }

    @Override    
    public void execute () {
        m_drive.resetEncoders();
        m_drive.zeroHeading();
        if (t.get() > .1) m_drive.resetOdometry(pose);
    }

    @Override
    public void end(boolean interrupted) {
        t.reset();
    }

    @Override
    public boolean isFinished () {
        return t.get() > .2;
    }
}
