package frc.robot;

import frc.robot.commands.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utils.Constants;
import frc.robot.utils.POV;
import frc.robot.utils.Xbox;
import frc.robot.utils.Constants.Drivetrain.Mode;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RobotContainer {

    private final Drivetrain m_drive = new Drivetrain();

    Xbox Pilot;
    Xbox Operator;
    POV PiPOV;
    POV OpPOV;

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        Pilot = new Xbox(0);
        Operator = new Xbox(1);

        PiPOV = new POV(new GenericHID(0));
        OpPOV = new POV(new GenericHID(1));

        PiPOV.left().whenPressed(new InstantCommand(() -> { m_drive.setMode(Mode.BRAKE); }, m_drive));
        PiPOV.right().whenPressed(new InstantCommand(() -> { m_drive.setMode(Mode.COAST); }, m_drive));

        m_drive.setDefaultCommand(
            new ArcadeDrive( m_drive, Pilot::getLeftY, Pilot::getRightX ));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
 