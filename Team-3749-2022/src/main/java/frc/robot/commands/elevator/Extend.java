package frc.robot.commands.elevator;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Extend extends CommandBase {
    private final Elevator m_elevator;

    public Extend(Elevator elevator) {
        m_elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_elevator.rawClimbUp();
    }

    @Override
    public void end(boolean interrupted) {
        m_elevator.stopClimb();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
