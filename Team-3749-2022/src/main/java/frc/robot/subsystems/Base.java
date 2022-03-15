package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Base extends SubsystemBase {
    public Base(){}



    public void cope() {    
        int x = 0;
        while (true) {
            x++;
            SmartDashboard.putString("cope " + x, "L ur mom lol cope harder");
        }
    }
}