// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.commands;
 
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Xbox;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
 
/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake m_intake;

    private double shintakeNum = 1;
    private double intakeNum = 1;
    private double pnuematicNum = 1;

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
        m_intake.intakePneumatics(kForward);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if((Xbox.leftTriggerValue.getAsDouble() > 0) && (shintakeNum % 2 == 1)) m_intake.setShintake(Constants.Intake.shintakeSpeed); shintakeNum++;
        
        if(Xbox.XBOX_L.get() && (intakeNum % 2 == 1)) m_intake.setShintake(Constants.Intake.shintakeSpeed); intakeNum++;
        if(Xbox.XBOX_R.get() && (intakeNum % 2 == 0)) m_intake.setShintake(-Constants.Intake.shintakeSpeed); intakeNum++;

        if(Xbox.XBOX_B.get() && (pnuematicNum % 2 == 1)) m_intake.intakePneumatics(kReverse); pnuematicNum++;
        if (Xbox.XBOX_B.get() && (pnuematicNum % 2 == 0)) m_intake.intakePneumatics(kForward); pnuematicNum++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.intakePneumatics(kReverse);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}