package frc.robot.utilities;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Xbox{

    public static final class Pilot {
        public static final XboxController PilotController = new XboxController(0);
        public static final JoystickButton x = new JoystickButton(PilotController, Button.kX.value);
        public static final JoystickButton y = new JoystickButton(PilotController, Button.kY.value);
        public static final JoystickButton a = new JoystickButton(PilotController, Button.kA.value);
        public static final JoystickButton b = new JoystickButton(PilotController, Button.kB.value);
        public static final JoystickButton lb = new JoystickButton(PilotController, Button.kLeftBumper.value);
        public static final JoystickButton rb = new JoystickButton(PilotController, Button.kRightBumper.value);
        public static final JoystickButton ls = new JoystickButton(PilotController, Button.kLeftStick.value);
        public static final JoystickButton rs = new JoystickButton(PilotController, Button.kRightStick.value);  

        public static final DoubleSupplier leftJoystickX = PilotController::getLeftX;
        public static final DoubleSupplier leftJoystickY = PilotController::getLeftY;
        public static final DoubleSupplier rightJoystickX = PilotController::getRightX;
        public static final DoubleSupplier rightJoystickY = PilotController::getRightY;

        public static final DoubleSupplier lt = PilotController::getLeftTriggerAxis;
        public static final DoubleSupplier rt = PilotController::getRightTriggerAxis;
    }

    public static final class Operator {
        public static final XboxController OpController = new XboxController(0);
        public static final JoystickButton x = new JoystickButton(OpController, Button.kX.value);
        public static final JoystickButton y = new JoystickButton(OpController, Button.kY.value);
        public static final JoystickButton a = new JoystickButton(OpController, Button.kA.value);
        public static final JoystickButton b = new JoystickButton(OpController, Button.kB.value);
        public static final JoystickButton lb = new JoystickButton(OpController, Button.kLeftBumper.value);
        public static final JoystickButton rb = new JoystickButton(OpController, Button.kRightBumper.value);
        public static final JoystickButton ls = new JoystickButton(OpController, Button.kLeftStick.value);
        public static final JoystickButton rs = new JoystickButton(OpController, Button.kRightStick.value);  

        public static final DoubleSupplier leftJoystickX = OpController::getLeftX;
        public static final DoubleSupplier leftJoystickY = OpController::getLeftY;
        public static final DoubleSupplier rightJoystickX = OpController::getRightX;
        public static final DoubleSupplier rightJoystickY = OpController::getRightY;

        public static final DoubleSupplier lt = OpController::getLeftTriggerAxis;
        public static final DoubleSupplier rt = OpController::getRightTriggerAxis;
    }
}