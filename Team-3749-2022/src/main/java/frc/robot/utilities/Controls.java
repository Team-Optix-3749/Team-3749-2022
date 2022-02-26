package frc.robot.utilities;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Controls {

    // PILOT CONTROLS 😁😁😁😁
    public static final class Drivetrain {
        public static final DoubleSupplier forward = () -> (Xbox.Pilot.leftJoystickY.getAsDouble());
        public static final DoubleSupplier turn = () -> (Xbox.Pilot.rightJoystickX.getAsDouble());
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
        public static final BooleanSupplier outakeBtn = () -> (Xbox.Operator.rt.getAsDouble() > 0);
        public static final BooleanSupplier runBtn = Xbox.Operator.lb::get;
    }
    public static final class Shooter {
        public static final BooleanSupplier shootTrigger = () -> (Xbox.Operator.rt.getAsDouble() > 0);
        public static final DoubleSupplier turnTurretTrigger = () -> (Xbox.Operator.lt.getAsDouble());
        public static final BooleanSupplier dirBtn = Xbox.Operator.y::get;
        public static final BooleanSupplier runBtn = Xbox.Operator.a::get;
    }

    public static final class Elevator {
        public static final BooleanSupplier extendBtn = () -> (Xbox.Operator.OpController.getPOV() == 0);
        public static final BooleanSupplier returnBtn = () -> (Xbox.Operator.OpController.getPOV() == 90);
        public static final BooleanSupplier tiltFwdBtn = () -> (Xbox.Operator.OpController.getPOV() == 180);
        public static final BooleanSupplier tiltBackBtn = () -> (Xbox.Operator.OpController.getPOV() == 270);
    }
}
