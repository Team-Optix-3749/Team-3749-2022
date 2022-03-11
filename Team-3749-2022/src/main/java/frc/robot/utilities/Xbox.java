package frc.robot.utilities;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class Xbox extends GenericHID {
	// reuses the Button and Axis enums from the original XboxController

	private JoystickButton m_leftBumper;
	private JoystickButton m_rightBumper;
	private JoystickButton m_leftStick;
	private JoystickButton m_rightStick;
	private JoystickButton m_a;
	private JoystickButton m_b;
	private JoystickButton m_x;
	private JoystickButton m_y;

	private POVButton m_upButton;
	private POVButton m_rightButton;
	private POVButton m_downButton; 
	private POVButton m_leftButton;

	public Xbox(final int port) {
		super(port);

		// XBOX controller buttons
		m_leftBumper = new JoystickButton(this, XboxController.Button.kLeftBumper.value);
		m_rightBumper = new JoystickButton(this, XboxController.Button.kRightBumper.value);
		m_leftStick = new JoystickButton(this, XboxController.Button.kLeftStick.value);
		m_rightStick = new JoystickButton(this, XboxController.Button.kRightStick.value);
		m_a = new JoystickButton(this, XboxController.Button.kA.value);
		m_b = new JoystickButton(this, XboxController.Button.kB.value);
		m_x = new JoystickButton(this, XboxController.Button.kX.value);
		m_y = new JoystickButton(this, XboxController.Button.kY.value);

		// POV controller buttons
		m_upButton = new POVButton(this, 0);
		m_rightButton = new POVButton(this, 90);
		m_downButton = new POVButton(this, 180);
		m_leftButton = new POVButton(this, 270);
	}

	public JoystickButton leftBumper() {
		return m_leftBumper;
	}

	public JoystickButton rightBumper() {
		return m_rightBumper;
	}

	public JoystickButton leftStick() {
		return m_leftStick;
	}

	public JoystickButton rightStick() {
		return m_rightStick;
	}

	public JoystickButton a() {
		return m_a;
	}

	public JoystickButton b() {
		return m_b;
	}

	public JoystickButton x() {
		return m_x;
	}

	public JoystickButton y() {
		return m_y;
	}

	public double getLeftX() {
		return getRawAxis(XboxController.Axis.kLeftX.value);
	}

	public double getRightX() {
		return getRawAxis(XboxController.Axis.kRightX.value);
	}

	public double getLeftY() {
		return getRawAxis(XboxController.Axis.kLeftY.value);
	}

	public double getRightY() {
		return getRawAxis(XboxController.Axis.kRightY.value);
	}

	public boolean getLeftTrigger() {
		return getRawAxis(XboxController.Axis.kLeftTrigger.value) > 0.1;
	}

	public boolean getRightTrigger() {
		return getRawAxis(XboxController.Axis.kRightTrigger.value) > 0.1;
	}

	public POVButton povUp() {
		return m_upButton;
	}

	public POVButton povRight() {
		return m_rightButton;
	}

	public POVButton povDown() {
		return m_downButton;
	}

	public POVButton povLeft() {
		return m_leftButton;
	}
}