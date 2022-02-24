// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lights;
import frc.robot.utilities.Xbox;

/**
 * @author Toby Leeder
 */
public class LightCommand extends CommandBase{
    
    private final Lights m_statusLights;

    public LightCommand(Lights statusLights){
        m_statusLights = statusLights;
        addRequirements(statusLights);
    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (Xbox.Pilot.x.get()) m_statusLights.setOptixColors();
    }

    @Override
    public void end(boolean interrupted){
        m_statusLights.setOff();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}