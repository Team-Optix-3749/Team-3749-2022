package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Print extends CommandBase {
    private final Drivetrain m_drive;

    public Print (Drivetrain base) {
        m_drive = base;
        m_drive.resetEncoders();
        addRequirements(base);
    }

    public void execute () {
        SmartDashboard.putNumber("boxo",m_drive.getAverageEncoderDistance());
        m_drive.arcadeDrive(-.5, 0);
    }
}
