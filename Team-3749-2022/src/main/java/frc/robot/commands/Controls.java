package frc.robot.commands;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utilities.Xbox;
import frc.robot.subsystems.Base;
import frc.robot.utilities.Constants;


public class Controls extends CommandBase {
    private final Xbox Pilot;
    private final Xbox Operator;


    public Controls (Base sub, Xbox p, Xbox o) {
        Operator = o;
        Pilot = p;
        addRequirements(sub);
    }

    public void execute () {
        Method[] PilotMethods = Pilot.getClass().getMethods();

        Method[] OpMethods = Pilot.getClass().getMethods();

        for (Method method : PilotMethods) {
            try{ 
                SmartDashboard.putBoolean(method.getName(), ((BooleanSupplier)method.invoke(Pilot)).getAsBoolean());
            } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
                try {
                    SmartDashboard.putNumber(method.getName(), ((DoubleSupplier)method.invoke(Pilot)).getAsDouble());
                } catch (IllegalAccessException | InvocationTargetException | ClassCastException z) {
                    System.out.println("uh oh");
                } 
            }
        }

        for (Method method : OpMethods) {
            try{ 
                SmartDashboard.putBoolean(method.getName(), ((BooleanSupplier)method.invoke(Operator)).getAsBoolean());
            } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
                try {
                    SmartDashboard.putNumber(method.getName(), ((DoubleSupplier)method.invoke(Operator)).getAsDouble());
                } catch (IllegalAccessException | InvocationTargetException | ClassCastException z) {
                    System.out.println("uh oh");
                } 
            }
        }
    }

}