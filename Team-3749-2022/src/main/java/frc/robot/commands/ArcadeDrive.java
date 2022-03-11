// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Drivetrain m_drive;
    private DoubleSupplier y;
    private DoubleSupplier x;


    public ArcadeDrive(Drivetrain drivetrain, DoubleSupplier leftY, DoubleSupplier rightX) {
        m_drive = drivetrain;
        y = leftY;
        x = rightX;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
       m_drive.arcadeDrive(Constants.round(y.getAsDouble()), Constants.round(x.getAsDouble()));
    }

    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
