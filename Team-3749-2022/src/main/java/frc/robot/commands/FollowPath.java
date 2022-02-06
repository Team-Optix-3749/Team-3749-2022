// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.path.Move;
import frc.robot.path.MoveType;
import frc.robot.subsystems.*;

import java.util.List;

import javax.xml.namespace.QName;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class FollowPath extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_drivetrain;
  private final List<Move> m_moves;
  private int movePos = 0;
  private boolean needsReset = true;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FollowPath(Drivetrain drivetrain, List<Move> moves) {
    m_drivetrain = drivetrain;
    m_moves = moves;

    m_drivetrain.resetEncoders();
    m_drivetrain.zeroHeading();

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (movePos > m_moves.size()) return;
    Move curMove = m_moves.get(movePos);

    if (curMove.getType() == MoveType.FWD) {
      doFowardMove(curMove);
    }
    if (curMove.getType() == MoveType.ROT) {
      doRotationMove(curMove);
    }
  }

  private void doFowardMove (Move m) {
    double encoderDist = m_drivetrain.getAverageEncoderDistance();

    //check if the encoder has not finished reseting
    if (needsReset && encoderDist != 0) return;

    needsReset = false;

    //going backwards
    if (m.getTarget() < 0) {
      //reached target
      if (encoderDist < m.getTarget()) {
        m_drivetrain.stop();
        m_drivetrain.resetEncoders();
        needsReset = true;
        movePos++;
      } 

      //target is close
      else if (Math.abs(encoderDist-m.getTarget()) < 1) {
        m_drivetrain.arcadeDrive(-0.2, 0);
      }

      //target is far
      else {
        m_drivetrain.arcadeDrive(-0.5, 0);
      }
    }

    //going forwards
    if (m.getTarget() > 0) {
      //reached target
      if (encoderDist > m.getTarget()) {
        m_drivetrain.stop();
        m_drivetrain.resetEncoders();
        needsReset = true;
        movePos++;
      } 

      //target is close
      else if (Math.abs(encoderDist-m.getTarget()) < 1) {
        m_drivetrain.arcadeDrive(0.2, 0);
      }

      //target is far
      else {
        m_drivetrain.arcadeDrive(0.5, 0);
      }
    }
  }

  private void doRotationMove (Move m) {
    double heading = m_drivetrain.getHeading();

    if (m.getTarget() < 0) {
      //reached target
      if (heading < m.getTarget()) {
        m_drivetrain.stop();
        m_drivetrain.resetEncoders();
        m_drivetrain.zeroHeading();
        needsReset = true;
        movePos++;
      } 

      //target is close
      else if (Math.abs(heading-m.getTarget()) < 20) {
        m_drivetrain.arcadeDrive(0, -0.2);
      }

      //target is far
      else {
        m_drivetrain.arcadeDrive(0, -0.5);
      }
    } 
    if (m.getTarget() > 0) {
      //reached target
      if (heading < m.getTarget()) {
        m_drivetrain.stop();
        m_drivetrain.resetEncoders();
        m_drivetrain.zeroHeading();
        needsReset = true;
        movePos++;
      } 

      //target is close
      else if (Math.abs(heading-m.getTarget()) < 20) {
        m_drivetrain.arcadeDrive(0, 0.2);
      }

      //target is far
      else {
        m_drivetrain.arcadeDrive(0, 0.5);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.resetEncoders();
    m_drivetrain.zeroHeading();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return movePos >= m_moves.size();
  }
}
