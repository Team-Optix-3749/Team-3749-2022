package frc.robot;

import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

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
    }


    public Command getAutonomousCommand() {
        return null;
    }

}
 