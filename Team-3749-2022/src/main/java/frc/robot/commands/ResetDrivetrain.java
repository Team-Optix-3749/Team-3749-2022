// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ResetDrivetrain extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    
    private final Drivetrain m_drive;
    Trajectory traj;
    Timer t = new Timer();


    public ResetDrivetrain(Drivetrain drivetrain, Trajectory trajectory) {
        m_drive = drivetrain;
        traj = trajectory;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
        if (t.get() < .1) m_drive.resetOdometry(traj.getInitialPose());
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        t.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return t.get() >= .1;
    }
}
