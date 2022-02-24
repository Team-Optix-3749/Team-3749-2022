// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;
import frc.robot.utilities.Controls;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class OldArcadeDrive extends CommandBase {

  private final OldDrivetrain m_drive;
  private boolean speedToggle = false;

  public OldArcadeDrive(OldDrivetrain drivetrain) {
    m_drive = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (speedToggle) m_drive.arcadeDrive(-Controls.Drivetrain.forward.getAsDouble()*.9, Controls.Drivetrain.turn.getAsDouble()*.75);
    else m_drive.arcadeDrive(-Controls.Drivetrain.forward.getAsDouble(), Controls.Drivetrain.turn.getAsDouble()*.75);

    if (Controls.Drivetrain.sprint.getAsBoolean()) speedToggle = !speedToggle;
  }

  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
