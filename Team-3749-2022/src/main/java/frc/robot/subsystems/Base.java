package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Base extends SubsystemBase {
    public Base(){}



    public void cope() throws IOException {
        Path trajpath = Filesystem.getDeployDirectory().toPath().resolve("cope lol");
    }
}