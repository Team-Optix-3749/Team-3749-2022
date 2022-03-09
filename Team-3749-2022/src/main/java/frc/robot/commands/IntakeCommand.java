// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.commands;
 
import frc.robot.subsystems.Intake;
import frc.robot.utilities.Controls;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
 
/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake m_intake;

    private boolean comp = false;

    /**
     * Creates a new ExampleCommand.
     *
     * @param intake The subsystem used by this command.
     * @param solenoid The subsystem used for solenoid
     */
    public IntakeCommand(Intake intake) {
        m_intake = intake;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // m_intake.startCompressor();
        // m_intake.intakePneumatics(kReverse);
        m_intake.stopCompressor();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {        
        if(Controls.Intake.intakeBtn.getAsBoolean()) {
            m_intake.setIntake(1);
            m_intake.intakePneumatics(kForward);
        } 
        if (Controls.aTestBtn.getAsBoolean()) m_intake.setIntake(1); 
        else {
            m_intake.stopMotors();
            m_intake.intakePneumatics(kReverse);
        }

        if (Controls.Intake.compBtn.getAsBoolean()) {
            if (comp) { m_intake.startCompressor(); comp = false; }
            else if (comp == false) { m_intake.stopCompressor(); comp = true; }
        } 

        if(Controls.Intake.pistonBtn.getAsBoolean()) m_intake.intakePneumatics(kForward);
        else m_intake.intakePneumatics(kReverse);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.intakePneumatics(kReverse);
        m_intake.stopCompressor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}