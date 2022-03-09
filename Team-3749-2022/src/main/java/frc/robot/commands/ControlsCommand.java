package frc.robot.commands;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utilities.Xbox;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utilities.Constants;


public class ControlsCommand extends CommandBase {
    public ControlsCommand (Drivetrain sub) {
        addRequirements(sub);
    }

    public void execute () {
        //check controls class
        Class<?>[] xbox = Xbox.class.getDeclaredClasses();
        for (int i = 0; i<xbox.length; i++) {
            Field[] fields = xbox[i].getDeclaredFields();
            for (int j = 1; j<fields.length; j++) {
                try {
                    boolean b = ((BooleanSupplier)fields[j].get(xbox[i])).getAsBoolean();
                    SmartDashboard.putBoolean(xbox[i].getSimpleName() + "." + fields[j].getName(), b);
                } catch (IllegalAccessException|ClassCastException e) {
                    try {
                        double t = Constants.round(((DoubleSupplier)fields[j].get(xbox[i])).getAsDouble());
                        SmartDashboard.putNumber(xbox[i].getSimpleName() + "." + fields[j].getName(), t);
                    } catch (IllegalAccessException|ClassCastException x) {
                        try {
                            int t = ((IntSupplier)fields[j].get(xbox[i])).getAsInt();
                            SmartDashboard.putNumber(xbox[i].getSimpleName() + "." + fields[j].getName(), t);
                        } catch (IllegalAccessException|ClassCastException y) {
                            System.out.println("NOT GOOD NOT GOOD");
                        }
                    }
                }
            }
        }
    }

}
