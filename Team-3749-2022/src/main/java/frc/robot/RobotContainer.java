// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/* 
@authors
@BING CHILLING
@Rohin Kumar Sood
@Dinesh K. Sahia

*/
package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

 
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Intake m_intake = new Intake();
 
  private final Drivetrain m_drivetrain = new Drivetrain();
  
  private final Shooter m_shooter = new Shooter();

  private final Elevator m_elevator = new Elevator();
    
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(
        m_drivetrain, 
        m_elevator,
        Xbox.leftJoystickY, 
        Xbox.rightJoystickX
      )
    );    
    m_elevator.setDefaultCommand(
      new ElevatorCommand(
        m_elevator
      )
    );
    

    m_shooter.setDefaultCommand(
      new Shoot(
        m_shooter
      )
    );

    m_intake.setDefaultCommand(
      new IntakeCommand(
        m_intake
      )
    );

    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(
        m_drivetrain, 
        m_elevator,
        Xbox.leftJoystickY, 
        Xbox.rightJoystickX
      )
    );

  }
 
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Xbox.XBOX_A.whenPressed(new RotateElevator(m_elevator,.5));
    Xbox.XBOX_L.whenPressed(new RotateElevator(m_elevator,.25));
  }
 
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // return null;
    // Create a voltage constraint to ensure we don't accelerate too fast

    // An example trajectory to follow.  All units in meters.

    Trajectory exampleTrajectory = new Trajectory();

    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("pathplanner/2m.path");
      exampleTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
   } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory", ex.getStackTrace());
   }
   
    RamseteCommand ramseteCommand =
        new RamseteCommand(
            exampleTrajectory,
            m_drivetrain::getPose,
            new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
            new SimpleMotorFeedforward(
                Constants.Auto.ksVolts,
                Constants.Auto.kvVoltSecondsPerMeter,
                Constants.Auto.kaVoltSecondsSquaredPerMeter),
            Constants.Auto.kDriveKinematics,
            m_drivetrain::getWheelSpeeds,
            new PIDController(Constants.Auto.kPDriveVel, 0, 0),
            new PIDController(Constants.Auto.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            m_drivetrain::tankDriveVolts,
            m_drivetrain);

    // Reset odometry to the starting pose of the trajectory.
    m_drivetrain.resetOdometry(exampleTrajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> m_drivetrain.tankDriveVolts(0, 0));
  }
}
