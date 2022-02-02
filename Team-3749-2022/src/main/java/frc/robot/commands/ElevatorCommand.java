
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Xbox;

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
        if (Xbox.XBOX_A.get()) m_elevator.extendUp();
        if (Xbox.XBOX_B.get()) m_elevator.raise();
        if (Xbox.XBOX_X.get()) m_elevator.tiltBack();
        if (Xbox.XBOX_Y.get()) m_elevator.tiltForward(.5);

        if(Xbox.XBOX_CONTROLLER.getPOV() == 0.0) m_elevator.tiltForwardIncrement();
        if(Xbox.XBOX_CONTROLLER.getPOV() == 180.0) m_elevator.tiltBackIncrement();

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
    return false;
    }
}