package frc.robot.subsystems;

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