// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Xbox;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {

  private final Drivetrain m_drive;
  private final Elevator m_elevator;
  private final DoubleSupplier m_forward;
  private final DoubleSupplier m_rotation;
  private int toggleNum = 0;

  public ArcadeDrive(Drivetrain drivetrain, Elevator elevator, DoubleSupplier forward, DoubleSupplier rotation) {
    m_drive = drivetrain;
    m_forward = forward;
    m_rotation = rotation;
    m_elevator = elevator;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_drive.arcadeDrive(-m_forward.getAsDouble()*.9, m_rotation.getAsDouble()*.75);

    if (Xbox.XBOX_LS.get()) toggleNum++;
    if (Xbox.XBOX_LS.get() && (toggleNum % 2 == 0)) m_drive.arcadeDrive(-m_forward.getAsDouble(), m_rotation.getAsDouble()*.75);

    if(Xbox.XBOX_CONTROLLER.getPOV() == 0.0) m_elevator.tiltForwardIncrement();
    if(Xbox.XBOX_CONTROLLER.getPOV() == 180.0) m_elevator.tiltBackIncrement();
  }

  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
