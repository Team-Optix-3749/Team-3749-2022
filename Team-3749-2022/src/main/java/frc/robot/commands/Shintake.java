// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.commands;
 
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Xbox;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
 
/** An example command that uses an example subsystem. */
public class Shintake extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake m_intake;
  private final Solenoid m_solenoid;
 
  /**
   * Creates a new ExampleCommand.
   *
   * @param intake The subsystem used by this command.
   * @param solenoid The subsystem used for solenoid
   */
  public Shintake(Intake intake, Solenoid solenoid) {
    m_intake = intake;
    m_solenoid = solenoid;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake, solenoid);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_solenoid.reverseAll();
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    m_intake.setShintake(-Constants.Intake.shintakeSpeed);

    if (Xbox.XBOX_A.get()) m_intake.setShintake(-Constants.Intake.shintakeSpeedInverted);
    // A == shintake up
    if (Xbox.XBOX_B.get()) m_intake.IntakeIn();
    // B == intake in 
    if (Xbox.XBOX_X.get()) m_intake.IntakeOut();
    boolean forward = false;
    if (Xbox.XBOX_Y.get()){
    if (forward == true){
      m_solenoid.IntakePneumatics(DoubleSolenoid.Value.kReverse);
      forward = false;
    }
    else{
      m_solenoid.IntakePneumatics(DoubleSolenoid.Value.kForward);
      forward = true;
    }
    }
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
//hi