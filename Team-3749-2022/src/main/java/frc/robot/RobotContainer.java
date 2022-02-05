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

import java.util.List;

import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
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

  private final Drivetrain m_drivetrain = new Drivetrain();

  private final Elevator m_elevator = new Elevator();
  //private final JoystickButton m_leftJoystick = new JoystickButton(m_xboxController);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // m_drivetrain.setDefaultCommand(
    //   new ArcadeDrive(
    //     m_drivetrain, 
    //     m_elevator,
    //     Xbox.leftJoystickY, 
    //     Xbox.rightJoystickX
    //   )
    // );
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Xbox.XBOX_A.whenPressed(new ElevatorCommand(m_elevator));
    // Xbox.XBOX_X.whenPressed(new ElevatorCommand(m_elevator));
    // Xbox.XBOX_B.whenPressed(new ElevatorCommand(m_elevator));
    // Xbox.XBOX_Y.whenPressed(new ElevatorCommand(m_elevator));
    Xbox.XBOX_A.whenPressed(new RotateElevator(m_elevator,.5));
    Xbox.XBOX_L.whenPressed(new RotateElevator(m_elevator,.25));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command tqw2o run in autonomous
   */
  public Command getAutonomousCommand() {

    // return ManualTrajectory(m_drivetrain);
    return null;
    // Create a voltage constraint to ensure we don't accelerate too fast

    // An example trajectory to follow.  All units in meters.

    // Trajectory exampleTrajectory = PathPlanner.loadPath("New New Path", .1, .1);
    // Trajectory exampleTrajectory = new Trajectory();
  //   var autoVoltageConstraint =
  //       new DifferentialDriveVoltageConstraint(
  //           new SimpleMotorFeedforward(
  //               Constants.Drivetrain.ksVolts,
  //               Constants.Drivetrain.kvVoltSecondsPerMeter,
  //               Constants.Drivetrain.kaVoltSecondsSquaredPerMeter),
  //               Constants.Drivetrain.kDriveKinematics,
  //           10);


  //   // Create config for trajectory
  //   TrajectoryConfig config =
  //       new TrajectoryConfig(
  //               Constants.Drivetrain.kMaxSpeedMetersPerSecond,
  //               Constants.Drivetrain.kMaxAccelerationMetersPerSecondSquared)
  //           // Add kinematics to ensure max speed is actually obeyed
  //           .setKinematics(Constants.Drivetrain.kDriveKinematics)
  //           // Apply the voltage constraint
  //           .addConstraint(autoVoltageConstraint);

  //   // An example trajectory to follow.  All units in meters.
  //   Trajectory exampleTrajectory =
  //       TrajectoryGenerator.generateTrajectory(
  //           // Start at the origin facing the +X direction
  //           new Pose2d(0, 0, new Rotation2d(0)),
  //           // Pass through these two interior waypoints, making an 's' curve path
  //           List.of(new Translation2d(.5, 0)),
  //           // End 3 meters straight ahead of where we started, facing forward
  //           new Pose2d(1, 0, new Rotation2d(0)),
  //           // Pass config
  //           config);

  // //   try {
  // //     Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/2m.wpilib.json");
  // //     exampleTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

  // //  } catch (IOException ex) {
  // //     DriverStation.reportError("Unable to open trajectory", ex.getStackTrace());
  // //  }
   
  //   RamseteCommand ramseteCommand =
  //       new RamseteCommand(
  //           exampleTrajectory,
  //           m_drivetrain::getPose,
  //           new RamseteController(Constants.Drivetrain.kRamseteB, Constants.Drivetrain.kRamseteZeta),
  //           new SimpleMotorFeedforward(
  //               Constants.Drivetrain.ksVolts,
  //               Constants.Drivetrain.kvVoltSecondsPerMeter,
  //               Constants.Drivetrain.kaVoltSecondsSquaredPerMeter),
  //           Constants.Drivetrain.kDriveKinematics,
  //           m_drivetrain::getWheelSpeeds,
  //           new PIDController(Constants.Drivetrain.kPDriveVel, 0, 0),
  //           new PIDController(Constants.Drivetrain.kPDriveVel, 0, 0),
  //           // RamseteCommand passes volts to the callback
  //           m_drivetrain::tankDriveVolts,
  //           m_drivetrain);

  //   // Reset odometry to the starting pose of the trajectory.
  //   m_drivetrain.resetOdometry(exampleTrajectory.getInitialPose());

  //   // Run path following command, then stop at the end.
  //   return ramseteCommand.andThen(() -> m_drivetrain.tankDriveVolts(0, 0));
  }
}
