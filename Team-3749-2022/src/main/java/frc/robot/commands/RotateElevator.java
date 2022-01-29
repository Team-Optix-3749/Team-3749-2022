package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class RotateElevator extends CommandBase {
    Elevator m_elevator;

    public RotateElevator (Elevator m_elevator) {
        this.m_elevator = m_elevator;
        addRequirements(m_elevator);
    }

    public void execute () {
        m_elevator.tiltForward();
    }

    public boolean isFinished () {
        return m_elevator.getTiltEncoders() > 0.5;
    }
}
