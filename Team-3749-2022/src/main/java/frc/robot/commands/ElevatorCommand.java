
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;
import frc.robot.utilities.Controls;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ElevatorCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Elevator m_elevator;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ElevatorCommand(Elevator subsystem) {
    m_elevator = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_elevator.resetEncoders();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(Controls.Elevator.extendBtn.getAsBoolean()) m_elevator.climb(.5);
        else if(Controls.Elevator.tiltFwdBtn.getAsBoolean()) m_elevator.tiltIncrement(0.1);
        else if(Controls.Elevator.returnBtn.getAsBoolean()) m_elevator.climb(0);
        else if(Controls.Elevator.tiltBackBtn.getAsBoolean()) m_elevator.tiltIncrement(-0.1);
        else {
            m_elevator.climb(0);
            m_elevator.tilt(0);
        }

        // m_elevator.extendClimber(.5);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_elevator.resetEncoders();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
    return false;
    }
}