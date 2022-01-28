// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.commands;
 
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
 
/** An example command that uses an example subsystem. */
public class IntakeOut extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake m_IntakeOutCommand;
 
  /**
   * Creates a new ExampleCommand.
   *
   * @param intake The subsystem used by this command.
   */
  public IntakeOut(Intake intake) {
    m_IntakeOutCommand = intake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
 
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    m_IntakeOutCommand.IntakeOut();
 
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
