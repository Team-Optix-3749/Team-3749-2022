// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Elevator;

// public class RotateElevator extends CommandBase {
//     Elevator m_elevator;

//     boolean reset = false;
//     double dist;

//     public RotateElevator (Elevator m_elevator, double dist) {
//         this.m_elevator = m_elevator;
//         this.dist = dist;
//         addRequirements(m_elevator);
//     }
    
//     public void initialize () {
//         System.out.println("Command initialized!!");
//         m_elevator.resetEncoders();
//         reset = false;
//     }

//     public void execute () {
//         System.out.println("Command running!");
//         if (m_elevator.getTiltEncoders() < 0.1) reset = true;
//         m_elevator.tiltForward(dist);
//     }

//     public boolean isFinished () {
//         System.out.println(m_elevator.getTiltEncoders());
//         return m_elevator.getTiltEncoders() > dist-.02 && reset;
//     }

//     @Override
//     public void end(boolean interupt) {
//         m_elevator.stopMotors();
//     }
// }
