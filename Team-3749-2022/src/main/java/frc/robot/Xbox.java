package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Xbox{
    public static final XboxController XBOX_CONTROLLER = new XboxController(0);
    public static final JoystickButton XBOX_X = new JoystickButton(XBOX_CONTROLLER, Button.kX.value);
    public static final JoystickButton XBOX_Y = new JoystickButton(XBOX_CONTROLLER, Button.kY.value);
    public static final JoystickButton XBOX_A = new JoystickButton(XBOX_CONTROLLER, Button.kA.value);
    public static final JoystickButton XBOX_B = new JoystickButton(XBOX_CONTROLLER, Button.kB.value);
    public static final JoystickButton XBOX_L = new JoystickButton(XBOX_CONTROLLER, Button.kLeftBumper.value);
    public static final JoystickButton XBOX_R = new JoystickButton(XBOX_CONTROLLER, Button.kRightBumper.value);
    public static final JoystickButton XBOX_LS = new JoystickButton(XBOX_CONTROLLER, Button.kLeftStick.value);
    public static final JoystickButton XBOX_RS = new JoystickButton(XBOX_CONTROLLER, Button.kRightStick.value);
    

    public static final DoubleSupplier leftJoystickX = XBOX_CONTROLLER::getLeftX;
    public static final DoubleSupplier leftJoystickY = XBOX_CONTROLLER::getLeftY;
    public static final DoubleSupplier rightJoystickX = XBOX_CONTROLLER::getRightX;
    public static final DoubleSupplier rightJoystickY = XBOX_CONTROLLER::getRightY;

    public static final DoubleSupplier m_leftTriggerValue = XBOX_CONTROLLER::getLeftTriggerAxis;
    public static final DoubleSupplier m_rightTriggerValue = XBOX_CONTROLLER::getRightTriggerAxis;
}