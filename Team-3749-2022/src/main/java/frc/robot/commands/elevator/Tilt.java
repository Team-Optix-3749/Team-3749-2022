// package frc.robot.commands.elevator;

// import frc.robot.subsystems.*;
// import edu.wpi.first.wpilibj2.command.CommandBase;

// public class Tilt extends CommandBase {
//     private final Elevator m_elevator;

//     public Tilt(Elevator elevator) {
//         m_elevator = elevator;
//         addRequirements(elevator);
//     }

//     @Override
//     public void initialize() {
//     }

//     @Override
//     public void execute() {
//         m_elevator.rawTilt(0.4);
//     }

//     @Override
//     public void end(boolean interrupted) {
//         m_elevator.stopTilt();
//     }

//     // Returns true when the command should end.
//     @Override
//     public boolean isFinished() {
//         return false;
//     }
// }
