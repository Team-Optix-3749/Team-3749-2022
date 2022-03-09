// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.
 
// package frc.robot.commands;
 
// import frc.robot.subsystems.*;
// import frc.robot.utilities.Constants;
// import frc.robot.utilities.Controls;
// import edu.wpi.first.wpilibj2.command.CommandBase;

// import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
 
// /** An example command that uses an example subsystem. */
// public class IntakeCommand extends CommandBase {
//     @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//     private final Intake m_intake;
//     private final Shintake m_shintake;

//     private boolean comp = false;

//     /**
//      * Creates a new ExampleCommand.
//      *
//      * @param intake The subsystem used by this command.
//      * @param solenoid The subsystem used for solenoid
//      */
//     public IntakeCommand(Intake intake, Shintake shintake) {
//         m_intake = intake;
//         m_shintake = shintake;

//         // Use addRequirements() here to declare subsystem dependencies.
//         addRequirements(intake, shintake);
//     }

//     // Called when the command is initially scheduled.
//     @Override
//     public void initialize() {
//         // m_intake.startCompressor();
//         // m_intake.intakePneumatics(kReverse);
//         // m_intake.stopCompressor();
//         System.out.println("init");
//     }

//     // Called every time the scheduler runs while the command is scheduled.
//     @Override
//     public void execute() {
//         System.out.println("start");
//         // if (Constants.round(Controls.Intake.intakeBtn.getAsDouble()) > 0) {
//         //     // m_intake.intakePneumatics(kForward);
//         //     m_intake.setIntake(1);
//         //     m_shintake.holdShintake();
//         //     System.out.println("asdf");
//         // } 
//         // else if (Controls.aTestBtn.getAsBoolean()) m_intake.setIntake(1); 
//         // else {
//         //     m_intake.stopMotors();
//         //     m_shintake.stopMotors();
//         //     System.out.println("asdf1");
//         //     // m_intake.intakePneumatics(kReverse);
//         // }

//         // if (Controls.Intake.compBtn.getAsBoolean()) { m_intake.startCompressor();             System.out.println("asdf2");}
//         // else if (comp == false) { m_intake.stopCompressor(); }
         

//         // if(Constants.round(Controls.Intake.intakeBtn.getAsDouble()) > 0) m_intake.intakePneumatics(kForward);
//         // else m_intake.intakePneumatics(kReverse);
//     }

//     // Called once the command ends or is interrupted.
//     @Override
//     public void end(boolean interrupted) {
//         m_intake.intakePneumatics(kReverse);
//         m_intake.stopCompressor();
//     }

//     // Returns true when the command should end.
//     @Override
//     public boolean isFinished() {
//         return false;
//     }
// }