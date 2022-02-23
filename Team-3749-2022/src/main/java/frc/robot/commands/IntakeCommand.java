// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.commands;
 
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Xbox;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
 
/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Intake m_intake;

    // private boolean intakeDir;
    // private boolean pistonDir;

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
        //m_intake.intakePneumatics(kForward);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {        
        // if(Xbox.XBOX_B.get() == true){
        // m_intake.setIntake(1);
        // System.out.println("a");}
        if(Xbox.XBOX_L.get()) {System.out.println("a");m_intake.setIntake(1);}
        else if(Xbox.XBOX_R.get()) {System.out.println("b");m_intake.setIntake(-1);}
        else {m_intake.setIntake(0);}

        // if(Xbox.XBOX_B.get() && (pistonDir)) {System.out.println("c");m_intake.intakePneumatics(kReverse); pistonDir = !pistonDir;}
        // if (Xbox.XBOX_B.get() && (pistonDir == false)) {m_intake.intakePneumatics(kForward); pistonDir =!pistonDir;}
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //m_intake.intakePneumatics(kReverse);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}