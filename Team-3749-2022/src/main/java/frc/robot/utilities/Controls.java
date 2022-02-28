package frc.robot.utilities;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Controls {

    // PILOT CONTROLS 😁😁😁😁
    public static final class Drivetrain {
        public static final DoubleSupplier forward = Xbox.Pilot.leftJoystickY;
        public static final DoubleSupplier turn = Xbox.Pilot.rightJoystickX;
        public static final BooleanSupplier sprint = Xbox.Pilot.ls::get;
    }

    public static final class Intake {
        public static final BooleanSupplier intakeBtn = Xbox.Pilot.rb::get;
        public static final BooleanSupplier runBtn = Xbox.Pilot.x::get;
        public static final BooleanSupplier pistonBtn = Xbox.Pilot.b::get;
        public static final BooleanSupplier compBtn = Xbox.Pilot.a::get;
    }

    // OPERATOR CONTROLS 💀💀💀💀
    public static final class Shintake {
        public static final BooleanSupplier intakeBtn = Xbox.Pilot.rb::get;
        public static final DoubleSupplier outakeBtn = Xbox.Operator.rt;
        public static final BooleanSupplier runBtn = Xbox.Operator.lb::get;
    }
    public static final class Shooter {
        public static final DoubleSupplier shootTrigger = Xbox.Operator.rt;
        public static final DoubleSupplier turretJoystick = Xbox.Operator.rightJoystickX;
        public static final BooleanSupplier runBtn = Xbox.Operator.a::get;
    }

    public static final class Elevator {
        public static final BooleanSupplier extendBtn = () -> (Xbox.Operator.OpController.getPOV() == 0);
        public static final BooleanSupplier returnBtn = () -> (Xbox.Operator.OpController.getPOV() == 90);
        public static final BooleanSupplier tiltFwdBtn = () -> (Xbox.Operator.OpController.getPOV() == 180);
        public static final BooleanSupplier tiltBackBtn = () -> (Xbox.Operator.OpController.getPOV() == 270);
    }
}
