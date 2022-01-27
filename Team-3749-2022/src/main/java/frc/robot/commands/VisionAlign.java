// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class VisionAlign extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Shooter m_shooter;
  NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = m_table.getEntry("tx");
  double multiplier = 1;

  public VisionAlign(Shooter shooter) {
    m_shooter = shooter;
    addRequirements(shooter);
  }


  @Override
  public void initialize() {
  }


  @Override
  public void execute() {
    double x = tx.getDouble(0.0);
    if (Math.abs(x)<=5)
      multiplier = 5;

    else if (Math.abs(x)>=5)
      multiplier = 3;

    else if (Math.abs(x)>=10)
      multiplier = 2;

    double input = x * Constants.Vision.kVisionP * multiplier;

    if (input > 0.8)
        m_shooter.setTurretMotor(0.8);

    else if (input < -0.8)
      m_shooter.setTurretMotor(-0.8);

    else
    m_shooter.setTurretMotor(input);
    
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    if (tx.getDouble(0.0) == 0)
    return true;
    
    return false;
  }
}
