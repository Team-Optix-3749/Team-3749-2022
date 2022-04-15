package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.Constants;

public class Checks extends SequentialCommandGroup {
    private final Drivetrain m_drive;
    private final Intake m_intake;
    private final Shooter m_shooter;

    public void test (double time, InstantCommand... toRun) {
        new ParallelDeadlineGroup(
            new WaitCommand(time),
            (toRun)
        );
    }
    
    public Checks(Drivetrain drive, Intake intake, Shooter shooter) {
        m_drive = drive;
        m_intake = intake;
        m_shooter = shooter;

        addCommands(

            // tests fwd, right, left, back in brake mode
            // waits 1 sec to see full stop
            new ParallelRaceGroup(
                new PrintCommand("BRAKE FORWARD"),
                new InstantCommand(() -> m_drive.setBrake()),
                new InstantCommand(() -> m_drive.tankDriveVolts(1, 1)),
                new WaitCommand(.5)
            ), 
            new ParallelRaceGroup(
                new PrintCommand("BRAKE LEFT"),
                new InstantCommand(() -> m_drive.setBrake()),
                new InstantCommand(() -> m_drive.tankDriveVolts(-1, 1)),
                new WaitCommand(.5)
            ), 
            new ParallelRaceGroup(
                new PrintCommand("BRAKE RIGHT"),
                new InstantCommand(() -> m_drive.setBrake()),
                new InstantCommand(() -> m_drive.tankDriveVolts(1, -1)),
                new WaitCommand(.5)
            ), 
            new ParallelRaceGroup(
                new PrintCommand("BRAKE REVERSE"),
                new InstantCommand(() -> m_drive.setBrake()),
                new InstantCommand(() -> m_drive.tankDriveVolts(-1, -1)),
                new WaitCommand(.5)
            ), 
            new ParallelRaceGroup(
                new PrintCommand("BRAKE OFF"),
                new InstantCommand(() -> m_drive.tankDriveVolts(0, 0)),
                new WaitCommand(1.5)
            ),
            new ParallelRaceGroup(
                new WaitCommand(2),
                new PrintCommand("WAITING")
            ),

            // tests fwd, right, left, back in coast mode
            // waits 1 sec to see deceleration
            new ParallelRaceGroup(
                new PrintCommand("COAST FORWARD"),
                new InstantCommand(() -> m_drive.setCoast()),
                new InstantCommand(() -> m_drive.tankDriveVolts(1, 1)),
                new WaitCommand(.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("COAST LEFT"),
                new InstantCommand(() -> m_drive.setCoast()),
                new InstantCommand(() -> m_drive.tankDriveVolts(-1, 1)),
                new WaitCommand(.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("COAST RIGHT"),
                new InstantCommand(() -> m_drive.setCoast()),
                new InstantCommand(() -> m_drive.tankDriveVolts(1, -1)),
                new WaitCommand(.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("COAST LEFT"),
                new InstantCommand(() -> m_drive.setCoast()),
                new InstantCommand(() -> m_drive.tankDriveVolts(-1, -1)),
                new WaitCommand(.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new InstantCommand(() -> m_drive.tankDriveVolts(0, 0)),
                new WaitCommand(1.5)
            ),

            // open and closes intake pistons
            new ParallelRaceGroup(
                new PrintCommand("OPEN PISTONS"),
                new InstantCommand(() -> m_intake.intakeFwd()),
                new WaitCommand(0.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("CLOSE PISTONS"),
                new InstantCommand(() -> m_intake.intakeRev()),
                new WaitCommand(0.5)
            ),

            new ParallelRaceGroup(
                new WaitCommand(2),
                new PrintCommand("WAITING")
            ),

            // opens intake and runs fwd/rev for 1.5 secs each
            new ParallelRaceGroup(
                new PrintCommand("FWD INTAKE + PISTONS"),
                new InstantCommand(() -> m_intake.intakeFwd()),
                new InstantCommand(() -> m_intake.setIntake()),
                new WaitCommand(1.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("REV INTAKE + PISTONS"),
                new InstantCommand(() -> m_intake.intakeFwd()),
                new InstantCommand(() -> m_intake.setIntakeReverse(1)),
                new WaitCommand(1.5)
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new InstantCommand(() -> m_intake.intakeRev()),
                new InstantCommand(() -> m_intake.stopIntake()),
                new WaitCommand(2)
            ),

            // runs shintake fwd/hold/rev for 2 secs each, 1 sec in between
            new ParallelRaceGroup(
                new PrintCommand("SHINTAKE UP"),
                new InstantCommand(() -> m_intake.setShintake()),
                new WaitCommand(2)
            ),
            new WaitCommand(1),
            new ParallelRaceGroup(
                new PrintCommand("SHINTAKE REVERSE"),
                new InstantCommand(() -> m_intake.setShintakeReverse()),
                new WaitCommand(2)
            ),
            new WaitCommand(1),
            new ParallelRaceGroup(
                new PrintCommand("SHINTAKE HOLD"),
                new InstantCommand(() -> m_intake.holdShintake()),
                new WaitCommand(2)
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new InstantCommand(() -> m_intake.stopShintake()),
                new WaitCommand(2)
            ),

            // run (open intake + intake in + shintakeHold) for 4 secs
            new ParallelRaceGroup(
                new PrintCommand("open intake + intake in + shintakeHold"),
                new InstantCommand(() -> m_intake.intakeFwd()),
                new InstantCommand(() -> m_intake.setIntake()),
                new InstantCommand(() -> m_intake.holdShintake()),
                new WaitCommand(4)
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new WaitCommand(2),
                new InstantCommand(() -> m_intake.intakeRev()),
                new InstantCommand(() -> m_intake.stopShintake()),
                new InstantCommand(() -> m_intake.stopIntake())
            ),
            
            // run (open intake + intake out + shintake reverse) for 4 secs
            new ParallelRaceGroup(
                new PrintCommand("open intake + intake out + shintake reverse"),
                new InstantCommand(() -> m_intake.intakeFwd()),
                new InstantCommand(() -> m_intake.setIntakeReverse(1)),
                new InstantCommand(() -> m_intake.setShintakeReverse()),
                new WaitCommand(4)
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new WaitCommand(2),
                new InstantCommand(() -> m_intake.intakeRev()),
                new InstantCommand(() -> m_intake.stopShintake()),
                new InstantCommand(() -> m_intake.stopIntake())
            ),
            
            // run shooter at lower and upper rpm for for 5 secs each, check dashboard
            new ParallelRaceGroup(
                new PrintCommand("LOWER SHOOT RPM " + m_shooter.getRPM()),
                new WaitCommand(5),
                new InstantCommand(() -> m_shooter.setRPM(Constants.Shooter.lowerRPM))
            ),
            new ParallelRaceGroup(
                new PrintCommand("UPPER SHOOT RPM " + m_shooter.getRPM()),
                new WaitCommand(5),
                new InstantCommand(() -> m_shooter.setRPM(Constants.Shooter.upperRPM))
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new WaitCommand(2),
                new InstantCommand(() -> m_shooter.stopMotor())
            ),

            // turn turret to left limit, right limit, initial posision
            new ParallelRaceGroup(
                new PrintCommand("LEFT TURRET LIMIT"),
                new InstantCommand(() -> m_shooter.setRPM(.22))
            ),
            new ParallelRaceGroup(
                new PrintCommand("RIGHT TURRET LIMIT"),
                new InstantCommand(() -> m_shooter.setTurretPosition(-.22))
            ),
            new ParallelRaceGroup(
                new PrintCommand("RESET TURRET"),
                new InstantCommand(() -> m_shooter.resetTurret())
            ),
            new ParallelRaceGroup(
                new PrintCommand("WAITING"),
                new InstantCommand(() -> m_shooter.stopTurret()),
                new WaitCommand(2)
            ),

            // run shooter & shintake for 5 sec
            new ParallelRaceGroup(
                new PrintCommand("SHINTAKE + SHOOTER LOWER RPM"),
                new WaitCommand(5),
                new InstantCommand(() -> m_shooter.setRPM(Constants.Shooter.upperHubGreaterDistance)),
                new InstantCommand(() -> m_intake.setShintake())
            )

        );
        

    }

    // rip bozo
    /* public void test(double time, long wait, List<Method> commands, Class<?> subsystem, Method... args) {
        t.reset();
        t.start();
        if (t.get() < time) {
            for (Method command : commands) {
                try {command.invoke((Object)subsystem.getClass(), (Object[])args);}
                catch (IllegalAccessException | InvocationTargetException e) {}
            }
        }
    } */

}
