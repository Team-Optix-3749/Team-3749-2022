package frc.robot.utilities;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Controls {

    public static final BooleanSupplier testBtn = Xbox.Pilot.a::get;

    // PILOT CONTROLS 😁😁😁😁
    public static final class Drivetrain {
        public static final DoubleSupplier forward = Xbox.Pilot.leftJoystickY;
        public static final DoubleSupplier turn = Xbox.Pilot.rightJoystickX;
        public static final BooleanSupplier sprint = Xbox.Pilot.ls::get;
    }

    public static final class Intake {
        public static final BooleanSupplier intakeBtn = Xbox.Pilot.rb::get;
        public static final BooleanSupplier pistonBtn = Xbox.Pilot.y::get;
        public static final BooleanSupplier compBtn = Xbox.Pilot.x::get;
    }

    // OPERATOR CONTROLS 💀💀💀💀
    public static final class Shintake {
        public static final BooleanSupplier intakeBtn = Xbox.Pilot.rb::get;
        public static final DoubleSupplier outakeBtn = Xbox.Pilot.rt;
    }
    public static final class Shooter {
        public static final DoubleSupplier shootTrigger = Xbox.Pilot.rt;
        public static final DoubleSupplier otherShootTrigger = Xbox.Pilot.lt;
        public static final DoubleSupplier turretJoystick = Xbox.Pilot.leftJoystickX;
    }

    public static final class Elevator {
        public static final BooleanSupplier extendBtn = () -> (Xbox.Pilot.pov.getAsInt() == 0);
        public static final BooleanSupplier returnBtn = () -> (Xbox.Pilot.pov.getAsInt() == 90);
        public static final BooleanSupplier tiltFwdBtn = () -> (Xbox.Pilot.pov.getAsInt() == 180);
        public static final BooleanSupplier tiltBackBtn = () -> (Xbox.Pilot.pov.getAsInt() == 270);
    }
}
