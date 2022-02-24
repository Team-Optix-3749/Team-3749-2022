package frc.robot.utilities;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Controls {

    public static final class Drivetrain {
        public static final DoubleSupplier forward = () -> (Xbox.Pilot.leftJoystickY.getAsDouble());
        public static final DoubleSupplier turn = () -> (Xbox.Pilot.rightJoystickX.getAsDouble());
        public static final BooleanSupplier sprint = Xbox.Pilot.ls;
    }

    public static final class Shooter {
        public static final BooleanSupplier shootTrigger = () -> (Xbox.Operator.rt.getAsDouble() > 0);
        public static final DoubleSupplier turnTurretTrigger = () -> (Xbox.Operator.lt.getAsDouble());
        public static final BooleanSupplier dirBtn = Xbox.Operator.y;
    }

    public static final class Intake {
        public static final BooleanSupplier intakeBtn = Xbox.Pilot.rb;
    }

    public static final class Elevator {
        public static final BooleanSupplier extendBtn = () -> (Xbox.Operator.OpController.getPOV() == 0.0);
        public static final BooleanSupplier returnBtn = () -> (Xbox.Operator.OpController.getPOV() == 90.0);
        public static final BooleanSupplier tiltFwdBtn = () -> (Xbox.Operator.OpController.getPOV() == 180.0);
        public static final BooleanSupplier tiltBackBtn = () -> (Xbox.Operator.OpController.getPOV() == 270.0);
    }

    public static final class Shintake {
        public static final BooleanSupplier intakeBtn = Xbox.Pilot.rb;
        public static final BooleanSupplier outakeBtn = () -> (Xbox.Operator.rt.getAsDouble() > 0);
    }
}
