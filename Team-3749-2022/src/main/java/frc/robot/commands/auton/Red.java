package frc.robot.commands.auton;

import frc.robot.subsystems.*;

public class Red extends AutoGroups {
    Drivetrain m_drivetrain;
    Intake m_intake;
    Shooter m_shooter;

    public Red(Drivetrain drive, Intake intake, Shooter shoot) {
        super(drive, intake, shoot);
    }

}
